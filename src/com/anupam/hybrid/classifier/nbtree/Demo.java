package com.anupam.hybrid.classifier.nbtree;

import java.util.ArrayList;

import com.anupam.Constants;

public class Demo {

	public static void main(String[] args) {
		
		ArrayList<String> TestInstance=new ArrayList<String>();
		TestInstance.add("hello");
		TestInstance.add("porn");
		if(Constants.isDebug){
			System.out.println("Test Instance");
			for(String temp:TestInstance){
				System.out.println(temp);
			}
			System.out.println(" ");
		}
		// TODO Auto-generated method stub
		NBTree objNBTree=new NBTree();
		
		objNBTree.readSource("data\\SpamTrain.csv");
		//objNBTree.readSource("data\\train.dat");
		
		System.out.println(objNBTree.classifyInstance(TestInstance));
	}

}
