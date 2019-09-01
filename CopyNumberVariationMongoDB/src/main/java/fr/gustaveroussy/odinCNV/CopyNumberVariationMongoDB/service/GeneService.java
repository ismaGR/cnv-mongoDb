package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.bson.types.ObjectId;

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.Gene;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.SampleAnalysis;


public interface GeneService {

    Collection<Gene> getAllGenes();
    //Collection<Gene> findBySampleAnalysis(SampleAnalysis sampleAnalysis);

    void delete(ObjectId Id);

    void deleteAll();

    void insertGene(File filePath, boolean isTruncatedGene, SampleAnalysis sampleAnalysis) throws IOException;

    List<Gene> findBySampleNameAndIsTruncatedGene(String sampleName, Boolean isTruncatedGene);

    List<Gene> GetGenesInSegmentBAF(ObjectId sampleAnalysisId, String chrom, Integer segmentBAFStart, Integer segmentBAFEnd);
}
