package com.example.application.data.javamail;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class CommonsEmailService {

    boolean erledigt = false;
    int gesendet = 0;
    String port;

    public void send(String from, Collection<String> recipients, String subject, String text)
            throws IOException, EmailException {
        send(from, recipients, subject, text, null, null, null);
    }


    public void send(String from, Collection<String> recipients, String subject, String text,
                     List<InputStream> attachments, List<String> fileNames, List<String> mimeTypes)
            throws EmailException, IOException {

        Objects.requireNonNull(from);
        Objects.requireNonNull(recipients);

        Properties properties = new Properties();
        properties.load(CommonsEmailService.class.getResourceAsStream("/mail.properties"));
        String host = properties.getProperty("mail.smtp.host");
        String port = properties.getProperty("mail.smtp.port");
        this.port = port;
        String ssl = properties.getProperty("mail.smtp.ssl.enable");
        String username = String.valueOf(System.getenv("EMAILFROM"));
        String password = String.valueOf(System.getenv("PASSFROM"));

        HtmlEmail email = new HtmlEmail();

        email.setHostName(host);
        email.setSmtpPort(Integer.parseInt(port));
        email.setAuthentication(username, password);
        email.setSSLOnConnect(Boolean.parseBoolean(ssl));

        email.setFrom(from);
        email.addTo(recipients.toArray(new String[]{}));
        email.setSubject(subject);
        email.setHtmlMsg(text);

        email.send();
        gesendet++;
        erledigt = true;
    }

    public boolean isErledigt() {
        return erledigt;
    }

    public String getPort() {
        return port;
    }

    public int getGesendet() {
        return gesendet;
    }

}
