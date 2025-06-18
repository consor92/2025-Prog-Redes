package Chat;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server  extends connection implements AutoCloseable{

	private InputStreamReader disServer = null;
	private BufferedReader br = null;    
    
	public Server(Utils.enumType type) throws UnknownHostException, IOException {
		super(type);
	}

	public void serverOn() {
		
		try {
			
			ps.println(Utils.Colors.ANSI_BLUE+"Esperanco conexion de cliente ...."+Utils.Colors.ANSI_RESET);
			sockC = sockS.accept();
			
			ps.printf("%s - %s",
					sockC.getInetAddress().getHostAddress(),
					sockC.getInetAddress().getHostName()
					);
			
			dosClient = new DataOutputStream(sockC.getOutputStream());
			disServer = new InputStreamReader(sockC.getInputStream());
			br = new BufferedReader(disServer);
			
			ps.println(Utils.Colors.ANSI_GREEN+"Cliente conectado con exito."+Utils.Colors.ANSI_RESET);
			Thread.sleep( 200 );
			ps.println(Utils.Colors.ANSI_RED+"Esperando mensaje del cliente ...."+Utils.Colors.ANSI_RESET);
			
			while( (msg = br.readLine() )  != null )
			{
				ps.printf( Utils.Colors.ANSI_YELLOW+"\tMensaje: %s\n" +Utils.Colors.ANSI_RESET, msg );
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

	@Override
	public void close() throws IOException {
		sockC.close();
		if( br!=null)
			br.close();
		
		if(disServer != null)
			disServer.close();
		
		dosClient.close();
		sockS.close();		
	}
	
	
	
}
