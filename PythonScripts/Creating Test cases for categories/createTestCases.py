#!/usr/bin/python

#print ("Hello World") 

#name of file to read structure from 
FileStructure="SampleFile.eml"

f = open(FileStructure, 'r')
listfStructure=f.readlines()#read all the lines in list
f.close()

#file name to read data from
FILENAME="inputFile.txt"
#output file
FILEWRITE="output/test_"
FILEEXTENSION=".eml"
i=1
f2 = open(FILENAME, 'r')
listfcontent=f2.readlines()
for row in listfcontent:
	print (row)
	#Now For Each Row create a new file
	target = open(FILEWRITE+str(i)+FILEEXTENSION, 'w')
	for temp in listfStructure:
		if "insert data here" in temp.lower():
			target.write(row)
		else:
			target.write(temp)
	target.close()
	i=i+1
f2.close()

