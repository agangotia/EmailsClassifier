package com.anupam.hybrid.classifier.SNLP;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.anupam.Constants;


public class PartsOfSpeech {
	public static HashMap<String,Integer> mapPartsOfSpeech=new HashMap<String,Integer>();
	private static org.apache.log4j.Logger log = Logger
            .getLogger(PartsOfSpeech.class);
	static{
		BufferedReader br = null;
		try{
			br = new BufferedReader(new FileReader(Constants.PartsOfSpeechFileLocation));
			String line = br.readLine();
			int i=0;
			while (line!=null) {
				mapPartsOfSpeech.put(line,i++);
				line = br.readLine();
			}
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception"+e.getMessage());
			System.out.println("Unable to read from"+Constants.PartsOfSpeechFileLocation);
			e.printStackTrace();
			log.error("Exception"+e.getMessage());
			log.error("Unable to read from"+Constants.PartsOfSpeechFileLocation);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception"+e.getMessage());
			System.out.println("Unable to read from"+Constants.PartsOfSpeechFileLocation);
			log.error("Exception"+e.getMessage());
			log.error("Unable to read from"+Constants.PartsOfSpeechFileLocation);
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
