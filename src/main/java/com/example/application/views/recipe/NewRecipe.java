package com.example.application.views.recipe;

import com.example.application.data.entity.Rezept;
import com.example.application.data.entity.User;
import com.example.application.data.service.RezeptService;
import com.example.application.data.service.UserService;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.richtexteditor.RichTextEditor;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;


@PageTitle("Create New Recipe")
public class NewRecipe extends Div {

    private TextField rezeptName = new TextField("Recipe Name");
    private Div valueBlock = new Div();
    private RichTextEditor inhalt = new RichTextEditor();
    private Button saveBtn = new Button("Add new Recipe");
    private Binder<Rezept> binder = new Binder(Rezept.class);

    public NewRecipe(RezeptService rezeptService, UserService userService) {
        binder.setBean(new Rezept());
        add(rezeptName, inhalt, saveBtn, valueBlock);
        binder.bindInstanceFields(this);
        saveBtn.addClickListener(e -> {
            // TODO: get user from session
            var user = VaadinSession.getCurrent().getAttribute(User.class);
         //   User creator = userService.login("test@google.com", "123456");
            rezeptService.createRezept(user, rezeptName.getValue(), inhalt.getHtmlValue());
            Notification.show("Recipe created.");
            binder.setBean(new Rezept());
        });
    }
}
