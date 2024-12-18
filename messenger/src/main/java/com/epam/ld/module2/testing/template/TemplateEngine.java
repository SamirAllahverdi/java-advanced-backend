package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;
import com.epam.ld.module2.testing.PlaceHolderNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * The type Template engine.
 */
public class TemplateEngine {

    public static final List<String> PLACEHOLDERS = Arrays.asList("#{name}", "#{surname}");
    public static String TEMPLATE = String.format("Hi %s %s! Have a good day, Best Regards, Samir", PLACEHOLDERS.get(0), PLACEHOLDERS.get(1));

    /**
     * Generate message string.
     *
     * @param template the template
     * @param client   the client
     * @return the string
     */
    public String generateMessage(Template template, Client client) {
        final Map<String, String> placeholders = template.getPlaceholders();

        validateValues(placeholders);
        for (Map.Entry<String, String> ip : placeholders.entrySet()) {
            TEMPLATE = TEMPLATE.replace(ip.getKey(), ip.getValue());
        }
        return TEMPLATE;
    }


    private void validateValues(Map<String, String> placeholderValueMap) {

        for (String placeholder : PLACEHOLDERS) {
            if (!placeholderValueMap.containsKey(placeholder)) {
                throw new PlaceHolderNotFoundException(placeholder);
            }
        }

        List<String> unexpectedValues = new ArrayList<>();
        for (Map.Entry<String, String> placeholderValue : placeholderValueMap.entrySet()) {
            if (!PLACEHOLDERS.contains(placeholderValue.getKey())) {
                unexpectedValues.add(placeholderValue.getKey());
            }
        }

        unexpectedValues.forEach(placeholderValueMap::remove);
    }

}
