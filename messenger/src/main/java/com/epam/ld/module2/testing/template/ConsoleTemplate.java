package com.epam.ld.module2.testing.template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ConsoleTemplate extends Template{

    @Override
    public Map<String,String> getPlaceholders() {
        System.out.println("Please enter values for placeholders in as example in below:");
        System.out.println("#{name}=Samir, #{value1}=FirstValue, #{value2}=SecondValue");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String values = br.readLine();

            Map<String, String> placeholderValueMap = parseToMap(values);

            return placeholderValueMap;
        } catch (IOException ex) {
            throw new RuntimeException("Exception happened!", ex);
        }
    }

    private Map<String, String> parseToMap(String values) {
        Map<String, String> placeholderValueMap = new HashMap<>();

        for (String value : values.trim().split(",")) {
            String[] placeholderAndValue = value.split("=");
            placeholderValueMap.put(placeholderAndValue[0].trim(), placeholderAndValue[1].trim());
        }

        return placeholderValueMap;
    }
}
