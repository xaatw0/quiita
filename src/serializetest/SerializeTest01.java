package serializetest;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.SignedObject;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SealedObject;

import org.junit.Test;


public class SerializeTest01 {

	@Test
	public void シリアライズのテスト() throws IOException, ClassNotFoundException{

		Person person = new Person("test", 20);
		assertThat(person, instanceOf(Serializable.class));

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)){
			objectOutputStream.writeObject(person);
		}

		Person serializedPerson;

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		try(ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)){
			serializedPerson = (Person) objectInputStream.readObject();
		}

		assertThat(serializedPerson.getName(), is("test"));
		assertThat(serializedPerson.getAge(), is(20));
	}

	@Test
	public void シリアライズのテスト2() throws IOException, ClassNotFoundException{

		Person2 person = new Person2("test", 20);
		assertThat(person, instanceOf(Serializable.class));

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)){
			objectOutputStream.writeObject(person);
		}

		Person2 serializedPerson;

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		try(ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)){
			serializedPerson = (Person2) objectInputStream.readObject();
		}

		assertThat(serializedPerson.getName(), is("test"));
		assertThat(serializedPerson.getAge(), is(20));
	}

	@Test
	public void シリアライズの暗号化テスト() throws IOException, ClassNotFoundException,
			SignatureException, GeneralSecurityException {


		// 署名のための公開鍵/秘密鍵ペアを生成し、マップに署名
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
		KeyPair kp = kpg.generateKeyPair();
		Signature sig = Signature.getInstance("SHA1withDSA");

		// シリアライズするデータを作成し、暗号化する
		Person person = new Person("test", 20);
		SignedObject signedPerson = new SignedObject(person, kp.getPrivate(), sig);

		// 暗号化鍵を生成し、マップを暗号化
		KeyGenerator generator = KeyGenerator.getInstance("AES");
		generator.init(new SecureRandom());
		Key key = generator.generateKey();
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		SealedObject sealedPerson = new SealedObject(signedPerson, cipher);

		// マップをシリアライズ
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try (ObjectOutputStream out = new ObjectOutputStream(byteArrayOutputStream)) {
			out.writeObject(sealedPerson);
		}

		// マップを復元
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		try (ObjectInputStream in = new ObjectInputStream(byteArrayInputStream)) {
			sealedPerson = (SealedObject) in.readObject();
		}

		// マップを復号
		cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		signedPerson = (SignedObject) sealedPerson.getObject(cipher);

		// 署名を検証し、マップを取り出す
		if (!signedPerson.verify(kp.getPublic(), sig)) {
			throw new GeneralSecurityException("Person failed verification");
		}

		Person serializedPerson = (Person) signedPerson.getObject();
		assertThat(serializedPerson.getName(), is("test"));
		assertThat(serializedPerson.getAge(), is(20));
	}

	@Test(expected=BadPaddingException.class)
	public void シリアライズの暗号化テスト_キー違い() throws IOException, ClassNotFoundException,
			SignatureException, GeneralSecurityException {

		// 署名のための公開鍵/秘密鍵ペアを生成し、マップに署名
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
		KeyPair kp = kpg.generateKeyPair();
		Signature sig = Signature.getInstance("SHA1withDSA");

		// シリアライズするデータを作成し、暗号化する
		Person person = new Person("test", 20);
		SignedObject signedPerson = new SignedObject(person, kp.getPrivate(), sig);

		// 暗号化鍵を生成し、マップを暗号化
		KeyGenerator generator = KeyGenerator.getInstance("AES");
		generator.init(new SecureRandom());
		Key key = generator.generateKey();
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		SealedObject sealedPerson = new SealedObject(signedPerson, cipher);

		// マップをシリアライズ
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try (ObjectOutputStream out = new ObjectOutputStream(byteArrayOutputStream)) {
			out.writeObject(sealedPerson);
		}

		// マップを復元
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		try (ObjectInputStream in = new ObjectInputStream(byteArrayInputStream)) {
			sealedPerson = (SealedObject) in.readObject();
		}

		// マップを復号(暗号化した鍵以外で複合化する)
		cipher = Cipher.getInstance("AES");
		Key anotherKey = generator.generateKey();
		cipher.init(Cipher.DECRYPT_MODE, anotherKey);
		signedPerson = (SignedObject) sealedPerson.getObject(cipher);
	}

	@Test
	public void 暗号鍵取り扱い() throws NoSuchAlgorithmException, IOException{
		// 暗号化鍵を生成し、マップを暗号化
		KeyGenerator generator = KeyGenerator.getInstance("AES");
		generator.init(new SecureRandom());
		Key key = generator.generateKey();

		// マップをシリアライズ
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try (ObjectOutputStream out = new ObjectOutputStream(byteArrayOutputStream)) {
			out.writeObject(key);
		}
	}
}
