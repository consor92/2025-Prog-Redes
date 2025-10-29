package NIO_Socket;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.System.Logger;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

public class NIO_Servidor {

	private Selector selector;
	private ServerSocketChannel serverChanel;
	private boolean running = false;
	private final Gson gson = new Gson();
	PrintStream ps = new PrintStream(System.out);

	private Map<SocketChannel, String> usuarios = new HashMap<>();
	private Map<String, SocketChannel> canalesPorNombre = new HashMap<>();

	// Códigos ANSI para colores en consola
	private static final String RESET = "\u001B[0m";
	private static final String ROJO = "\u001B[31m";
	private static final String VERDE = "\u001B[32m";
	private static final String AMARILLO = "\u001B[33m";
	private static final String AZUL = "\u001B[34m";
	private static final String MAGENTA = "\u001B[35m";
	private static final String CIAN = "\u001B[36m";

	public void start(int port) throws IOException {
		selector = Selector.open();
		serverChanel = ServerSocketChannel.open();
		serverChanel.configureBlocking(false);
		serverChanel.register(selector, SelectionKey.OP_ACCEPT);
		serverChanel.bind(new InetSocketAddress(port));

		running = true;
		// colocar mensaje de servidor iniciado y en que puerto

		new Thread(() -> {
			try {
				while (running) {
					selector.select();

					Iterator<SelectionKey> iter = selector.selectedKeys().iterator();

					while (iter.hasNext()) {
						SelectionKey key = iter.next();
						iter.remove();

						if (!key.isValid())
							continue;

						if (key.isAcceptable()) {
							ServerSocketChannel server = (ServerSocketChannel) key.channel();
							SocketChannel client = server.accept();

							if (client != null) {
								client.configureBlocking(false);
								client.register(selector, SelectionKey.OP_READ);
								// colocar menesaje de cliente conectado ( client.getRemoteAddress() )
								// sendMenssage(client);
								JsonObject json = new JsonObject();
								json.addProperty("type", "info");
								json.addProperty("text", "Welcome!! Por favor registrese enviando su Usuario: ");

								ByteBuffer buff = ByteBuffer.wrap(gson.toJson(json).getBytes());
								try {
									client.write(buff);
								} catch (IOException ex) {
									ps.println(ROJO + "[ERROR] no se pudo enviar mensaje" + RESET);
								}
								/*
								 * { 
								 * 	"type":"info", 
								 *  "text":"mensaje a enviar" 
								 *  }
								 */
							}
						} else if (key.isReadable()) {
							readFromClient(key);
						}
					}
				}
			} catch (IOException ex) {

			} finally {
				running = false;
				if (selector != null && selector.isOpen())
					selector.close();
				if (serverChanel != null && serverChanel.isOpen())
					serverChanel.close();
				// mensaje de servidor detenido
				ps.println(AMARILLO + "[info] SERVIDOR DETENIDO" + RESET);
			}
		}).start();

	}

	private void readFromClient(SelectionKey key) {
		SocketChannel cliente = (SocketChannel) key.channel();
		ByteBuffer buff = ByteBuffer.allocate(2024);

		int byteLeidos;
		try {
			byteLeidos = cliente.read(buff);
		} catch (IOException e) {
			System.out.println(ROJO + "[ERROR] Error leyendo del cliente" + RESET);
			disconnectClient(key, cliente);
			return;
		}

		if (byteLeidos == -1) {
			disconnectClient(key, cliente);
			return;
		}

		buff.flip();
		String msg = new String(buff.array(), 0, buff.limit()).trim();

		ps.println(AMARILLO + "[INFO] Mensaje recibido del cliente " 
						    + cliente.getRemoteAddress().toString() + ":" + msg
						    + RESET);
		
		
		try {
			JsonObject json = JsonParser.parseString(msg).getAsJsonObject();
			String tipo = json.get("type").getAsString();
			
			/*	
			 * {
			 * 		"type":"command",
			 * 		"text":"MD"
			 * }
			 */
			if( "command".equals(msg) ) {
				String comando = json.get("command").getAsString().trim().toLowerCase();
				handlerCommand( comando , cliente );
				return;
			}
			
			switch (tipo) {
				case "register" ->{
					
					}
				case "message" ->{
					
				}
				case "private" ->{
					
				}
				default -> {
					
				}
			}
			
			
		}catch(Exception ex) {
		
		}
		
		
	}

	private void handlerCommand( String cmd , SocketChannel cli ) {
		try {	
			switch(cmd) {
				case "/listUsers" ->{
					 Set<String> listadoUsuarios = new HashSet<>(usuarios.values());
					 
					 JsonObject json = new JsonObject();
					 json.addProperty("type", "cmd_res");
					 json.addProperty("cmd", "/listUsers");
					 json.addProperty("text", "Usuarios Conectados: " + String.join( ",\n" , listadoUsuarios ) );
					 sendToClient( gson.toJson( json ) , cli );
					 ps.println( AZUL + "[CMD] comando /listUsers solicitado por " 
							 		  + cli.getRemoteAddress().toString() );
				}
				case "/logout" ->{
					//Desconectao al cliente
						sendInfo( cli , "Desconectando..." );
						ps.println(AMARILLO + "[INFO] Desconectado cliente " 
							    + cli.getRemoteAddress().toString()
							    + RESET);
						disconnectClient( cli.keyFor(selector) , cli);
				}
				default ->{
					sendError(cli , "Comando desconocido: " + cmd );
					ps.println(AMARILLO + "[INFO] comando descnocido " 
						    + cmd
						    + RESET);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	
	
	
	private void sendToClient(String json, SocketChannel cli) {		
	}

	private void disconnectClient(SelectionKey keyFor, SocketChannel cli) {		
	}

	private void sendInfo(SocketChannel cli, String string) {
	}

	private void sendError(SocketChannel cli, String string) 		
	}

	private void broadcast(String message, SocketChannel sender) {
	}


	public static void main(String[] args) {
		NIO_Servidor server = new NIO_Servidor();

		try {
			server.start(25565);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
