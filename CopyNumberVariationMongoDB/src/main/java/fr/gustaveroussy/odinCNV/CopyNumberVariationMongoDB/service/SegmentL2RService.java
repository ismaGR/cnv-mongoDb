package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.SampleAnalysis;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.SegmentL2R;

public interface SegmentL2RService {

    Collection<SegmentL2R> getAllSegmentL2Rs();

    List<SegmentL2R> findBySampleName(String sampleName);

    void deleteAll();

    void insertSegmentL2R(File filePath, SampleAnalysis sampleAnalysis) throws IOException;

}
