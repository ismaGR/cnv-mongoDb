package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.embedded.Comment;

@Document(collection = "Gene")
public class Gene {

    @Id
    private ObjectId geneId;

    @Field(value = "sample_name")
    private String sampleName;

    @Field(value = "sample_analysis_id")
    private ObjectId sampleAnalysisId;

    @Field(value = "gene_symbol")
    private String geneSymbol;

    @Field(value = "gene_start")
    private Integer geneStart;

    @Field(value = "gene_end")
    private Integer geneEnd;

    @Field(value = "gene_width")
    private Integer geneWidth;

    @Field(value = "gene_chromosome")
    private String geneChromosome;

    @Field(value = "gene_strand")
    private String geneStrand;

    @Field(value = "gene_cytoband")
    private String geneCytoband;

    @Field(value = "match_start")
    private Integer matchStart;

    @Field(value = "match_end")
    private Integer matchEnd;

    @Field(value = "match_width")
    private Integer matchWidth;

    @Field(value = "gene_L2R_status")
    private String geneL2RStatus;

    @Field(value = "gene_L2R_value")
    private Float geneL2RValue;

    @Field(value = "L2R_segment_width")
    private Integer L2RSegmentWidth;

    @Field(value = "gene_BAF_status")
    private String geneBAFStatus;

    @Field(value = "gene_BAF_value")
    private Float geneBAFValue;

    @Field(value = "BAF_segment_width")
    private Integer BAFSegmentWidth;

    @Field(value = "is_truncated_gene")
    private boolean isTruncatedGene;

    @Field(value = "is_in_clinical_report")
    private boolean isInClinicalReport;

    @Field(value = "comments")
    public List<Comment> comments;

    public ObjectId getGeneId() {
        return geneId;
    }

    public void setGeneId(ObjectId Id) {
        this.geneId = Id;
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

    public String getGeneSymbol() {
        return geneSymbol;
    }

    public void setGeneSymbol(String geneSymbol) {
        this.geneSymbol = geneSymbol;
    }

    public Integer getGeneStart() {
        return geneStart;
    }

    public void setGeneStart(Integer geneStart) {
        this.geneStart = geneStart;
    }

    public Integer getGeneEnd() {
        return geneEnd;
    }

    public void setGeneEnd(Integer geneEnd) {
        this.geneEnd = geneEnd;
    }

    public Integer getGeneWidth() {
        return geneWidth;
    }

    public void setGeneWidth(Integer geneWidth) {
        this.geneWidth = geneWidth;
    }

    public String getGeneChromosome() {
        return geneChromosome;
    }

    public void setGeneChromosome(String geneChromosome) {
        this.geneChromosome = geneChromosome;
    }

    public String getGeneStrand() {
        return geneStrand;
    }

    public void setGeneStrand(String geneStrand) {
        this.geneStrand = geneStrand;
    }

    public String getGeneCytoband() {
        return geneCytoband;
    }

    public void setGeneCytoband(String geneCytoband) {
        this.geneCytoband = geneCytoband;
    }

    public Integer getMatchStart() {
        return matchStart;
    }

    public void setMatchStart(Integer matchStart) {
        this.matchStart = matchStart;
    }

    public Integer getMatchEnd() {
        return matchEnd;
    }

    public void setMatchEnd(Integer matchEnd) {
        this.matchEnd = matchEnd;
    }

    public Integer getMatchWidth() {
        return matchWidth;
    }

    public void setMatchWidth(Integer matchWidth) {
        this.matchWidth = matchWidth;
    }

    public String getGeneL2RStatus() {
        return geneL2RStatus;
    }

    public void setGeneL2RStatus(String geneL2RStatus) {
        this.geneL2RStatus = geneL2RStatus;
    }

    public Float getGeneL2RValue() {
        return geneL2RValue;
    }

    public void setGeneL2RValue(Float geneL2RValue) {
        this.geneL2RValue = geneL2RValue;
    }

    public Integer getL2RSegmentWidth() {
        return L2RSegmentWidth;
    }

    public void setL2RSegmentWidth(Integer l2rSegmentWidth) {
        L2RSegmentWidth = l2rSegmentWidth;
    }

    public String getGeneBAFStatus() {
        return geneBAFStatus;
    }

    public void setGeneBAFStatus(String geneBAFStatus) {
        this.geneBAFStatus = geneBAFStatus;
    }

    public Float getGeneBAFValue() {
        return geneBAFValue;
    }

    public void setGeneBAFValue(Float geneBAFValue) {
        this.geneBAFValue = geneBAFValue;
    }

    public Integer getBAFSegmentWidth() {
        return BAFSegmentWidth;
    }

    public void setBAFSegmentWidth(Integer bAFSegmentWidth) {
        BAFSegmentWidth = bAFSegmentWidth;
    }

    public boolean isTruncatedGene() {
        return isTruncatedGene;
    }

    public void setTruncatedGene(boolean isTruncatedGene) {
        this.isTruncatedGene = isTruncatedGene;
    }

    public boolean isInClinicalReport() {
        return isInClinicalReport;
    }

    public void setInClinicalReport(boolean isInClinicalReport) {
        this.isInClinicalReport = isInClinicalReport;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Gene() {
        super();
    }

}
