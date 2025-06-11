package Archivos;

/**
 * Importaciones necesarias para operaciones criptográficas y seguridad,
 * incluyendo generación y manejo de claves (simétricas y asimétricas),
 * cifrado y descifrado de datos, manejo de excepciones específicas de
 * seguridad, codificación Base64, y utilidades para logging.
 * 
 * Estas clases permiten implementar cifrado con algoritmos estándar,
 * manejo de vectores de inicialización (IV), y generación de números
 * aleatorios seguros para criptografía.
 */
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

/**
 * Clase utilitaria para operaciones criptográficas usando el algoritmo RSA.
 * 
 * Esta clase provee métodos para generar pares de claves (pública y privada),
 * cifrar y descifrar datos, y posiblemente otras funciones relacionadas con RSA.
 * 
 * Está diseñada para facilitar el uso de RSA en aplicaciones Java,
 * manejando internamente las excepciones y formatos necesarios.
 * 
 * @author [Tu Nombre]
 * @version 1.0
 * @since 2025
 */
public class RSAUtil {

	/**
	 * Genera una clave secreta AES de 256 bits.
	 *
	 * Este método utiliza el generador de claves de Java para crear
	 * una clave AES segura y adecuada para cifrado simétrico.
	 *
	 * @return Una instancia de {@link SecretKey} que representa la clave AES generada,
	 *         o {@code null} si ocurre algún error durante la generación.
	 */
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
	
    /**
     * Genera un par de claves RSA (pública y privada) con tamaño de 2048 bits.
     *
     * Este método utiliza el generador de claves RSA de Java para crear un
     * par de claves que pueden ser usadas para cifrado y firma digital.
     *
     * @return Un objeto {@link KeyPair} que contiene la clave pública y privada generadas,
     *         o {@code null} si ocurre un error durante la generación.
     */    
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

    /**
     * Cifra un texto utilizando la clave pública RSA proporcionada.
     *
     * Este método emplea el cifrado RSA con el esquema OAEP y SHA-256 para
     * cifrar el texto dado. El texto se codifica en UTF-8 antes de ser cifrado,
     * y el resultado cifrado se devuelve como una cadena codificada en Base64.
     *
     * @param texto El texto plano que se desea cifrar.
     * @param clavePublica La clave pública RSA que se utilizará para el cifrado.
     * @return Una cadena codificada en Base64 que representa el texto cifrado,
     *         o {@code null} si ocurre algún error durante el proceso.
     */    
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
	
	/**
	 * Descifra un texto cifrado en Base64 utilizando la clave privada RSA proporcionada.
	 *
	 * Este método decodifica el texto cifrado desde Base64 y utiliza el esquema
	 * RSA con OAEP y SHA-256 para descifrar el contenido. El resultado se devuelve
	 * como una cadena en formato UTF-8.
	 *
	 * @param textoCifrado El texto cifrado codificado en Base64 que se desea descifrar.
	 * @param clavePrivada La clave privada RSA que se utilizará para el descifrado.
	 * @return El texto descifrado en formato de cadena UTF-8,
	 *         o {@code null} si ocurre algún error durante el proceso.
	 */	
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

	/**
	 * Cifra un texto utilizando AES en modo CBC con padding PKCS5.
	 *
	 * Este método genera un vector de inicialización (IV) aleatorio de 16 bytes,
	 * cifra el texto con la clave AES proporcionada y concatena el IV con el texto cifrado.
	 * El resultado se codifica en Base64 para facilitar su almacenamiento o transmisión.
	 *
	 * @param texto El texto plano que se desea cifrar.
	 * @param clave La clave secreta AES utilizada para el cifrado.
	 * @return Una cadena codificada en Base64 que contiene el IV concatenado con el texto cifrado,
	 *         o {@code null} si ocurre algún error durante el proceso.
	 */
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
	
	/**
	 * Descifra un texto cifrado en Base64 utilizando AES en modo CBC con padding PKCS5.
	 *
	 * Este método recibe una cadena codificada en Base64 que contiene concatenados
	 * el vector de inicialización (IV) y el texto cifrado. Extrae el IV, realiza el
	 * descifrado con la clave AES proporcionada y devuelve el texto plano en UTF-8.
	 *
	 * @param textoCifradoBase64 Cadena Base64 que contiene el IV concatenado con el texto cifrado.
	 * @param clave La clave secreta AES que se utilizará para el descifrado.
	 * @return El texto descifrado en formato UTF-8,
	 *         o {@code null} si ocurre algún error durante el proceso.
	 */	
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
	
	/**
	 * Cifra una clave AES utilizando un par de claves RSA generadas internamente.
	 *
	 * Primero codifica la clave AES en Base64 y luego la cifra con la clave pública RSA.
	 * El método genera un nuevo par de claves RSA, cifra la clave AES con la clave pública,
	 * y retorna un mapa que asocia la clave privada RSA con la clave AES cifrada en Base64.
	 *
	 * Este método es útil para proteger la clave AES con RSA para su intercambio seguro.
	 *
	 * @param claveAES La clave secreta AES que se desea cifrar.
	 * @return Un {@code Map} donde la clave es la clave privada RSA y el valor es la clave AES cifrada en Base64.
	 */
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
