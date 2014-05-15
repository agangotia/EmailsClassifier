package com.anupam.hybrid.classifier.nbtree;

import java.util.ArrayList;
import java.util.Iterator;

import com.anupam.Constants;
import com.anupam.hybrid.classifier.naivebayes.Decision;
import com.anupam.hybrid.classifier.naivebayes.NaiveBayes;



/*
 * NBTree Algorithm
 * https://www.google.com/patents/US6182058
 * 
 * How to use
 * 1.Create an object
 * 2.call readSource(String fileName)
 * remember filename is a csv file
 * 3.call classifyInstance(ArrayList<String> TestInstance)
 * which returns true or false
 */
public class NBTree {
	private MatrixData matrixSource;
	public Decision dcsn;
	
	public NBTree(){
		dcsn=new Decision();
	}
	public void readSource(String FileNameToRead){
		// read matrixSource
		matrixSource=new MatrixData();
		matrixSource.prepareMatrix(FileNameToRead, 100);
		//matrixSource.printMatrix();
	}
	
	public boolean classifyInstance(ArrayList<String> TestInstance){
		dcsn=NBTreeClassifier(matrixSource,TestInstance);
		return dcsn.classification;
	}
	
	public Decision NBTreeClassifier(MatrixData matrixData,ArrayList<String> TestInstance){
				
				if(matrixData==null){
					System.out.println("Fatal Exception");
					return null;
				}
				//Step 1
				//Estimate Naive Bayes , to calculate A
				NaiveBayes nbNode=new NaiveBayes(matrixData);
				if(Constants.showNaiveBayes){
					matrixData.printMatrix();
				}
				Decision a=nbNode.getProbClassified(TestInstance);
				boolean initialClassification=a.classification;
				if(Constants.showNaiveBayes){
					System.out.println("Initial Classification"+initialClassification);
					System.out.println("Value"+a.Prob);	
				}
				
				
				
				if (Double.isNaN(a.Prob)){
					System.out.println("Error");
					return a;
				}
				
				if(TestInstance.size()<2){
					return a;
				}
					
				
				//Step 2
				//for each attribute
				ArrayList<Double> pValues=new ArrayList<Double>();
				ArrayList<String> pAtributes=new ArrayList<String>();
				for(String atr:matrixData.Headers){
					if(!atr.equals("class")){
						
						
						//Step 2.1
						//Split Matrix for each attribute in test
						MatrixData matrixSPlit=matrixData.splitMatrix(atr, 1);
						if(matrixSPlit==null || matrixSPlit.Numrows==0 || matrixSPlit.coloumns==2){
							continue;
						}
						
						//step 2.2 
						//Remove this attribure from TestInstance
						ArrayList<String> testInstanceSplit=new ArrayList<String>(TestInstance);
						testInstanceSplit.remove(atr);
						removeAtribute(testInstanceSplit,atr);
						
						//Step 2.3
						//For Each (Xi), in sample, Find Max(Ui)=B
						NaiveBayes nbNodeSplit=new NaiveBayes(matrixSPlit);
						Decision xi=nbNodeSplit.getProbClassified(testInstanceSplit);
						
						
						
						if(!Double.isNaN(xi.Prob) && xi.classification==initialClassification){
							if(Constants.showNaiveBayes){
								matrixSPlit.printMatrix();
								System.out.println("removing"+atr);
								System.out.println("Classification"+xi.classification);
								System.out.println("Probability"+xi.Prob);	
							}
							
							pValues.add(xi.Prob);
							pAtributes.add(atr);
						}
						
					}
					
				}
			
				double maxPVal;
				String maxAtr;
				if(pValues.size()==0){
					return a;
				}else if(pValues.size()==1){
					maxPVal=pValues.get(0);
					maxAtr=pAtributes.get(0);
				}else{
					maxPVal=pValues.get(0);
					maxAtr=pAtributes.get(0);
					for(int i=1;i<pValues.size();i++){
						if(pValues.get(i)>maxPVal){
							maxPVal=pValues.get(i);
							maxAtr=pAtributes.get(i);
						}
					}
					
				}
				//Find the max
				if(a.Prob>maxPVal){
					return a;
				}
				if(Constants.showNaiveBayes){
					System.out.println("*******************************");
					System.out.println("*********MAx"+maxPVal+"for atribute"+maxAtr);
				}
				
				
				//Step 4
				//else call this recursively with trimmed Matrix
				MatrixData matrixSPlit=matrixData.splitMatrix(maxAtr, 1);
				if(matrixSPlit==null || matrixSPlit.Numrows==0 || matrixSPlit.coloumns==2){
					return a;
				}
				
				//Remove this attribure from TestInstance
				ArrayList<String> testInstanceSplit=new ArrayList<String>(TestInstance);
				testInstanceSplit.remove(maxAtr);
				removeAtribute(testInstanceSplit,maxAtr);
				
				if(testInstanceSplit.size()<2){
					return a;
				}
				return NBTreeClassifier(matrixSPlit,TestInstance);
		
	}
	
	public void removeAtribute(ArrayList<String> testInstanceSplit,String atr){
		Iterator<String> it=testInstanceSplit.iterator();
		while(it.hasNext()){
			String s=(String)it.next();
			if(atr.equals(s)){
				it.remove();
			}
		}
	} 
	
	public MatrixData getMatrixSource() {
		return matrixSource;
	}

	public void setMatrixSource(MatrixData matrixSource) {
		this.matrixSource = matrixSource;
	}


}
