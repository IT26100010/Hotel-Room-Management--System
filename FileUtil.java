package com.hotel.util;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public final class FileUtil {
    private FileUtil() {}

    public static void ensureFile(String path) {
        try {
            Path file = Paths.get(path);
            if (file.getParent() != null) {
                Files.createDirectories(file.getParent());
            }
            if (!Files.exists(file)) {
                Files.createFile(file);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not prepare file: " + path, e);
        }
    }

    public static List<String> readAll(String path) {
        ensureFile(path);
        try {
            return Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException("Could not read file: " + path, e);
        }
    }

    public static void writeAll(String path, List<String> lines) {
        ensureFile(path);
        try {
            Files.write(Paths.get(path), lines,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException("Could not write file: " + path, e);
        }
    }

    public static void append(String path, String line) {
        ensureFile(path);
        try {
            Files.writeString(Paths.get(path), line + System.lineSeparator(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Could not append to file: " + path, e);
        }
    }
}
