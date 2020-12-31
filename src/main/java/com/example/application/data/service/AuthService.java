package com.example.application.data.service;

import com.example.application.data.Repos.UserRepo;
import com.example.application.data.entity.Role;
import com.example.application.data.entity.User;
import com.example.application.views.cardlist.CardListView;
import com.example.application.views.logout.LogoutView;
import com.example.application.views.main.MainView;
import com.example.application.views.masterdetail.MasterDetailView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import javax.security.auth.message.AuthException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {


    private final UserRepo userRepo;

    public AuthService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void authen(String username, String passwort) throws AuthenticationException, AuthException {

        User user = userRepo.getByUsername(username);
        if (user != null && user.checkueberEinStimmung(passwort)) {
            VaadinSession.getCurrent().setAttribute(User.class, user);
            createRoutes(user.getRole());
        } else {
            throw new AuthException();
        }

    }

    public void registrieren(String username, String pass1) {//TODO


        userRepo.save(new User(username, pass1, Role.USER));


    }


    public record AuthRoute(String route, String name, Class<? extends Component> view) {

    }

    private void createRoutes(Role role) {
        getAuthoRoutes(role).stream()
                .forEach(route ->
                        RouteConfiguration.forSessionScope().setRoute(
                                route.route, route.view, MainView.class));

    }

    public List<AuthRoute> getAuthoRoutes(Role role) {
        var routes = new ArrayList<AuthRoute>();

        if (role.equals(Role.USER)) {
            routes.add(new AuthRoute("home", "Home", CardListView.class));//Hier werden die Routes bestimmt !!!!!!!!!!!!!!
            routes.add(new AuthRoute("logout", "Logout", LogoutView.class));


        } else if (role.equals(Role.ADMIN)) {
            routes.add(new AuthRoute("home", "Home", CardListView.class));
            routes.add(new AuthRoute("admin", "Admin", MasterDetailView.class));
            routes.add(new AuthRoute("logout", "Logout", LogoutView.class));
          //  routes.add(new AuthRoute("rezept", "Rezept", RezeptView.class));
        }

        return routes;
    }



    public UserRepo getUserRepo() {
        return userRepo;
    }
}
