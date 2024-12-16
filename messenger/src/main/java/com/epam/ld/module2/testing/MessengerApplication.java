package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.template.ConsoleTemplate;
import com.epam.ld.module2.testing.template.FileTemplate;
import com.epam.ld.module2.testing.template.TemplateEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MessengerApplication {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Messenger Application!");
        System.out.println("Type 1 for console mode and 2 for file mode");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String opt = br.readLine();
        Messenger messenger = new Messenger(new MailServer(), new TemplateEngine());
        switch (opt) {
            case "1":
                messenger.sendMessage(new Client(), new ConsoleTemplate());
                break;
            case "2":
                messenger.sendMessage(new Client(), new FileTemplate(args));
                break;
            default:
                throw new IllegalArgumentException("Invalid typing..");
        }
    }
}
