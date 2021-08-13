package dev.oz.propmerger.hexagonal.adapters.adapters.controller;

import dev.oz.propmerger.hexagonal.adapters.adapters.parser.BasicMerge;
import dev.oz.propmerger.hexagonal.adapters.adapters.parser.Merge;
import dev.oz.propmerger.hexagonal.adapters.ports.IOutput;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;

@ApplicationScoped
public class MergeController implements ControllerInterface {


    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
    private IOutput output;
    private Merge merge;

    public void setOutput(IOutput output) {
        this.output = output;
    }

    public void setMerge(Merge merge) {
        this.merge = merge;
    }

    public void process(List<File> inputFiles) {
        BasicMerge.ParserBuilder builder = BasicMerge.builder();
        try {
            for (File inputFile : inputFiles) {
                builder.addProperties(inputFile);
            }
            BasicMerge.ParsedProperty assemblyProp = builder.merge();
            output.write(assemblyProp.getPropertyMap());
        } catch (IOException ex) {
            LOGGER.error(ex);
        }
    }
}
