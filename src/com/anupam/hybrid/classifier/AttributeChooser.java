package com.anupam.hybrid.classifier;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.anupam.Constants;
import com.anupam.email.email;
import com.anupam.file.FileRead;
import com.anupam.hybrid.classifier.SNLP.PreProcess;

/**
 * @author Anupam Gangotia
 * Profile::http://en.gravatar.com/gangotia
 * github::https://github.com/agangotia
 */

/**
 * Class: AttributeChooser This class from a list of emails, filters out the
 * most frequent K words
 * */
public class AttributeChooser {
	ClassifierType classifierType;
	
	/**
	 * Map containing count of the words from all the samples.
	 */
	private HashMap<String, Integer> mapCountWordsInAllSamples;
	
	/**
	 * List of Hashmaps containing individual instance counts words, which are classified as true.
	 */
	private ArrayList<HashMap<String, Integer>> listSampleMapsTrue;
	
	/**
	 * List of Hashmaps containing individual instance counts words, which are classified as false.
	 */
	private ArrayList<HashMap<String, Integer>> listSampleMapsFalse;
	
	private static org.apache.log4j.Logger log = Logger
			.getLogger(AttributeChooser.class);
	int countTrain;
	ArrayList<String> listFrequentWords;

	/**
	 * Default Constructor
	 * */
	AttributeChooser(ClassifierType classifierType) {
		mapCountWordsInAllSamples = new HashMap<String, Integer>();
		listSampleMapsTrue = new ArrayList<HashMap<String, Integer>>();
		listSampleMapsFalse = new ArrayList<HashMap<String, Integer>>();
		countTrain = 0;
		listFrequentWords = new ArrayList<String>();
		this.classifierType=classifierType;
	}

	/**
	 * Function: fillMaps
	 * 
	 * @param ArrayList
	 *            <String>- ArrayList of
	 *            String This
	 *            function fills the maps.
	 * */
	public void fillMaps2(ArrayList<String> srcEmailsCLassifiedTrue,
			ArrayList<email> srcEmailsCLassifiedFalse) {

		for (String objEMail : srcEmailsCLassifiedTrue) {
			boolean hasData = false;
			HashMap<String, Integer> mapCountWords = new HashMap<String, Integer>();
			if (objEMail != null) {
				List<String> listWords = PreProcess.lemmatize(objEMail);
				if (listWords != null) {
					fillMaps(listWords, mapCountWords);
					hasData = true;
				}
			}
			listSampleMapsTrue.add(mapCountWords);
			if (hasData)
				countTrain++;
		}

		for (email objEMail : srcEmailsCLassifiedFalse) {
			boolean hasData = false;
			HashMap<String, Integer> mapCountWords = new HashMap<String, Integer>();
			if (objEMail.getContent() != null) {
				List<String> listWords = PreProcess.lemmatize(objEMail
						.getContent());
				if (listWords != null) {
					fillMaps2(listWords, mapCountWords);
					hasData = true;
				}

			}
			if (objEMail.getSubject() != null) {
				List<String> listWords = PreProcess.lemmatize(objEMail
						.getContent());
				if (listWords != null) {
					fillMaps2(listWords, mapCountWords);
					hasData = true;
				}
			}
			listSampleMapsFalse.add(mapCountWords);
			if (hasData)
				countTrain++;
		}
	}
	
	
	/**
	 * Function: fillMaps
	 * 
	 * @param ArrayList
	 *            <email>- ArrayList of
	 *            com.anupam.emailclassifier.textpreprocessing.email This
	 *            function fills the maps.
	 * */
	public void fillMaps(ArrayList<email> srcEmailsCLassifiedTrue,
			ArrayList<email> srcEmailsCLassifiedFalse) {

		for (email objEMail : srcEmailsCLassifiedTrue) {
			boolean hasData = false;
			HashMap<String, Integer> mapCountWords = new HashMap<String, Integer>();
			if (objEMail.getContent() != null) {
				List<String> listWords = PreProcess.lemmatize(objEMail
						.getContent());
				if (listWords != null) {
					fillMaps(listWords, mapCountWords);
					hasData = true;
				}
			}
			if (objEMail.getSubject() != null) {
				List<String> listWords = PreProcess.lemmatize(objEMail
						.getSubject());
				if (listWords != null) {
					fillMaps(listWords, mapCountWords);
					hasData = true;
				}
			}
			listSampleMapsTrue.add(mapCountWords);
			if (hasData)
				countTrain++;
		}

		for (email objEMail : srcEmailsCLassifiedFalse) {
			boolean hasData = false;
			HashMap<String, Integer> mapCountWords = new HashMap<String, Integer>();
			if (objEMail.getContent() != null) {
				List<String> listWords = PreProcess.lemmatize(objEMail
						.getContent());
				if (listWords != null) {
					fillMaps2(listWords, mapCountWords);
					hasData = true;
				}

			}
			if (objEMail.getSubject() != null) {
				List<String> listWords = PreProcess.lemmatize(objEMail
						.getContent());
				if (listWords != null) {
					fillMaps2(listWords, mapCountWords);
					hasData = true;
				}
			}
			listSampleMapsFalse.add(mapCountWords);
			if (hasData)
				countTrain++;
		}
	}

