package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.SegmentBAF;

public interface SegmentBAFRepository extends MongoRepository<SegmentBAF, String> {

    public SegmentBAF findBySegmentBAFId(ObjectId segmentBAFId);

    public List<SegmentBAF> findBySampleName(String sampleName);
    //public List<SegmentBAF> findBySampleAnalysisId(ObjectId sampleAnalysisId);
}
