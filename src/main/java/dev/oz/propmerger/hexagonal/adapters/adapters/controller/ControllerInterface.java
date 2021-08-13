package dev.oz.propmerger.hexagonal.adapters.adapters.controller;

import dev.oz.propmerger.hexagonal.adapters.adapters.parser.Merge;
import dev.oz.propmerger.hexagonal.adapters.ports.IOutput;

import java.io.File;
import java.util.List;

public interface ControllerInterface {

    IOutput output = null;
    Merge merge = null;

    void setOutput(IOutput output);

    void setMerge(Merge merge);

    void process(List<File> inputFiles);
}