	/**
	 * Function: fillMaps
	 * 
	 * @param List
	 *            <String>- List of Words
	 * @param HashMap
	 *            <String,Integer>- Map to be filled This function fills the
	 *            maps.
	 * */
	public void fillMaps(List<String> listWords,
			HashMap<String, Integer> mapCountWords) {
		for (String word : listWords) {

			if (mapCountWords.containsKey(word)) {
				mapCountWords.put(word, mapCountWords.get(word) + 1);
			} else {
				mapCountWords.put(word, 1);
			}

			if (mapCountWordsInAllSamples.containsKey(word)) {
				mapCountWordsInAllSamples.put(word,
						mapCountWordsInAllSamples.get(word) + 1);
			} else {
				mapCountWordsInAllSamples.put(word, 1);
			}

		}

	}
	
	
	/**
	 * Function: fillMaps2
	 * This function Fills the count of words for a particular instance.
	 * Hence it is used when i am filling for Instance that's class is 0.
	 * As for Instances whose class is 1, we also need to keep track of total words count in all instances,
	 * which we use further in finding out most frequent words.
	 * @param List
	 *            <String>- List of Words
	 * @param HashMap
	 *            <String,Integer>- Map to be filled This function fills the
	 *            maps.
	 * */
	public void fillMaps2(List<String> listWords,
			HashMap<String, Integer> mapCountWords) {
		for (String word : listWords) {

			if (mapCountWords.containsKey(word)) {
				mapCountWords.put(word, mapCountWords.get(word) + 1);
			} else {
				mapCountWords.put(word, 1);
			}

		}

	}

	/**
	 * Function: prepareTrainingSet This function prepares the training sets
	 * */
	public ArrayList<String[]> prepareTrainingSet() {

		if (listFrequentWords.size() == 0) {//If called First time
			this.listFrequentWords = getMaxFrequentWords();
		}

		/*
		 * First creating the container for Matrixdata type
		 */
		int coloumns = Constants.ChooseFrequent + 1;// Number of Coloumns in
													// MatrixData
		// +1 as we add "Class" Header
		int Numrows = countTrain;// Number of rows(of instances excluding
									// headers) in MatrixData
		ArrayList<String[]> rows = new ArrayList<String[]>();
		String[] header = new String[this.listFrequentWords.size() + 1];
		int indexHeader = 0;
		for (String word : this.listFrequentWords) {
			header[indexHeader++] = word;
		}
		header[indexHeader] = "Class";
		rows.add(header);

		/*
		 * Lets fill in the values
		 */
		for (HashMap<String, Integer> mapOfWords : listSampleMapsTrue) {
			String[] rowValue = new String[coloumns];
			int index = 0;
			for (String attribute : this.listFrequentWords) {
				if (mapOfWords.containsKey(attribute)) {
					rowValue[index] = String.valueOf(mapOfWords.get(attribute));
				} else {
					rowValue[index] = String.valueOf(0);
				}
				index++;
			}
			rowValue[index] = String.valueOf(1);
			;// This is for Attribute "Class"
			rows.add(rowValue);
		}
		for (HashMap<String, Integer> mapOfWords : listSampleMapsFalse) {
			String[] rowValue = new String[coloumns];
			int index = 0;
			for (String attribute : this.listFrequentWords) {
				if (mapOfWords.containsKey(attribute)) {
					rowValue[index] = String.valueOf(mapOfWords.get(attribute));
				} else {
					rowValue[index] = String.valueOf(0);
				}
				index++;
			}
			rowValue[index] = String.valueOf(0);
			;// This is for Attribute "Class"
			rows.add(rowValue);
		}

		/*
		 * Now we shall create the matrix
		 */

		return rows;

	}

