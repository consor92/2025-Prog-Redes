package Archivos;

import java.io.PrintStream;

public class main {

	public static void main(String[] args) {
		PrintStream ps = new PrintStream(System.out);
		archivos arch = new archivos("consorti.txt");
		
		arch.crearFileConBuffer(arch.getFiles(),"hola mundo");
		ps.println( arch.LeerFileConBuffer(arch.getFiles()) );
	}

}
