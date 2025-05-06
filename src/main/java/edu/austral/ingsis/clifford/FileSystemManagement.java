package edu.austral.ingsis.clifford;

import java.util.*;
import java.util.stream.Collectors;

// separar comendo de file
public final class FileSystemManagement {
  private final Directory root;
  private Directory current;

  public FileSystemManagement() {
    this.root = new Directory("/", null); // raiz del file system, el null es  porquer no tiene papa
    this.current = root; // donde estamos parados ahora, puntero de navegación).
  }

  public Directory getRoot() {
    return root;
  }

  public Directory getCurrent() {
    return current;
  }

  public void setCurrent(Directory directory) {
    this.current = directory; // mover el cursor del filesystem.
    // cd nombre-directorio - necesito esto para movernos
  }

  // Crear un archivo en el directorio actual
  public Optional<String> touch(String name) {
    if (isInvalidName(name)) {
      return Optional.empty();
    }
    current.addChild(new File(name));
    return Optional.of("'" + name + "' file created");
  }

  // Crear un directorio en el directorio actual
  public Optional<String> mkdir(String name) {
    if (isInvalidName(name)) {
      return Optional.empty(); // Optional.empty() SI HAY ERROR
    }
    current.addChild(new Directory(name, current));
    return Optional.of(
        "'" + name + "' directory created"); // Optional.of(mensaje) SI TODO ESTA BIEN
  }

  // Ayudante privado para validar nombres
  private boolean isInvalidName(String name) {
    return name.contains("/") || name.contains(" ");
  }

  public Optional<String> ls(Optional<String> order) {
    List<FileSystem> children = new ArrayList<>(current.getChildren());

    if (order.isPresent()) {
      switch (order.get()) {
        case "asc" -> children.sort(Comparator.comparing(FileSystem::getName));
        case "desc" -> children.sort((a, b) -> b.getName().compareTo(a.getName()));
          // Si no es "asc" ni "desc", no se ordena → mantiene orden de creación
      }
    }

    String result = children.stream().map(FileSystem::getName).collect(Collectors.joining(" "));

    return Optional.of(result);
  }

  /*public Optional<String> ls(Optional<String> order) { //recibe unorden desc, normal o asc
          List<FileSystem> children = new ArrayList<>(current.getChildren()); // todos los archivos y carpetas del directorio actual (current).
  //Optional<String> devuelve siempre lista de archivos
           //Copiamos esa lista en una nueva (ArrayList) porque queremos ordenarla si es necesario.
          if (children.isEmpty()) {
              return Optional.of(""); // Directorio vacío
          }

          // Ordenar si corresponde
          if (order.isPresent()) { //isPresent() de un Optional metodo optional t/f
              switch (order.get()) { //switch para ver qué tipo de orden quiere el usuario.
                  //.get() de un Optional quiere decir "dame el valor que hay adentro".
                  case "asc":
                      children.sort((a, b) -> a.getName().compareTo(b.getName()));
                      break;
                  case "desc":
                      children.sort((a, b) -> b.getName().compareTo(a.getName()));
                      break;
                  default:
                      // No ordenar si el parámetro no es válido
                      break;
              }
          }

          // Armar el resultado
          StringBuilder builder = new StringBuilder();
          for (FileSystem node : children) { //for-each:
              builder.append(node.getName()).append(" ");
          } //agregando el nombre de cada archivo/carpeta seguido de un espacio " "

          return Optional.of(builder.toString().trim()); //Devolvemos el resultado como un Optional<String>
      }*/
  // hacemos CD
  // . es donde estas
  // .. subimos al padre (si hay )ç
  // si empieza con / buscamos desde raiz , sino desde donde estamos parados
  /*En cada parte:
  Buscamos un hijo con ese nombre.
  Si existe y es un directorio, nos movemos a él.
  Si no, fallamos (porque no existe o es un archivo).*/

  public Optional<String> cd(String path) {
    if (path.equals(".")) {
      return Optional.of("Stayed in current directory.");
    }

    if (path.equals("..")) {
      if (current.getParent() != null) {
        current = current.getParent();
      } // si no hay parent, ya estás en raíz, no cambies nada
      return Optional.of("moved to directory '/'");
    }

    // Si empieza con "/", voy con raíz
    Directory start = path.startsWith("/") ? root : current;
    String[] parts = path.split("/"); // Separamos la ruta
    Directory pointer = start;

    for (String part : parts) {
      if (part.isEmpty() || part.equals(".")) {
        continue; // Ignorar partes vacías o "."
      }
      Optional<FileSystem> next = pointer.getChild(part);
      if (next.isPresent() && next.get().isDirectory()) {
        pointer = (Directory) next.get();
      } else {
        return Optional.empty(); // No existe o no es un directorio
      }
    }

    current = pointer;
    return Optional.of("moved to directory '" + current.getName() + "'");
  }

  /* Imprimir la ruta completa desde la raíz / hasta donde estás parado.
  Ejemplo:
  Si estás en la raíz → /
  Si estás en una carpeta musica/rock → /musica/rock*/

  //  PWD

  public String pwd() {
    List<String> pathParts = new ArrayList<>();
    Directory pointer = current;

    while (pointer != null && pointer.getParent() != null) { // OJO: no incluir root
      pathParts.add(pointer.getName());
      pointer = pointer.getParent();
    }

    Collections.reverse(pathParts);

    return "/" + String.join("/", pathParts);
  }

  /*
  Borrar un archivo o un directorio en el directorio actual.
  Si es un archivo, lo borra sin problema.
  Si es un directorio:
  Si NO pasás --recursive → falla (no podés borrar directorios no vacíos sin permiso).
  Si SÍ pasás --recursive → borra todo el contenido y el directorio.*/
  public Optional<String> rm(String name, boolean recursive) {
    Optional<FileSystem> nodeOpt = current.getChild(name);
    // Busco el FileSystemNode con el nombre 'name' dentro del current (directorio actual).

    if (nodeOpt.isEmpty()) {
      return Optional.empty(); // No existe
    }

    FileSystem node = nodeOpt.get();

    if (node.isDirectory()) {
      Directory dir = (Directory) node;
      if (!recursive && !dir.getChildren().isEmpty()) {
        //  Si no es recursivo y la carpeta tiene hijos, devuelvo el error SIN borrar
        return Optional.of("cannot remove '" + name + "', is a directory");
      }
    }

    current.removeChild(node); // Si llegamos acá, borramos de verdad
    return Optional.of("'" + name + "' removed");
  }
}

// PARA MI ES IGNORAR EN EL CODIGO
// Crear archivo (touch)
// Crear directorio (mkdir)
// Listar archivos (ls)
// Cambiar de directorio (cd)
// Borrar archivo o directorio (rm)
// Obtener ruta actual (pwd)

// decir explícitamente que una función puede o no devolver algo. -> OPTIONAL
// puede fallar o no existir, devolvés Optional.
// siempre va a existir y nunca falla, devolvés directamente el tipo.
