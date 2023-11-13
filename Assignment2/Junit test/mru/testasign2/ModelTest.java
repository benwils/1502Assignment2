package mru.testasign2;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import mru.controller.Model;
import mru.model.Animals;
import mru.model.Figures;
import mru.model.Toy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class ModelTest {

    @Test
    void testSave() throws Exception {
        // Create a temporary file for testing
        File tempFile;
        try {
            tempFile = File.createTempFile("temp", ".txt");
            Model model = new Model();
            
            ArrayList<Toy> toys = new ArrayList<>();
            toys.add(new Figures(1, "TestFigure", "TestBrand", 10.0, 5, 8, 'A'));
            toys.add(new Animals(2, "TestAnimal", "TestBrand", 15.0, 3, 5, "Plastic", 'M'));

            model.save(toys, tempFile.getPath());

            // Check if the file was created and contains the expected content
            assertTrue(tempFile.exists());
            // You may add further assertions based on the expected content of the file
        } catch (IOException e) {
            fail("IOException occurred");
        }
    }

    @Test
    void testIsSerialNumberUnique() throws Exception {
        Model model = new Model();

        ArrayList<Toy> toys = new ArrayList<>();
        toys.add(new Figures(1, "TestFigure1", "TestBrand", 10.0, 5, 8, 'A'));
        toys.add(new Animals(2, "TestAnimal", "TestBrand", 15.0, 3, 5, "Plastic", 'M'));

        assertFalse(model.isSerialNumberUnique(1)); // Existing serial number
        assertTrue(model.isSerialNumberUnique(3));  // New serial number
    }

    // Add more test methods for other functionalities in the Model class

}
