package controller;

import model.Group;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GroupHandlerTest {
    public GroupHandler handler = new GroupHandler();

    GroupHandlerTest() throws IOException {
    }

    @Test
    public void testGroupValidations() {
        assertEquals(handler.getDefaultVerified().size(), 5);

        Group prank = new Group(10, "prank group");
        assertFalse(handler.groupValidated(prank));

        ArrayList<Group> fakeGroups = new ArrayList<>();
        fakeGroups.add(prank);

        System.out.println(handler.getGroups().get(4));
        assertEquals(handler.getGroupNames().size(), 5);
        assertEquals(handler.getGroupByName("csirke"), new Group(-1, "undefined"));
    }
}