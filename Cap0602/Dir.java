/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficheros;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import static java.nio.file.FileVisitResult.CONTINUE;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;

/**
 *
 * @author franmatias
 */
public class Dir implements FileVisitor<Path> {
    private Path root;
    private PathMatcher matcher;
    private LinkedList<Path> ficheros = new LinkedList<>(); 
    private LinkedList<Path> carpetas = new LinkedList<>(); 

    public LinkedList<Path> getCarpetas(){
        return carpetas;
    }
    
    public LinkedList<Path> getFicheros(){
        return ficheros;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        ficheros.add(file);
        return CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        carpetas.add(dir);
        return CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        return CONTINUE;
    }
}
