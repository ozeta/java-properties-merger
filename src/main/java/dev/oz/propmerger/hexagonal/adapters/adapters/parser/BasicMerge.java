package dev.oz.propmerger.hexagonal.adapters.adapters.parser;

import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@ApplicationScoped
public class BasicMerge implements Merge {
    private static final Logger LOGGER = Logger.getLogger("BasicMerger");

    public static ParserBuilder builder() {
        return new ParserBuilder();
    }

    public static class ParserException extends Exception {

        private static final long serialVersionUID = 4751286052225386697L;

        public ParserException() {
            super();
        }

        public ParserException(String message) {
            super(message);
        }

        public ParserException(Exception ex) {
            super(ex);
        }
    }

    public static class ParserBuilder {
        List<Properties> props;

        public ParserBuilder() {
            props = new LinkedList<>();
        }

        public ParserBuilder addProperties(File file) throws IOException {
            Properties properties = new Properties();
            try (FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath())) {
                properties.load(fileInputStream);
                props.add(properties);
            }
            return this;
        }

        public ParsedProperty merge() {
            Properties res;
            LOGGER.info("collecting the properties");
            res = props
                    .stream()
                    .collect(Properties::new, Map::putAll, Map::putAll);
            return new ParsedProperty(res);
        }

    }

    static public class ParsedProperty {
        Properties parsedProperties;

        public ParsedProperty(Properties res) {
            this.parsedProperties = res;
        }

        public Properties getPropertyMap() {
            return parsedProperties;
        }

        public void write(File target) throws IOException {
            OutputStream out = new FileOutputStream(target);
            LOGGER.info("Writing to new file: " + target.getAbsolutePath());
            this.parsedProperties.store(out, "");
        }
    }

}
