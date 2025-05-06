package edu.austral.ingsis;

import edu.austral.ingsis.clifford.*;
import java.util.ArrayList;
import java.util.List;

public class FileSystemRunner {

  private final FileSystemManagement fileSystem = new FileSystemManagement();

  public List<String> executeCommands(List<String> commands) {
    List<String> results = new ArrayList<>();

    for (String commandLine : commands) {
      String[] parts = commandLine.split(" ");
      String commandName = parts[0];
      String[] args = new String[parts.length - 1];
      System.arraycopy(parts, 1, args, 0, args.length);

      Command command = getCommand(commandName);
      if (command == null) {
        results.add("Unknown command: " + commandName);
      } else {
        results.add(command.execute(args));
      }
    }

    return results;
  }

  private Command getCommand(String name) {
    return switch (name) {
      case "ls" -> new LsCommand(fileSystem);
      case "cd" -> new CdCommand(fileSystem);
      case "mkdir" -> new MkdirCommand(fileSystem);
      case "touch" -> new TouchCommand(fileSystem);
      case "pwd" -> new PwdCommand(fileSystem);
      case "rm" -> new RmCommand(fileSystem);
      default -> null;
    };
  }
}
