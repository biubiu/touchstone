package com.shawn.touchstone.metrics.config;

import com.google.common.base.Joiner;
import com.sun.mail.smtp.SMTPTransport;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    private static final String EMAIL_FROM = "From@gmail.com";


    private static volatile EmailSender instance;
    private Properties prop;
    private static final String EMAIL_SUBJECT = "Subject";
    private static final String SMTP_SERVER = "smtp server ";
    private static final String USERNAME = "";
    private static final String PASSWORD = "";
    private EmailSender(){}

    public static EmailSender getInstance() {
            if (null == instance) {
                synchronized (EmailSender.class) {
                    if (null == instance) {
                        Properties prop = System.getProperties();
                        prop.put("mail.smtp.auth", "true");
                        instance = new EmailSender();
                    }
            }
        }
        return instance;
    }


    public void send(List<String> emails, String content) throws MessagingException {
        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(EMAIL_FROM));
        msg.setRecipients(RecipientType.TO, InternetAddress.parse(Joiner.on(",").join(emails), false));
        msg.setSubject(EMAIL_SUBJECT);
        msg.setDataHandler(new DataHandler(new HTMLDataSource(content)));
        SMTPTransport transport = (SMTPTransport) session.getTransport("smtp");
        transport.connect(SMTP_SERVER, USERNAME, PASSWORD);
        transport.sendMessage(msg, msg.getAllRecipients());
        System.out.println("Response: " + transport.getLastServerResponse());

        transport.close();
    }

    static class HTMLDataSource implements DataSource {

        private String html;

        public HTMLDataSource(String htmlString) {
            html = htmlString;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            if (html == null) throw new IOException("html message is null!");
            return new ByteArrayInputStream(html.getBytes());
        }

        @Override
        public OutputStream getOutputStream() throws IOException {
            throw new IOException("This DataHandler cannot write HTML");
        }

        @Override
        public String getContentType() {
            return "text/html";
        }

        @Override
        public String getName() {
            return "HTMLDataSource";
        }
    }
}
