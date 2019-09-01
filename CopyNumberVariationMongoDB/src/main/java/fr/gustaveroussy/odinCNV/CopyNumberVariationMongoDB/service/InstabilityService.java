package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.Instability;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.SampleAnalysis;

public interface InstabilityService {

    Collection<Instability> getAllInstability();

    void deleteAll();

    public Instability save(Instability entity);

    void insertInstab(File filePath, SampleAnalysis sampleAnalysis) throws IOException;
}
