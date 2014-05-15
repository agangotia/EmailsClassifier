EmailsClassifier
================

Email Classifier based on supervised Machine Learning (NBTree and Multinomial Naive bayes Implementation)

Very Imp : Please Download "stanford-corenlp-3.3.1-models.jar"(http://nlp.stanford.edu/software/stanford-corenlp-full-2014-01-04.zip)
As i was not able to upload this jar, because of size limit.
Download and copy it inside lib folder.
Then you should be able to Build this project,

-------------------------------------------------------------------------
Instructions on how to build and run Email Classifier:
-------------------------------------------------------------------------
There are two ways to run 
1.In ecclipse
	a).Import the folder "HybridEmailClassifier", inside ecclipse.
	b).To do this, inside package explorer of eclipse right click and select import.
	c).Under General , select existing Projects into workspace.
	d).do check copy projects into existing workspace.
	e).Once the project is imported, right click project and click on "Run configuration" inside Run as options.
	f).In the arguments tab, make sure your are passing "production" as Program Arguments.
	g).Finally run the com.anupam.hybrid.HybridMain class. This has the main method.
2.Using Ant
	a).You should have ant installed and configured.
	b).Open cmd prompt.
	c).cd to folder "HybridEmailClassifier".
	d).compile the source code by simply typing ant in cmd prompt.
	e).This compiles the files and generates a folder "build".
	f).Now run the program by typing ant HybridMain.
