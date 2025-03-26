package CadenaDeCaracteres;

public class main {

	public static void main(String[] args) {
		
		String palabra = "LinKevin";
		
		palabra.charAt( 5 ); //agarra un caracter x
		palabra.compareTo("Kevin"); //false compara texto completo
		palabra.compareToIgnoreCase("linkevin"); //true
		palabra.concat("/profile");  //agrege texto al FINAL
		palabra.contains("Ke");   // devuelve si existe el texto
		palabra.indexOf( '\n' ); //lo mismo qur charAt pero busca un caracter 
		palabra.indexOf("Ke"); //ubicacion de esa palabra devuelve 3
		palabra.length();  //largo total de lacadena
		palabra.lastIndexOf('i'); //ultima aparicion de un texto o caracter
		palabra.replace("Kevin", "Roman");
		palabra.replace("i", "X");  //  LXnKevin
		palabra.replaceAll("i", "x.X"); // Lx.XnKevx.Xn
		palabra.toString();
		palabra.valueOf(5); //tranfoma a texto  
		palabra.trim();//quita espacios en blanco adelante y al final
		palabra.toLowerCase();
		palabra.toUpperCase();
		
		palabra.toCharArray(); //conviete un string en un vector
		// ['L','i','n','K','e','v','i','n']
		palabra.split("K"); // vec[] => { "Lin" , "evin" } Lin evin
		palabra.substring( 2 , 4 ); //LinKevin  -> nKev 
		palabra.
		
		//conversion de Enteros a texto y viceversa
		//Tokenized
	}

}
