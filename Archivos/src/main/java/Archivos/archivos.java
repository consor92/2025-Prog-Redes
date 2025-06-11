package Archivos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class archivos {

	PrintStream ps;
	File file;

	/**
	 * Constructor
	 */
	// throw delega el trycatch a quien está llamando el método
	public archivos(String ruta) {
		ps = new PrintStream(System.out);
		// "c:\\user\\Redes-04\\omg.txt"
		file = new File(ruta);// crea un nuevo archivo

		// file.createNewFile();
		// file.delete();
		// file.deleteOnExit(); //borra el archivo una vez terminado
		// file.exists();//para ver si el archivo está o no está
		// file.getAbsoluteFile();//te devuelve el archivo directamente
		// file.getName();
		// file.getPath(); //devuelve la ruta de origen
		// file.getParent();//devuelve el directorio
		// file.getTotalSpace();//devuelve la cantidad de bytes que pesa un archivo
		// file.isDirectory();//para saber si es una carpeta
		// file.isHidden();
		// file.isFile();
		// file.list();//devuelve un listado de strings
		// file.listFiles();
		// file.mkdir();//para crear carpetas
		// file.renameTo(new File("waa.txt"));//cambia el nombre

		// this.rutaFiles(file);// llama al método
		// this.crearFileConPrintStreamEasy(file);
	}

	/**
	 * JavaDoc ESTE TEXTO NO TIENE NINGUNA ETIQUETA DE IDENTIFICACION. ESTO NO VA A
	 * AAPARECER :( Tambien se puede agregar referencias a class o methodos o
	 * atributos con la instruccion: { @ por ejemplo: {@code <html></html>} o usar
	 * {@link String}
	 * 
	 * @param f Este metodo recibe un archivo.
	 * @see FlujoDeDatos.File.
	 * @since v1.0
	 * @exception
	 * @throws
	 * @return
	 */
	public File getFiles() {
		return this.file;
	}

	// con esto se puede empezar a escribir en el archivo
	public void crearFileConPrintStreamEasy(File f) {
		FileOutputStream fos = null;
		PrintStream fs = null;
		try {
			fos = new FileOutputStream(f);
			fs = new PrintStream(fos);
			fs.print("Una linea");
			fs.println("Nueva linea");
			fs.write('d');// escribe
			fs.append(("HAIII"));// es como un print, pero escribe en la posición del cursor
			fs.flush();// limpia todo lo que hay en el canal
		} catch (FileNotFoundException e) {
			Logger.getLogger(archivos.class.getName()).log(Level.WARNING, null, e);
		} finally { // se ejecuta independientemente si hubo o no un error
			try {
				if (fs != null)
					fs.close();
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			} // end try/catch
		} // end try/catch/finally
	}// end crearFileConPrintStreamEasy

	/**
	 * 
	 * @param f
	 */
	public void crearFileConPrinter(File f) {
		FileWriter fw = null; // usa buffered directo y ademas crea canales de comunicacion
		PrintWriter pw = null; // es el escritor

		try {
			if (!f.exists()) {
				try {
					f.createNewFile();
				} catch (IOException e) {
					Logger.getLogger(archivos.class.getName()).log(Level.WARNING, null, e);
				}
			}

			fw = new FileWriter(f);
			pw = new PrintWriter(fw);

			pw.print("Una linea");
			pw.println("Nueva linea");
			pw.write('d');// escribe
			pw.append(("HAIII"));// es como un print, pero escribe en la posición del cursor
			pw.flush();// limpia todo lo que hay en el canal
		} catch (FileNotFoundException e) {
			Logger.getLogger(archivos.class.getName()).log(Level.WARNING, null, e);
		} catch (IOException e) {
			Logger.getLogger(archivos.class.getName()).log(Level.WARNING, null, e);
		} finally {
			try {
				if (pw != null)
					pw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				Logger.getLogger(archivos.class.getName()).log(Level.SEVERE, null, ex);
			}
		} // finally

	}

	/**
	 * @param f
	 */
	public void crearFileConBuffer(File f, String texto) {
		FileWriter fw = null;
		BufferedWriter bw = null;// escritor

		try {
			fw = new FileWriter(f, false); // true = append
			bw = new BufferedWriter(fw);

			// bw.append("ss");
			// bw.write('s');
			bw.append(texto);
			bw.newLine();
			bw.flush(); // opcional Buffered
		} catch (IOException e) {
			Logger.getLogger(archivos.class.getName()).log(Level.WARNING, null, e);
		} finally {
			try {
				if (fw != null)
					fw.close();
				if (bw != null)
					bw.close();
			} catch (IOException e) {
				Logger.getLogger(archivos.class.getName()).log(Level.WARNING, null, e);
			}
		}

	}

	/**
	 * Descripcion
	 * 
	 * @param f un archivo al leer
	 * @return Todo el texto leido.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public String LeerFileConBuffer(File f) {
		FileReader fr = null;
		BufferedReader br = null;

		try {
			fr = new FileReader(f);
			br = new BufferedReader(fr);

			String line = "";
			String texto = "";
			while ((line = br.readLine()) != null) {		
				texto = texto.concat(line);
			}

			return texto;
		} catch (FileNotFoundException e) {
			Logger.getLogger(archivos.class.getName()).log(Level.WARNING, null, e);
		} catch (IOException e) {
			Logger.getLogger(archivos.class.getName()).log(Level.WARNING, null, e);
		} finally {
			try {
				if (fr != null)
					fr.close();
				if (br != null)
					br.close();
			} catch (IOException e) {
				Logger.getLogger(archivos.class.getName()).log(Level.WARNING, null, e);
			}
		}

		return null;
	}

	public String leerFileCaracterCaracter(File f) {
		FileReader fr = null;

		try {
			fr = new FileReader(f);

			int caracter, EOF = -1;
			String texto = "";
			while ((caracter = fr.read()) != EOF) {

				if (caracter == '\n') {
					texto = texto.concat("\n");
				} else {
					texto = texto.concat(String.valueOf(caracter));
				}
			}
			return texto;
		} catch (IOException e) {
			Logger.getLogger(archivos.class.getName()).log(Level.WARNING, null, e);
		} finally {
			try {
				if (fr != null)
					fr.close();
			} catch (IOException e) {
				Logger.getLogger(archivos.class.getName()).log(Level.WARNING, null, e);
			}
		}
		return null;
	}

	
	public void modificarArchivoTemporalLinea(File archivoOriginal, String buscar, String reemplazar)  {
		File archTemp = new File( archivoOriginal.getAbsolutePath() + ".tmp" );
		
		try (
			BufferedReader br = new BufferedReader( new FileReader(archivoOriginal) );
			BufferedWriter bw = new BufferedWriter( new FileWriter(archTemp) );
			)
		{
			String linea = ""; String EOF = null;
			while( (linea = br.readLine()) != EOF )
			{
				//la edicion necesaria
				if( linea.contains(buscar) )
				{
					linea = linea.replace(buscar, reemplazar);
				}
				
				bw.write(linea);
				bw.newLine();
			}
			
		if( !archivoOriginal.delete() )
			throw new IOException("No se pudo borrar el archivo original");  
			
		if( !archTemp.renameTo(archivoOriginal) )
			throw new IOException("No puedo renombrar el rchivo temporal.");
		}catch (Exception e) {
			Logger.getLogger(archivos.class.getName()).log(Level.WARNING, null, e);
		}	
	}
	
	public void modificarArchivoConLinkedList(File archivoOriginal, String buscar, String reemplazar) {
		List<String> textoCompleto = new LinkedList<>();
		
		//Leer archivo y volcar los datos en memoria VOLATIL (un array)
		try(BufferedReader br = new BufferedReader(new FileReader(archivoOriginal)))
		{
			String lineas = ""; String EOF = null;
			while( (lineas = br.readLine()) != EOF )
			{
				//puede tener logica para filtrar qué entra al Array o no
				textoCompleto.add(lineas);
			}
		} catch (IOException e) {
			Logger.getLogger(archivos.class.getName()).log(Level.WARNING, null, e);
		} 
		
		// Modificar líneas que contengan el texto a buscar
		//for completo linea a linea
		for( int i = 0 ; i < textoCompleto.size() ; i++ )
		{
			//aca logica para modificar lo que necesitemos
			if( textoCompleto.get(i).contains(buscar) )
				textoCompleto.set(i, textoCompleto.get(i).replace(buscar,reemplazar));
		}
		
		for( String linea : textoCompleto )
		{
			if( linea.contains(buscar))
				textoCompleto.set(
						textoCompleto.indexOf(linea), //(index)
						linea.replace(buscar, reemplazar));//(datomodificado)
		}
		
	    // Escribir de nuevo todo el contenido modificado en el archivo original
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(archivoOriginal)))
		{
			for( String linea : textoCompleto ){
				bw.write(linea);
				bw.newLine();
			}
		} catch (IOException e) {
			Logger.getLogger(archivos.class.getName()).log(Level.WARNING, null, e);
		}
		
	}
}


