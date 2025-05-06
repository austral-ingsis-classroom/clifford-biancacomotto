package edu.austral.ingsis.clifford;

import java.util.Optional;

public class CdCommand implements Command {
    private final FileSystemManagement fileSystem;

    public CdCommand(FileSystemManagement fileSystem) {
        this.fileSystem = fileSystem;
    }

    @Override
    public String execute(String[] args) {
        if (args.length != 1) {
            return "Invalid usage. Expected one argument for 'cd'.";
        }

        String path = args[0];
        return fileSystem.cd(path)
                .orElse("'" + path + "' directory does not exist");
    }
}