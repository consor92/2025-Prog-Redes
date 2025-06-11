package Archivos;

/**
 * Importaciones necesarias para la manipulación avanzada de archivos y flujos de entrada/salida,
 * incluyendo clases para lectura y escritura con buffers, manejo de excepciones,
 * impresión formateada, y gestión de colecciones para operaciones en memoria.
 * También se incluyen utilidades para logging.
 */
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

/**
 * Clase 'archivos' que proporciona métodos para manipulación de archivos en Java.
 * 
 * Esta clase incluye funcionalidades para:
 * <ul>
 *   <li>Crear y escribir en archivos usando diferentes streams y buffers.</li>
 *   <li>Leer archivos carácter por carácter o línea por línea.</li>
 *   <li>Modificar archivos directamente o a través de estructuras de datos en memoria.</li>
 * </ul>
 * 
 * Campos:
 * <ul>
 *   <li>{@code PrintStream ps} - Stream para imprimir texto, puede usarse para imprimir en consola o archivo.</li>
 *   <li>{@code File file} - Representa el archivo sobre el cual se realizan las operaciones.</li>
 * </ul>
 * 
 * Nota: La clase gestiona internamente excepciones relacionadas con IO y uso de streams.
 * 
 * @author [Tu Nombre]
 * @version 1.0
 * @since 2025
 */
public class archivos {

    /** Flujo de salida para imprimir texto (por ejemplo, en consola) */
	PrintStream ps;
    /** Archivo asociado a la instancia para realizar operaciones */
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
	 * Devuelve el archivo asociado a este objeto.
	 * 
	 * JavaDoc ESTE TEXTO NO TIENE NINGUNA ETIQUETA DE IDENTIFICACION. ESTO NO VA A
	 * APARECER :( También se pueden agregar referencias a clases, métodos o
	 * atributos con la instrucción: {@code <html></html>} o usar {@link String}.
	 * 
	 * @return El objeto File representando el archivo.
	 * @since v1.0
	 */
	public File getFiles() {
		return this.file;
	}

	/**
	 * Escribe contenido en el archivo especificado usando un PrintStream.
	 * Este método crea un flujo de salida para el archivo, escribe varias líneas
	 * y caracteres, y asegura que los datos se escriban correctamente.
	 * 
	 * @param f Archivo donde se escribirá el contenido.
	 * @throws FileNotFoundException Si el archivo no puede ser abierto para escritura.
	 * @throws IOException Si ocurre un error al cerrar los flujos de salida.
	 */
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
 	* Método para crear o escribir en un archivo usando un objeto Printer.
 *
 * @param f Archivo donde se realizará la operación.
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
	 * Escribe texto en un archivo usando FileWriter y BufferedWriter.
	 * El archivo se sobrescribe a menos que se cambie el modo append.
	 *
	 * @param f     Archivo donde se escribirá el texto.
	 * @param texto Cadena de texto que se añadirá al archivo.
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
	 * Lee todo el contenido de un archivo usando BufferedReader y devuelve el texto completo.
	 *
	 * @param f un archivo al leer
	 * @return Todo el texto leído del archivo como una cadena.
	 * @throws FileNotFoundException si el archivo no existe.
	 * @throws IOException si ocurre un error durante la lectura.
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

	/**
	 * Lee el contenido de un archivo carácter por carácter y devuelve el texto completo.
	 * Maneja saltos de línea adecuadamente y concatena los caracteres en una cadena.
	 *
	 * @param f Archivo desde donde se leerán los caracteres.
	 * @return El contenido completo del archivo como una cadena, o null si ocurre un error.
	 */	
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

	/**
	 * Modifica un archivo original creando un archivo temporal donde reemplaza
	 * todas las ocurrencias de una cadena buscada por otra cadena dada.
	 * Luego elimina el archivo original y renombra el temporal con el nombre del archivo original.
	 *
	 * @param archivoOriginal El archivo que será modificado.
	 * @param buscar         La cadena que se busca en cada línea para ser reemplazada.
	 * @param reemplazar     La cadena con la cual se reemplazarán las ocurrencias encontradas.
	 */	
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
	
	/**
	 * Modifica el contenido de un archivo leyendo todas sus líneas en una LinkedList,
	 * reemplazando en memoria las ocurrencias de una cadena dada, y luego escribiendo
	 * el contenido modificado de nuevo en el archivo original.
	 *
	 * @param archivoOriginal El archivo que será leído y modificado.
	 * @param buscar         La cadena que se desea buscar y reemplazar en el archivo.
	 * @param reemplazar     La cadena que reemplazará las ocurrencias encontradas.
	 */	
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


