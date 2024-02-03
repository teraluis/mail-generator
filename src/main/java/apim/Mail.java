 package apim;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class Mail {
	 Properties properties;
	 Session session;
	 MimeMessage message;
	 
	 public Mail() {
		 properties = new Properties();
         session = Session.getInstance(properties, null);
         message = new MimeMessage(session);
	}
	 
	public void setsubject(String subject) throws MessagingException {
		message.setSubject(subject);
	}
	
	/**
	 * Set recipient
	 * @throws AddressException 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws MessagingException
	 */
	public void setRecipient(List<String> recipients) {
		 recipients.stream().forEach(r -> {
			try {
				if(r != null) message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(r));
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		});
		 
	}
	
	public void setRecipientCi(List<String> recipients) throws AddressException, MessagingException {
		 recipients.stream().forEach(r -> {
			try {
				if(r != null) message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(r));
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		});
	}
	public void setBody(String body) throws MessagingException {
		message.setText(body);
	}
	
	public MimeMessage getMessage() {
		return message;
	}
	
	public void saveMail(String name) throws FileNotFoundException, IOException, MessagingException {
		 try (FileOutputStream out = new FileOutputStream(name + ".eml")) {
             message.writeTo(out);
         }
	}
}
