import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;
import org.artofsolving.jodconverter.office.OfficeUtils;
import org.junit.Ignore;
import org.junit.Test;

public class MailTest {

	@Test @Ignore
	public void send() {
		String msgText = "添付ファイルがあるメール送信サンプルです。";

		System.out.println();

		String to = "s-sakurama@archims.co.jp";
		String from = "s-sakurama@archims.co.jp";
		String host = "mail.archims.co.jp";
		String filename = "c:\\user.js";
		boolean debug = false;

		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.host", host);
		props.put("mail.from", from);
		if (debug) {
			props.put("mail.debug", debug);
		}

		Session session = Session.getInstance(props);
		session.setDebug(debug);

		try {
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			InternetAddress[] address = InternetAddress.parse(to);
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject("JavaMail APIs Test", "ISO-2022-JP");
			msg.setSentDate(new Date());

			/* 添付ファイルの処理 */
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText(msgText, "ISO-2022-JP");

			MimeBodyPart mbp2 = new MimeBodyPart();
			FileDataSource fds = new FileDataSource(filename);
			mbp2.setDataHandler(new DataHandler(fds));
			mbp2.setFileName(MimeUtility.encodeWord(fds.getName()));

			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
			mp.addBodyPart(mbp2);

			msg.setContent(mp);

			Transport.send(msg);
		} catch (MessagingException mex) {
			System.out.println("¥n--Exception handling in msgsendsample.java");
			mex.printStackTrace();
		} catch (java.io.UnsupportedEncodingException uex) {
		}
	}

	@Test @Ignore
	public void convertExcel2Pdf(){

		System.setProperty("office.home", "C:\\Program Files (x86)\\LibreOffice 5");

		File officeHome = OfficeUtils.getDefaultOfficeHome();
		OfficeManager manager = new DefaultOfficeManagerConfiguration().buildOfficeManager();
		manager.start();
		OfficeDocumentConverter converter =new OfficeDocumentConverter(manager);

		File excel = new File("C:\\project\\20170112_利用状況表\\ASMO大和郡山店AB.xlsx");
		assertThat(excel.exists(), is(true));

	    File pdfFile = new File("C:\\project\\20170112_利用状況表\\ASMO大和郡山店AB.pdf");
	    assertThat(pdfFile.exists(), is(false));

	    converter.convert(excel, pdfFile);

	    assertThat(pdfFile.exists(), is(true)); // PDFが出来た
	    manager.stop();
	}


}
