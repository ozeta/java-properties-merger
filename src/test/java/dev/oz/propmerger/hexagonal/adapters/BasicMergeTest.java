package dev.oz.propmerger.hexagonal.adapters;

import dev.oz.propmerger.hexagonal.adapters.adapters.parser.BasicMerge;
import dev.oz.propmerger.hexagonal.adapters.adapters.parser.Merge;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@TestInstance(Lifecycle.PER_CLASS)
// @QuarkusTest
public class BasicMergeTest {
    private static final Logger LOGGER = Logger.getLogger("ParserTest");
    private final List<File> properties = new ArrayList<>();
    ClassLoader cLoad = getClass().getClassLoader();
    File oracle_target;
    File assembly;

    @BeforeAll
    public void beforeAll() {
        this.properties.add(new File(cLoad.getResource("properties_0.properties").getFile()));
        this.properties.add(new File(cLoad.getResource("properties_2.properties").getFile()));
        this.properties.add(new File(cLoad.getResource("properties_1.properties").getFile()));
        this.oracle_target = new File(cLoad.getResource("oracle_target.properties").getFile());
        this.assembly = new File("assembly.properties");
    }

    private boolean mapsEqual(Map<String, String> first, Map<String, String> second) {
        if (first.size() != second.size()) {
            return false;
        }
        boolean firstMatch = first
                .entrySet()
                .stream()
                .allMatch(e -> e.getValue().equals(second.get(e.getKey())));
        boolean secondMatch = second
                .entrySet()
                .stream()
                .allMatch(e -> e.getValue().equals(first.get(e.getKey())));
        return firstMatch && secondMatch;

    }

    private void verifyProperties(Properties assemblyProp) throws IOException {
        Map<String, String> oraclemap = (Map) Merge.loadPropertiesFromFile(oracle_target);
        assemblyProp.values().stream().forEach(System.out::println);
        mapsEqual((Map) assemblyProp, oraclemap);
    }

    @Test
    public void parserTest() throws IOException {
        BasicMerge.ParserBuilder builder = BasicMerge.builder();
        try {
            for (File property : properties) {
                builder.addProperties(property);
            }
            BasicMerge.ParsedProperty assemblyProp = builder.merge();
            assemblyProp.write(assembly);
            verifyProperties(assemblyProp.getPropertyMap());
        } catch (IOException ex) {
            LOGGER.error(ex);
        }
    }
}
