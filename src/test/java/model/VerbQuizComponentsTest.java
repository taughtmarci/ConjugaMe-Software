package model;

import model.Form;
import model.Group;
import model.Pronoun;
import model.VerbQuizComponents;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VerbQuizComponentsTest {

    VerbQuizComponents setupValidComps() {
        /*
        Instantiates a VerbQuizComponents, fills up with valid data
        Checks if the component container object evaluates itself valid
         */
        VerbQuizComponents comps = new VerbQuizComponents();
        assertFalse(comps.isWorkingCorrectly());

        comps.setParticipioPresenteSelected(true);
        comps.setParticipioPasadoSelected(false);

        comps.setWordAmount(10);

        comps.setDurationMin(4);
        comps.setDurationSec(50);

        comps.getSelectedGroups().add(new Group(0, "Conejito"));
        comps.getSelectedPronouns().add(Pronoun.Yo);
        comps.getSelectedForms().add(Form.SubjuntivoPresente);

        assertTrue(comps.isWorkingCorrectly());
        return comps;
    }

    @Test
    void setupInvalidComps() {
        /*
        Instantiates a VerbQuizComponents, fills up with invalid data
        Checks if the component container object evaluates itself invalid
         */
        VerbQuizComponents comps = setupValidComps();

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


        // no pronouns
        comps.getSelectedPronouns().remove(0);
        assertFalse(comps.isWorkingCorrectly());

        // back to OK
        comps.getSelectedPronouns().add(Pronoun.Usted);
        comps.getSelectedPronouns().add(Pronoun.Ustedes);
        assertTrue(comps.isWorkingCorrectly());

    }

    @Test
    void testFormOptions() {
        VerbQuizComponents comps = setupValidComps();

        // FORM SECTION
        // no form, no participio -> false
        comps.setParticipioPresenteSelected(false);
        comps.setParticipioPasadoSelected(false);
        comps.getSelectedForms().remove(0);
        assertFalse(comps.isWorkingCorrectly());

        // no form, 1 participio -> true
        comps.setParticipioPasadoSelected(true);
        assertTrue(comps.isWorkingCorrectly());

        // 1 form, 0 participio -> true
        comps.setParticipioPasadoSelected(false);
        comps.getSelectedForms().add(Form.IndicativoCondicional);
        assertTrue(comps.isWorkingCorrectly());

        // 1 form, 1 participio -> true
        comps.setParticipioPasadoSelected(true);
        assertTrue(comps.isWorkingCorrectly());

        // back to OK
        comps.getSelectedForms().add(Form.SubjuntivoFuturo);
        comps.getSelectedForms().add(Form.SubjuntivoImperfecto);
        comps.getSelectedForms().add(Form.IndicativoPreterito);
        comps.getSelectedForms().add(Form.ImperativoAffirmativo);
        assertTrue(comps.isWorkingCorrectly());
    }


}