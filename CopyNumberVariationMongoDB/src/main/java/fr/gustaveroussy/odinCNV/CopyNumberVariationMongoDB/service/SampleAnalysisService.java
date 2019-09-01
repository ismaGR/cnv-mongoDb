package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.bson.types.ObjectId;

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.SampleAnalysis;

public interface SampleAnalysisService {

    Collection<SampleAnalysis> getAllSamplesAnalysis();

    SampleAnalysis findBySampleName(String sampleName);

    SampleAnalysis findBySampleAnalysisId(ObjectId sampleAnalysisId);

    void deleteAll();

    void save(SampleAnalysis entity);

    void insertMicroarray(File filePath, SampleAnalysis sampleAnalysis, String microarrayPlotPath) throws IOException;

    void insertGenomicPlot(String pathFileBAF, String pathFileL2R, String pathFileL2RBAF, SampleAnalysis sampleAnalysis);

    void insertChromosomePlot(List<String> ChromosomesFile, SampleAnalysis sampleAnalysis);
}
