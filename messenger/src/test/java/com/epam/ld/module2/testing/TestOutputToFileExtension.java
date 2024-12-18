package com.epam.ld.module2.testing;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.FileWriter;
import java.time.LocalDateTime;

public class TestOutputToFileExtension implements AfterTestExecutionCallback {
    @Override
    public void afterTestExecution(ExtensionContext context) {

        StringBuilder builder = new StringBuilder(LocalDateTime.now().toString());
        context.getTestClass().ifPresent(t -> builder.append(" ").append(t.getSimpleName()));
        context.getTestMethod().ifPresent(t -> builder.append(".").append(t.getName()));

        builder.append('\n').append(context.getDisplayName());
        context.getExecutionException().ifPresentOrElse(
                e -> builder.append('\n').append("Failed: ").append(e.getMessage()),
                () -> builder.append('\n').append("Passed "));

        writeToFile(builder);
    }

    private void writeToFile(StringBuilder builder) {
        try (FileWriter fw = new FileWriter("files/testOutput.txt", true);) {
            fw.write("\n\n");
            fw.write(builder.toString());
        } catch (Exception ex) {
            throw new RuntimeException("Exception happened: ", ex);
        }
    }
}
