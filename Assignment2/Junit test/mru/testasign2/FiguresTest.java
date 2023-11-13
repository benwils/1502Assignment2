package mru.testasign2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import mru.model.Figures;

class FiguresTest {


    @Test
    void testToStringDollClassification() {
        Figures dollFigure = new Figures(456, "Doll1", "BrandB", 15.99, 8, 4, 'D');
        String expected = "SN: 456, Name: Doll1, Brand: BrandB, Price: 15.99, Available Count: 8, Age Appropriate: 4 Classification: Doll";
        assertEquals(expected, dollFigure.toString());
    }

  
    }


