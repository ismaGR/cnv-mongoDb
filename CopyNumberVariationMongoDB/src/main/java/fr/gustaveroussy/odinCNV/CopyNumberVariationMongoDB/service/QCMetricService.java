package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.Batch;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.QCMetric;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.SampleAnalysis;

public interface QCMetricService {
    Collection<QCMetric> getAllQCMetric();

    void deleteAll();

    public QCMetric save(QCMetric entity);

    void insertQCMetric(File filePath, SampleAnalysis sampleAnalysis, Batch batch) throws IOException;
}
