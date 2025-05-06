package edu.austral.ingsis.clifford;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Directory implements FileSystem {
  private final String name;
  private final List<FileSystem> children =
      new ArrayList<>(); // los hijos son los archivos o carpetas tmb,  dentro de la carpeta
  private Directory
      parent; // la carpeta por eso padre, funciona como un arbol, para hacer cd nombre

  public Directory(String name, Directory parent) {
    this.name = name;
    this.parent = parent;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean isDirectory() {
    return true; // true porque si es
  }

  public List<FileSystem> getChildren() {
    return Collections.unmodifiableList(
        children); // Devuelvo la lista de hijos, pero no dejo que el que la recibe la pueda
    // modificar
    // children sí es modificable por adentro de la clase (addChild, removeChild).
    // es una función de Java que envuelve una lista y le saca las operaciones de agregar, borrar,
    // etc.
  }

  public void addChild(FileSystem node) {
    children.add(node); // agregar un archivo o carpeta
  }

  public void removeChild(FileSystem node) {
    children.remove(node); // borrar un archivo o carpeta
  }

  public Directory getParent() {
    return parent;
  }

  public Optional<FileSystem> getChild(
      String name) { // children uno que se llame como name, o devuelve Optional.empty() si no hay.
    for (FileSystem child : children) {
      if (child.getName().equals(name)) {
        return Optional.of(child);
      }
    }
    return Optional.empty();
  }
}
