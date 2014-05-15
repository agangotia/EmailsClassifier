package com.anupam.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.anupam.Constants;
import com.anupam.email.ReadLearningEmails;
/**
 * @author Anupam Gangotia
 * Profile::http://en.gravatar.com/gangotia
 * github::https://github.com/agangotia
 */
import com.anupam.email.email;

/**
 * Class::FileRead This is the helper class for reading files.
 */
public class FileRead {

	private static org.apache.log4j.Logger log = Logger
			.getLogger(ReadLearningEmails.class);

	/**
	 * Function:getFileNamesInDirectory
	 * 
	 * @param String
	 *            : the location where i want to search all the file names
	 *            present
	 * @return a list of Filenames in the given directory
	 */
	public static ArrayList<String> getFileNamesInDirectory(String location) {
		log.info("--Getting Files In directory " + location);
		if (Constants.isDebug) {
			System.out.println("--Getting Files In directory " + location);
		}
		ArrayList<String> listfileNames = new ArrayList<String>();
		File folder = new File(location);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				listfileNames.add(listOfFiles[i].getName());
			}
		}
		log.info("--" + listfileNames.size() + " Files Found in " + location);
		if (Constants.isDebug) {
			System.out.println("--" + listfileNames.size() + " Files Found in "
					+ location);
			System.out.println("");
		}
		return listfileNames;
	}

	/**
	 * readLearningMails
	 * 
	 * @param String
	 *            : the location where i want to read the files This reads all
	 *            the files inside the given directory.
	 * @return a list of String in the given directory
	 */
	public static ArrayList<String> readLearningData(String location) {

		// first get the filenames to read
		ArrayList<String> listfileNames = getFileNamesInDirectory(location);
		ArrayList<String> dataRead = new ArrayList<String>();

		BufferedReader br = null;
		for (String tempFile : listfileNames) {
			try {
				br = new BufferedReader(new FileReader(location + "\\"
						+ tempFile));
				String line = br.readLine();
				StringTokenizer st = new StringTokenizer(line, ",");
				while (line != null) {
					dataRead.add(line);
					line = br.readLine();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (br != null)
						br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return dataRead;
	}


	public static void updateFile(String location,String text) throws IOException {
		File file = new File(location);
		FileWriter fileWriter = new FileWriter(file, true);
		BufferedWriter bufferFileWriter = new BufferedWriter(fileWriter);
		fileWriter.append(System.getProperty("line.separator")+text);
		bufferFileWriter.close();
		log.info("Text Appended");
		log.info(text);
	}

	public static void updateFile(String location,ArrayList<String> text) throws IOException {
		File file = new File(location);
		FileWriter fileWriter = new FileWriter(file, true);
		BufferedWriter bufferFileWriter = new BufferedWriter(fileWriter);
		for(String s:text)
		fileWriter.append(System.getProperty("line.separator")+s);
		bufferFileWriter.close();
		log.info("Text Appended");
		log.info(text);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<String> test = readLearningData(Constants.ATHEISMTRAINDATALOC);
		System.out.println(test);
	}

}
