package Utils;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Clase base que maneja la conexión cliente-servidor y la transferencia de
 * archivos. Implementa la interfaz FileTransfer para envío y recepción de
 * archivos.
 */
public class Connection implements Utils.FileTransfer {

	private int port = 2006; // Puerto por defecto para la conexión
	private String ip = "127.0.0.1"; // IP por defecto (localhost)
	protected String msg = ""; // Mensaje auxiliar para comunicación

	protected PrintStream ps; // PrintStream para salida estándar (consola)
	protected Socket sockC; // Socket cliente
	protected ServerSocket sockS; // ServerSocket para el servidor

	private InetAddress direction; // Dirección IP resuelta
	protected DataOutputStream dosServer, dosClient; // Streams para enviar datos

	/**
	 * Constructor para servidor o cliente con IP y puerto por defecto.
	 * 
	 * @param type Tipo de conexión (SERVER o CLIENT)
	 * @throws UnknownHostException si la IP no es válida
	 * @throws IOException          si hay error creando sockets
	 */
	public Connection(Utils.enumType type) throws UnknownHostException, IOException {
		ps = new PrintStream(System.out);
		direction = InetAddress.getByName(ip);

		switch (type) {
		case SERVER:
			sockS = new ServerSocket(this.port);
			sockC = new Socket();
			break;
		case CLIENT:
			sockC = new Socket(direction, port);
			break;
		}
	}

	/**
	 * Constructor para cliente con IP y puerto específicos.
	 * 
	 * @param type   Tipo de conexión (solo CLIENT permitido acá)
	 * @param iP     Dirección IP a conectar
	 * @param puerto Puerto a usar
	 * @throws UnknownHostException si la IP no es válida
	 * @throws IOException          si hay error al crear el socket
	 */
	public Connection(enumType type, String iP, int puerto) throws UnknownHostException, IOException {
		ps = new PrintStream(System.out);
		direction = InetAddress.getByName(iP);

		switch (type) {
		case CLIENT:
			sockC = new Socket(iP, puerto);
			break;
		}
	}

	public int getPort() {
		return port;
	}

	public void setPort(int p) {
		this.port = p;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String i) {
		this.ip = i;
	}

	/**
	 * Envía un archivo a través del DataOutputStream. Primero envía metadatos
	 * (tamaño y nombre), luego envía el contenido en bytes.
	 * 
	 * @param ruta Ruta completa del archivo a enviar
	 * @param out  Stream de salida para enviar los datos
	 * @throws IOException si el archivo no existe o hay error de IO
	 */
	@Override
	public void sendFile(String ruta, DataOutputStream out) throws IOException {
		File archivo = new File(ruta);

		BufferedOutputStream bos = null;
		FileInputStream fis = null;

		if (!archivo.exists()) {
			throw new IOException("Archivo no existe");
		}

		// Enviar metadatos
		out.writeLong(archivo.length());
		out.writeUTF(archivo.getName());
		out.flush();

		// Enviar contenido binario con BufferedOutputStream envuelto sobre
		// DataOutputStream
		try {
			bos = new BufferedOutputStream(out);
			fis = new FileInputStream(archivo);

			byte[] buffer = new byte[64 * 1024];
			int bytesRead;
			while ((bytesRead = fis.read(buffer)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			bos.flush();
		} finally {
			// Cerrar FileInputStream, pero NO cerramos bos para no cerrar el socket
			if (fis != null)
				fis.close();
		}
	}

	/**
	 * Recibe un archivo desde el DataInputStream. Primero lee metadatos (tamaño y
	 * nombre), luego recibe el contenido y lo guarda.
	 * 
	 * @param dis Stream de entrada para recibir datos
	 * @throws IOException si hay error de IO
	 */
	@Override
	public void recibeFile(DataInputStream dis) throws IOException {
		long size = dis.readLong();
		String nombre = dis.readUTF();

		File carpeta = new File("Recibidos");
		if (!carpeta.exists())
			carpeta.mkdirs();

		File archivo = new File(carpeta, nombre);

		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(archivo));

			byte[] buffer = new byte[64 * 1024];
			long restante = size;
			int bytesRead;

			// Leer bytes directamente del DataInputStream (que está sobre el InputStream
			// del socket)
			while (restante > 0 && (bytesRead = dis.read(buffer, 0, (int) Math.min(buffer.length, restante))) != -1) {
				bos.write(buffer, 0, bytesRead);
				restante -= bytesRead;
			}
			bos.flush();
		} finally {
			if (bos != null)
				bos.close();
		}

		// Opcional: validar tamaño recibido
		if (archivo.length() == size) {
			System.out.println("Archivo recibido con éxito: " + archivo.getName());
		} else {
			System.out.println("Error: tamaño recibido no coincide");
		}
	}

	/**
	 * Cierra todos los recursos abiertos: streams y sockets. Intenta cerrar todo y
	 * lanza la primera excepción ocurrida, si hay.
	 * 
	 * @throws IOException Si hay error cerrando los recursos.
	 */
	@Override
	public void close() throws IOException {
		IOException exception = null;

		// Cerrar DataOutputStreams si existen
		if (dosServer != null) {
			try {
				dosServer.close();
			} catch (IOException e) {
				exception = e;
			}
		}
		if (dosClient != null) {
			try {
				dosClient.close();
			} catch (IOException e) {
				exception = e;
			}
		}

		// Cerrar Socket cliente si existe
		if (sockC != null && !sockC.isClosed()) {
			try {
				sockC.close();
			} catch (IOException e) {
				exception = e;
			}
		}

		// Cerrar ServerSocket si existe
		if (sockS != null && !sockS.isClosed()) {
			try {
				sockS.close();
			} catch (IOException e) {
				exception = e;
			}
		}

		// No cerramos 'ps' porque es System.out encapsulado, que no debe cerrarse.

		// Si hubo alguna excepción al cerrar, la lanzamos al final
		if (exception != null) {
			throw exception;
		}
	}

}
