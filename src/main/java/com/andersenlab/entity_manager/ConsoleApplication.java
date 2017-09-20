package com.andersenlab.entity_manager;

import com.andersenlab.entity_manager.command.AbstractCommand;
import com.andersenlab.entity_manager.command.CommandFactory;

import java.util.Scanner;

public class ConsoleApplication {
    private static final String WELLCOME_TEXT = "Waiting for the next command...";
    private static final String COMMAND_CHUNKS_DELIMITER = " ";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(WELLCOME_TEXT);
            String line = scanner.nextLine();
            try {
                AbstractCommand command = CommandFactory.create(line.split(COMMAND_CHUNKS_DELIMITER));
                System.out.println(command.execute().getResult());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
