import com.example.application.Application;
import com.example.application.data.entity.FoodProduct;
import com.example.application.data.service.FoodProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class FoodProductServiceTest {

    @Autowired
    FoodProductService subject;

    @Test
    public void testCreate() {
        FoodProduct foodProduct = new FoodProduct("test name", "test ean code");
        assertNull(foodProduct.getId());
        subject.update(foodProduct);
        assertNotNull(foodProduct.getId());
    }

    @Test
    public void testRead() {
        FoodProduct foodProduct = new FoodProduct("test name", "test ean code");
        subject.update(foodProduct);
        assertNotNull(foodProduct.getId());
        Optional<FoodProduct> dbFoodProductOptional = subject.get(foodProduct.getId());
        assertTrue(dbFoodProductOptional.isPresent());
        FoodProduct dbFoodProduct = dbFoodProductOptional.get();
        assertEquals(dbFoodProduct.getId(), foodProduct.getId());
        assertEquals(dbFoodProduct.getName(), foodProduct.getName());
        assertEquals(dbFoodProduct.getEanCode(), foodProduct.getEanCode());
        assertEquals(dbFoodProduct.getImage(), foodProduct.getImage());
        assertNull(dbFoodProduct.getImage());
    }

    @Test
    public void testUpdate() {
        FoodProduct foodProduct = new FoodProduct("test name", "test ean code");
        subject.update(foodProduct);
        assertNotNull(foodProduct.getId());
        String updatedName = "updated name";
        foodProduct.setName(updatedName);
        subject.update(foodProduct);
        Optional<FoodProduct> dbFoodProductOptional = subject.get(foodProduct.getId());
        assertTrue(dbFoodProductOptional.isPresent());
        FoodProduct dbFoodProduct = dbFoodProductOptional.get();
        assertEquals(dbFoodProduct.getId(), foodProduct.getId());
        assertEquals(dbFoodProduct.getName(), updatedName);
        assertEquals(dbFoodProduct.getEanCode(), foodProduct.getEanCode());
        assertEquals(dbFoodProduct.getImage(), foodProduct.getImage());
    }

    @Test
    public void testDelete() {
        FoodProduct foodProduct = new FoodProduct("test name", "test ean code");
        subject.update(foodProduct);
        assertNotNull(foodProduct.getId());
        subject.delete(foodProduct.getId());
        Optional<FoodProduct> dbFoodProductOptional = subject.get(foodProduct.getId());
        assertFalse(dbFoodProductOptional.isPresent());
    }
}
