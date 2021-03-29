import com.example.application.views.sendmail.SendMailView;
import com.vaadin.flow.component.textfield.TextField;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertTrue;

public class EmailTests {

    SendMailView sendMailView;

    @Before
    public void setup() {
        sendMailView = new SendMailView();
    }

    @Test
    public void testSendSucsess() {
        TextField textField = new TextField();
        textField.setValue("a@gmail.com");
        sendMailView.setFromTextField(textField);
        sendMailView.sendMail();
        assertTrue(sendMailView.getCommonsEmailService().isErledigt());

    }

    @Test
    public void testGesendteEmails() {
        TextField textField = new TextField();
        textField.setValue("a@gmail.com");
        sendMailView.setFromTextField(textField);
        assertTrue(sendMailView.getCommonsEmailService().getGesendet() == 0);
        sendMailView.sendMail();
        assertTrue(sendMailView.getCommonsEmailService().getGesendet() == 2);


    }

    @Test
    public void testSendPort() {
        TextField textField = new TextField();
        textField.setValue("a@gmail.com");
        sendMailView.setFromTextField(textField);
        sendMailView.sendMail();
        assertTrue(sendMailView.getCommonsEmailService().getPort().equals("465"));

    }
}
