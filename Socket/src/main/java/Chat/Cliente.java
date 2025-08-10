package Chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import Utils.Connection;
import Utils.enumType;

/**
 * Cliente representa un cliente en un sistema de chat con funcionalidad para
 * enviar archivos y mensajes al servidor.
 * 
 * Extiende Connection para manejo de sockets y streams, e implementa
 * AutoCloseable para manejo automático de recursos.
 */
public class Cliente extends Connection implements AutoCloseable {

	/** Stream para recibir datos desde el servidor */
	private DataInputStream disClient;

	/**
	 * Constructor que inicializa el cliente con tipo, IP y puerto para la conexión.
	 * 
	 * @param type Enum que indica el tipo de conexión.
	 * @param IP   Dirección IP del servidor.
	 * @param port Puerto del servidor.
	 * @throws UnknownHostException si la IP es inválida.
	 * @throws IOException          si falla la conexión.
	 */
	public Cliente(enumType type, String IP, int port) throws UnknownHostException, IOException {
		super(type, IP, port);
	}

	/**
	 * Método principal que inicia la comunicación con el servidor.
	 * 
	 * Abre los streams de entrada y salida, envía un archivo seleccionado por
	 * diálogo, luego envía mensajes de texto y finalmente escucha mensajes
	 * recibidos del servidor.
	 */
	public void clientOn() {

		try {
			// Inicializa stream de entrada para recibir datos
			this.disClient = new DataInputStream(sockC.getInputStream());
			// Inicializa stream de salida para enviar datos
			dosClient = new DataOutputStream(sockC.getOutputStream());

			ps.println(Utils.Colors.ANSI_RED + "Enviando archivo ...." + Utils.Colors.ANSI_RESET);
			sendFile(dialogFile(), dosClient);

			// Envía mensajes de texto al servidor
			dosClient.writeUTF("Hola, soy kevin y llegue reee tarde.");
			dosClient.flush();

			dosClient.writeUTF("kevin esta muy ansioso");
			dosClient.flush();

			// Escucha mensajes del servidor hasta que se cierre la conexión
			String msg;
			while (!sockC.isClosed() && (msg = disClient.readUTF()) != null) {
				ps.printf(Utils.Colors.ANSI_YELLOW + "\tMensaje: %s\n" + Utils.Colors.ANSI_RESET, msg);
				msg = "";
			}

		} catch (IOException ex) {
			Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Cierra todos los recursos asociados al cliente.
	 * 
	 * @throws IOException Si ocurre error al cerrar streams o sockets.
	 */
	@Override
	public void close() throws IOException {
		if (disClient != null)
			disClient.close();
		if (dosClient != null)
			dosClient.close();
		if (sockC != null && !sockC.isClosed())
			sockC.close();
	}

	/**
	 * Método para desconectar manualmente el cliente enviando un mensaje de
	 * desconexión, cerrando streams y cerrando el socket.
	 * 
	 * @param i DataInputStream a cerrar.
	 * @param o DataOutputStream a cerrar.
	 */
	public void disconect(DataInputStream i, DataOutputStream o) {
		// Intenta enviar mensaje de desconexión al servidor
		if (dosClient != null) {
			try {
				dosClient.writeUTF("CLIENTE_SE_DESCONECTA");
				dosClient.flush();
			} catch (IOException ex) {
				Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
			}

		}

		// Cierra streams y socket
		try {
			if (disClient != null) {
				disClient.close();
			}
			if (dosClient != null) {
				dosClient.close();
			}
			if (sockC != null && !sockC.isClosed()) {
				sockC.close();
			}
			ps.println("Cliente desconectado correctamente.");
		} catch (IOException ex) {
			Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
