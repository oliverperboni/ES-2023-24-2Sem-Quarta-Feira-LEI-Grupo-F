import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CSVFileChooserTest {

    @Test
    void testOpenTableWindow_FilePath() throws InterruptedException {
        // Criando uma instância da classe CSVFileChooser
        CSVFileChooser fileChooser = new CSVFileChooser();

        // Definindo o caminho do arquivo
        String filePath = "csv/HorarioDeExemplo.csv";
        // Não queremos carregar do Git
        boolean loadFromGit = false;

        Thread.sleep(3000);

        // Chamando o método a ser testado
        fileChooser.openTableWindow(filePath, loadFromGit);

        // Verificando se o caminho do arquivo foi atualizado corretamente
        assertEquals(filePath, fileChooser.getFilePath());
    }
}