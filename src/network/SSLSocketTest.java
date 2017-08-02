package network;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignedObject;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SealedObject;

public class SSLSocketTest {

	static class SerializableMap<K, V> implements Serializable {
		final static long serialVersionUID = -2648720192864531932L;
		private Map<K, V> map;

		public SerializableMap() {
			map = new HashMap<K, V>();
		}

		public Object getData(K key) {
			return map.get(key);
		}

		public void setData(K key, V data) {
			map.put(key, data);
		}
	}

	static class MapSerializer {
		public static SerializableMap<String, Integer> buildMap() {
			SerializableMap<String, Integer> map = new SerializableMap<String, Integer>();
			map.setData("John Doe", new Integer(123456789));
			map.setData("Richard Roe", new Integer(246813579));
			return map;
		}

		public static void InspectMap(SerializableMap<String, Integer> map) {
			System.out.println("John Doe's number is "
					+ map.getData("John Doe"));
			System.out.println("Richard Roe's number is "
					+ map.getData("Richard Roe"));
		}
	}

	public static void main(String[] args) throws IOException,
			GeneralSecurityException, ClassNotFoundException {
		// マップを作成
		SerializableMap<String, Integer> map = MapSerializer.buildMap();

		// 署名のための公開鍵/秘密鍵ペアを生成し、マップに署名
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
		KeyPair kp = kpg.generateKeyPair();
		Signature sig = Signature.getInstance("SHA1withDSA");
		SignedObject signedMap = new SignedObject(map, kp.getPrivate(), sig);

		// 暗号化鍵を生成し、マップを暗号化
		KeyGenerator generator;
		generator = KeyGenerator.getInstance("AES");
		generator.init(new SecureRandom());
		Key key = generator.generateKey();
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		SealedObject sealedMap = new SealedObject(signedMap, cipher);

		// マップをシリアライズ
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(
				"data"));
		out.writeObject(sealedMap);
		out.close();

		// マップを復元
		ObjectInputStream in = new ObjectInputStream(
				new FileInputStream("data"));
		sealedMap = (SealedObject) in.readObject();
		in.close();

		// マップを復号
		cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		signedMap = (SignedObject) sealedMap.getObject(cipher);

		// 署名を検証し、マップを取り出す
		if (!signedMap.verify(kp.getPublic(), sig)) {
			throw new GeneralSecurityException("Map failed verification");
		}
		map = (SerializableMap<String, Integer>) signedMap.getObject();

		// マップを検証
		MapSerializer.InspectMap(map);
	}
}
