package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Instability")
public class Instability {

    @Id
    public ObjectId instabilityId;

    @Field(value = "sample_analysis_id")
    public ObjectId sampleAnalysisId;

    @Field(value = "sample_name")
    public String sampleName;

    @Field(value = "CNA_status")
    public String CNAStatus;

    @Field(value = "nb_segments")
    public Integer nbSegments;

    @Field(value = "total_width")
    public Long totalWidth;

    @Field(value = "genome_fraction")
    public Double genomeFraction;

    @Field(value = "median_width")
    public Long medianWidth;

    @Field(value = "median_log2_ratio")
    public Double medianLog2Ratio;

    public ObjectId getInstabilityId() {
        return instabilityId;
    }

    public void setInstabilityId(ObjectId instabilityId) {
        this.instabilityId = instabilityId;
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

    public String getCNAStatus() {
        return CNAStatus;
    }

    public void setCNAStatus(String cNAStatus) {
        CNAStatus = cNAStatus;
    }

    public Integer getNbSegments() {
        return nbSegments;
    }

    public void setNbSegments(Integer nbSegments) {
        this.nbSegments = nbSegments;
    }

    public Long getTotalWidth() {
        return totalWidth;
    }

    public void setTotalWidth(Long totalWidth) {
        this.totalWidth = totalWidth;
    }

    public Double getGenomeFraction() {
        return genomeFraction;
    }

    public void setGenomeFraction(Double genomeFraction) {
        this.genomeFraction = genomeFraction;
    }

    public Long getMedianWidth() {
        return medianWidth;
    }

    public void setMedianWidth(Long medianWidth) {
        this.medianWidth = medianWidth;
    }

    public Double getMedianLog2Ratio() {
        return medianLog2Ratio;
    }

    public void setMedianLog2Ratio(Double medianLog2Ratio) {
        this.medianLog2Ratio = medianLog2Ratio;
    }

    public Instability(ObjectId instabilityId, ObjectId sampleAnalysisId, String sampleName, String cNAStatus,
                       Integer nbSegments, Long totalWidth, Double genomeFraction, Long medianWidth, Double medianLog2Ratio) {
        super();
        this.instabilityId = instabilityId;
        this.sampleAnalysisId = sampleAnalysisId;
        this.sampleName = sampleName;
        CNAStatus = cNAStatus;
        this.nbSegments = nbSegments;
        this.totalWidth = totalWidth;
        this.genomeFraction = genomeFraction;
        this.medianWidth = medianWidth;
        this.medianLog2Ratio = medianLog2Ratio;
    }

    public Instability() {
        super();
    }


}
