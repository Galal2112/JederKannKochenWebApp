package com.example.application.views.profile;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.example.application.views.main.MainView;

@Route(value = "profile", layout = MainView.class)
@PageTitle("Profile")
public class ProfileView extends Div {

    public ProfileView() {
        setId("profile-view");
        add(new Text("Content placeholder"));
    }

}
