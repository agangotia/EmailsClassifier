package com.anupam;
/**
 * @author Anupam Gangotia
 * Profile::http://en.gravatar.com/gangotia
 * github::https://github.com/agangotia
 */


/**
 * Interface :: Constants
 * All the constants for Hybrid Email Classifier are defined here
 */
public interface Constants {
	
	
	/**
	 * If showResult=true, program will print support SOP's For results.
	 */
	public static boolean showResult = false;
	
	/**
	 * If isDebug=true, program will print support SOP's else not.
	 */
	public static boolean isDebug = false;
	
	/**
	 * If showEmailRead=true, program will print emailsRead.
	 */
	public static boolean showEmailRead = false;
	
	/**
	 * If showEmailRead=true, program will print emailsRead.
	 */
	public static boolean showNaiveBayes = false;
	
	/**
	 * Chooses the K most frequent words
	 * This is the limit upon which this Spam Filter Words.
	 * If k=100, then for classification we choose most frequent 100 words from training data,
	 * and finally try to classify test based on the algorithm result.
	 */
	public static int ChooseFrequent = 500;
	
	
	
	/**
	 * You want to take feedback from user
	 */
	public static boolean ifFeedback = true;
	
	/**
	 * Name of the folder where i have stored the test datasets.
	 */
	public static final String TestDataLocation="data//test";
	
	/**
	 * SNLP PArser Parts of speech to consider
	 * This is the location of the textfile, which contains which parts of speech to be considered
	 * after sentence parsing using SNLP.
	 */
	public static final String PartsOfSpeechFileLocation="constants//SNLP_PartsOfSpeech.txt";
	
	/**
	 * Stop Words Path
	 * This is the location of textfile, which contains the stop words
	 */
	public static final String StopWOrdsFileLocation="constants//StopWords.txt";
	
	//All the Training Data Locations
	//1.SPAM
	/**
	 * Name of the folder where i have stored the training datasets for CLASS SPAM.
	 */
	public static String SPAMTRAINDATALOC="data//train//spam";
	/**
	 * Name of the folder where i have stored the training datasets for CLASS HAM.
	 */
	public static String HAMTRAINDATALOC="data//train//ham";
	
	/**
	 * Name of the folder where i have stored the training datasets for CLASS GENERAL.
	 */
	public static String GENERALTRAINDATALOC="data//train//general";
	
	//2.ATHEISM
	/**
	 * Name of the folder where i have stored the training datasets for CLASS ATHEISM.
	 */
	public static String ATHEISMTRAINDATALOC="data//train//atheism";
	
	
	//3.SPORTS
	/**
	 * Name of the folder where i have stored the training datasets for CLASS SPORT.
	 */
	public static String SPORTSTRAINDATALOC="data//train//sports";
	
	
	//4.AUTOS
	/**
	 * Name of the folder where i have stored the training datasets for CLASS AUTO.
	 */
	public static String AUTOTRAINDATALOC="data//train//autos";
	
	//5.MEDICAL
	/**
	 * Name of the folder where i have stored the training datasets for CLASS MEDICAL.
	 */
	public static String MEDICALTRAINDATALOC="data//train//medical";
	
	/**
	 * This describes the log for frequent words.
	 */
	public static final String logForFrequentWords="log//logFrequent.log";

}
