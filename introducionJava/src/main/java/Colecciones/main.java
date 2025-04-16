package Colecciones;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class main {

	public static void main(String[] args) {
		// vector
		int[] vector = new int[5];

		// Crear una Lista
		ArrayList<String> nombres = new ArrayList<>();// clasica
		ArrayList comidas = new ArrayList<>();
		List<Integer> numeros = new ArrayList<>();

		LinkedList<Integer> otraLista = new LinkedList<>();
		List<String> productos;

		nombres.add("Pablo");
		nombres.add("Maria");
		nombres.add("Gonzalo");
		nombres.add("Matias");
		nombres.add("Nicolas");
		
		nombres.set(4, "Pedro");
		nombres.addAll(comidas);
		
		nombres.clone();
		nombres.contains("Juan");
		nombres.containsAll(comidas);
		nombres.get(1);
		nombres.indexOf("Juan");
		nombres.isEmpty();
		nombres.remove(3);
		nombres.remove("Juan");
		nombres.removeAll(comidas);
		nombres.size();
		nombres.sort(null);
		nombres.subList(0, 2);
		nombres.toArray(); // arrayList -> vec[]
		nombres.clear();
		
		// hash
		HashMap<Integer, String> diccionario = new HashMap<>();
		LinkedHashMap<CadenaDeCaracteres.main,String> diccEnlace = new LinkedHashMap<>();
		Map<Integer, String> dicc = new HashMap<>();
		
		diccionario.containsKey( 5 );
		diccionario.get( 2 );
		diccionario.put(4, "hola");
		diccionario.remove(5);
		diccionario.remove(4,"hola");
		diccionario.entrySet();
		
		HashSet<Integer> DNI = new HashSet<>();
	    LinkedHashSet<Integer> CUIL = new LinkedHashSet<>();
	    Set<Integer> dni = new HashSet<>();
   
	}

	public static void recorrido()
	{
		ArrayList<String> nombres = new ArrayList<>();// clasica

		for(int i=0;i<=nombres.size();i++)
		{
			nombres.get(i);
		}
		
		for( String item : nombres)
		{
			System.out.println( item );
		}
		
		Iterator it = nombres.iterator();
		while( it.hasNext() )
		{
			System.out.println( it.next() );
		}
		
		//recorrer Map y Set
		
	}
	
}



