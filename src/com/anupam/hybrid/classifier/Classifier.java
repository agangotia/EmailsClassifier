package com.anupam.hybrid.classifier;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.anupam.Constants;
import com.anupam.email.ReadLearningEmails;
import com.anupam.email.email;
import com.anupam.file.FileRead;
import com.anupam.hybrid.classifier.naivebayes.Decision;
import com.anupam.hybrid.classifier.nbtree.MatrixData;
import com.anupam.hybrid.classifier.nbtree.NBTree;

public class Classifier {
	/**
	 * classifierType
	 * {SPAM, SPORTS, BUSINESS, TECHNOLOGY, ENTERTAINMENT}
	 */	
	private final ClassifierType classifierType;
	

	/**
	 * Location where trained data is found which classifies as TRUE
	 */	
	private final String trainDataClassifiedLOC;
	
	/**
	 * Location where trained data is found which classifies as False
	 */	
	private final String trainDataNtClassifiedLOC;
	
	/**
	 * The Result after the classification, whether classified as True or False
	 */
	private boolean classifiedAs;
	
	/**
	 * Probability of classification
	 */
	private float probability;
	
	/**
	 * Object to hold instances data
	 */
	AttributeChooser objTrainInstaceMaker;
	
	/**
	 * Object to hold results
	 */
	ArrayList<String[]> reesults;
	
	/**
	 * Logger
	 */	
	private static org.apache.log4j.Logger log = Logger
            .getLogger(Classifier.class);
	
	/**
	 * Constructor
	 */	
	public Classifier(ClassifierType classifierType,String trainDataClassifiedLOC,String trainDataNtClassifiedLOC){
		this.classifierType=classifierType;
		this.trainDataClassifiedLOC=new String(trainDataClassifiedLOC);
		this.trainDataNtClassifiedLOC=new String(trainDataNtClassifiedLOC);
		classifiedAs=false;
		probability=1;
	}
	
	/**
	 * Function: prepareClassifierFromText
	 * Prepares the classifier from the input data
	 */	
	public void prepareClassifierFromText(){
		if(Constants.isDebug){//If debugging is on, then PRint on Console
			System.out.println(this.classifierType+"--com.anupam.hybrid.Classifier.prepareClassifierFromText()");
		}
		log.info(this.classifierType+"--com.anupam.hybrid.Classifier.prepareClassifierFromText()");
		
		if(trainDataClassifiedLOC!=null || trainDataNtClassifiedLOC!=null){
			/*
			 * Task 1: Read Training dataset.
			 */	
			ArrayList<String> InstancesReadClassifiedTrue=FileRead.readLearningData(trainDataClassifiedLOC);
			ArrayList<email> emailsReadClassifiedFalse=ReadLearningEmails.readLearningMails(trainDataNtClassifiedLOC);
			if(Constants.isDebug){
				System.out.println(this.classifierType+"--Number of emails read Classified True"+InstancesReadClassifiedTrue.size());
				System.out.println(this.classifierType+"--Number of emails read Classified False"+emailsReadClassifiedFalse.size());
			}
			log.info(this.classifierType+"--Number of emails read Classified True"+InstancesReadClassifiedTrue.size());
			log.info(this.classifierType+"--Number of emails read Classified False"+emailsReadClassifiedFalse.size());
			
			/*
			 * 
			 * Task 3: Find the most frequent words, in the emails read and 
			 * prepare the input for NBTree
			 */
			this.objTrainInstaceMaker=new AttributeChooser(this.classifierType);
			this.objTrainInstaceMaker.fillMaps2(InstancesReadClassifiedTrue,emailsReadClassifiedFalse);
			this.reesults=this.objTrainInstaceMaker.prepareTrainingSet();
			if(Constants.isDebug){
				System.out.println(this.classifierType+"--Training Set Instances"+reesults.size());
			}
			log.info(this.classifierType+"--Training Set Instances"+reesults.size());
			
		}else{
			System.out.println(this.classifierType+"--Error!!!!");
			log.error(this.classifierType+"--trainDataClassifiedLOC is null or trainDataNtClassifiedLOC is null");
		}
		
		if(Constants.isDebug){//If debugging is on, then PRint on Console
			System.out.println(this.classifierType+"--com.anupam.hybrid.Classifier.prepareClassifierFromText() ends");
		}
		log.info(this.classifierType+"--com.anupam.hybrid.Classifier.prepareClassifierFromText() ends");
	}
	
