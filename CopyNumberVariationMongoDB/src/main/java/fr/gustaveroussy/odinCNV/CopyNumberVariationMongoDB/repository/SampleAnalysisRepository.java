package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.SampleAnalysis;

public interface SampleAnalysisRepository extends MongoRepository<SampleAnalysis, String> {

    public SampleAnalysis findBySampleName(String sampleName);

    public SampleAnalysis findBySampleAnalysisId(ObjectId sampleAnalysisId);

    public void deleteAll();

    @SuppressWarnings("unchecked")
    public SampleAnalysis save(SampleAnalysis entity);

}
