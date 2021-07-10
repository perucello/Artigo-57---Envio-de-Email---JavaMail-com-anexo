package JavaMail;

import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class JavaMail {

	public static void main(String[] args) {
		//authentication
		final String seuEmail = "seuEmail@provedor.com.br";
		final String senha = "suaSenha";
		String fromEmail = seuEmail;
		String emailTo = "emailDestino@provedor.com.br";
		
		//dados de validação do provedor
		Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.live.com");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(seuEmail,senha);
			}
		});
 
		MimeMessage msg = new MimeMessage(session);
		try {
			//Email De:
			msg.setFrom(new InternetAddress(fromEmail));
			//Email Para:
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
			//Subtitulo do email
			msg.setSubject("Teste de envio de email com anexo.");
			
			Multipart emailContent = new MimeMultipart();
			
			//Corpo do email
			MimeBodyPart corpoEmail = new MimeBodyPart();
			corpoEmail.setText(
					"Email EducaCiencia FastCode com anexo, verificar anexo do email e comprove o envio do documento !");			
			//Anexando arquivo pdf
			MimeBodyPart anexoPDF = new MimeBodyPart();
			String pathArquivoPDF = "C:\\teste\\emailPDF.pdf";
			anexoPDF.attachFile(pathArquivoPDF);

			emailContent.addBodyPart(corpoEmail);
			emailContent.addBodyPart(anexoPDF);

			msg.setContent(emailContent);
			
			//Enviando
			Transport.send(msg);
			//validando no console - sysout
			System.out.println("Email enviado por EducaCiencia FastCode!");
			System.out.println("Email enviado para: " + emailTo);
			System.out.println("Email - path anexado: " + pathArquivoPDF);

		} 
		catch (MessagingException em) {
			em.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}