package com.example.application.views.dashboard;

import com.example.application.views.main.MainView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "dashboard", layout = MainView.class)
@PageTitle("Dashboard")
@CssImport(value = "./styles/views/dashboard/dashboard-view.css", include = "lumo-badge")
@JsModule("@vaadin/vaadin-lumo-styles/badge.js")
@RouteAlias(value = "", layout = MainView.class)
public class DashboardView extends Div {

    public DashboardView() {
        setId("Dashboard-view");
        add(new Text("Dashboard placeholder"));
    }
}