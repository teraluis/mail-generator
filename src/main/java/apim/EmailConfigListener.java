package apim;

import jakarta.mail.MessagingException;

public interface EmailConfigListener {
	void onSubmit(String api, String abonement, String product) throws MessagingException;
}
