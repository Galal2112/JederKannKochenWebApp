import com.example.application.data.entity.Role;
import com.example.application.data.entity.User;
import com.example.application.data.repository.UserRepository;
import com.example.application.data.service.AuthService;
import com.example.application.views.notifications.NotificationSender;
import com.example.application.views.sendmail.SendMailView;
import com.vaadin.flow.server.VaadinSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import javax.naming.AuthenticationException;
import javax.security.auth.message.AuthException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class LoginTest {

    AuthService authService;
    User user;
    User admin;

    @Before
    public void setup() {
        user = new User("amro", "123456", Role.USER);
        admin = new User("admin", "123456", Role.ADMIN);
        VaadinSession.setCurrent(mock(VaadinSession.class));
        UserRepository userRepository = mock(UserRepository.class);
        userRepository.save(user);
        userRepository.save(admin);
        when(userRepository.getByUsername("amro")).thenReturn(user);
        when(userRepository.getByUsername("admin")).thenReturn(admin);
        authService = new AuthService(userRepository);

    }

    @Test
    public void testResgistrieren() {


        User amro = authService.getUserRepo().getByUsername("amro");

        assertTrue(amro.checkueberEinStimmung("123456"));


    }

    @Test
    public void testPassUeberEinStimmung() {

        User user = new User("mac", "123456", Role.USER);
        assertTrue(user.checkueberEinStimmung("123456"));
    }

    @Test
    public void testLoginFailed() {


        Assertions.assertThrows(AuthException.class, () -> {
            authService.authen("amro", "12345");
        });

    }

    @Test
    public void testLoginSucsess() {


        try {
            authService.authen("amro", "123456");
            assertTrue(authService.getRoutes() != null);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (AuthException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testRoutes() {


        try {
            authService.authen("amro", "123456");
            assertTrue(authService.getRoutes().size() == 8);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (AuthException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testLoginFailedSecond() {


        Assertions.assertThrows(AuthException.class, () -> {
            authService.authen("amro", "12345");
        });
        assertTrue(authService.getRoutes() == null);
    }

    @Test
    public void testUserRoutes() {

        try {
            authService.authen("amro", "123456");

            AuthService.AuthRoute email = new AuthService.AuthRoute("SendAnEmail", "Send an E-mail", SendMailView.class);

            assertTrue(authService.getRoutes().contains(email));
        } catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (AuthException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testAdminRoutes() {

        try {
            authService.authen("admin", "123456");

            AuthService.AuthRoute adminNotificatioin = new AuthService.AuthRoute("admin-notifications", "Send System notification", NotificationSender.class);

            assertTrue(authService.getRoutes().contains(adminNotificatioin));
        } catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (AuthException e) {
            e.printStackTrace();
        }


    }

}
