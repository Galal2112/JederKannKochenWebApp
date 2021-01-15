import com.example.application.Application;
import com.example.application.data.entity.Gender;
import com.example.application.data.entity.Rezept;
import com.example.application.data.entity.User;
import com.example.application.data.entity.Video;
import com.example.application.data.service.RezeptService;
import com.example.application.data.service.UserService;
import com.example.application.data.service.VideoService;
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
public class VideoServiceTest {

    @Autowired
    VideoService subject;
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
    public void testCreateVideo() {
        String link = getTestUrl();
        Video video = subject.addVideoToRezept(rezept, link);
        assertNotNull(video.getId());
        assertEquals(video.getLink(), link);
    }

    @Test
    public void testRezeptVideos() {
        Video video1 = subject.addVideoToRezept(rezept, getTestUrl());
        Video video2 = subject.addVideoToRezept(rezept, getTestUrl());
        Video video3 = subject.addVideoToRezept(rezept, getTestUrl());
        List<Video> originalList = new ArrayList<>(Arrays.asList(video1, video2, video3));
        List<Video> dbList = subject.getRezeptVideos(rezept.getId());
        assertEquals(originalList.size(), dbList.size());
        if (originalList.size() == dbList.size()) {
            for (int i = 0; i < originalList.size(); i ++) {
                assertEquals(originalList.get(i).getLink(), dbList.get(i).getLink());
            }
        }
    }

    @Test
    public void testDeleteVideo() {
        Video video1 = subject.addVideoToRezept(rezept, getTestUrl());
        Video video2 = subject.addVideoToRezept(rezept, getTestUrl());
        subject.delete(video1.getId());
        List<Video> dbList = subject.getRezeptVideos(rezept.getId());
        assertEquals(dbList.size(), 1);
        assertEquals(dbList.get(0).getLink(), video2.getLink());
    }

    private String getTestUrl() {
        return "https://testUrl" + rand.nextInt() + ".com";
    }
}
