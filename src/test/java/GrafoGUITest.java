

import org.junit.jupiter.api.Test;

import core.GrafoGUI;
import structures.LineSchedule;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GrafoGUITest {

    @Test
    void testCompararHoras() {
        assertTrue(GrafoGUI.GrafoPanel.compararHoras("12:00", "11:00"));
        assertFalse(GrafoGUI.GrafoPanel.compararHoras("10:00", "11:00"));
        assertTrue(GrafoGUI.GrafoPanel.compararHoras("10:30", "10:00"));
        assertFalse(GrafoGUI.GrafoPanel.compararHoras("10:00", "10:30"));
        assertFalse(GrafoGUI.GrafoPanel.compararHoras("10:00", "10:00"));
    }

   
    
}
