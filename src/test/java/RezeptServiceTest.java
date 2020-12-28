import com.example.application.Application;
import com.example.application.data.entity.Gender;
import com.example.application.data.entity.Rezept;
import com.example.application.data.entity.User;
import com.example.application.data.service.RezeptService;
import com.example.application.data.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RezeptServiceTest {

    @Autowired
    RezeptService subject;
    @Autowired
    UserService userService;
    private final Random rand = new Random();
    private User creator;

    @Before
    public void setup() {
        String firstName = "firstName" + rand.nextInt();
        String lastName = "lastName" + rand.nextInt();
        String email = UUID.randomUUID().toString() + "@test.com";
        creator = userService.createUser(firstName, lastName
                , "123456789", email, "123456",
                LocalDate.of(1900, 12, 12), Gender.MALE);
    }

    @Test
    public void testCreateRezept() {
        String rezeptName = "rezeptName" + rand.nextInt();
        String inhalt = "inhalt" + rand.nextInt();
        Rezept rezept = subject.createRezept(creator, rezeptName, inhalt);
        assertNotNull(rezept.getId());
        assertEquals(rezept.getRezeptName(), rezeptName);
        assertEquals(rezept.getInhalt(), inhalt);
    }

    @Test
    public void testGetRezeptCount() {
        subject.createRezept(creator, "rezeptName" + rand.nextInt(), "inhalt" + rand.nextInt());
        subject.createRezept(creator, "rezeptName" + rand.nextInt(), "inhalt" + rand.nextInt());
        int rezeptCount = subject.getUserRezepteCount(creator.getId());
        assertEquals(rezeptCount, 2);
    }

    @Test
    public void testGetUserRezepte() {
        String rezeptName1 = "rezeptName" + rand.nextInt();
        String inhalt1 = "inhalt" + rand.nextInt();
        Rezept rezept1 = subject.createRezept(creator, rezeptName1, inhalt1);
        String rezeptName2 = "rezeptName" + rand.nextInt();
        String inhalt2 = "inhalt" + rand.nextInt();
        Rezept rezept2 = subject.createRezept(creator, rezeptName2, inhalt2);
        List<Rezept> rezepte = subject.getUserRezepte(creator.getId());
        assertEquals(rezepte.size(), 2);
        assertEquals(rezepte.get(0).getRezeptName(), rezept1.getRezeptName());
        assertEquals(rezepte.get(0).getInhalt(), rezept1.getInhalt());
        assertEquals(rezepte.get(1).getRezeptName(), rezept2.getRezeptName());
        assertEquals(rezepte.get(1).getInhalt(), rezept2.getInhalt());
    }

    @Test
    public void testUpdateRezept() {
        Rezept rezept = subject.createRezept(creator, "rezeptName" + rand.nextInt(), "inhalt" + rand.nextInt());
        String suffix = "#updated";
        subject.updateRezept(rezept.getId(), rezept.getRezeptName() + suffix, rezept.getInhalt() + suffix);
        Rezept dbRezept = subject.get(rezept.getId()).get();
        assertNotNull(dbRezept);
        assertEquals(rezept.getRezeptName() + suffix, dbRezept.getRezeptName());
        assertEquals(rezept.getInhalt() + suffix, dbRezept.getInhalt());
    }

    @Test
    public void testDeleteRezept() {
        Rezept rezept = subject.createRezept(creator, "rezeptName" + rand.nextInt(), "inhalt" + rand.nextInt());
        subject.delete(rezept.getId());
        Optional<Rezept> dbRezept = subject.get(rezept.getId());
        assertFalse(dbRezept.isPresent());
    }
}
