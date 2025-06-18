package Chat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends connection{

    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_MAGENTA = "\u0033[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";	
	
	public Server(enumType type) throws UnknownHostException, IOException {
		super(type);
	}

	public void serverOn() {
		InputStreamReader disServer = null;
		BufferedReader br = null;
		
		try {
			ps.println(ANSI_BLUE+"Esperanco conexion de cliente ...."+ANSI_RESET);
			sockC = sockS.accept();
			
			ps.printf("%s - %s",
					sockC.getInetAddress().getHostAddress(),
					sockC.getInetAddress().getHostName()
					);
			
			dosClient = new DataOutputStream(sockC.getOutputStream());
			disServer = new InputStreamReader(sockC.getInputStream());
			br = new BufferedReader(disServer);
			
			ps.println(ANSI_GREEN+"Cliente conectado con exito."+ANSI_RESET);
			Thread.sleep( 200 );
			ps.println(ANSI_RED+"Esperando mensaje del cliente ...."+ANSI_RESET);
			
			while( (msg = br.readLine() )  != null )
			{
				ps.printf( ANSI_YELLOW+"\tMensaje: %s\n" +ANSI_RESET, msg );
				dosClient.writeUTF("ok");
				dosClient.flush();
			}
		}catch (IOException | InterruptedException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}finally {
			try {
				sockC.close();
				if( br!=null)
					br.close();
				
				if(disServer != null)
					disServer.close();
				
				dosClient.close();
				sockS.close();
			} catch (IOException ex) {
	            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	
	
}
