package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Chromosome")
public class Chromosome {

    @Id
    private ObjectId chromosomeID;

    @Field(value = "chr")
    private String chr;

    @Field(value = "file_name")
    private String fileName;

    @Field(value = "sample_name")
    private String sampleName;

    @Field(value = "sample_analysis_id")
    private ObjectId sampleAnalysisId;

    public ObjectId getChromosomeID() {
        return chromosomeID;
    }

    public void setChromosomeID(ObjectId chromosomeID) {
        this.chromosomeID = chromosomeID;
    }

    public String getChr() {
        return chr;
    }

    public void setChr(String chr) {
        this.chr = chr;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public ObjectId getSampleAnalysisId() {
        return sampleAnalysisId;
    }

    public void setSampleAnalysisId(ObjectId sampleAnalysisId) {
        this.sampleAnalysisId = sampleAnalysisId;
    }

    public Chromosome() {
        super();
    }

    public Chromosome(ObjectId chromosomeID, String chr, String fileName, String sampleName, ObjectId sampleAnalysisId) {
        super();
        this.chromosomeID = chromosomeID;
        this.chr = chr;
        this.fileName = fileName;
        this.sampleName = sampleName;
        this.sampleAnalysisId = sampleAnalysisId;
    }
}
