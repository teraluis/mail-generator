package apim;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.SwingUtilities;

import org.apache.poi.hsmf.MAPIMessage;
import org.apache.poi.hsmf.datatypes.RecipientChunks;
import org.apache.poi.hsmf.exceptions.ChunkNotFoundException;

import jakarta.mail.MessagingException;

public class Main {

	public static void main(String[] args) throws MessagingException {



		EmailConfigListener listener = new EmailConfigListener() {
			public void onSubmit(String api, String abonement, String product) throws MessagingException {
				System.out.println("API: " + api + ", Abonnement: " + abonement + ", Produit: " + product);
				final var filePath = "src/main/resources/templates/prod.msg";

				try {
					final var message = new MAPIMessage(filePath);
					final var subject = message.getSubject();

					final var body = message.getTextBody().replaceAll("SIROP", product)
							.replaceAll("PROSPECT", api).replaceAll("APP_QTP", abonement);
					
					System.out.println(body);
					RecipientChunks[] recipientsTo = message.getRecipientDetailsChunks();

					final var recipients = new ArrayList<String>(
							Arrays.asList("olivier.teutsch-prestataire@labanquepostale.fr",
									"hugo.peron-prestataire@labanquepostale.fr", "audrey.balat@labanquepostale.fr"));
					for (RecipientChunks recipient : recipientsTo) {
						//recipients.add(recipient.getRecipientEmailAddress());
					}

					RecipientChunks[] recipientsCc = message.getRecipientDetailsChunks();
					for (RecipientChunks recipient : recipientsCc) {
						//System.out.println(RecipientChunks.RECIPIENT_NAME + " " + recipient.getRecipientEmailAddress());
					}

					Mail mail = new Mail();
					mail.setBody(body);
					mail.setRecipient(recipients);
					mail.saveMail("production");
					
					System.out.println(mail.getMessage());
				} catch (IOException | ChunkNotFoundException e) {
					e.printStackTrace();
				}

			}
		};
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				EmailUI ex = new EmailUI(listener);
				ex.setVisible(true);
			}
		});

	}

}
