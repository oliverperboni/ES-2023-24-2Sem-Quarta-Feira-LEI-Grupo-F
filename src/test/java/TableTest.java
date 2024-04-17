import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TableTest {

    @BeforeEach
    public void setUpHeadlessMode() {
        System.setProperty("java.awt.headless", "true");
    }

    @Test
    public void getWeekOfYear() {
        // Caso de teste 1: data válida
        assertEquals(1, Table.getWeekOfYear("01/01/2022"));

        // Caso de teste 2: data vazia
        assertEquals(-1, Table.getWeekOfYear(""));

        // Caso de teste 3: data inválida
        assertEquals(-1, Table.getWeekOfYear("32/01/2022"));

        // Caso de teste 4: data no formato diferente
        assertEquals(-1, Table.getWeekOfYear("2022-01-01"));
    }

    @Test
    public void countWeeksBetween() {

        // Caso de teste 1: datas válidas
        assertEquals(2, Table.countWeeksBetween("01/01/2022", "15/01/2022"));

        // Caso de teste 2: data de término vazia
        assertEquals(-1, Table.countWeeksBetween("01/01/2022", ""));

        // Caso de teste 3: data de início vazia
        assertEquals(-1, Table.countWeeksBetween("", "15/01/2022"));

        // Caso de teste 4: ambas as datas vazias
        assertEquals(-1, Table.countWeeksBetween("", ""));

        // Caso de teste 5: mesmo dia
        assertEquals(0, Table.countWeeksBetween("01/01/2022", "01/01/2022"));

        // Caso de teste 6: data de término antes da data de início
        assertEquals(-1, Table.countWeeksBetween("15/01/2022", "01/01/2022"));
    }
}