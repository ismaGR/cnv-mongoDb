package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service.Impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.Batch;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.repository.BatchRepository;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service.BatchService;

@Service(value = "BatchService")
public class BatchServiceImpl implements BatchService {

    @Autowired
    private BatchRepository batchRepository;

    @Override
    public void deleteAll() {
        batchRepository.deleteAll();
    }

    @Override
    public void save(Batch entity) {
        batchRepository.save(entity);
    }

    @Override
    public Batch findByBatchId(ObjectId batchId) {
        return batchRepository.findByBatchId(batchId);
    }
}
