package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.MiscellaneousInformation;

public interface MiscellaneousInformationRepository extends MongoRepository<MiscellaneousInformation, String> {

    public List<MiscellaneousInformation> findAll();

    public void deleteAll();

    public MiscellaneousInformation findBySampleAnalysisId(ObjectId sampleAnalysisId);

    @SuppressWarnings("unchecked")
    public MiscellaneousInformation save(MiscellaneousInformation entity);
}
