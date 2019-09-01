package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.Batch;

public interface BatchRepository extends MongoRepository<Batch, String> {

    public List<Batch> findAll();

    public Batch findByBatchId(ObjectId batchId);

    public void deleteAll();

    @SuppressWarnings("unchecked")
    public Batch save(Batch entity);
}
