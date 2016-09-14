package de.user.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ejb.Stateless;

import org.apache.commons.io.IOUtils;

/**
 * The {@code Mailer} is responsible for sending mails. In further releases this
 * class will also integrate an template system, to send beautiful mails.
 * 
 */
@Stateless
public class MailTemplate {

	/**
	 * Method to construct full email structure
	 * 
	 * @param emailContent
	 * @return full email structure
	 */
	public String getHtmlTemplate(String emailContent, String title, String inboxPreview) throws IOException {

		// Get template files as string
		String emailBaseBody = IOUtils.toString(MailTemplate.class.getResourceAsStream("/HTMLEmailTemplate/base.tmpl"),
				"UTF-8");
		String head = IOUtils.toString(MailTemplate.class.getResourceAsStream("/HTMLEmailTemplate/head.tmpl"), "UTF-8");
		String header = IOUtils.toString(MailTemplate.class.getResourceAsStream("/HTMLEmailTemplate/header.tmpl"),
				"UTF-8");
		String footer = IOUtils.toString(MailTemplate.class.getResourceAsStream("/HTMLEmailTemplate/footer.tmpl"),
				"UTF-8");
		// Set general key values for templates
		Map<String, String> emailBody = new HashMap<String, String>();
		emailBody.put("{Head}", head);
		emailBody.put("{Content}", emailContent);
		emailBody.put("{Header}", header);
		emailBody.put("{Footer}", footer);
		emailBody.put("{title}", title);
		emailBody.put("{inboxPreview}", inboxPreview);
		// Replace variables in templates with actual values & return string
		// that contains HTML text
		String completeEmailTemplate = createCompleteEmailTemplate(emailBaseBody, emailBody);
		return completeEmailTemplate;

	}

	/**
	 * Generates the Mail-HTML for the verification-mail send to a user
	 * 
	 * @param user
	 * @param token
	 * @return full email structure
	 */
	public String verificationMailForUser(final String verificationLink) {

		String emailTmpl = null;
		String fullMail = null;
		try {
			emailTmpl = IOUtils.toString(MailTemplate.class
					.getResourceAsStream("/HTMLEmailTemplate/users-content/content-verification-mail.tmpl"), "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Set custom key values
		Map<String, String> customInput = new HashMap<String, String>();
		customInput.put("{verificationLink}", verificationLink);
		String emailContent = replaceValuesInString(emailTmpl, customInput);

		try {
			fullMail = getHtmlTemplate(emailContent, "", "");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fullMail;
	}

	/**
	 * Generates the Mail-HTML for the password-reset-mail send to a user
	 * 
	 * @param token
	 * @return full email structure
	 */
	public String passwordResetMailToUser(final String resetPasswordLink) {

		String emailTmpl = null;
		String fullMail = null;
		try {
			emailTmpl = IOUtils.toString(MailTemplate.class
					.getResourceAsStream("/HTMLEmailTemplate/users-content/content-password-reset-mail.tmpl"), "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Set custom key values
		Map<String, String> customInput = new HashMap<String, String>();
		customInput.put("{resetPasswordLink}", resetPasswordLink);
		String emailContent = replaceValuesInString(emailTmpl, customInput);
		try {
			fullMail = getHtmlTemplate(emailContent, "", "");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fullMail;
	}

	/**
	 * Generates the Mail-HTML for the contract invitation send to payer
	 * 
	 * @param contractReducedLink
	 *            format example: webapp.paylax.de/0816ABCD/reduced this link is
	 *            sent by the payee to the payer to join the contract
	 * @param contractReducedTitle
	 * @return full email structure
	 */
	public String invitationMailToPayer(String contractReducedLink, String contractReducedTitle) {

		String emailTmpl = null;
		String fullMail = null;
		try {
			emailTmpl = IOUtils.toString(MailTemplate.class
					.getResourceAsStream("/HTMLEmailTemplate/users-content/content-invitepayer-mail.tmpl"), "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Set custom key values
		Map<String, String> customInput = new HashMap<String, String>();
		customInput.put("{contractReducedLink}", contractReducedLink);
		customInput.put("{contractReducedTitle}", contractReducedTitle);
		String emailContent = replaceValuesInString(emailTmpl, customInput);
		try {
			fullMail = getHtmlTemplate(emailContent, "", "");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fullMail;
	}

	/**
	 * Method to replace values for keys in the global email template
	 * 
	 * @param base
	 * @param input
	 * @return email content with key values
	 */
	private String createCompleteEmailTemplate(String base, Map<String, String> input) {
		String completeEmailText = null;
		try {
			completeEmailText = base.replace("{Head}", input.get("{Head}")).replace("{Content}", input.get("{Content}"))
					.replace("{Header}", input.get("{Header}")).replace("{Footer}", input.get("{Footer}"))
					.replace("{title}", input.get("{title}")).replace("{inboxPreview}", input.get("{inboxPreview}"));
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return completeEmailText;
	}

	/**
	 * Method to replace custom values in custom templates
	 * 
	 * @param templateAsString
	 * @param input
	 * @return template with key values as a string
	 */
	private String replaceValuesInString(String templateAsString, Map<String, String> input) {
		try {
			Set<Entry<String, String>> entries = input.entrySet();
			for (Map.Entry<String, String> entry : entries) {
				templateAsString = templateAsString.replace(entry.getKey().trim(), entry.getValue().trim());
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return templateAsString;
	}

}
