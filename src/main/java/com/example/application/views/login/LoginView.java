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
import com.example.application.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;

import javax.naming.AuthenticationException;

@Route(value = "login")
@PageTitle("Login")
@RouteAlias(value = "", layout = MainView.class)
@CssImport("./styles/views/login.css")

public class LoginView extends Div {

    public LoginView(AuthService auth) {
        setId("login-view");

        TextField username = new TextField("Username : ");
        var passwort = new PasswordField("Passwort : ");
        var button = new Button("Login");
        var h1 = new H1("Willkommen !");


        add(h1, username, passwort, new Button("Login ", event -> {


            try {
                auth.authen(username.getValue(), passwort.getValue());
                UI.getCurrent().navigate("home");//Wenn alles gut laueft !!
            } catch (AuthenticationException e) {
                Notification.show("Falsche Eingabe !");
            }


        }));


    }

}
