package com.anupam.hybrid.classifier.SNLP;


import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

/**
 * Class: PreProcess
 * Makes Use of Stanford CORENLP library
 * http://nlp.stanford.edu/software/corenlp.shtml#Download
 * 
 * It is used for
 * tokenize and lemmatize
 * */
public class PreProcess {

	private static StanfordCoreNLP pipeline;
	private static Properties props;
	/**
	 * Static Initializer Block
	 * As we dont want it to be loaded again and again.
	 * But only once
	 * */
	static {
		 props = new Properties();
	        props.put("annotators", "tokenize, ssplit, pos, lemma");

	        /*
	         * This is a pipeline that takes in a string and returns various analyzed linguistic forms. 
	         * The String is tokenized via a tokenizer (such as PTBTokenizerAnnotator), 
	         * and then other sequence model style annotation can be used to add things like lemmas, 
	         * POS tags, and named entities. These are returned as a list of CoreLabels. 
	         * Other analysis components build and store parse trees, dependency graphs, etc. 
	         * 
	         * This class is designed to apply multiple Annotators to an Annotation. 
	         * The idea is that you first build up the pipeline by adding Annotators, 
	         * and then you take the objects you wish to annotate and pass them in and 
	         * get in return a fully annotated object.
	         * 
	         *  StanfordCoreNLP loads a lot of models, so you probably
	         *  only want to do this once per execution
	         */
	        
	        pipeline= new StanfordCoreNLP(props);
	}
	
	 public static List<String> lemmatize(String documentText)
	    {
		 if(documentText==null || documentText.length()==0){
			 return null;
		 }
	        List<String> lemmas = new LinkedList<String>();
	        String result = documentText.replaceAll("[\\-\\+\\^\\<\\>\\;\\#,]","");
	        String lowerResult = result.toLowerCase();
	        // Create an empty Annotation just with the given text
	        Annotation document = new Annotation(lowerResult);
	        
	        // run all Annotators on this text
	        pipeline.annotate(document);
	        // Iterate over all of the sentences found
	        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
	        for(CoreMap sentence: sentences) {
	            // Iterate over all tokens in a sentence
	            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
	                // Retrieve and add the lemma for each word into the
	                // list of lemmas
	            	 if(PartsOfSpeech.mapPartsOfSpeech.containsKey(token.tag()) && !StopWords.mapStopWords.containsKey(token.originalText()) && token.originalText().length()>2 && token.originalText().length()<50 && token.get(LemmaAnnotation.class).length()>2){
	   	    		  //System.out.println("Adding -"+temp.tag());
	            		 lemmas.add(token.get(LemmaAnnotation.class));
	            		  /*System.out.println(token.tag());
	  	                System.out.println(token.originalText());
	  	                System.out.println(token.lemma());*/
	   	    	  }
	                
	              
	            }
	        }
	        return lemmas;
	    }
	 
	
	 
	 public static void main(String[] args) {
	        System.out.println("Starting Stanford Lemmatizer");
	        String text = "How could you be seeing into my eyes like open doors? \n"+
	                "You led me down into my core where I've became so numb \n"+
	                "Without a soul my spirit's sleeping somewhere cold \n"+
	                "Until you find it there and led it back home \n"+
	                "You woke me up inside \n"+
	                "Called my name and saved me from the dark \n"+
	                "You have bidden my blood and it ran \n"+
	                "Before I would become undone \n"+
	                "You saved me from the nothing I've almost become \n"+
	                "You were bringing me to life \n"+
	                "Now that I knew what I'm without \n"+
	                "You can've just left me \n"+
	                "You breathed into me and made me real \n"+
	                "Frozen inside without your touch \n"+
	                "Without your love, darling \n"+
	                "Only you are the life among the dead \n"+
	                "I've been living a lie, there's nothing inside \n"+
	                "You were bringing me to life.";
	        
	       // System.out.println(PreProcess.lemmatize(text));
	        System.out.println(PreProcess.lemmatize("i was planning to go out;\"<>"));
	        //System.out.println(PreProcess.lemmatize("alt atheism faq atheist resources archive name atheism resources alt atheism archive name resources last"));
	    }
}
