package com.andersenlab.entity_manager.command;

public class HelpCommand extends AbstractCommand {
    public AbstractCommand execute() throws Exception {
        result = "Acceptable command's format is: 'action' 'entity' [params]\n"
            + "Available commands are create|remove|show\n"
            + "Available entities are customer|product|purchase\n";
        return this;
    }
}
