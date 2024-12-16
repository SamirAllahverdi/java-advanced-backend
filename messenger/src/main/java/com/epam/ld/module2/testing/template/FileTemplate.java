package com.epam.ld.module2.testing.template;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class FileTemplate extends Template {
    private static final String PREFIX_INPUT_FILE = "--inputFile=";
    private static final String PREFIX_OUTPUT_FILE = "--outputFile=";
    private String inputFile;
    private String outputFile;

    public FileTemplate(String... args) {
        super();
        getInputAndOutputFile(args);
    }

    private void getInputAndOutputFile(String... args) {
        for (String arg : args) {
            if (arg.startsWith(PREFIX_INPUT_FILE)) {
                this.inputFile = arg.replace(PREFIX_INPUT_FILE, "");
            } else if (arg.startsWith(PREFIX_OUTPUT_FILE)) {
                this.outputFile = arg.replace(PREFIX_OUTPUT_FILE, "");
            }
        }

        if (inputFile == null || inputFile.isEmpty() || outputFile == null || outputFile.isEmpty()) {
            throw new RuntimeException("Input or Output file is not entered correctly in program arguments");
        }
    }

    @Override
    public Map<String, String> getPlaceholders() {
        Map<String, String> values = new HashMap<>();
        try (FileInputStream inputStream = new FileInputStream(getInputFile());
             InputStreamReader reader = new InputStreamReader(inputStream, "windows-1252");
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] placeholderAndValue = line.split(":");
                values.put(placeholderAndValue[0].trim(), placeholderAndValue[1].trim());
            }
        } catch (Exception ex) {
            throw new RuntimeException("Exception happened!", ex);
        }

        return values;
    }
    // used by tests
    public String getInputFile() {
        return inputFile;
    }
}
