package Utils;

import java.awt.FileDialog;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.swing.JFrame;

/**
 * Interfaz que define operaciones básicas para transferencia de archivos entre
 * cliente y servidor.
 * 
 * Extiende AutoCloseable para asegurar cierre correcto de recursos.
 */
public interface FileTransfer extends AutoCloseable {

	/**
	 * Envía un archivo al destino definido por el DataOutputStream.
	 * 
	 * @param ruta Ruta completa del archivo a enviar.
	 * @param out  Stream de salida para enviar los datos.
	 * @throws IOException si ocurre algún error en la lectura o escritura.
	 */
	void sendFile(String ruta, DataOutputStream out) throws IOException;

	/**
	 * Recibe un archivo desde el DataInputStream.
	 * 
	 * @param dis Stream de entrada desde donde se reciben los datos.
	 * @throws IOException si ocurre algún error en la lectura o escritura.
	 */
	void recibeFile(DataInputStream dis) throws IOException;

	/**
	 * Método por defecto que muestra un diálogo gráfico para seleccionar un
	 * archivo.
	 * 
	 * @return La ruta completa del archivo seleccionado, o null si no se seleccionó
	 *         ninguno.
	 */
	default public String dialogFile() {

		FileDialog window = new FileDialog(new JFrame(), "Eleccion de un archivo.", FileDialog.LOAD);
		window.setVisible(true);

		if (window.getFile() != null)
			return window.getDirectory().concat(window.getFile());

		return null;
	}

	/**
	 * Cierra los recursos abiertos relacionados con la transferencia de archivos.
	 * 
	 * @throws IOException Si ocurre un error al cerrar los recursos.
	 */
	@Override
	void close() throws IOException;
}
