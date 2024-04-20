import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TableTest {

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

    @Test
    public void testSaveChangesSuccess() {
        // Caminho do arquivo a ser verificado
        String testFilePath = "csv/HorarioDeExemplo.csv";

        // Testa o método em caso de sucesso
        assertTrue(Table.saveChanges());

        // Verifica se o arquivo foi criado e contém os dados corretos
        try {
            List<String> lines = Files.readAllLines(new File(testFilePath).toPath());
            assertEquals(2, lines.size());
            assertTrue(lines.get(1).contains("Teste 101"));
        } catch (IOException e) {
            fail("Falha ao ler o arquivo: " + e.getMessage());
        }
    }

    @Test
    public void testSaveChangesFailure() {
        // Simula uma falha ao tentar salvar
        Table.filePath = "/invalido/teste.csv";

        // Testa o método em caso de falha
        assertFalse(Table.saveChanges());
    }
}