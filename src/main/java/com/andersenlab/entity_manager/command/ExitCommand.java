package com.andersenlab.entity_manager.command;

import com.andersenlab.entity_manager.ConsoleApplication;

public class ExitCommand extends AbstractCommand {
    public AbstractCommand execute() throws Exception {
        result = "Bye!";
        ConsoleApplication.exitApplication();
        return this;
    }
}
