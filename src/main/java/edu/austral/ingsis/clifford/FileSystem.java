package edu.austral.ingsis.clifford;

public interface FileSystem {
    String getName(); // cada sistema archivo y carpeta mantiene un nombre
    boolean isDirectory();// solo para saber si es un directorio o no
}
