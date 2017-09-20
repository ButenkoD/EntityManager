package com.andersenlab.entity_manager.command;

import com.andersenlab.entity_manager.entity.AbstractRepository;

import java.lang.reflect.Method;
import java.util.List;

public class RepositoryCommand extends AbstractCommand {
    private static final String INVALID_ACTION_MESSAGE = "Invalid command: ";
    private final List<String> params;
    private final AbstractRepository repository;
    private final String commandName;

    RepositoryCommand(AbstractRepository repository, String commandName, List<String> params) throws Exception {
        if (!repository.hasAccessibleMethod(commandName)) {
            throw new Exception(INVALID_ACTION_MESSAGE + commandName + "\n" + CommandFactory.RUN_HELP_NESSAGE);
        }
        this.repository = repository;
        this.commandName = commandName;
        this.params = params;
    }

    public AbstractCommand execute() throws Exception {
        Method method = params.isEmpty()
            ? repository.getClass().getMethod(commandName)
            : repository.getClass().getMethod(commandName, List.class);
        result = params.isEmpty()
            ? (String) method.invoke(repository)
            : (String) method.invoke(repository, params);
        return this;
    }
}
