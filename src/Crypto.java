import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Test;


public class Crypto {

	@Test
	public void AES() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
	      String key = "1234567890123456"; // 暗号化方式「AES」の場合、キーは16文字で
	      String original = "This is a source of string!!"; // 元の文字列
	      String algorithm = "AES"; // 暗号化方式「AES(Advanced Encryption Standard)」

	      // 暗号化
	      SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
	      Cipher cipher = Cipher.getInstance(algorithm);
	      cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
	      byte[] encryptByte = cipher.doFinal(original.getBytes());
	      String encryptText = new String(Base64.getEncoder().encode(encryptByte));
	      assertThat(encryptText, is("B/UZj3IF6F6tObxiTAqdDsDLxUKGCBuAYjVTQP1GSnI="));

	      assertThat(Base64.getDecoder().decode(encryptText), is(encryptByte));
	      cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
	      byte[] bytes = cipher.doFinal(encryptByte);
	      assertThat(new String(bytes), is(original));
	}

	@Test
	public void DESede() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
	      String key = "123456789012345678901234"; // 暗号化方式「トリプル DES 暗号化 (DES-EDE)」の場合、キーは24文字で
	      String original = "This is a source of string!!"; // 元の文字列
	      String algorithm = "DESede"; // 暗号化方式「トリプル DES 暗号化 (DES-EDE)」

	      // 暗号化
	      SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
	      Cipher cipher = Cipher.getInstance(algorithm);
	      cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
	      byte[] encryptByte = cipher.doFinal(original.getBytes());
	      String encryptText = new String(Base64.getEncoder().encode(encryptByte));
	      assertThat(encryptText, is("kj7tyyDhjj66zBembWdz6+nXdNyf4zw+r6rkvjxvxeg="));

	      assertThat(Base64.getDecoder().decode(encryptText), is(encryptByte));
	      cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
	      byte[] bytes = cipher.doFinal(encryptByte);
	      assertThat(new String(bytes), is(original));
	}

	@Test
	public void Blowfish() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
	      String key = "123452222"; // 暗号化方式「Blowfish」の場合、キーの文字長は任意（限度はありそうだが）
	      String original = "This is a source of string!!"; // 元の文字列
	      String algorithm = "Blowfish"; // 暗号化方式「Blowfish」

	      // 暗号化
	      SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
	      Cipher cipher = Cipher.getInstance(algorithm);
	      cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
	      byte[] encryptByte = cipher.doFinal(original.getBytes());
	      String encryptText = new String(Base64.getEncoder().encode(encryptByte));
	      assertThat(encryptText, is("Qp/PELuctvrEfkflMrpUkMEaCxOhrIxHpuzSAGOyosA="));

	      assertThat(Base64.getDecoder().decode(encryptText), is(encryptByte));
	      cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
	      byte[] bytes = cipher.doFinal(encryptByte);
	      assertThat(new String(bytes), is(original));
	}
}
