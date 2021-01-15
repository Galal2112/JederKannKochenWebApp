package com.example.application.views.login;

import com.example.application.data.service.AuthService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import javax.naming.AuthenticationException;
import javax.security.auth.message.AuthException;


@Route(value = "login")
@PageTitle("Login")
@CssImport("./styles/views/login.css")
public class LoginView extends Div {
    private final AuthService auth;

    public LoginView(AuthService auth) {
        this.auth = auth;
        setId("login-view");
        TextField username = new TextField("Username : ");
        PasswordField passwort = new PasswordField("Passwort : ");
        H1 h1 = new H1("Willkommen !");

        add(h1, username, passwort, new Button("Login ", event -> {

            try {
                auth.authen(username.getValue(), ( passwort).getValue());
                UI.getCurrent().navigate("home");

            } catch (AuthenticationException | AuthException e) {
                Notification.show("Falsche Eingabe !");
            }

        }), new RouterLink("Registrieren", RegisterView.class));

    }

}
