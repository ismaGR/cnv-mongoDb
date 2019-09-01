package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.Instability;

public interface InstabilityRepository extends MongoRepository<Instability, String> {

    public List<Instability> findAll();

    public List<Instability> findBySampleName(String sampleName);

    public List<Instability> findBySampleAnalysisId(ObjectId sampleAnalysisId);

    public void deleteAll();

    @SuppressWarnings("unchecked")
    public Instability save(Instability entity);
}
