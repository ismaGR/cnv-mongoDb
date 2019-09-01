package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service;

import org.bson.types.ObjectId;

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.Batch;

public interface BatchService {
    void deleteAll();

    void save(Batch entity);

    public Batch findByBatchId(ObjectId batchId);
}
