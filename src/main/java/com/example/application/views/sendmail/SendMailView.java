package com.example.application.views.sendmail;

import com.example.application.data.javamail.CommonsEmailService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import org.apache.commons.mail.EmailException;

import java.io.IOException;
import java.util.Arrays;


public class SendMailView extends VerticalLayout {

    TextField fromTextField = new TextField("Your email:");
    TextField subjectTextField = new TextField("Subject:");
    private TextArea notificationTextArea = new TextArea("Your Message:");
    private Button send = new Button("Send");

    public SendMailView() {
        setId("send-mail-view");
        add(new Text("Send Mail:"));
        setSizeFull();
        notificationTextArea.setHeight("30%");
        notificationTextArea.setWidth("20%");
        add(fromTextField);
        add(subjectTextField);
        add(notificationTextArea);
        add(send);
        send.addClickListener(e -> sendMail());
    }

    private void sendMail() {
        try {

            String to = "aamadel100@gmail.com";
            CommonsEmailService.send(fromTextField.getValue(), Arrays.asList(to),
                    subjectTextField.getValue(),
                    "from \n" + fromTextField.getValue() + "\n" + notificationTextArea.getValue());

            CommonsEmailService.send(fromTextField.getValue(), Arrays.asList(fromTextField.getValue()),
                    "Danke für Ihre E-mail", "Vielen Dank fuer Ihre E-mail , wir kontaktieren Sie so schnell wie moeglich \n \n LG\n Jeder kann kochen Team ");

        } catch (IOException | EmailException e) {
            e.printStackTrace();
        }
    }

}
