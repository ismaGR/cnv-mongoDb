package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.embedded.Comment;

@Document(collection = "QC_metric")
public class QCMetric {

    @Id
    public ObjectId qcMetricId;

    @Field(value = "sample_analysis_id")
    public ObjectId sampleAnalysisId;

    @Field(value = "sample_name")
    public String sampleName;

    @Field(value = "batch_name")
    public String batchName;

    @Field(value = "MAPD")
    public Float mapd;

    @Field(value = "IQR")
    public Float iqr;

    @Field(value = "SNPQC")
    public Float snpqc;

    @Field(value = "waviness_SD")
    public Float wavinessSD;

    @Field(value = "Predicted_gender")
    public String predictedGender;

    @Field(value = "Median_raw_intensity")
    public Integer medRawIntensity;

    @Field(value = "Outliers")
    public Integer Outlier;

    @Field(value = "nb_breakpoint")
    public Integer nbBreakpoint;

    @Field(value = "comments")
    public List<Comment> comments;

    public ObjectId getQcMetricId() {
        return qcMetricId;
    }

    public void setQcMetricId(ObjectId qcMetricId) {
        this.qcMetricId = qcMetricId;
    }

    public ObjectId getSampleAnalysisId() {
        return sampleAnalysisId;
    }

    public void setSampleAnalysisId(ObjectId sampleAnalysisId) {
        this.sampleAnalysisId = sampleAnalysisId;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public Float getMapd() {
        return mapd;
    }

    public void setMapd(Float mapd) {
        this.mapd = mapd;
    }

    public Float getIqr() {
        return iqr;
    }

    public void setIqr(Float iqr) {
        this.iqr = iqr;
    }

    public Float getSnpqc() {
        return snpqc;
    }

    public void setSnpqc(Float snpqc) {
        this.snpqc = snpqc;
    }

    public Float getWavinessSD() {
        return wavinessSD;
    }

    public void setWavinessSD(Float wavinessSD) {
        this.wavinessSD = wavinessSD;
    }

    public String getPredictedGender() {
        return predictedGender;
    }

    public void setPredictedGender(String predictedGender) {
        this.predictedGender = predictedGender;
    }

    public Integer getMedRawIntensity() {
        return medRawIntensity;
    }

    public void setMedRawIntensity(Integer medRawIntensity) {
        this.medRawIntensity = medRawIntensity;
    }

    public Integer getOutlier() {
        return Outlier;
    }

    public void setOutlier(Integer outlier) {
        Outlier = outlier;
    }

    public Integer getNbBreakpoint() {
        return nbBreakpoint;
    }

    public void setNbBreakpoint(Integer nbBreakpoint) {
        this.nbBreakpoint = nbBreakpoint;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /**
     * @param qcMetricId
     * @param sampleAnalysisId
     * @param sampleName
     * @param batchName
     * @param mapd
     * @param iqr
     * @param snpqc
     * @param wavinessSD
     * @param predictedGender
     * @param medRawIntensity
     * @param outlier
     * @param nbBreakpoint
     * @param comments
     */
    public QCMetric(ObjectId qcMetricId, ObjectId sampleAnalysisId, String sampleName, String batchName, Float mapd,
                    Float iqr, Float snpqc, Float wavinessSD, String predictedGender, Integer medRawIntensity, Integer outlier,
                    Integer nbBreakpoint, List<Comment> comments) {
        super();
        this.qcMetricId = qcMetricId;
        this.sampleAnalysisId = sampleAnalysisId;
        this.sampleName = sampleName;
        this.batchName = batchName;
        this.mapd = mapd;
        this.iqr = iqr;
        this.snpqc = snpqc;
        this.wavinessSD = wavinessSD;
        this.predictedGender = predictedGender;
        this.medRawIntensity = medRawIntensity;
        Outlier = outlier;
        this.nbBreakpoint = nbBreakpoint;
        this.comments = comments;
    }

    public QCMetric() {
        super();
    }


}
