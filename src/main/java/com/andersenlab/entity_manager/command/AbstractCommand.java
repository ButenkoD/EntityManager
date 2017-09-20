package com.andersenlab.entity_manager.command;

public abstract class AbstractCommand {
    String result;
    public abstract AbstractCommand execute() throws Exception;
    public String getResult() {
        return result;
    };
}
