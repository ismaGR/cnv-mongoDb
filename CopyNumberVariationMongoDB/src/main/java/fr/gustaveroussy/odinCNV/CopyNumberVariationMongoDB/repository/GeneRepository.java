package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.Gene;

public interface GeneRepository extends MongoRepository<Gene, String> {

    public List<Gene> findAll();

    public Gene findByGeneId(ObjectId geneId);

    public Gene findByGeneSymbol(String GeneSymbol);

    public List<Gene> findBySampleName(String string);

    public List<Gene> findBySampleNameAndIsTruncatedGene(String sampleName, Boolean IsTruncatedGene);

    public List<Gene> findBySampleAnalysisId(ObjectId sampleAnalysisId);

}
