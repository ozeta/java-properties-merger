package dev.oz.propmerger.utils;

import org.jboss.logging.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

public class EnvParser {

    private static final Logger LOGGER = Logger.getLogger("Parser");


    public static class ParserBuilder {
        List<Set<String>> properties;

        public ParserBuilder() {
            properties = new LinkedList<>();
        }

        public ParserBuilder addEnv(File file) throws IOException {
            Set<String> envFile = Files
                    .readAllLines(file.toPath())
                    .stream()
                    .filter(not(line -> line.startsWith("#")))
                    .filter(not(String::isBlank))
                    .collect(Collectors.toSet());

            for (String line : envFile) {
                System.out.println(line);
            }
            return this;
        }

    }


}
