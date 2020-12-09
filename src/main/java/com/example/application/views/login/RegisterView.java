package com.example.application.views.login;

import com.example.application.data.service.AuthService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("register")
@CssImport("./styles/views/login.css")
public class RegisterView extends Composite {

    private final AuthService authService;

    public RegisterView(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected Component initContent() {

        TextField user = new TextField("Username");
        var h2 = new H2("Registrieren");
        PasswordField pass = new PasswordField("Password");
        PasswordField pass2 = new PasswordField(" Confirm Password");
        return new VerticalLayout(

                h2,
                user,
                pass,
                pass2,

                new Button("Senden", event -> register(user.getValue(), pass.getValue(), pass2.getValue()))

        );
    }

    private void register(String username, String pass1, String pass2) {

        if (username.trim().isEmpty()) {

            Notification.show("Username EINGEBEN !");

        } else if (pass1.trim().isEmpty()) {

            Notification.show("Passwort EINGEBEN !");

        } else if (!pass1.equals(pass2)) {

            Notification.show("Passworte stimmen nicht ueberein !!");

        } else if (authService.getUserRepo().getByUsername(username) != null) {


            Notification.show("Username ist schon besitzt !!");

        } else {


            authService.registrieren(username, pass1);

            Notification.show("Erfolgreich regstiriert !");

        }
    }

}


