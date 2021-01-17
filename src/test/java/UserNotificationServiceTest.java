import com.example.application.Application;
import com.example.application.data.entity.Gender;
import com.example.application.data.entity.User;
import com.example.application.data.entity.UserNotification;
import com.example.application.data.service.UserNotificationService;
import com.example.application.data.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserNotificationServiceTest {

    @Autowired
    UserNotificationService subject;
    @Autowired
    UserService userService;
    private User adminUser;
    private final Random rand = new Random();

    @Before
    public void setup() {
        String firstName = "firstName" + rand.nextInt();
        String lastName = "lastName" + rand.nextInt();
        String email = UUID.randomUUID().toString() + "@test.com";
        adminUser = userService.createAdmin(firstName, lastName
                , "123456789", email, "123456",
                LocalDate.of(1900, 12, 12), Gender.MALE);
    }

    @Test
    public void testSendNotification() {
        String message = "Test notification";
        UserNotification userNotification = subject.sendNotificationToAllUser(adminUser, message);
        assertNotNull(userNotification.getId());
        assertEquals(userNotification.getText(), message);
    }

    @Test
    public void testSendNotificationNotAllowedForNormalUsers() {
        String message = "Test notification";
        User normalUser = userService.createUser("firstName" + rand.nextInt(),
                "lastName" + rand.nextInt()
                , "123456789", UUID.randomUUID().toString() + "@test.com",
                "123456",
                LocalDate.of(1900, 12, 12), Gender.MALE);

        assertThrows(IllegalArgumentException.class, () -> {
            subject.sendNotificationToAllUser(normalUser, message);
        });
    }

    @Test
    public void testGetUserNotifications() {
        User normalUser = userService.createUser("firstName" + rand.nextInt(),
                "lastName" + rand.nextInt()
                , "123456789", UUID.randomUUID().toString() + "@test.com",
                "123456",
                LocalDate.of(1900, 12, 12), Gender.MALE);

        String message1 = "Test notification1";
        UserNotification userNotification1 = subject.sendNotificationToAllUser(adminUser, message1);
        String message2 = "Test notification2";
        UserNotification userNotification2 = subject.sendNotificationToUser(adminUser, normalUser.getId(), message2);
        List<UserNotification> notifications = new ArrayList<>(Arrays.asList(userNotification2, userNotification1));
        List<UserNotification> dbNotifications = subject.getUserNotifications(normalUser.getId());
        assertEquals(dbNotifications.size(), 2);
        for (int i = 0; i < dbNotifications.size(); i++) {
            assertEquals(dbNotifications.get(i).getText(), notifications.get(i).getText());
        }
    }
}
