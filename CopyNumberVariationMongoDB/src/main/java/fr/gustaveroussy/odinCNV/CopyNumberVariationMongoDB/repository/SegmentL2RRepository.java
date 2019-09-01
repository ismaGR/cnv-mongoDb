package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.SegmentL2R;

public interface SegmentL2RRepository extends MongoRepository<SegmentL2R, String> {

    public SegmentL2R findBySegmentL2RId(ObjectId segmentL2RId);

    public List<SegmentL2R> findBySampleName(String sampleName);

    public List<SegmentL2R> findBySampleAnalysisId(ObjectId sampleAnalysisId);

    public void deleteAll();
}
