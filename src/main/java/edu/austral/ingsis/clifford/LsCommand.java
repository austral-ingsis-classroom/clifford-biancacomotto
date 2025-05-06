package edu.austral.ingsis.clifford;

import java.util.Optional;

public class LsCommand implements Command {
  private final FileSystemManagement fileSystem; // acceso para listar los archivos.

  public LsCommand(FileSystemManagement fileSystem) {
    this.fileSystem = fileSystem;
  }

  @Override
  public String execute(String[] args) {
    Optional<String> order = Optional.empty();

    if (args.length > 0 && args[0].startsWith("--ord=")) {
      // Ve si hay argumentos.  : Chequea si el argumento pide orden.
      String value = args[0].substring(6); // saca "--ord="
      // Corta el --ord= y se queda solo con asc o desc.
      order = Optional.of(value);
    }

    return fileSystem.ls(order).orElse("");
  }
}
