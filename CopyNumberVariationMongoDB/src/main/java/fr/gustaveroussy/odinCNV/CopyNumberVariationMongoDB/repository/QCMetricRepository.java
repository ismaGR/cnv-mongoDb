package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.QCMetric;

public interface QCMetricRepository extends MongoRepository<QCMetric, String> {
    public List<QCMetric> findAll();

    public List<QCMetric> findBySampleName(String sampleName);

    public List<QCMetric> findBySampleAnalysisId(ObjectId sampleAnalysisId);

    public void deleteAll();

    @SuppressWarnings("unchecked")
    public QCMetric save(QCMetric entity);
}
