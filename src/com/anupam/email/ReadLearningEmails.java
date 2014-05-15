package com.anupam.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;

import com.anupam.Constants;
/**
 * @author Anupam Gangotia
 * Profile::http://en.gravatar.com/gangotia
 * github::https://github.com/agangotia
 */


/**
 * Class::ReadLearningEmails
 * This is the helper class for reading emails from eml files.
 */
public class ReadLearningEmails {
	private static org.apache.log4j.Logger log = Logger
			.getLogger(ReadLearningEmails.class);

	/**
	 * Function:getFileNamesInDirectory
	 * @param String : the location where i want to search all the file names present
	 * @return a list of Filenames in the given directory
	 */
	public static ArrayList<String> getFileNamesInDirectory(String location) {
		log.info("--Getting Files In directory "+location);
		if (Constants.isDebug) {
			System.out.println("--Getting Files In directory "+location);
		}
		ArrayList<String> listfileNames = new ArrayList<String>();
		File folder = new File(location);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				listfileNames.add(listOfFiles[i].getName());
			}
		}
		log.info("--"+listfileNames.size()+ " Files Found in "+location);
		if (Constants.isDebug) {
			System.out.println("--"+listfileNames.size()+ " Files Found in "+location);
			System.out.println("");
		}
		return listfileNames;
	}

	/**
	 * readLearningMails
	 * @param String : the location where i want to read the mails
	 * This reads all the emails inside the given directory.
	 * @return a list of email in the given directory
	 */
	public static ArrayList<email> readLearningMails(String location) {
		
		// first get the filenames to read
		ArrayList<String> listfileNames = getFileNamesInDirectory(location);
		ArrayList<email> emailsRead=new ArrayList<email>(); 
		
		for(String tempFile:listfileNames){
			email objEmail=readLearningMail(location+"\\"+tempFile);
			if(objEmail!=null)
				emailsRead.add(objEmail);
		}
		return emailsRead;
	}
	
	
	/**
	 * readLearningMail
	 * @param String : The .eml file to be read
	 * This reads the .eml file into the memory
	 * @return an email object
	 */
public static email readLearningMail(String fileNameWithPath) {

			InputStream is;
			email objEmail=new email(); 
			try {
				log.info("Reading File: " + fileNameWithPath);
				if(Constants.showEmailRead)
					System.out.print("Reading File: " +fileNameWithPath);
				is = new FileInputStream(fileNameWithPath);

				Properties props = System.getProperties();
				props.put("mail.host", "smtp.gmail.com");
				props.put("mail.transport.protocol", "smtp");
				Session mailSession = Session.getDefaultInstance(props, null);

				MimeMessage message = new MimeMessage(mailSession, is);
				if(message.getSubject()!=null){
					if(Constants.showEmailRead)
						System.out.println("Subject : " + message.getSubject());
					//log.info("Subject : " + message.getSubject());
					objEmail.setSubject(message.getSubject());
				}
				
				if(message.getFrom()!=null){
					if(Constants.showEmailRead)
						System.out.println("From : " + message.getFrom()[0]);
					//log.info("From : " + message.getFrom()[0]);
					objEmail.setSender(message.getFrom()[0].toString());
				}
				
				if(message.getRecipients(Message.RecipientType.CC)!=null){
					if(Constants.showEmailRead)
						System.out.println("Cc : Found" +message.getRecipients(Message.RecipientType.CC).length);
					//log.info("Cc : Found" +message.getRecipients(Message.RecipientType.CC).length);
					objEmail.setCc(message.getRecipients(Message.RecipientType.CC));
				}
				
				if(message.getRecipients(Message.RecipientType.BCC)!=null){
					if(Constants.showEmailRead)
						System.out.println("BCc : FOund" +message.getRecipients(Message.RecipientType.BCC).length);
					//log.info("BCc : FOund" +message.getRecipients(Message.RecipientType.BCC).length);
					objEmail.setBcc(message.getRecipients(Message.RecipientType.BCC));
				}
				
				if(message.getSentDate()!=null){
					if(Constants.showEmailRead)
						System.out.println("Date : " +message.getSentDate());
					//log.info("Date : " +message.getSentDate());
					objEmail.setDate(message.getSentDate());
				}
				
				
				//System.out.println("--------------"+message.getContentType());
				if(message.getContentType().startsWith("multipart")){
					Multipart multipart = (Multipart) message.getContent();

					for (int x = 0; x < multipart.getCount(); x++) {
						BodyPart bodyPart = multipart.getBodyPart(x);
						String disposition = bodyPart.getDisposition();
						if (disposition != null &&
								  (!disposition.equals(BodyPart.ATTACHMENT)) && (!disposition.equals(BodyPart.INLINE))) {
							String content=html2text((String) bodyPart.getContent()).trim();
							if(Constants.showEmailRead)
								System.out.println("Content : " +content);
							//log.info("Content : " +content);
							objEmail.setContent(content);
						}
					}

				}else{
					//System.out.println("Content : " +message.getContent().toString());
					String content=html2text(message.getContent().toString().trim());
					if(Constants.showEmailRead)
						System.out.println("Content : " +content);
					//log.info("Content : " +content);
					objEmail.setContent(content);
				}

				
				return objEmail;
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				System.out.println("File Not found :"+fileNameWithPath);
				e1.printStackTrace();
				log.error("FileNotFoundException : " +e1.getLocalizedMessage());
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				System.out.println("MessagingException :"+fileNameWithPath);
				e.printStackTrace();
				log.error("MessagingException : " +e.getLocalizedMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("IOException :"+fileNameWithPath);
				e.printStackTrace();
				log.error("IOException : " +e.getLocalizedMessage());
			}

			return null;
	}

	/**
	 * Function : html2text Converts html String to plain texts
	 * */
	public static String html2text(String html) {
		return Jsoup.parse(html).text();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		email obj=readLearningMail("data//train//spam//TRAIN_00013.eml");
		System.out.println(obj.getContent());
	}

}
