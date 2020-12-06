package com.example.application.data.service;

import com.example.application.data.entity.Role;
import com.example.application.data.entity.User;
import com.example.application.views.cardlist.CardListView;
import com.example.application.views.main.MainView;
import com.example.application.views.masterdetail.MasterDetailView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {


    private final UserRepo userRepo;

    public AuthService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void authen(String username, String passwort) throws AuthenticationException {

        User user = userRepo.getByUsername(username);

        if (user != null && user.checkueberEinStimmung(passwort)) {//wenn klappt , muss was passieren !!
            VaadinSession.getCurrent().setAttribute(User.class, user);//der aktuelle speichern , um die richte Tap zu erzeugen!!

            AuthRoute authRoute = new AuthRoute("home", "Home", MainView.class);

        } else
            throw new AuthenticationException();

    }


    public record AuthRoute(String route, String name, Class<? extends Component> view) {


    }

    private void creatRoutes(Role role) {

        getAuthoRoute(role).stream().forEach(route -> RouteConfiguration.forSessionScope().setRoute(route.route, route.view, MainView.class));


    }

    public List<AuthRoute> getAuthoRoute(Role role) {


        var routes = new ArrayList<AuthRoute>();


        if (role.equals(Role.USER)) {


            routes.add(new AuthRoute("home", "Home", CardListView.class));


        } else if (role.equals(Role.ADMIN)) {
            routes.add(new AuthRoute("home", "Home", MainView.class));
            routes.add(new AuthRoute("admin", "Admin", MasterDetailView.class));

        }
        return routes;

    }


}