	/**
	 * Function: prepareClassifier
	 * Prepares the classifier from the inout data in the form of multiple email files
	 */	
	public void prepareClassifier(){
		if(Constants.isDebug){//If debugging is on, then PRint on Console
			System.out.println(this.classifierType+"--com.anupam.hybrid.Classifier.prepareClassifier()");
		}
		log.info(this.classifierType+"--com.anupam.hybrid.Classifier.prepareClassifier()");
		
		if(trainDataClassifiedLOC!=null || trainDataNtClassifiedLOC!=null){
			/*
			 * Task 1: Read Training dataset.
			 */	
			ArrayList<email> emailsReadClassifiedTrue=ReadLearningEmails.readLearningMails(trainDataClassifiedLOC);
			ArrayList<email> emailsReadClassifiedFalse=ReadLearningEmails.readLearningMails(trainDataNtClassifiedLOC);
			if(Constants.isDebug){
				System.out.println(this.classifierType+"--Number of emails read Classified True"+emailsReadClassifiedTrue.size());
				System.out.println(this.classifierType+"--Number of emails read Classified False"+emailsReadClassifiedFalse.size());
			}
			log.info(this.classifierType+"--Number of emails read Classified True"+emailsReadClassifiedTrue.size());
			log.info(this.classifierType+"--Number of emails read Classified False"+emailsReadClassifiedFalse.size());
			
			/*
			 * 
			 * Task 3: Find the most frequent words, in the emails read and 
			 * prepare the input for NBTree
			 */
			this.objTrainInstaceMaker=new AttributeChooser(this.classifierType);
			this.objTrainInstaceMaker.fillMaps(emailsReadClassifiedTrue,emailsReadClassifiedFalse);
			this.reesults=this.objTrainInstaceMaker.prepareTrainingSet();
			if(Constants.isDebug){
				System.out.println(this.classifierType+"--Training Set Instances"+reesults.size());
			}
			log.info(this.classifierType+"--Training Set Instances"+reesults.size());
		}else{
			System.out.println(this.classifierType+"--Error!!!!");
			log.error(this.classifierType+"--trainDataClassifiedLOC is null or trainDataNtClassifiedLOC is null");
		}
		
		if(Constants.isDebug){//If debugging is on, then PRint on Console
			System.out.println(this.classifierType+"--com.anupam.hybrid.Classifier.prepareClassifier() ends");
		}
		log.info(this.classifierType+"--com.anupam.hybrid.Classifier.prepareClassifier() ends");
	}
	
	/**
	 * Function: classify
	 * Returns True is classified successfully else False
	 */	
	public Decision classify(String testFileName){
		if(Constants.isDebug){//If debugging is on, then PRint on Console
			System.out.println(this.classifierType+"Classify called()");
		}
		log.info(this.classifierType+"Classify called()");
		
		/*
		 * Creating The TEst String
		 */
		email emailTest=ReadLearningEmails.readLearningMail(Constants.TestDataLocation+"//"+testFileName);
		if(emailTest==null){
			
			System.out.println("Can't read the contents of Test Instance:");
			System.out.println("returning null");
			
			log.error("*******-------------------*******************-----------------*******");
			log.error("Can't read the contents of Test Instance:");
			log.error("returning null");
			
			return null;
		}
		ArrayList<String> TestInstance=this.objTrainInstaceMaker.getTestInstance(emailTest);
		if(TestInstance==null){
			
			System.out.println("Can't read the contents of Test Instance:");
			System.out.println("returning false");
			
			log.error("*******-------------------*******************-----------------*******");
			log.error("Can't read the contents of Test Instance:");
			log.error("returning null");
			
			return null;
		}
		if(TestInstance.size()==0){
			System.out.println("No word in test matches the knowledge base");
			System.out.println("returning false");
			
			log.error("*******-------------------*******************-----------------*******");
			log.error("No word in test matches the knowledge base");
			log.error("returning null");
			
			
			return null;
		}
		if(Constants.isDebug){
			System.out.println("Test Instance :"+TestInstance.size());
			for(String tmp:TestInstance){
				System.out.print(","+tmp);
			}
		}
		/*
		 * 
		 * Calling the NBTree Algo
		 */
		NBTree objNBTree=new NBTree();
		MatrixData inputNBTree;
		try {
			inputNBTree = new MatrixData(this.reesults);
			//inputNBTree.printMatrix();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		objNBTree.setMatrixSource(inputNBTree);
		this.classifiedAs=objNBTree.classifyInstance(TestInstance);
		
		if(Constants.isDebug){
			System.out.println(this.classifierType+"--Decision"+this.classifiedAs);	
			System.out.println(this.classifierType+"--Probability"+objNBTree.dcsn.Prob);	
		}
		
		
		if(Constants.isDebug){//If debugging is on, then PRint on Console
			System.out.println(this.classifierType+"--Classify end()");
		}
		log.info(this.classifierType+"--Classify end()");
		return objNBTree.dcsn;
	}

}
