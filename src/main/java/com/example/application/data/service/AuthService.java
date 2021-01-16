package com.example.application.data.service;


import com.example.application.data.entity.Role;
import com.example.application.data.entity.User;
import com.example.application.data.repository.UserRepository;
import com.example.application.views.logout.LogoutView;
import com.example.application.views.main.MainView;
import com.example.application.views.notifications.NotificationSender;
import com.example.application.views.notifications.NotificationsGridView;
import com.example.application.views.profile.MyProfile;
import com.example.application.views.recipe.MyRecipe;
import com.example.application.views.recipe.NewRecipe;
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

    private final UserRepository userRepo;

    public AuthService(UserRepository userRepo) {
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


        routes.add(new AuthRoute("home", "My profile", MyProfile.class));
        routes.add(new AuthRoute("MyReceipt", "Recipes CRUD", MyRecipe.class));
        routes.add(new AuthRoute("CreateNewRecipe", "Create New Recipe", NewRecipe.class));
        routes.add(new AuthRoute("notifications", "My notifications", NotificationsGridView.class));

        if (role.equals(Role.ADMIN)) {
            routes.add(new AuthRoute("admin-notifications", "Send System notification", NotificationSender.class));

        }
        routes.add(new AuthRoute("logout", "Logout", LogoutView.class));

        return routes;
    }

    public void registrieren(String username, String pass1) {

        userRepo.save(new User(username, pass1, Role.USER));
    }

    public UserRepository getUserRepo() {
        return userRepo;
    }
}
