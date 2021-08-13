package dev.oz.propmerger;

import dev.oz.propmerger.hexagonal.adapters.adapters.controller.ControllerInterface;
import dev.oz.propmerger.hexagonal.adapters.adapters.controller.MergeController;
import dev.oz.propmerger.hexagonal.adapters.adapters.parser.Merge;
import dev.oz.propmerger.hexagonal.adapters.ports.FileOutput;
import dev.oz.propmerger.hexagonal.adapters.ports.IOutput;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.jboss.logging.Logger;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import javax.inject.Inject;
import java.io.File;
import java.lang.invoke.MethodHandles;
import java.util.List;

@QuarkusMain
@CommandLine.Command(
        name = "Startup properties merger",
        mixinStandardHelpOptions = true,
        version = "prop merger 1.0",
        description = "I merge properties")
public class Application implements Runnable, QuarkusApplication {

    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());

    @Inject
    CommandLine.IFactory factory;
    @Inject
    IOutput fp;
    @Inject
    Merge merge;
    @Inject
    ControllerInterface mergeController;

    @Parameters(paramLabel = "FILE[S]", description = "one or more input files to parse")
    List<File> inputFiles;
    @Option(names = {"-o", "--output"}, description = "Optional output file")
    private String output = "assembly.properties";

    @Override
    public void run() {
        if (inputFiles == null) {
            LOGGER.error("No input properties were specified");
            Quarkus.asyncExit(1);
        }

        if (inputFiles != null) {
            LOGGER.info("Output Properties file:" + this.output);
            inputFiles.forEach(s -> LOGGER.info("Input file: " + s));
            fp.setFileOutput(this.output);
            mergeController.setMerge(merge);
            mergeController.setOutput(fp);
            mergeController.process(inputFiles);
        }
    }

    @Override
    public int run(String... args) throws Exception {
        return new CommandLine(this, factory).execute(args);
    }
}
