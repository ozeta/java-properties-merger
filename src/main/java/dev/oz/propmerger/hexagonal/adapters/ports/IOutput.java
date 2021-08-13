package dev.oz.propmerger.hexagonal.adapters.ports;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public interface IOutput {
    File fileOutput = null;

    void setFileOutput(String fileOutput);
    void write(Properties assembly) throws IOException;
}
