package dev.oz.propmerger.hexagonal.adapters.ports;

import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.util.Properties;

@ApplicationScoped
public class FileOutput implements IOutput {

    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
    private File fileOutput;

    public void setFileOutput(String outputString) {
        this.fileOutput = new File(outputString);
    }

    public void write(Properties assembly) throws IOException {
        OutputStream out = new FileOutputStream(fileOutput);
        LOGGER.info("Writing to new file: " + fileOutput.getAbsolutePath());
        assembly.store(out, "");
    }
}
