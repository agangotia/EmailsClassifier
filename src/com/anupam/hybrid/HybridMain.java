package com.anupam.hybrid;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.anupam.Constants;
import com.anupam.email.ReadLearningEmails;
import com.anupam.email.email;
import com.anupam.file.FileRead;
import com.anupam.hybrid.classifier.Classifier;
import com.anupam.hybrid.classifier.ClassifierType;
import com.anupam.hybrid.classifier.SNLP.PreProcess;
import com.anupam.hybrid.classifier.naivebayes.Decision;

import java.util.Scanner;

import static java.nio.file.StandardCopyOption.*;

/**
 * @author Anupam Gangotia
 * Profile::http://en.gravatar.com/gangotia
 * github::https://github.com/agangotia
 */

/**
 * Class: HybridMain
 * Email classifier that using a test data set 
 * classifies the emails into two following categories (Spam, Sports, Business, Technology and Entertainment)
 * */
public class HybridMain {

	private static org.apache.log4j.Logger log = Logger
			.getLogger(HybridMain.class);
	
	/**
	 * Function: main
	 * This is the start of the program.
	 * parameter 1: production
	 * 				test
	 * parameter 2: auto
	 * 				medical
	 * 				sports
	 * 				spam
	 * 				atheism
	 * production:If parameter 1 is production,
	 * The program will classify the test instances
	 * test		:	If it is test,
	 * then you can check classification of test
	 * instances into
	 * autos, medical,sports, spam, atheism
	 * */
	public static void main(String[] args) {
	
		if(args.length==1){
			if(args[0].equals("production")){
				log.info("----Hybrid Main Started");
				log.info("----Production Mode");
				log.info("----K(chooses these many frequent words)"+Constants.ChooseFrequent);
				log.info("----Accept User FeedBack"+Constants.ifFeedback);
				log.info("----(When testing for multiple test mails,User FeedBack should be false");
				
				System.out.println("----Hybrid Main Started");
				System.out.println("----Production Mode");
				System.out.println("----K(chooses these many frequent words)"+Constants.ChooseFrequent);
				System.out.println("----Accept User FeedBack"+Constants.ifFeedback);
				System.out.println("----(When testing for multiple test mails,User FeedBack should be false");
				
				init();
			}else{
				System.out.println("Invalid Parameter");
			}
			
		}else if(args.length==2){
			log.info("----Hybrid Main Started");
			log.info("----Test Mode");
			log.info("----Runs the Binary classification for Test using NBTree algorithm");
			log.info("----No User FeedBack available");
			
			
			log.info("----Hybrid Main Started");
			log.info("----Test Mode");
			log.info("----Runs the Binary classification for Test using NBTree algorithm");
			log.info("----No User FeedBack available");
			
			if(args[0].equals("test")){
				if(args[1].equals("atheism")){
					testATHEISM();
				}else if(args[1].equals("autos")){
					testAUTOS();
				}else if(args[1].equals("medical")){
					testMEDICAL();
				}else if(args[1].equals("sports")){
					testSPORTS();
				}else if(args[1].equals("spam")){
					testSPAM();
				}else{
					System.out.println("Invalid 2nd Parameter");
				}
			}else{
				System.out.println("Invalid Parameter");
			}
			
		}		
	}
	
