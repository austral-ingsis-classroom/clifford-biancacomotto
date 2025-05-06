

package edu.austral.ingsis.clifford;

import java.util.Optional;

public class RmCommand implements Command {
    private final FileSystemManagement fileSystem;

    public RmCommand(FileSystemManagement fileSystem) {
        this.fileSystem = fileSystem;
    }

    @Override
    public String execute(String[] args) {
        if (args.length == 0 || args.length > 2) {
            return "Invalid usage. Usage: rm [--recursive] <name>";
        }

        boolean recursive = false;
        String name;

        if (args.length == 1) { //simple: rm archivo.txt
            name = args[0];
        } else { //Caso recursivo: rm --recursive carpeta
            // args.length == 2
            if (!args[0].equals("--recursive")) {
                return "Invalid usage. Did you mean '--recursive'?";
            }
            recursive = true;
            name = args[1];
        }

        return fileSystem.rm(name, recursive)
                .orElse("cannot remove '" + name + "', is a directory");
    }
}