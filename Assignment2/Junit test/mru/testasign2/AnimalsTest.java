package mru.testasign2;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import mru.model.Animals;

public class AnimalsTest {

    private Animals animal;

    @Before
    public void setUp() throws Exception {
        animal = new Animals("123", "Elephant Toy", "ToyCo", 19.99, 10, 3, "Plush", 'M');
    }

    @Test
    public void testGetMaterial() {
        assertEquals("Plush", animal.getMaterial());
    }

    @Test
    public void testSetMaterial() {
        animal.setMaterial("Cotton");
        assertEquals("Cotton", animal.getMaterial());
    }

    @Test
    public void testGetSize() {
        assertEquals('M', animal.getSize());
    }

    @Test
    public void testSetSize() {
        animal.setSize('L');
        assertEquals('L', animal.getSize());
    }

}