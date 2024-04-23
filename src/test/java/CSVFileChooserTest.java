import core.CSVFileChooser;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;

import static java.awt.event.KeyEvent.*;
import static org.apache.commons.lang3.SystemUtils.IS_OS_MAC;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CSVFileChooserTest {

    @Test
    public void testOpenTableWindow_FilePath() throws InterruptedException, AWTException {

        // Definindo o caminho do arquivo
        String filePath = "csv/HorarioDeExemplo.csv";
        StringSelection stringSelection = new StringSelection(filePath);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);

        Robot testRobot = new Robot();
        // Criando uma instância da classe core.CSVFileChooser
        CSVFileChooser fileChooser = new CSVFileChooser();

        int firstKey = IS_OS_MAC ? VK_META : VK_CONTROL;
        testRobot.keyPress(firstKey);
        testRobot.delay(100);
        testRobot.keyPress(VK_V);
        testRobot.keyRelease(firstKey);
        testRobot.keyRelease(VK_V);

        testRobot.mouseMove(300, 120);
        testRobot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        testRobot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        boolean loadFromGit = false;

        Thread.sleep(10000);

        // Chamando o método a ser testado
//        fileChooser.openTableWindow(filePath, loadFromGit);

        // Verificando se o caminho do arquivo foi atualizado corretamente
        assertEquals(filePath, fileChooser.getScheduleFilePath());
    }
}