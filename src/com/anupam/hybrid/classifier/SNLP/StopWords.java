package com.anupam.hybrid.classifier.SNLP;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.anupam.Constants;


public class StopWords {
	public static HashMap<String,Integer> mapStopWords=new HashMap<String,Integer>();
	private static org.apache.log4j.Logger log = Logger
            .getLogger(StopWords.class);
	
	static{
		BufferedReader br = null;
		try{
			br = new BufferedReader(new FileReader(Constants.StopWOrdsFileLocation));
			String line = br.readLine();
			
			int i=0;
			while (line!=null) {
				mapStopWords.put(line,i++);
				line = br.readLine();
			}
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception"+e.getMessage());
			System.out.println("Unable to read from"+Constants.StopWOrdsFileLocation);
			e.printStackTrace();
			log.error("Exception"+e.getMessage());
			log.error("Unable to read from"+Constants.StopWOrdsFileLocation);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception"+e.getMessage());
			System.out.println("Unable to read from"+Constants.StopWOrdsFileLocation);
			log.error("Exception"+e.getMessage());
			log.error("Unable to read from"+Constants.StopWOrdsFileLocation);
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
