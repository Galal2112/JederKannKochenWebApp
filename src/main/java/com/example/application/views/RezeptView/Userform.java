package com.example.application.views.RezeptView;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;

public class Userform extends FormLayout {

    TextField rezeptname = new TextField("Rezeptname");
    TextField video = new TextField("Video");
    TextField inhalte = new TextField("Inhalte");
    TextField zutaten = new TextField("Zutaten");

    //ComboBox<User.Status> status = new ComboBox<>("Status");
    //ComboBox<Company> company = new ComboBox<>("Company");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");


    public Userform() {


        addClassName("User-Form");

        add(

                rezeptname,
                video,
                inhalte,
                zutaten,

                creatButtons()

        );
    }

    private Component creatButtons() {

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);


        save.addClickShortcut(Key.ENTER);

        close.addClickShortcut(Key.ESCAPE);


        return new HorizontalLayout(save, delete, close);
    }


}
