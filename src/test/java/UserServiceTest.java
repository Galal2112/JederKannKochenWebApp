import com.example.application.Application;
import com.example.application.data.entity.Gender;
import com.example.application.data.entity.User;
import com.example.application.data.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserServiceTest {

    @Autowired
    UserService subject;
    private final Random rand = new Random();

    @Test
    public void testCreateUser() {
        String firstName = "firstName" + rand.nextInt();
        String lastName = "lastName" + rand.nextInt();
        String email = UUID.randomUUID().toString() + "@test.com";
        User user = subject.createUser(firstName, lastName
                , "123456789", email, "123456",
                LocalDate.of(1900, 12, 12), Gender.MALE);
        assertNotNull(user.getId());
        assertEquals(user.getFirstName(), firstName);
        assertEquals(user.getLastName(), lastName);
        assertEquals(user.getEmail(), email);
    }

    @Test
    public void testCreateUserWithExistingEmail() {
        String firstName1 = "firstName" + rand.nextInt();
        String lastName1 = "lastName" + rand.nextInt();
        String email = UUID.randomUUID().toString() + "@test.com";
        LocalDate dateOfBirth = LocalDate.of(1900, 12, 12);
        subject.createUser(firstName1, lastName1 , "123456789",
                email, "123456", dateOfBirth, Gender.MALE);

        String firstName2 = "firstName" + rand.nextInt();
        String lastName2 = "lastName" + rand.nextInt();
        assertThrows(IllegalArgumentException.class, () -> {
            subject.createUser(firstName2, lastName2 , "123456789",
                    email, "123456", dateOfBirth, Gender.FEMALE);
        });
    }

    @Test
    public void testGetUserByCredentials() {
        String email = UUID.randomUUID().toString() + "@test.com";
        String password = "123456";
        User originalUser = subject.createUser("firstName" + rand.nextInt(), "lastName" + rand.nextInt()
                , "123456789", email, password,
                LocalDate.of(1900, 12, 12), Gender.MALE);

        User loggedInUser = subject.login(email, password);
        assertEquals(originalUser.getId(), loggedInUser.getId());
        assertEquals(originalUser.getFirstName(), loggedInUser.getFirstName());
        assertEquals(originalUser.getLastName(), loggedInUser.getLastName());
        assertEquals(originalUser.getEmail(), loggedInUser.getEmail());
    }

    @Test
    public void testUpdateUser() {
        String firstName = "firstName" + rand.nextInt();
        String lastName = "lastName" + rand.nextInt();
        String email = UUID.randomUUID().toString() + "@test.com";
        User originalUser = subject.createUser(firstName, lastName
                , "123456789", email, "123456",
                LocalDate.of(1900, 12, 12), Gender.MALE);
        String suffix = "#updated";
        subject.updateUserInfo(originalUser.getId(), firstName + suffix, lastName + suffix);
        User updatedUser = subject.get(originalUser.getId()).get();
        assertNotNull(updatedUser);
        assertEquals(originalUser.getId(), updatedUser.getId());
        assertEquals(originalUser.getFirstName() + suffix, updatedUser.getFirstName());
        assertEquals(originalUser.getLastName() + suffix, updatedUser.getLastName());
    }

    @Test
    public void testDeleteUser() {
        String firstName = "firstName" + rand.nextInt();
        String lastName = "lastName" + rand.nextInt();
        String email = UUID.randomUUID().toString() + "@test.com";
        User originalUser = subject.createUser(firstName, lastName
                , "123456789", email, "123456",
                LocalDate.of(1900, 12, 12), Gender.MALE);
        subject.delete(originalUser.getId());
        Optional<User> dbUserOptional = subject.get(originalUser.getId());
        assertFalse(dbUserOptional.isPresent());
    }
}