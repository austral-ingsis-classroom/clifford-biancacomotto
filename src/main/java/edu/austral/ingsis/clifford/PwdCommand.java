package edu.austral.ingsis.clifford;

public class PwdCommand implements Command {
  private final FileSystemManagement fileSystem;

  public PwdCommand(FileSystemManagement fileSystem) {
    this.fileSystem = fileSystem;
  }

  @Override
  public String execute(String[] args) {
    if (args.length != 0) {
      return "Invalid usage. 'pwd' does not take any arguments.";
    }
    // cualquier cosa tipo pwd algo, da error de uso.
    return fileSystem.pwd();
  }
}
