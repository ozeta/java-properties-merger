package dev.oz.propmerger.hexagonal.adapters.adapters.parser;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public interface Merge {
    static Properties loadPropertiesFromFile(File file) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(file.getAbsolutePath()));
        return properties;
    }
}
