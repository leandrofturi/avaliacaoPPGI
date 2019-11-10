package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVmanager {

	public ArrayList<String[]> CSVread(String path, char sep, char dec, boolean header) throws IOException {
		
		File file = new File(path);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		ArrayList<String[]> list = new ArrayList<String[]>();
		
		if(header == true)
			br.readLine();
			
		while(br.ready()) {
			String[] linha = br.readLine().split(";");
			if(linha.length != 0)
				list.add(linha);
		}
		
		br.close();
		fr.close();
		
		return list;
	}
}