	/**
	 * Function :  init()
	 * Actual Starting Point for Text Classifier Application.
	 * */
	public static void init() {
		
		int COUNTSPAM=0;
		int COUNTHAM=0;
		int COUNTAUTO=0;
		int COUNTMEDICAL=0;
		int COUNTSPORTS=0;
		int COUNTATHEISM=0;
		
		/*
		 * Task 1: Get FileNames In Test Directory
		 * */
		ArrayList<String> filesTest=ReadLearningEmails.getFileNamesInDirectory(Constants.TestDataLocation);
		
		/*
		 * Task 2: For each of the Test File, Classify the test on NBTRee algorithm 
		 * */
		
		ClassifierType classifierTypeSPAM=ClassifierType.SPAM;
		Classifier classifierSPAM=new Classifier(classifierTypeSPAM,Constants.SPAMTRAINDATALOC,Constants.GENERALTRAINDATALOC);
		classifierSPAM.prepareClassifier();
		
		//PREPARE ALL OTHER CLASSIFIERS
		ClassifierType classifierTypeATHEISM=ClassifierType.ATHEISM;
		Classifier classifierATHEISM=new Classifier(classifierTypeATHEISM,Constants.ATHEISMTRAINDATALOC,Constants.GENERALTRAINDATALOC);
		classifierATHEISM.prepareClassifierFromText();
		
		ClassifierType classifierTypeMEDICAL=ClassifierType.MEDICAL;
		Classifier classifierMEDICAL=new Classifier(classifierTypeMEDICAL,Constants.MEDICALTRAINDATALOC,Constants.GENERALTRAINDATALOC);
		classifierMEDICAL.prepareClassifierFromText();
		
		ClassifierType classifierTypeAUTOS=ClassifierType.AUTOS;
		Classifier classifierAUTOS=new Classifier(classifierTypeAUTOS,Constants.AUTOTRAINDATALOC,Constants.GENERALTRAINDATALOC);
		classifierAUTOS.prepareClassifierFromText();
		
		ClassifierType classifierTypeSPORTS=ClassifierType.SPORTS;
		Classifier classifierSPORTS=new Classifier(classifierTypeSPORTS,Constants.SPORTSTRAINDATALOC,Constants.GENERALTRAINDATALOC);
		classifierSPORTS.prepareClassifierFromText();
		
		for(String fileTest:filesTest){
			/*
			 * Task 2.1: First Classify for SPAM 
			 * */
			Decision dcsnSPAM=classifierSPAM.classify(fileTest);
			if(dcsnSPAM==null){
				continue;
			}else if(dcsnSPAM.classification==true){
				COUNTSPAM++;
				System.out.println("--Classified "+classifierTypeSPAM);
				System.out.println("--Classified Probability"+dcsnSPAM.Prob);
				log.info("--Classified "+classifierTypeSPAM);
				log.info("--Classified Probability"+dcsnSPAM.Prob);
				if(Constants.ifFeedback)
					provideFeedBack(fileTest);
				
			}else if(dcsnSPAM.classification==false){
				
				ArrayList<ClassifierType> listClassifierVal=new ArrayList<ClassifierType>();
				ArrayList<Double> listClassifierProbVal=new ArrayList<Double>();

				
				/*
				 * Task 2.2: Second Classify for Atheism 
				 * */
				{
					Decision dcsnAtheism=classifierATHEISM.classify(fileTest);
					if(dcsnAtheism!=null){
						if(Constants.showResult){
							System.out.println("--Checking  "+classifierTypeATHEISM);
							System.out.println("--Returned"+dcsnAtheism.classification);
							System.out.println("--Probability"+dcsnAtheism.Prob);
						}
						
						if(dcsnAtheism.classification==true){
							listClassifierVal.add(classifierTypeATHEISM);
							listClassifierProbVal.add(dcsnAtheism.Prob);
						}
					}
					
				}
				
				
				/*
				 * Task 2.3: Second Classify for MEDICAL 
				 * */
				{
					Decision dcsnMedical=classifierMEDICAL.classify(fileTest);
					if(dcsnMedical!=null){
						if(Constants.showResult){
							System.out.println("--Checking  "+classifierTypeMEDICAL);
							System.out.println("--Returned"+dcsnMedical.classification);
							System.out.println("--Probability"+dcsnMedical.Prob);
						}
						if(dcsnMedical.classification==true){
							listClassifierVal.add(classifierTypeMEDICAL);
							listClassifierProbVal.add(dcsnMedical.Prob);
						}
					}
					
				}
				
				/*
				 * Task 2.4: Second Classify for AUTOS 
				 * */
				{
					Decision dcsnAutos=classifierAUTOS.classify(fileTest);
					if(dcsnAutos!=null){
						if(Constants.showResult){
							System.out.println("--Checking  "+classifierTypeAUTOS);
							System.out.println("--Returned"+dcsnAutos.classification);
							System.out.println("--Probability"+dcsnAutos.Prob);
						}
						if(dcsnAutos.classification==true){
							listClassifierVal.add(classifierTypeAUTOS);
							listClassifierProbVal.add(dcsnAutos.Prob);
						}
					}
					
				}
				
				/*
				 * Task 2.4: Second Classify for SPORTS 
				 * */
				{
					Decision dcsnSports=classifierSPORTS.classify(fileTest);
					if(dcsnSports!=null){
						if(Constants.showResult){
							System.out.println("--Checking  "+classifierTypeSPORTS);
							System.out.println("--Returned"+dcsnSports.classification);
							System.out.println("--Probability"+dcsnSports.Prob);
						}
						if(dcsnSports.classification==true){
							listClassifierVal.add(classifierTypeSPORTS);
							listClassifierProbVal.add(dcsnSports.Prob);
						}
					}
					
				}
				
				{
					if(listClassifierProbVal.size()==1){
						System.out.println("--Classified "+listClassifierVal.get(0));
						System.out.println("--Classified Probability"+listClassifierProbVal.get(0));
						log.info("--Classified "+listClassifierVal.get(0));
						log.info("--Classified Probability"+listClassifierProbVal.get(0));
						ClassifierType finalClassifier=listClassifierVal.get(0);

						if(finalClassifier==ClassifierType.ATHEISM){
							COUNTATHEISM++;
						}else if(finalClassifier==ClassifierType.AUTOS){
							COUNTAUTO++;
						}
						else if(finalClassifier==ClassifierType.MEDICAL){
							COUNTMEDICAL++;
						}else if(finalClassifier==ClassifierType.SPORTS){
							COUNTSPORTS++;
						}
						
					}else if(listClassifierProbVal.size()>1){
						double maxval=listClassifierProbVal.get(0);
						int maxIndex=0;
						for(int i=1;i<listClassifierProbVal.size();i++){
							if(listClassifierProbVal.get(i)>maxval){
								maxval=listClassifierProbVal.get(i);
								maxIndex=i;
							}
						}
						
						ClassifierType finalClassifier=listClassifierVal.get(maxIndex);
						System.out.println("--Classified "+finalClassifier);
						System.out.println("--Classified Probability"+listClassifierProbVal.get(maxIndex));
						log.info("--Classified "+finalClassifier);
						log.info("--Classified Probability"+listClassifierProbVal.get(maxIndex));

						if(finalClassifier==ClassifierType.ATHEISM){
							COUNTATHEISM++;
						}else if(finalClassifier==ClassifierType.AUTOS){
							COUNTAUTO++;
						}
						else if(finalClassifier==ClassifierType.MEDICAL){
							COUNTMEDICAL++;
						}else if(finalClassifier==ClassifierType.SPORTS){
							COUNTSPORTS++;
						}
						
					}else{
						System.out.println("--Classified HAM");
						System.out.println("--Classified Probability"+dcsnSPAM.Prob);
						log.info("--Classified HAM");
						log.info("--Classified Probability"+dcsnSPAM.Prob);
						COUNTHAM++;
					}
				}
				
				if(Constants.ifFeedback)
					provideFeedBack(fileTest);
				
			}
			
		
		}
		
		System.out.println("\n\n--Results");
		System.out.println("--Classified Spam"+COUNTSPAM);
		System.out.println("--Classified Ham"+COUNTHAM);
		System.out.println("--Classified ATHEISM"+COUNTATHEISM);
		System.out.println("--Classified AUTO"+COUNTAUTO);
		System.out.println("--Classified MEDICAL"+COUNTMEDICAL);
		System.out.println("--Classified SPORTS"+COUNTSPORTS);
		
		log.info("\n\n--Results");
		log.info("--Classified Spam"+COUNTSPAM);
		log.info("--Classified Ham"+COUNTHAM);
		log.info("--Classified ATHEISM"+COUNTATHEISM);
		log.info("--Classified AUTO"+COUNTAUTO);
		log.info("--Classified MEDICAL"+COUNTMEDICAL);
		log.info("--Classified SPORTS"+COUNTSPORTS);
	}
	
	
	/**
	 * Function :  provideFeedBack()
	 * Function that gets the user FeedBack 
	 * */
	public static void provideFeedBack(String fileTest){
		
		while(true){
			System.out.println("Do you want think the Categorization is wrong ?[Y/N]");
			System.out.println("Please provide your feedback , so that we can improve our knowledge bank.");
			System.out.println("And next time you get more accurate results");
			 
			String sWhatever;
			Scanner scanIn = new Scanner(System.in);
		    sWhatever = scanIn.nextLine();
		    if(sWhatever.equals("Y")){
		    	while(true){
		    	
		    		System.out.println("Please enter the integer, that matches the correct class");
					System.out.println("1.Spam");
					System.out.println("2.Not Spam");
					System.out.println("3.Atheism");
					System.out.println("4.Medical");
					System.out.println("5.Autos");
					System.out.println("6.Sports");
					
					scanIn = new Scanner(System.in);
				    sWhatever = scanIn.nextLine();
				    if(sWhatever.equals("1")||sWhatever.equals("2")||sWhatever.equals("3")||sWhatever.equals("4")||sWhatever.equals("5")){
				    	int y = Integer.parseInt(sWhatever);
				    	switch (y) {
				    	case 1:	System.out.println("1.Spam");
				    			improveKnowledgeSimpleCopy(Constants.TestDataLocation+"\\"+fileTest,Constants.SPAMTRAINDATALOC+"\\"+fileTest);
				    			break;
				    	case 2:	System.out.println("2.Not Spam");
				    			improveKnowledgeSimpleCopy(Constants.TestDataLocation+"\\"+fileTest,Constants.GENERALTRAINDATALOC+"\\"+fileTest);
		    					break;
				    	case 3:	System.out.println("3.Atheism");
				    			ArrayList<String> listFName=FileRead.getFileNamesInDirectory(Constants.ATHEISMTRAINDATALOC);
				    			improveKnowledge(Constants.TestDataLocation+"\\"+fileTest,Constants.ATHEISMTRAINDATALOC+"\\"+listFName.get(0));
    							break;
				    	case 4:	System.out.println("4.Medical");
				    			ArrayList<String> listFName1=FileRead.getFileNamesInDirectory(Constants.MEDICALTRAINDATALOC);
				    			improveKnowledge(Constants.TestDataLocation+"\\"+fileTest,Constants.MEDICALTRAINDATALOC+"\\"+listFName1.get(0));
								break;
				    	case 5:	System.out.println("5.Autos");
				    			ArrayList<String> listFName2=FileRead.getFileNamesInDirectory(Constants.AUTOTRAINDATALOC);
				    			improveKnowledge(Constants.TestDataLocation+"\\"+fileTest,Constants.AUTOTRAINDATALOC+"\\"+listFName2.get(0));
								break;
				    	case 6:	System.out.println("6.Sports");
				    			ArrayList<String> listFName3=FileRead.getFileNamesInDirectory(Constants.SPORTSTRAINDATALOC);
				    			improveKnowledge(Constants.TestDataLocation+"\\"+fileTest,Constants.SPORTSTRAINDATALOC+"\\"+listFName3.get(0));
								break;
				    	}
				    	System.out.println("Feedback Saved");
				    	log.info("Feedback Saved");
				    	break;
				    }else{
				    	System.out.println("Invalid Input");
				    }
				    
		    	}
		    	break;
			    
		    }else if(sWhatever.equals("N")){
		    	System.out.println("Thanks!..");
		    	break;
		    }else{
		    	System.out.println("Invalid Input");
		    }
		    scanIn.close();            
		    
		}
		
	}
	
	
	/**
	 * Function :  improveKnowledge()
	 * Function that copies email files from test to train location
	 * Usefull for getting feedbackfrom user 
	 * */
	public static void improveKnowledgeSimpleCopy(String inputTestPath,String outputTestPath){
		Path p1 = Paths.get(inputTestPath);
		Path p2 = Paths.get(outputTestPath);
		
		try {
			Files.copy(p1, p2, REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error Improving knowledgebank");
			System.out.println("unable to save your feedback");
			log.error("Error Improving knowledgebank");
			log.error("unable to save your feedback");
		}
	} 
	
	/**
	 * Function :  improveKnowledge()
	 * Function that copies email files from test to train location
	 * Usefull for getting feedbackfrom user 
	 * */
	public static void improveKnowledge(String inputTestPath,String outputTestPath){
		
		
		email emailTest=ReadLearningEmails.readLearningMail(inputTestPath);
		
		String source = null;
		if (emailTest.getSubject() != null)
			source += emailTest.getSubject();
		if (emailTest.getContent() != null)
			source += emailTest.getContent();
		if (source == null){
			System.out.println("Unable to read from Test file"+inputTestPath);
			System.out.println("Error Improving knowledgebank");
			System.out.println("unable to save your feedback");
			log.error("Unable to read from Test file"+inputTestPath);
			log.error("Error Improving knowledgebank");
			log.error("unable to save your feedback");
			return ;
		}
			
		List<String> listWords = PreProcess.lemmatize(source);
		if(listWords==null || listWords.size()==0){
			System.out.println("Unable to read from Test file"+inputTestPath);
			System.out.println("Error Improving knowledgebank");
			System.out.println("unable to save your feedback");
			log.error("Unable to read from Test file"+inputTestPath);
			log.error("Error Improving knowledgebank");
			log.error("unable to save your feedback");
			return ;
		}
		StringBuffer str = new StringBuffer();
		for(String s:listWords){
			str.append(s+" ");
		}
		try {
			FileRead.updateFile(outputTestPath, str.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error Improving knowledgebank");
			System.out.println("unable to save your feedback");
			log.error("Error Improving knowledgebank");
			log.error("unable to save your feedback");
		}
	} 
	
	/**
	 * Function :  testATHEISM()
	 * Sample Function to Check how our Text Classifier 
	 * works for filtering category ATHEISM
	 * */
	public static void testATHEISM() {
		ArrayList<String> filesTest=ReadLearningEmails.getFileNamesInDirectory(Constants.TestDataLocation);

		ClassifierType classifierTypeATHEISM=ClassifierType.ATHEISM;
		Classifier classifierATHEISM=new Classifier(classifierTypeATHEISM,Constants.ATHEISMTRAINDATALOC,Constants.GENERALTRAINDATALOC);
		classifierATHEISM.prepareClassifierFromText();
		
		int COUNTTRUE=0;
		int COUNTFALSE=0;
		int trigger=0;
		for(String fileTest:filesTest){
			trigger++;
			Decision dcsnAtheism=classifierATHEISM.classify(fileTest);
			//System.out.println("\n\n--Results");
			//System.out.println("--Classified "+ClassifierType.ATHEISM+" "+dcsnAtheism.classification);
			//System.out.println("--Classified Spam"+ClassifierType.ATHEISM+" "+dcsnAtheism.Prob);
			if(dcsnAtheism!=null){
				if(dcsnAtheism.classification==true){
					COUNTTRUE++;
				}else{
					COUNTFALSE++;
				}
			}
			if(trigger==40){
				
				System.out.println("--Classified "+COUNTTRUE);
				System.out.println("--Classified False"+COUNTFALSE);
				trigger=0;
			}
			
		}
		System.out.println("\n\n--Final Results");
		System.out.println("--Classified "+ClassifierType.ATHEISM+" "+COUNTTRUE);
		System.out.println("--Classified False"+ClassifierType.ATHEISM+" "+COUNTFALSE);
	}
	
	/**
	 * Function :  testAUTOS()
	 * Sample Function to Check how our Text Classifier 
	 * works for filtering category AUTOS
	 * */
	public static void testAUTOS() {
		ArrayList<String> filesTest=ReadLearningEmails.getFileNamesInDirectory(Constants.TestDataLocation);

		ClassifierType classifierTypeAutos=ClassifierType.AUTOS;
		Classifier classifierAutos=new Classifier(classifierTypeAutos,Constants.AUTOTRAINDATALOC,Constants.GENERALTRAINDATALOC);
		classifierAutos.prepareClassifierFromText();

		int COUNTTRUE=0;
		int COUNTFALSE=0;
		int trigger=0;
		for(String fileTest:filesTest){
			trigger++;
			Decision dcsnAutos=classifierAutos.classify(fileTest);
			//System.out.println("\n\n--Results");
			//System.out.println("--Classified "+ClassifierType.AUTOS+" "+dcsnSPAM2.classification);
			//System.out.println("--Classified Spam"+ClassifierType.AUTOS+" "+dcsnSPAM2.Prob);
			if(dcsnAutos!=null){
				if(dcsnAutos.classification==true){
					COUNTTRUE++;
				}else{
					COUNTFALSE++;
				}
			}
			if(trigger==40){
				
				System.out.println("--Classified "+COUNTTRUE);
				System.out.println("--Classified False"+COUNTFALSE);
				trigger=0;
			}
		}
		System.out.println("\n\n--Final Results");
		System.out.println("--Classified "+ClassifierType.AUTOS+" "+COUNTTRUE);
		System.out.println("--Classified False"+ClassifierType.AUTOS+" "+COUNTFALSE);
	}
	
	/**
	 * Function :  testMEDICAL()
	 * Sample Function to Check how our Text Classifier 
	 * works for filtering category Medical
	 * */
	public static void testMEDICAL() {
		ArrayList<String> filesTest=ReadLearningEmails.getFileNamesInDirectory(Constants.TestDataLocation);

		ClassifierType classifierTypeMedical=ClassifierType.MEDICAL;
		Classifier classifierMedical=new Classifier(classifierTypeMedical,Constants.MEDICALTRAINDATALOC,Constants.GENERALTRAINDATALOC);
		classifierMedical.prepareClassifierFromText();

		int COUNTTRUE=0;
		int COUNTFALSE=0;
		int trigger=0;
		for(String fileTest:filesTest){
			trigger++;
			Decision dcsnMedical=classifierMedical.classify(fileTest);
			//System.out.println("\n\n--Results");
			//System.out.println("--Classified "+ClassifierType.MEDICAL+" "+dcsnSPAM2.classification);
			//System.out.println("--Classified Spam"+ClassifierType.MEDICAL+" "+dcsnSPAM2.Prob);
			if(dcsnMedical!=null){
				if(dcsnMedical.classification==true){
					COUNTTRUE++;
				}else{
					COUNTFALSE++;
				}
			}
			if(trigger==40){
				
				System.out.println("--Classified "+COUNTTRUE);
				System.out.println("--Classified False"+COUNTFALSE);
				trigger=0;
			}
		}
		
		System.out.println("\n\n--Final Results");
		System.out.println("--Classified "+ClassifierType.MEDICAL+" "+COUNTTRUE);
		System.out.println("--Classified False"+ClassifierType.MEDICAL+" "+COUNTFALSE);
	}
	
	/**
	 * Function :  testSPORTS()
	 * Sample Function to Check how our Text Classifier 
	 * works for filtering category SPORTS
	 * */
	public static void testSPORTS() {
		ArrayList<String> filesTest=ReadLearningEmails.getFileNamesInDirectory(Constants.TestDataLocation);

		ClassifierType classifierTypeSports=ClassifierType.SPORTS;
		Classifier classifierSports=new Classifier(classifierTypeSports,Constants.SPORTSTRAINDATALOC,Constants.GENERALTRAINDATALOC);
		classifierSports.prepareClassifierFromText();

		int COUNTTRUE=0;
		int COUNTFALSE=0;
		int trigger=0;
		for(String fileTest:filesTest){
			trigger++;
			Decision dcsnSports=classifierSports.classify(fileTest);
			//System.out.println("\n\n--Results");
			//System.out.println("--Classified "+ClassifierType.SPORTS+" "+dcsnSPAM2.classification);
			//System.out.println("--Classified Spam"+ClassifierType.SPORTS+" "+dcsnSPAM2.Prob);
			if(dcsnSports!=null){
				if(dcsnSports.classification==true){
					COUNTTRUE++;
				}else{
					COUNTFALSE++;
				}
			}
			if(trigger==40){
				
				System.out.println("--Classified "+COUNTTRUE);
				System.out.println("--Classified False"+COUNTFALSE);
				trigger=0;
			}
		}
		System.out.println("\n\n--Final Results");
		System.out.println("--Classified "+ClassifierType.SPORTS+" "+COUNTTRUE);
		System.out.println("--Classified False"+ClassifierType.SPORTS+" "+COUNTFALSE);
	}
	
	
	/**
	 * Function :  testSPAM()
	 * Sample Function to Check how our Text Classifier 
	 * works for filtering category SPAM
	 * */
	public static void testSPAM() {
		ArrayList<String> filesTest=ReadLearningEmails.getFileNamesInDirectory(Constants.TestDataLocation);
		
		ClassifierType classifierTypeSPAM=ClassifierType.SPAM;
		Classifier classifierSPAM=new Classifier(classifierTypeSPAM,Constants.SPAMTRAINDATALOC,Constants.GENERALTRAINDATALOC);
		classifierSPAM.prepareClassifier();


		int COUNTTRUE=0;
		int COUNTFALSE=0;
		int trigger=0;
		for(String fileTest:filesTest){
			trigger++;
			Decision dcsnSPAM2=classifierSPAM.classify(fileTest);
			//System.out.println("\n\n--Results");
			//System.out.println("--Classified "+ClassifierType.SPAM+" "+dcsnSPAM2.classification);
			//System.out.println("--Classified Spam"+ClassifierType.SPAM+" "+dcsnSPAM2.Prob);
			if(dcsnSPAM2!=null){
				if(dcsnSPAM2.classification==true){
					COUNTTRUE++;
				}else{
					COUNTFALSE++;
				}
			}
			if(trigger==40){
				
				System.out.println("--Classified "+COUNTTRUE);
				System.out.println("--Classified False"+COUNTFALSE);
				trigger=0;
			}
		}
		System.out.println("\n\n--Final Results");
		System.out.println("--Classified "+ClassifierType.SPAM+" "+COUNTTRUE);
		System.out.println("--Classified False"+ClassifierType.SPAM+" "+COUNTFALSE);
	}
}
