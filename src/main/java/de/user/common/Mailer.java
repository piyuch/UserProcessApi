package de.user.common;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The {@code Mailer} is responsible for sending mails. In further releases this
 * class will also integrate an template system, to send beautiful mails.
 * 
 */
@Stateless
public class Mailer {

	private static final Logger LOG = LogManager.getLogger(Mailer.class);

	@Resource(name = "java:/javaxMail")
	private Session session;

	/**
	 * Sends a mail to the {@code recipient} from {@code from} with subject
	 * {@code subject}.
	 * 
	 * @param from
	 *            the sender address
	 * @param recipient
	 *            the recipient address
	 * 
	 * @param subject
	 *            the subject
	 * @param content
	 *            email content in HTML template
	 */
	public void sendHtmlMail(final String from, final String recipient, final String subject, final String content) {

		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(from);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			message.setSubject(subject);
			message.setContent(content, "text/html; charset=UTF-8");

			Transport.send(message);
		} catch (MessagingException e) {
			LOG.catching(e);
		}
	}
}
