import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {

    @Test
    void getWeekOfYear() {
    }

    @Test
    void countWeeksBetween() {
        // Criação de um objeto da sua classe que contém o método countWeeksBetween
        Table suaClasse = new Table(); // Substitua "SuaClasse" pelo nome real da sua classe

        // Caso de teste 1: datas válidas
        assertEquals(2, suaClasse.countWeeksBetween("01/01/2022", "15/01/2022"));

        // Caso de teste 2: data de término vazia
        assertEquals(-1, suaClasse.countWeeksBetween("01/01/2022", ""));

        // Caso de teste 3: data de início vazia
        assertEquals(-1, suaClasse.countWeeksBetween("", "15/01/2022"));

        // Caso de teste 4: ambas as datas vazias
        assertEquals(-1, suaClasse.countWeeksBetween("", ""));

        // Caso de teste 5: mesmo dia
        assertEquals(0, suaClasse.countWeeksBetween("01/01/2022", "01/01/2022"));

        // Caso de teste 6: data de término antes da data de início
        assertEquals(-1, suaClasse.countWeeksBetween("15/01/2022", "01/01/2022"));
    }
}