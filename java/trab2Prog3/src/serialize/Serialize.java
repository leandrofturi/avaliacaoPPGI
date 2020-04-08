package serialize;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import exceptions.Desconhecido;
import exceptions.ErroDeIO;

public class Serialize {

	public static void serializar(String path, Object obj) throws ErroDeIO, Desconhecido {
		
		/*
		File dirData = new File("data");
		if(!dirData.exists()) {
			try {
				dirData.mkdir();
		    } catch(SecurityException se) {
		    	se.getMessage();
		    	throw new Desconhecido();
		    }
		}
		*/
		
		try {
			/*
			File dir = new File("data/tmp");
			if(!dir.exists()) {
				try {
					dir.mkdir();
			    } catch(SecurityException se) {
			    	se.getMessage();
			    	throw new Desconhecido();
			    }
			}
			*/
			
			//File file = new File("data/tmp/recredenciamento.dat");
			File file = new File(path);
			if(!file.exists()){
				file.createNewFile();
			}
			
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			
			out.writeObject(obj);

			out.close();
			fileOut.close();
			//System.out.printf("Serialização dos dados foi salvo em data/tmp/recredenciamento.dat" + '\n');
	    } catch(IOException i) {
	    	throw new ErroDeIO();
	    }
	}
	
	public static Object desserializar(String path) throws ErroDeIO, Desconhecido {
		
	    try {
	    	//FileInputStream fileIn = new FileInputStream("data/tmp/recredenciamento.dat");
	    	FileInputStream fileIn = new FileInputStream(path);
	    	ObjectInputStream in = new ObjectInputStream(fileIn);
	       
	    	Object obj = in.readObject();
	        
	    	in.close();
	    	fileIn.close();
	    	//System.out.println("Dados desserializados" + '\n');
	    	return obj;
	    } catch(IOException i) {
	    	throw new ErroDeIO();
	    } catch(ClassNotFoundException c) {
	    	throw new Desconhecido();
	    }
	}
	
}
