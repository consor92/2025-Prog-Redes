package EjemploResolucionTPs;

import java.io.PrintStream;

public class main {

	public static void main(String[] args) {

		UsandoSystem punto1 = new UsandoSystem();
		UsandoReader punto2 = new UsandoSystem();
		UsandoColecciones punto3 = new UsandoSystem();
		UsandoFiles punto4 = new UsandoSystem();
		
		PrintStream ps = new PrintStream(System.out);
		
		//menu infinito seleccion de ejercicio
		while(  )
		{
			ps.println("Aca te armaes el listado de opciones para elegir ejecutar ejercicio");
			//pedir dato al usuario (opcion ejercicio a ejecutar)
			switch()
			{
				case 1:
					punto1.ejercicio1a();
				break;
				case 2:
					punto1.ejercicio1b();
				break;
			}
		}
		
		
		
	}

}
