package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service.Impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.Gene;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.SampleAnalysis;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.repository.GeneRepository;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service.GeneService;

@Service(value = "GeneService")
public class GeneServiceImpl implements GeneService {

    private static final Logger logger = LoggerFactory.getLogger(GeneServiceImpl.class);

    @Autowired
    private GeneRepository geneRepository;

    @Autowired
    public MongoTemplate mongoTemplate;

    @Override
    public Collection<Gene> getAllGenes() {
        return geneRepository.findAll();
    }

    @Override
    public void delete(ObjectId Id) {
        geneRepository.deleteAll();
    }

    @Override
    public void insertGene(File filePath, boolean isTruncatedGene, SampleAnalysis sampleAnalysis) throws IOException {
        this.insertGene(filePath, "\t", isTruncatedGene, sampleAnalysis);
    }

    /**
     * Insert truncated and/or targeted files genes in Gene collection
     *
     * @param filePath:        path of gene files
     * @param separator:       example "\t"
     * @param isTruncatedGene: boolean
     * @param sampleAnalysis:  specify the sample analysis object
     * @throws IOException
     */
    private void insertGene(File filePath, String separator, boolean isTruncatedGene, SampleAnalysis sampleAnalysis) throws IOException {

        logger.info("---------------------------------------------");
        logger.info("--------  GENES TARGETED / TRUNCATED  -------");
        logger.info("---------------------------------------------");

        logger.info("Import Gene file: " + filePath.getName());
        Gene gene;

        if (filePath.exists()) {
            BufferedReader fileBR = new BufferedReader(new FileReader(filePath));
            fileBR.readLine().split(separator);
            String line;

            while ((line = fileBR.readLine()) != null) {
                String[] value = line.split(separator);

                gene = new Gene();
                gene.setGeneId(new ObjectId());
                gene.setSampleAnalysisId(sampleAnalysis.getSampleAnalysisId());
                gene.setSampleName(sampleAnalysis.getSampleName());
                gene.setGeneSymbol(value[0]);
                gene.setGeneChromosome(value[1]);
                gene.setGeneStart(Integer.parseInt(value[2]));
                gene.setGeneEnd(Integer.parseInt(value[3]));
                gene.setGeneWidth(Integer.parseInt(value[4]));
                gene.setGeneStrand(value[5]);
                gene.setGeneCytoband(value[6]);
                gene.setMatchStart(Integer.parseInt(value[7]));
                gene.setMatchEnd(Integer.parseInt(value[8]));
                gene.setMatchWidth(Integer.parseInt(value[9]));
                gene.setGeneL2RStatus(value[10]);
                gene.setGeneL2RValue(Float.parseFloat(value[11]));
                gene.setL2RSegmentWidth(Integer.parseInt(value[12]));

                if (value[13].equals("NA")) {
                    gene.setGeneBAFStatus(null);
                } else {
                    gene.setGeneBAFStatus(value[13]);
                }
                try {
                    gene.setGeneBAFValue(Float.parseFloat(value[14]));
                } catch (NumberFormatException e) {
                    gene.setGeneBAFValue(null);
                }
                try {
                    gene.setBAFSegmentWidth(Integer.parseInt(value[15]));
                } catch (NumberFormatException e) {
                    gene.setBAFSegmentWidth(null);
                }

                gene.setTruncatedGene(isTruncatedGene);
                gene.setInClinicalReport(false);

                geneRepository.save(gene);

            }
            fileBR.close();
        }
    }

    @Override
    public List<Gene> findBySampleNameAndIsTruncatedGene(String sampleName, Boolean isTruncatedGene) {
        return geneRepository.findBySampleNameAndIsTruncatedGene(sampleName, isTruncatedGene);
    }

    @Override
    public void deleteAll() {
        geneRepository.deleteAll();
    }

    /**      * Return a list of genes in segment BAF      */
    @Override
    public List<Gene> GetGenesInSegmentBAF(ObjectId sampleAnalysisId, String chrom, Integer segmentBAFStart, Integer segmentBAFEnd) {
        Query query = new Query();
        query.addCriteria(Criteria.where("sample_analysis_id").is(sampleAnalysisId).and("gene_chromosome").is(chrom)
                .and("gene_start").gt(segmentBAFStart).and("gene_end").lt(segmentBAFEnd));
        return mongoTemplate.find(query, Gene.class);
    }
}
