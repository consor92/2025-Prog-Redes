package Archivos;

public class main {

	public static void main(String[] args) {
		archivos arch = new archivos("consorti.txt");
		
		arch.crearFileConBuffer(arch.getFiles(),"hola mundo");
	}

}
