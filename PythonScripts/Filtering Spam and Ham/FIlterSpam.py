#!/usr/bin/python
import shutil


DIRECTORYWRITE='train_spam'
DIRECTORYLOOKUP='TRAINING'
FILENAME='FileList_Spam.txt'
f = open(FILENAME, 'r')
#print (f.read())
listfnames=f.readlines()
for row in listfnames:
	#File NAme to read
	#print (row)  
	shutil.copyfile(DIRECTORYLOOKUP+'\\'+row.rstrip(),DIRECTORYWRITE+'\\'+row.rstrip())
print ("complete")
f.close()