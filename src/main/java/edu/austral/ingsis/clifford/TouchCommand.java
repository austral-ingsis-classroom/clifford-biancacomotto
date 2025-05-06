package edu.austral.ingsis.clifford;

import java.util.Optional;

public class TouchCommand implements Command {
    private final FileSystemManagement fileSystem;

    public TouchCommand(FileSystemManagement fileSystem) {
        this.fileSystem = fileSystem;
    }

    @Override
    public String execute(String[] args) {
        if (args.length != 1) {
            return "Invalid usage. Expected one argument for 'touch'.";
        }

        String fileName = args[0];
        return fileSystem.touch(fileName)
                .orElse("Invalid file name.");
    }
}
