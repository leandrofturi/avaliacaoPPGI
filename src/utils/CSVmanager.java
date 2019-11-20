package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import exceptions.ErroDeIO;

public class CSVmanager {

	public static ArrayList<String[]> CSVread(String path, char sep, boolean header) throws ErroDeIO {
		
		int colSize = 0;
		try(BufferedReader br = new BufferedReader(new FileReader(path))) {
			ArrayList<String[]> list = new ArrayList<String[]>();
			
			String[] linha;
			if(header == true) {
				colSize = br.readLine().split(";").length;
			}
				
			while(br.ready()) {
				String content = br.readLine();
				if(header = true) {
					for(int i = content.split(";").length; i < colSize; i++)
						content += ";" + "NA";
				}
				linha = content.split(";");
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
	
	public static void CSVwriter(ArrayList<String[]> content, String path, char sep, char dec) throws ErroDeIO, IOException {
		
		File file = new File(path);
		
		File dir = new File("data");
		if(!dir.exists()) {
			try {
				dir.mkdir();
		    } catch(SecurityException se) {
		        se.getMessage();
		    }
		}
		
		if(!file.exists()){
			file.createNewFile();
		}
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
				
			for(String[] line : content) {
				for(int i = 0; i < line.length-1; i++)
					bw.write(line[i] + sep);
				bw.write(line[line.length-1]);
				bw.newLine();
			}
			
			bw.close();
		}
		catch (IOException e){
			throw new ErroDeIO();
		}
	}
	
}
