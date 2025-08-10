package Chat;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import Utils.Connection;

/**
 * Server representa un servidor que acepta conexiones de clientes, recibe
 * archivos y mensajes.
 * 
 * Extiende Connection para manejo de sockets y streams e implementa
 * AutoCloseable para manejo correcto de recursos.
 */
public class Server extends Connection implements AutoCloseable {

	/** Stream para recibir datos del cliente */
	private DataInputStream disServer = null;
	/** BufferedReader para leer texto (no usado activamente aquí) */
	private BufferedReader br = null;

	/**
	 * Constructor que inicializa el servidor con un tipo de conexión.
	 * 
	 * @param type Tipo de conexión (enum).
	 * @throws UnknownHostException Si el host no es conocido.
	 * @throws IOException          Si ocurre error de IO.
	 */
	public Server(Utils.enumType type) throws UnknownHostException, IOException {
		super(type);
	}

	/**
	 * Método principal para arrancar el servidor.
	 * 
	 * Espera conexiones de cliente, acepta una conexión, luego recibe un archivo, y
	 * escucha mensajes UTF enviados por el cliente, respondiendo con un "ok" a cada
	 * mensaje recibido.
	 */
	public void serverOn() {
		try {
			ps.printf("");
			ps.printf(Utils.Colors.ANSI_YELLOW + "Esperando conexion de cliente\n\tPort:%s\n" + Utils.Colors.ANSI_RESET,
					getPort());

			// Espera y acepta la conexión entrante del cliente
			sockC = sockS.accept();

			// Muestra la IP y el nombre del host del cliente conectado
			ps.printf("%s - %s \n", sockC.getInetAddress().getHostAddress(), sockC.getInetAddress().getHostName());

			// Inicializa streams para comunicación con cliente
			dosClient = new DataOutputStream(sockC.getOutputStream());
			disServer = new DataInputStream(sockC.getInputStream());

			ps.println(Utils.Colors.ANSI_GREEN + "Cliente conectado con exito." + Utils.Colors.ANSI_RESET);
			Thread.sleep(200);

			ps.println(Utils.Colors.ANSI_RED + "Esperando archivo ...." + Utils.Colors.ANSI_RESET);
			recibeFile(disServer);

			ps.println(Utils.Colors.ANSI_RED + "Esperando mensaje del cliente ...." + Utils.Colors.ANSI_RESET);

			// Bucle para recibir mensajes UTF mientras la conexión esté abierta
			String msg;
			while (!sockC.isClosed() && (msg = disServer.readUTF()) != null) {
				ps.printf(Utils.Colors.ANSI_YELLOW + "\tMensaje: %s\n" + Utils.Colors.ANSI_RESET, msg);

				// Aquí respondés o no, o podés enviar un OK:
				dosClient.writeUTF("ok");
				dosClient.flush();
			}

		} catch (IOException | InterruptedException ex) {
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Cierra todos los recursos asociados al servidor y a la conexión cliente.
	 * 
	 * @throws IOException Si ocurre error al cerrar streams o sockets.
	 */
	@Override
	public void close() throws IOException {
		if (sockC != null && !sockC.isClosed())
			sockC.close();
		if (br != null)
			br.close();
		if (disServer != null)
			disServer.close();
		if (dosClient != null)
			dosClient.close();
		if (sockS != null && !sockS.isClosed())
			sockS.close();

		// Llama al método close() de la superclase Connection para limpiar recursos
		// adicionales
		super.close();
	}

}
