package edu.austral.ingsis.clifford;

public class MkdirCommand implements Command {
  private final FileSystemManagement fileSystem;

  public MkdirCommand(FileSystemManagement fileSystem) {
    this.fileSystem = fileSystem;
  }

  @Override
  public String execute(String[] args) {
    if (args.length != 1) {
      return "Invalid usage. Expected one argument for 'mkdir'.";
    } // Verifica que haya exactamente 1 argumento (el nombre del directorio).

    String directoryName = args[0];
    return fileSystem.mkdir(directoryName).orElse("Invalid directory name.");
  }
}
