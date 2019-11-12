package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import exceptions.ErroDeIO;

public class CSVmanager {

	public static ArrayList<String[]> CSVread(String path, char sep, char dec, boolean header) throws ErroDeIO {
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))) {
			ArrayList<String[]> list = new ArrayList<String[]>();
			
			if(header == true)
				br.readLine();
				
			while(br.ready()) {
				String[] linha = br.readLine().split(";");
				if(linha.length != 0)
					list.add(linha);
			}
			
			br.close();
			return list;
		}
		catch (IOException e){
			throw new ErroDeIO();
		}
		
	}
}
