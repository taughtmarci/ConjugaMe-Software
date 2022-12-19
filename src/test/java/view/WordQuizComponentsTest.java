package view;

import model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordQuizComponentsTest {
    WordQuizComponents setupValidComps() {
        /*
        Instantiates a WordQuizComponents, fills up with valid data
        Checks if the component container object evaluates itself valid
         */
        WordQuizComponents comps = new WordQuizComponents();
        assertFalse(comps.isWorkingCorrectly());

        comps.setWordAmount(10);

        comps.setDurationMin(4);
        comps.setDurationSec(50);

        comps.getSelectedGroups().add(new Group(2, "Intermedio"));
        assertTrue(comps.isWorkingCorrectly());

        return comps;
    }

    @Test
    void setupInvalidComps() {
        /*
        Instantiates a WordQuizComponents, fills up with invalid data
        Checks if the component container object evaluates itself invalid
         */
        WordQuizComponents comps = setupValidComps();

        // greater word amount
        comps.setWordAmount(90000000);
        assertFalse(comps.isWorkingCorrectly());

        // smaller word amount
        comps.setWordAmount(0);
        assertFalse(comps.isWorkingCorrectly());

        // back to OK
        comps.setWordAmount(35);
        assertTrue(comps.isWorkingCorrectly());


        // greater duration minutes
        comps.setDurationMin(1000);
        assertFalse(comps.isWorkingCorrectly());

        // smaller duration minutes
        comps.setDurationMin(0);
        assertFalse(comps.isWorkingCorrectly());

        // back to OK
        comps.setDurationMin(3);
        assertTrue(comps.isWorkingCorrectly());


        // greater duration seconds
        comps.setDurationSec(60);
        assertFalse(comps.isWorkingCorrectly());

        // smaller duration seconds
        comps.setDurationSec(-1);
        assertFalse(comps.isWorkingCorrectly());

        // back to OK
        comps.setDurationSec(0);
        assertTrue(comps.isWorkingCorrectly());


        // no groups
        comps.getSelectedGroups().remove(0);
        assertFalse(comps.isWorkingCorrectly());

        // back to OK
        comps.getSelectedGroups().add(new Group(3, "Avanzado"));
        comps.getSelectedGroups().add(new Group(4, "Experto"));
        assertTrue(comps.isWorkingCorrectly());


    }


}