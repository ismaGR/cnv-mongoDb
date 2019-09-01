package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.SampleAnalysis;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.SegmentBAF;

public interface SegmentBAFService {

    Collection<SegmentBAF> getAllSegmentBAFs();

    List<SegmentBAF> findBySampleName(String sampleName);

    void deleteAll();

    void insertSegmentBAF(File filePath, SampleAnalysis sampleAnalysis) throws IOException;

}
