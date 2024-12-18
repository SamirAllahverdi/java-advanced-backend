package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;
import com.epam.ld.module2.testing.PlaceHolderNotFoundException;
import com.epam.ld.module2.testing.TestOutputToFileExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static com.epam.ld.module2.testing.template.TemplateEngine.PLACEHOLDERS;
import static com.epam.ld.module2.testing.template.TemplateEngine.TEMPLATE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, TestOutputToFileExtension.class})
@DisabledOnOs(OS.OTHER)
public class TemplateEngineTest {

    private Map<String, String> placeholderValues;
    @Spy
    private TemplateEngine engine;
    @Mock
    private FileTemplate template;

    @BeforeEach
    public void init() {
        engine = new TemplateEngine();
        placeholderValues = buildMap();
    }

    private static Map<String, String> buildMap() {
        return new HashMap<>(Map.of(PLACEHOLDERS.get(0), "Samir", PLACEHOLDERS.get(1), "Allahverdiyev"));
    }

    private static String buildTemplate(Map<String, String> map) {
        for (Map.Entry<String, String> ip : map.entrySet()) {
            TEMPLATE = TEMPLATE.replace(ip.getKey(), ip.getValue());
        }

        return TEMPLATE;
    }

    public static Stream<Arguments> arguments() {
        // regular
        Map<String, String> placeholderValues1 = buildMap();
        String template = buildTemplate(placeholderValues1);

        Map<String, String> map = new HashMap<>(Map.copyOf(placeholderValues1));

        // with placeHolder syntax
        map.put(PLACEHOLDERS.get(0), "#{Samir}");

        //with latin chars
        map.put(PLACEHOLDERS.get(1), "¯°±²³´µ¶·¸¹º»¼½");
        String templateSecond = buildTemplate(map);

        return Stream.of(
                Arguments.of(placeholderValues1, template),
                Arguments.of(map, templateSecond)
        );
    }

    @ParameterizedTest
    @MethodSource("arguments")
    @Tag("required")
    public void parametrizedTest(Map<String, String> placeholder, String expected) {
        //when
        when(template.getPlaceholders()).thenReturn(placeholder);
        String message = engine.generateMessage(template, new Client());
        //then
        assertEquals(expected, message);
    }

    @Test()
    @Tag("required")
    public void shouldGeneratorThrowExceptionIfExpectedValueNotFound() {
        //given
        placeholderValues.remove(PLACEHOLDERS.get(0));
        //when
        PlaceHolderNotFoundException exception = assertThrows(PlaceHolderNotFoundException.class,
                () -> engine.generateMessage(template, new Client()));
        //then
        String expected = String.format("Placeholder with '%s' was not found!", PLACEHOLDERS.get(0));
        Assertions.assertEquals(expected, exception.getMessage());
    }

    @Test
    @Tag("required")
    public void shouldGeneratorIgnoreUnexpectedValues() {
        //given
        placeholderValues.put("#{Hi}", "#{unexpectedValue}");
        String templ = buildTemplate(placeholderValues);
        //when
        when(template.getPlaceholders()).thenReturn(placeholderValues);
        String message = engine.generateMessage(template, new Client());
        //then
        assertEquals(templ, message);
    }
}