	/**
	 * Function: getTestInstance This function prepares the TestInstance
	 * */
	public ArrayList<String> getTestInstance(email emailTest) {
		ArrayList<String> testList = new ArrayList<String>();
		String source = null;
		if (emailTest.getSubject() != null)
			source += emailTest.getSubject()+" ";
		if (emailTest.getContent() != null)
			source += emailTest.getContent();
		if (source == null)
			return null;
		List<String> listWords = PreProcess.lemmatize(source);
		if (Constants.isDebug) {
			System.out.println("listWords()"+listWords.size());
		}
		log.info("getTestInstance()");
		if (listFrequentWords.size() == 0)
			return null;
		for (String attribute : listFrequentWords) {
			if (listWords.contains(attribute)) {
				testList.add(attribute);
			}
		}
		
		return testList;
	}
	


	/**
	 * Function: getMaxFrequentWords This function returns the K most frequent
	 * words
	 * */
	public ArrayList<String> getMaxFrequentWords() {


		if (mapCountWordsInAllSamples.size() < 1) {
			System.out.println("HashMaps are empty.");
			System.out.println("There is some problem with your input .");
			log.error("HashMaps are empty. Returning..");
			return null;
		}
		
		
		// TAsk 1: SOrt the Map by Values
		ValueComparator bvc =  new ValueComparator(mapCountWordsInAllSamples);
        TreeMap<String,Integer> sorted_map = new TreeMap<String,Integer>(bvc);
        sorted_map.putAll(mapCountWordsInAllSamples);

		ArrayList<String> listFrequentWords = new ArrayList<String>();
		StringBuilder sb=new StringBuilder();
		int count = 0;
		for (Map.Entry<String, Integer> entry : sorted_map.entrySet()) {
		    String key = entry.getKey();
		    int value = entry.getValue();
		    listFrequentWords.add(key);
		    
		    sb.append(key+" "+value);
			sb.append(System.getProperty("line.separator"));
			count++;
			if (count == Constants.ChooseFrequent) {
				break;
			}
		}
		
		try {
			FileRead.updateFile(Constants.logForFrequentWords, "\n Frequent Words For "+this.classifierType);
			FileRead.updateFile(Constants.logForFrequentWords, sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Unable to write to"+Constants.logForFrequentWords);
			log.error("Unable to write to"+Constants.logForFrequentWords);
		}
		
		return listFrequentWords;

	}
	
	public void printFreqWords(ArrayList<String> listFrequentWords){
		System.out.println("-----Frequent Words");
		log.info("-----Frequent Words");
		int freqWordsInARow=10;
		int index=0;
		//Logging the most frequent words
		for(String temp:listFrequentWords){
			if(index==freqWordsInARow){
				System.out.println("");
				log.info("\n");
				index=0;
			}else{
				if(index==0){
					System.out.print(temp);
					log.info(temp);
				}else{
					System.out.print(","+temp);
					log.info(","+temp);
				}
				index++;
			}
			
		}
	}
	public void logFreqWords(ArrayList<String> listFrequentWords){
		log.info("-----Frequent Words");
		int freqWordsInARow=10;
		int index=0;
		//Logging the most frequent words
		for(String temp:listFrequentWords){
			if(index==freqWordsInARow){
				//System.out.println("");
				log.info("\n");
				index=0;
			}else{
				if(index==0){
					//System.out.print(temp);
					log.info(temp);
				}else{
					//System.out.print(","+temp);
					log.info(","+temp);
				}
				index++;
			}
			
		}
	}
}

class ValueComparator implements Comparator<String> {

    Map<String, Integer> base;
    public ValueComparator(Map<String, Integer> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.    
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}
