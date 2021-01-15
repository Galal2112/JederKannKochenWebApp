import com.example.application.Application;
import com.example.application.data.entity.Gender;
import com.example.application.data.entity.Rezept;
import com.example.application.data.entity.User;
import com.example.application.data.entity.Zutat;
import com.example.application.data.service.RezeptService;
import com.example.application.data.service.UserService;
import com.example.application.data.service.ZutatService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ZutatServiceTest {

    @Autowired
    ZutatService subject;
    @Autowired
    UserService userService;
    @Autowired
    RezeptService rezeptService;
    private final Random rand = new Random();
    private User creator;
    private Rezept rezept;

    @Before
    public void setup() {
        String firstName = "firstName" + rand.nextInt();
        String lastName = "lastName" + rand.nextInt();
        String email = UUID.randomUUID().toString() + "@test.com";
        creator = userService.createUser(firstName, lastName
                , "123456789", email, "123456",
                LocalDate.of(1900, 12, 12), Gender.MALE);
        rezept = rezeptService.createRezept(creator, "Test rezept", "Test inhalt");
    }

    @Test
    public void testCreateZutat() {
        double menge = rand.nextDouble();
        String item = "Item" + rand.nextInt();
        Zutat zutat = subject.addZutatToRezept(rezept, menge, item);
        assertNotNull(zutat.getId());
        assertEquals(zutat.getMenge(), menge, 0);
        assertEquals(zutat.getItem(), item);
    }

    @Test
    public void testRezeptZutaten() {
        Zutat zutat1 = subject.addZutatToRezept(rezept, rand.nextDouble(), "Item" + rand.nextInt());
        Zutat zutat2 = subject.addZutatToRezept(rezept, rand.nextDouble(), "Item" + rand.nextInt());
        Zutat zutat3 = subject.addZutatToRezept(rezept, rand.nextDouble(), "Item" + rand.nextInt());

        List<Zutat> originalList = new ArrayList<>(Arrays.asList(zutat1, zutat2, zutat3));
        List<Zutat> dbList = subject.getRezeptZutaten(rezept.getId());
        assertEquals(originalList.size(), dbList.size());
        if (originalList.size() == dbList.size()) {
            for (int i = 0; i < originalList.size(); i ++) {
                assertEquals(originalList.get(i).getMenge(), dbList.get(i).getMenge(), 0);
                assertEquals(originalList.get(i).getItem(), dbList.get(i).getItem());
            }
        }
    }

    @Test
    public void testDeleteZutat() {
        Zutat zutat1 = subject.addZutatToRezept(rezept, rand.nextDouble(), "Item" + rand.nextInt());
        Zutat zutat2 = subject.addZutatToRezept(rezept, rand.nextDouble(), "Item" + rand.nextInt());
        subject.delete(zutat1.getId());
        List<Zutat> dbList = subject.getRezeptZutaten(rezept.getId());
        assertEquals(dbList.size(), 1);
        assertEquals(dbList.get(0).getMenge(), zutat2.getMenge(), 0);
        assertEquals(dbList.get(0).getItem(), zutat2.getItem());
    }
}
