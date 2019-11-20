package serialize;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serialize {

	public static void serializar(Object obj) throws IOException {
		
		File dirData = new File("data");
		if(!dirData.exists()) {
			try {
				dirData.mkdir();
		    } catch(SecurityException se) {
		    	se.getMessage();
		    	throw se;
		    }
		}
		
		try {
			File dir = new File("data/tmp");
			if(!dir.exists()) {
				try {
					dir.mkdir();
			    } catch(SecurityException se) {
			    	se.getMessage();
			    	throw se;
			    }
			}
			
			File file = new File("data/tmp/recredenciamento.dat");
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			
			out.writeObject(obj);

			out.close();
			fileOut.close();
			System.out.printf("Serialização dos dados foi salvo em data/tmp/recredenciamento.dat" + '\n');
	    } catch(IOException i) {
	    	throw i;
	    }
	}
	
	public static Object desserializar() throws IOException, ClassNotFoundException {
		
	    try {
	    	FileInputStream fileIn = new FileInputStream("data/tmp/recredenciamento.dat");
	    	ObjectInputStream in = new ObjectInputStream(fileIn);
	       
	    	Object obj = in.readObject();
	        
	    	in.close();
	    	fileIn.close();
	    	System.out.println("Dados desserializados" + '\n');
	    	return obj;
	    } catch(IOException i) {
	    	throw i;
	    } catch(ClassNotFoundException c) {
	    	throw c;
	    }
	}
	
}
