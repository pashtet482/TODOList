package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import static java.lang.System.*;

public final class ScanFiles {
    private ScanFiles() {
        throw new UnsupportedOperationException("Utility class should not be instantiated!");
    }

    public static void scan(){
        Path dir = Paths.get("src/main/resources/TODO");

        try (Stream<Path> files = Files.list(dir)) {
            files.forEach(file -> out.println(file.getFileName()));
        } catch (IOException e) {
            out.println("Ошибка чтения директории: " + e.getMessage());
        }
    }
}
