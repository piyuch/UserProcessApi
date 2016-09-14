package de.user.control;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.user.common.MailTemplate;
import de.user.common.Mailer;
import de.user.model.UserEntity;
import de.user.properties.PropertiesHandler;

/**
 * Controler zum senden von E-Mails.
 *
 * @author claas.buedding
 */
@Stateless
public class MailControl {

	@Inject
	private Mailer mailer;

	@Inject
	private MailTemplate mailTemplate;

	@Inject
	PropertiesHandler propertiesHandler;

	private static final Logger LOG = LogManager.getLogger(MailControl.class);

	@Resource(name = "java:/javaxMail")
	private Session session;

	/**
	 * Sends a verification mail to a user
	 * 
	 * @param user
	 * @param token
	 */
	public void sendVerificationMailToUser(final UserEntity user, final String token) {

		String mailHtmlVerifyMailForUser = mailTemplate.verificationMailForUser(getVerificationLink(token));
		mailer.sendHtmlMail(propertiesHandler.getProperty("mail.sender.verification"), user.getEmail(),
				propertiesHandler.getProperty("mail.subject.verification"), mailHtmlVerifyMailForUser);
		LOG.info("send registration email");
	}

	/**
	 * Sends password-reset-mail to a user
	 * 
	 * @param user
	 * @param token
	 */
	public void sendPasswordResetMailToUser(final UserEntity user, final String token) {
		String mailPasswordResetMailForUser = mailTemplate.passwordResetMailToUser(getResetPasswordLink(token));
		mailer.sendHtmlMail(propertiesHandler.getProperty("mail.sender.resetpassword"), user.getEmail(),
				propertiesHandler.getProperty("mail.subject.resetpassword"), mailPasswordResetMailForUser);
		LOG.info("send password reset email");
	}

	/**
	 * Sends contractReducedLink to a payer
	 * 
	 * @param payerEmail
	 * @param contractReducedLink
	 * @param contractReducedTitle
	 */
	public void sendInvitationMailToPayer(final String payerEmail, final String contractReducedLink,
			final String contractReducedTitle) {
		String mailInvitationMailToPayer = mailTemplate.invitationMailToPayer(contractReducedLink,
				contractReducedTitle);
		mailer.sendHtmlMail(propertiesHandler.getProperty("mail.sender.invitationtopayer"), payerEmail,
				propertiesHandler.getProperty("mail.subject.invitationtopayer"), mailInvitationMailToPayer);
		LOG.info("send ivitation mail to payer");
	}

	/**
	 * Get verification link
	 * 
	 * @param token
	 * @return verificationUrl
	 */
	private String getVerificationLink(String token) {
		String baseUrl = propertiesHandler.getProperty("url.base.webapplication");
		String verificationPath = propertiesHandler.getProperty("url.path.verificationlink").replace("{token}", token);
		String verificationLink = baseUrl.concat(verificationPath);
		LOG.exit(verificationLink);
		return verificationLink;

	}

	/**
	 * Get password reset link
	 * 
	 * @param token
	 * @return resetPasswordLink
	 */
	private String getResetPasswordLink(String token) {
		String baseUrl = propertiesHandler.getProperty("url.base.webapplication");
		String resetPasswordPath = propertiesHandler.getProperty("url.path.resetpassword").replace("{token}", token);
		String resetPasswordLink = baseUrl.concat(resetPasswordPath);
		LOG.exit(resetPasswordLink);
		return resetPasswordLink;

	}
}
