package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.embedded.Comment;

@Document(collection = "Segment_L2R")
public class SegmentL2R {

    @Id
    private ObjectId segmentL2RId;

    @Field(value = "chrom")
    private String chrom;

    @Field(value = "chr")
    private String chr;

    @Field(value = "sample_name")
    private String sampleName;

    @Field(value = "sample_analysis_id")
    private ObjectId sampleAnalysisId;

    @Field(value = "segment_start")
    private Integer segmentStart;

    @Field(value = "segment_end")
    private Integer segmentEnd;

    @Field(value = "segment_width")
    private Integer segmentWidth;

    @Field(value = "segment_start_cytoband")
    private String segmentStartCytoband;

    @Field(value = "segment_end_cytoband")
    private String segmentEndCytoband;

    @Field(value = "segment_L2R_status")
    private String segmentL2RStatus;

    @Field(value = "segment_L2R_value")
    private Float segmentL2RValue;

    @Field(value = "segment_L2R_ratio")
    private Float segmentL2RRatio;

    @Field(value = "nb_markers")
    private Integer nbMarkers;

    @Field(value = "nb_genes")
    private Integer nbGenes;

    @Field(value = "is_in_clinical_report")
    private Boolean isInClinicalReport;

    @Field(value = "segment_focality")
    private String segmentFocality;

    @Field(value = "segment_ploidy")
    private String segmentPloidy;

    @Field(value = "comments")
    public List<Comment> comments;

    public ObjectId getSegmentL2RId() {
        return segmentL2RId;
    }

    public void setSegmentL2RId(ObjectId id) {
        this.segmentL2RId = id;
    }

    public String getChrom() {
        return chrom;
    }

    public void setChrom(String chrom) {
        this.chrom = chrom;
    }

    public String getChr() {
        return chr;
    }

    public void setChr(String chr) {
        this.chr = chr;
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

    public Integer getSegmentStart() {
        return segmentStart;
    }

    public void setSegmentStart(Integer segmentStart) {
        this.segmentStart = segmentStart;
    }

    public Integer getSegmentEnd() {
        return segmentEnd;
    }

    public void setSegmentEnd(Integer segmentEnd) {
        this.segmentEnd = segmentEnd;
    }

    public Integer getSegmentWidth() {
        return segmentWidth;
    }

    public void setSegmentWidth(Integer segmentWidth) {
        this.segmentWidth = segmentWidth;
    }

    public String getSegmentStartCytoband() {
        return segmentStartCytoband;
    }

    public void setSegmentStartCytoband(String segmentStartCytoband) {
        this.segmentStartCytoband = segmentStartCytoband;
    }

    public String getSegmentEndCytoband() {
        return segmentEndCytoband;
    }

    public void setSegmentEndCytoband(String segmentEndCytoband) {
        this.segmentEndCytoband = segmentEndCytoband;
    }

    public String getSegmentL2RStatus() {
        return segmentL2RStatus;
    }

    public void setSegmentL2RStatus(String segmentL2RStatus) {
        this.segmentL2RStatus = segmentL2RStatus;
    }

    public Float getSegmentL2RValue() {
        return segmentL2RValue;
    }

    public void setSegmentL2RValue(Float segmentL2RValue) {
        this.segmentL2RValue = segmentL2RValue;
    }

    public Float getSegmentL2RRatio() {
        return segmentL2RRatio;
    }

    public void setSegmentL2RRatio(Float segmentL2RRatio) {
        this.segmentL2RRatio = segmentL2RRatio;
    }

    public Integer getNbMarkers() {
        return nbMarkers;
    }

    public void setNbMarkers(Integer nbMarkers) {
        this.nbMarkers = nbMarkers;
    }

    public Integer getNbGenes() {
        return nbGenes;
    }

    public void setNbGenes(Integer nbGenes) {
        this.nbGenes = nbGenes;
    }

    public Boolean getIsInClinicalReport() {
        return isInClinicalReport;
    }

    public void setIsInClinicalReport(Boolean isInClinicalReport) {
        this.isInClinicalReport = isInClinicalReport;
    }

    public String getSegmentFocality() {
        return segmentFocality;
    }

    public void setSegmentFocality(String segmentFocality) {
        this.segmentFocality = segmentFocality;
    }

    public String getSegmentPloidy() {
        return segmentPloidy;
    }

    public void setSegmentPloidy(String segmentPloidy) {
        this.segmentPloidy = segmentPloidy;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}