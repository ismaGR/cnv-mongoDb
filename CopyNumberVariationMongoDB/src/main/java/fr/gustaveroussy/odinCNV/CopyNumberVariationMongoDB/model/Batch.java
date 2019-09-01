package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.embedded.Comment;

@Document(collection = "Batch")
public class Batch {

    @Id
    public ObjectId batchId;

    @Field(value = "batch_name")
    public String batchName;

    @Field(value = "batch_date")
    public Date batchDate;

    @Field(value = "batch_instrument")
    public String batchInstrument;

    @Field(value = "batch_technology")
    public String batchTechnology;

    @Field(value = "batch_detail")
    public String batchDetail;

    @Field(value = "is_valid")
    public boolean isValid;

    @Field(value = "comments")
    public List<Comment> comments;

    @DBRef(db = "Sample_Analysis", lazy = true)
    private List<SampleAnalysis> sampleAnalysisList;

    public ObjectId getBatchId() {
        return batchId;
    }

    public void setBatchId(ObjectId batchId) {
        this.batchId = batchId;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public Date getBatchDate() {
        return batchDate;
    }

    public void setBatchDate(Date batchDate) {
        this.batchDate = batchDate;
    }

    public String getBatchInstrument() {
        return batchInstrument;
    }

    public void setBatchInstrument(String batchInstrument) {
        this.batchInstrument = batchInstrument;
    }

    public String getBatchTechnology() {
        return batchTechnology;
    }

    public void setBatchTechnology(String batchTechnology) {
        this.batchTechnology = batchTechnology;
    }

    public String getBatchDetail() {
        return batchDetail;
    }

    public void setBatchDetail(String batchDetail) {
        this.batchDetail = batchDetail;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public List<SampleAnalysis> getSampleAnalysisList() {
        return sampleAnalysisList;
    }

    public void setSampleAnalysisList(List<SampleAnalysis> sampleAnalysisList) {
        this.sampleAnalysisList = sampleAnalysisList;
    }


    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /**
     * @param batchId
     * @param batchName
     * @param batchDate
     * @param batchInstrument
     * @param batchTechnology
     * @param batchDetail
     * @param sampleName
     * @param isValid
     * @param comments
     * @param sampleAnalysisList
     */
    public Batch(ObjectId batchId, String batchName, Date batchDate, String batchInstrument, String batchTechnology,
                 String batchDetail, boolean isValid, List<Comment> comments,
                 List<SampleAnalysis> sampleAnalysisList) {
        super();
        this.batchId = batchId;
        this.batchName = batchName;
        this.batchDate = batchDate;
        this.batchInstrument = batchInstrument;
        this.batchTechnology = batchTechnology;
        this.batchDetail = batchDetail;
        this.isValid = isValid;
        this.comments = comments;
        this.sampleAnalysisList = sampleAnalysisList;
    }

    public Batch() {
        super();
    }
}
