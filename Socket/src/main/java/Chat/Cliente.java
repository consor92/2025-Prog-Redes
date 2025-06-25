package Chat;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import Utils.enumType;

public class Cliente extends Connection  {

	public Cliente(enumType type) throws UnknownHostException, IOException {
		super(type);
	}

	public void clientOn() {
		DataInputStream disClient = null;
		
		try {
			disClient = new DataInputStream(sockC.getInputStream());
			dosClient = new DataOutputStream(sockC.getOutputStream());
			
			dosClient.writeUTF("Hola, soy kevin y llegue reee tarde ");
			dosClient.flush();
			
			dosClient.writeUTF("kevin esta muy ansioso");
			dosClient.flush();
			while( !sockC.isClosed() && (msg = disClient.readUTF()) != null)
			{
				ps.printf( Utils.Colors.ANSI_YELLOW+"\tMensaje: %s\n" +Utils.Colors.ANSI_RESET, msg );		
				msg = "";
				//this.disconect( disClient , dosClient);
			}
	
		} catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
		}finally {	
			try {
				dosClient.close();
				disClient.close();
				
				//sockS.close();
			} catch (IOException ex) {
	            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
			}			
		}

	}
	
	public void disconect(DataInputStream i ,DataOutputStream o)
	{
		try {
			i.close();
			o.close();
			
			sockC.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
