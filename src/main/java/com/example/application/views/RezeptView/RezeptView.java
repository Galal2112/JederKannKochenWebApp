package com.example.application.views.RezeptView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;


import com.example.application.data.entity.Rezept;
import com.example.application.data.entity.Zutat;
import com.example.application.data.service.PersonService;
import com.example.application.data.service.RezeptService;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;

import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.textfield.TextField;


@Route("")
@PageTitle("Master-Detail")
public class RezeptView extends VerticalLayout {


    private RezeptService rezeptService;

    TextField filter = new TextField();

    private Grid<Rezept> grid = new Grid<>(Rezept.class);


    public RezeptView(@Autowired RezeptService rezeptService) {

        this.rezeptService = rezeptService;

        setSizeFull();

        configureGrid();
        configureFilter();


        add(filter, grid);
        updatelist();
    }

    private void configureFilter() {

        filter.setPlaceholder("Suchen durch Zutatenname : ");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.LAZY);//InputField wartet nur ein paar Sekunden , wenn man wasa eingibt , dann merkt die Änderung von eingegebenen Wert
        filter.addValueChangeListener(e -> updatelist());
    }


    private void configureGrid() {

        grid.setSizeFull();

        grid.removeColumnByKey("inhalt");
        grid.removeColumnByKey("zutaten");
        grid.setColumns("rezeptname", "video");

        grid.addColumn(rezept -> {

            String[] inhalt = rezept.getInhalt();

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < inhalt.length; i++) {
                sb.append(inhalt[i]);
            }
            String str = Arrays.toString(inhalt);


            return inhalt == null ? " - " : str;


        }).setHeader("Inhalte");


        grid.addColumn(rezept -> {

            String[] zutats = rezept.getZutaten();


            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < zutats.length; i++) {
                sb.append(zutats[i]);
            }
            String str = Arrays.toString(zutats);


            return zutats == null ? " - " : str;


        }).setHeader("Zutaten");


        grid.getColumns().forEach(col -> col.setAutoWidth(true));


    }

    private void updatelist() {
        grid.setItems(rezeptService.findAll(filter.getValue()));

    }

}