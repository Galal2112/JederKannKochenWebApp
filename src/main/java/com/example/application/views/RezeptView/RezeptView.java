package com.example.application.views.RezeptView;


import com.example.application.data.entity.Rezept;
import com.example.application.data.service.RezeptService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;


@PageTitle("Verfügbare Rezepte")

public class RezeptView extends VerticalLayout {


    private RezeptService rezeptService;

    TextField filter = new TextField();

    private Grid<Rezept> grid = new Grid<>(Rezept.class); // ein Grid fuer eine bestimmte Klasse

    public RezeptView(@Autowired RezeptService rezeptService) {

        this.rezeptService = rezeptService;
        setSizeFull();
        configureGrid();
        configureFilter();
        Div content = new Div(grid);
        content.addClassName("content");
        content.setSizeFull();
        add(filter, content);
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

        grid.removeColumnByKey("id");

        grid.removeColumnByKey("creator");

        grid.removeColumnByKey("beschreibung");

        grid.removeColumnByKey("zutaten");

        grid.removeColumnByKey("videos");

        grid.addColumn(rezept -> {
            return rezept.getBeschreibung();

        }).setHeader("Inhalte");

       /*
        grid.addColumn(rezept -> {

            List<Zutat> zutats = rezept.getZutaten();

            String[] zutatNamen = new String[zutats.size()];
            for (int i = 0; i < zutats.size(); i++) {
                zutatNamen[i] = zutats.get(i).getItem();
            }
            String str = Arrays.toString(zutatNamen);

            return zutats == null ? " - " : str;

        }).setHeader("Zutaten");
        */


        grid.getColumns().forEach(col -> col.setAutoWidth(true));//das alle Spalten genaug Platz fuer Ihre Inhalte haben

    }

    private void updatelist() {
        grid.setItems(rezeptService.findAll(filter.getValue()));

    }
}
