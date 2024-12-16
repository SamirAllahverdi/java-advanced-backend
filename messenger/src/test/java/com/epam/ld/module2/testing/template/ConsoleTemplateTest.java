package com.epam.ld.module2.testing.template;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
@DisabledOnOs(OS.OTHER)
public class ConsoleTemplateTest {

    private Template template;

    @BeforeEach
    public void init() {
        template = new ConsoleTemplate();
    }


    @Test
    @Tag("required")
    public void shouldModeSupportValuesWithPlaceholderSyntax() {
        //given
        String nameValue = "#{Samir}";
        String nameKey = "#{name}";
        String valueWithPlaceholderSyntax = nameKey + "=" + nameValue;
        System.setIn(new ByteArrayInputStream(valueWithPlaceholderSyntax.getBytes()));
        //when
        Map<String, String> placeholders = template.getPlaceholders();

        //then
        String value = placeholders.get(nameKey);
        Assertions.assertEquals(nameValue, value);
    }

    @Test
    @Tag("required")
    public void shouldModeSupportLatin1Values() {
        //given
        String nameKey = "#{name}";
        String nameValue = "¡¢£¤¥¦§¨©ª«¬®¯°±²³´µ¶·¸¹º»¼½¾¿À?ÂÃÄÅÆÇÈÉÊËÌ?Î??ÑÒÓÔÕÖ×ØÙÚÛÜ?Þßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ}";
        String placeholderWithLatin1Value = nameKey + "=" + nameValue;
        System.setIn(new ByteArrayInputStream(placeholderWithLatin1Value.getBytes()));
        //when
        Map<String, String> placeholders = template.getPlaceholders();

        //then
        String value = placeholders.get(nameKey);
        Assertions.assertEquals(nameValue, value);
    }

}
