package com.andersenlab.entity_manager.command;

import com.andersenlab.entity_manager.ConsoleApplication;
import com.andersenlab.entity_manager.entity.AbstractRepository;
import com.andersenlab.entity_manager.entity.CustomerRepository;
import com.andersenlab.entity_manager.entity.ProductRepository;
import com.andersenlab.entity_manager.entity.RepositoryFactory;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandFactory {
    static final String RUN_HELP_NESSAGE = "Run \"help\" to see correct format";
    private static final String INVALID_COMMAND_MESSAGE = "Invalid command. "
            + RUN_HELP_NESSAGE;
    private final static int CHUNK_POSITION_COMMAND = 0;
    private final static int CHUNK_POSITION_MODEL = 1;
    private final static int CHUNK_POSITION_PARAMS_BEGINNING = 2;

    public static AbstractCommand create(String[] chunks) throws Exception {
        if (chunks.length == 1) {
            switch (chunks[CHUNK_POSITION_COMMAND]) {
                case "help":
                    return new HelpCommand();
                case "exit":
                    return new ExitCommand();
            }
        }
        if (chunks.length > 1) {
            return createRepositoryCommand(chunks);
        }
        throw new IllegalArgumentException(INVALID_COMMAND_MESSAGE);
    }

    private static AbstractCommand createRepositoryCommand(String[] chunks) throws Exception {
        AbstractRepository repository = RepositoryFactory.createRepository(chunks[CHUNK_POSITION_MODEL]);
        ArrayList<String> params = new ArrayList<>(
            Arrays.asList(chunks).subList(CHUNK_POSITION_PARAMS_BEGINNING, chunks.length)
        );
        return new RepositoryCommand(repository, chunks[CHUNK_POSITION_COMMAND], params);
    }
}
