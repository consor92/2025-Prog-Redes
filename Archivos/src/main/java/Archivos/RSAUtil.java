package Archivos;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class RSAUtil {

	//aca cifrado AES
    public static SecretKey generarClaveAES() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256); // AES de 256 bits
            return keyGen.generateKey();
        } catch (Exception e) {
			Logger.getLogger(RSAUtil.class.getName()).log(Level.WARNING, null, e);
        }
        return null;
    }	
	public static KeyPair   generarParRSA() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(2048); //tamaño de la clave
			
			return keyGen.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			Logger.getLogger(RSAUtil.class.getName()).log(Level.WARNING, null, e);
		}
		return null;
	}
	
	public static String cifrarConClavePublica(String texto, PublicKey clavePublica) {
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, clavePublica);
			
			byte[] byteCifrados = cipher.doFinal( texto.getBytes("UTF-8") );
			
			return Base64.getEncoder().encodeToString(byteCifrados);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
			Logger.getLogger(RSAUtil.class.getName()).log(Level.WARNING, null, e);
		}
		return null;
	}	
	public static String descifrarConClavePrivada(String textoCifrado, PrivateKey clavePrivada) {
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
			cipher.init(Cipher.DECRYPT_MODE, clavePrivada);
			
			byte[] byteDecifrados = cipher.doFinal(  Base64.getDecoder().decode(textoCifrado)  );
			
			return new String( byteDecifrados , "UTF-8" );
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
			Logger.getLogger(RSAUtil.class.getName()).log(Level.WARNING, null, e);
		}
		return null;		
	}

	public static String cifrarConAES(String texto,SecretKey clave) {
		try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            
            // Generar IV aleatorio
            byte[] iv = new byte[16];
            SecureRandom random = new SecureRandom();
            random.nextBytes(iv);
            IvParameterSpec ivParams = new IvParameterSpec(iv);

            cipher.init(Cipher.ENCRYPT_MODE, clave, ivParams);
            byte[] byteCifrados = cipher.doFinal(texto.getBytes("UTF-8"));

            // Concatenar IV + texto cifrado para enviar juntos
            byte[] ivMasCifrado = new byte[iv.length + byteCifrados.length];
            System.arraycopy(iv, 0, ivMasCifrado, 0, iv.length);
            System.arraycopy(byteCifrados, 0, ivMasCifrado, iv.length, byteCifrados.length);

            return Base64.getEncoder().encodeToString(ivMasCifrado);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException | InvalidAlgorithmParameterException e) {
			Logger.getLogger(RSAUtil.class.getName()).log(Level.WARNING, null, e);
		}
		return null;		
	}
	public static String descifrarConAES(String textoCifradoBase64, SecretKey clave) {
	    try {
	        byte[] ivMasCifrado = Base64.getDecoder().decode(textoCifradoBase64);

	        byte[] iv = new byte[16];
	        byte[] textoCifrado = new byte[ivMasCifrado.length - 16];

	        System.arraycopy(ivMasCifrado, 0, iv, 0, 16);
	        System.arraycopy(ivMasCifrado, 16, textoCifrado, 0, textoCifrado.length);

	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        cipher.init(Cipher.DECRYPT_MODE, clave, new IvParameterSpec(iv));

	        byte[] textoDescifrado = cipher.doFinal(textoCifrado);
	        return new String(textoDescifrado, "UTF-8");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	//primero cifrar datos con cifrarConAES, luego cifrar claves y retonar 
	//		      String        ,  Map<PrivateKey, String>
	// <  textoCifrarConAES()   ,    cifrarClavesAES()   >
	public static Map<PrivateKey, String> cifrarClavesAES(SecretKey claveAES)
	{
		//SecretKey claveAES = generarClaveAES();
		KeyPair  clavesRSA = generarParRSA();
		String claveAESBase64 = Base64.getEncoder().encodeToString(claveAES.getEncoded());
		String claveAESCifrada = RSAUtil.cifrarConClavePublica(claveAESBase64, clavesRSA.getPublic());
		
		clavesRSA.getPrivate();
		Map<PrivateKey, String> resultado = new HashMap<>();
        resultado.put(clavesRSA.getPrivate(), claveAESCifrada);

        return resultado;		
	}
	
}
