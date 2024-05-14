import structures.ScheduleDataModel;
import visualize.Table;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TableTest {

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
    public void testSaveChangesFailure() {
        // Caminho do arquivo a ser verificado
        String testFilePath = "csv/HorarioDeExemplo.csv";

        ScheduleDataModel model = new ScheduleDataModel("csv/HorarioDeExemplo.csv",
                false, "csv/CaracterizaçãoDasSalas.csv", false);

        // Simula uma falha ao tentar salvar
        try {
            Table table = new Table(model);
            table.dataModel.setScheduleFilePath("/invalido/teste.csv");

            // Testa o método em caso de falha
            Assertions.assertFalse(table.saveChanges());
        } catch (IOException e) {
            System.out.println("Failure captured successfully.");
        }
    }

}