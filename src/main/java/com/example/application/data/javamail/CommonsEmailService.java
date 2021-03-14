package com.example.application.data.javamail;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class CommonsEmailService {

    public static void send(String from, Collection<String> recipients, String subject, String text)
            throws IOException, EmailException {
        send(from, recipients, subject, text, null, null, null);
    }

    public static void send(String from, String recipient, String subject, String text, InputStream attachment,
                            String fileName, String mimeType)
            throws IOException, EmailException {
        send(from, Arrays.asList(recipient), subject, text, Arrays.asList(attachment), Arrays.asList(fileName),
                Arrays.asList(mimeType));
    }

    public static void send(String from, Collection<String> recipients, String subject, String text,
                            List<InputStream> attachments, List<String> fileNames, List<String> mimeTypes)
            throws EmailException, IOException {

        Objects.requireNonNull(from);
        Objects.requireNonNull(recipients);

        Properties properties = new Properties();
        properties.load(CommonsEmailService.class.getResourceAsStream("/mail.properties"));
        String host = properties.getProperty("mail.smtp.host");
        String port = properties.getProperty("mail.smtp.port");
        String ssl = properties.getProperty("mail.smtp.ssl.enable");
        String username = properties.getProperty("mail.smtp.username");
        String password = properties.getProperty("mail.smtp.password");

        HtmlEmail email = new HtmlEmail();

        email.setHostName(host);
        email.setSmtpPort(Integer.parseInt(port));
        email.setAuthentication(username, password);
        email.setSSLOnConnect(Boolean.parseBoolean(ssl));

        email.setFrom(from);
        email.addTo(recipients.toArray(new String[]{}));
        email.setSubject(subject);
        email.setHtmlMsg(text);

        if (attachments != null) {
            for (int i = 0; i < attachments.size(); i++) {

                ByteArrayDataSource dataSource = new ByteArrayDataSource(attachments.get(i), mimeTypes.get(i));

                email.attach(dataSource, fileNames.get(i), "attachment");
            }
        }

        email.send();
    }
}
