package com.epam.ld.module2.testing.template;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Spy;

import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

@DisabledOnOs(OS.OTHER)
public class FileTemplateTest {
    private final String[] TEST_ARGS = {"--inputFile=files/input.txt", "--outputFile=files/output.txt"};
    @Spy
    private FileTemplate template;

    public FileTemplateTest() {
        this.template = spy(new FileTemplate(TEST_ARGS));
    }

    @Test
    @Tag("additional")
    public void shouldFileModeThrowExceptionInputOutputFileNotDefined() {
        //given
        //when
        RuntimeException outputFileMissed = assertThrows(RuntimeException.class,
                () -> new FileTemplate(TEST_ARGS[0]));
        RuntimeException inputFileMissed = assertThrows(RuntimeException.class,
                () -> new FileTemplate(TEST_ARGS[1]));
        //then
        String expected = "Input or Output file is not entered correctly in program arguments";
        Assertions.assertEquals(expected, outputFileMissed.getMessage());
        Assertions.assertEquals(expected, inputFileMissed.getMessage());
    }


    @Test
    @Tag("required")
    public void shouldFileModeReadLatin1CharsFromFile(@TempDir Path tempDir) {
        //given
        Path path = tempDir.resolve("inputTest.txt");
        String latinValue = "¡¢£¤¥¦§¨©ª«¬®¯°±²³´µ¶·¸¹º»¼½¾¿À?ÂÃÄÅÆÇÈÉÊËÌ?Î??ÑÒÓÔÕÖ×ØÙÚÛÜ?Þßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ";
        createInputFile(latinValue, path);

        //when
        doReturn(path.toString()).when(template).getInputFile();
        Map<String, String> placeholders = template.getPlaceholders();

        //then
        assertArrayEquals(latinValue.getBytes(Charset.defaultCharset()), placeholders.get("#{name}").getBytes());
    }


    private void createInputFile(String latinValue, Path path) {
        try (FileOutputStream fos = new FileOutputStream(path.toString())) {
            String placeholderAndValue = "#{name}: " + latinValue;
            fos.write(placeholderAndValue.getBytes(Charset.defaultCharset()));
        } catch (Exception ex) {
            throw new RuntimeException("Exception happened!", ex);
        }
    }
}
