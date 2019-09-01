package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.embedded.Comment;


@Document(collection = "Segment_BAF")
public class SegmentBAF {

    @Id
    private ObjectId segmentBAFId;

    @Field(value = "sample_name")
    private String sampleName;

    @Field(value = "sample_analysis_id")
    private ObjectId sampleAnalysisId;

    @Field(value = "batch_name")
    private String batchName;

    @Field(value = "chrom")
    private String chrom;

    @Field(value = "chr")
    private String chr;

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

    @Field(value = "BAF_value")
    private Float BAFValue;

    @Field(value = "BAF_status")
    private String BAFStatus;

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

    public SegmentBAF() {
    }

    public ObjectId getSegmentBAFId() {
        return segmentBAFId;
    }

    public void setSegmentBAFId(ObjectId id) {
        this.segmentBAFId = id;
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

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
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

    public Float getBAFValue() {
        return BAFValue;
    }

    public void setBAFValue(Float bAFValue) {
        BAFValue = bAFValue;
    }

    public String getBAFStatus() {
        return BAFStatus;
    }

    public void setBAFStatus(String bAFStatus) {
        BAFStatus = bAFStatus;
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

    public SegmentBAF(ObjectId segmentBAFId, String sampleName, ObjectId sampleAnalysisId, String batchName,
                      String chrom, String chr, Integer segmentStart, Integer segmentEnd, Integer segmentWidth,
                      String segmentStartCytoband, String segmentEndCytoband, Float bAFValue, String bAFStatus, Integer nbMarkers,
                      Integer nbGenes, Boolean isInClinicalReport, String segmentFocality, String segmentPloidy,
                      List<Comment> comments) {
        super();
        this.segmentBAFId = segmentBAFId;
        this.sampleName = sampleName;
        this.sampleAnalysisId = sampleAnalysisId;
        this.batchName = batchName;
        this.chrom = chrom;
        this.chr = chr;
        this.segmentStart = segmentStart;
        this.segmentEnd = segmentEnd;
        this.segmentWidth = segmentWidth;
        this.segmentStartCytoband = segmentStartCytoband;
        this.segmentEndCytoband = segmentEndCytoband;
        BAFValue = bAFValue;
        BAFStatus = bAFStatus;
        this.nbMarkers = nbMarkers;
        this.nbGenes = nbGenes;
        this.isInClinicalReport = isInClinicalReport;
        this.segmentFocality = segmentFocality;
        this.segmentPloidy = segmentPloidy;
        this.comments = comments;
    }
}
