package Archivos;

import java.io.PrintStream;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

public class main {

	public static void main(String[] args) {
		PrintStream ps = new PrintStream(System.out);
		archivos arch = new archivos("consorti.txt");
		
		arch.crearFileConBuffer(arch.getFiles(),"hola mundo");
		ps.println( arch.LeerFileConBuffer(arch.getFiles()) );
		
		arch.modificarArchivoTemporalLinea(arch.getFiles(), "hola", "chau");
		
		
		
		KeyPair claves =  RSAUtil.generarParRSA();
		KeyPair claves2 = RSAUtil.generarParRSA();
		SecretKey claveAES = RSAUtil.generarClaveAES();

		String texto = "Encriptame esta palabra";

		//Para Textos grandes usar el cifradoAES
		String textoCifrado = RSAUtil.cifrarConAES( texto , claveAES);
		
		//Luego Encriptar la clase AES con RSA para protegerla
		Map<String , Map<PrivateKey, String> > DatosCifradosAESyRSA = new HashMap<>();
		DatosCifradosAESyRSA.put(textoCifrado, RSAUtil.cifrarClavesAES(claveAES));
		
		//para textos chicos (hasta 255 caracteres) usar el cifradoRSA
		String textoEncriptado = RSAUtil.cifrarConClavePublica(texto, claves.getPublic());
		
		ps.printf("Texto a Enctiptar:%s - Clave:%s\n\n",texto,claves.getPrivate());
		ps.printf("Encriptado:%s \n",textoEncriptado);
		
		String textpDesifrado = RSAUtil.descifrarConClavePrivada(textoEncriptado, claves.getPrivate() );
		ps.printf("Desencriptado:%s\n", textpDesifrado );
	}

}
