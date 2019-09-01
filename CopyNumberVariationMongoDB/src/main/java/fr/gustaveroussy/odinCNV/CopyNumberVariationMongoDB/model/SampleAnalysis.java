package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.embedded.ChromosomePlot;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.embedded.Comment;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.embedded.GenomicPlot;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.embedded.Microarray;

@Document(collection = "Sample_Analysis")
public class SampleAnalysis {

    @Id
    public ObjectId sampleAnalysisId;

    @Field(value = "sample_name")
    public String sampleName;

    @Field(value = "microarray")
    public List<Microarray> microarrays;

    @Field(value = "genomic_plot")
    public GenomicPlot genomicPlot;

    @Field(value = "chromosome_plots")
    public List<ChromosomePlot> listChromosomePlots;

    @Field(value = "comments")
    public List<Comment> comments;

    @DBRef(db = "Instability", lazy = true)
    private List<Instability> instability;

    @DBRef(db = "QCMetric", lazy = true)
    private List<QCMetric> qcMetric;

    public SampleAnalysis() {
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

    public List<Microarray> getMicroarrays() {
        return microarrays;
    }

    public void setMicroarrays(List<Microarray> microarrays) {
        this.microarrays = microarrays;
    }

    public GenomicPlot getGenomicPlot() {
        return genomicPlot;
    }

    public void setGenomicPlot(GenomicPlot genomicPlot) {
        this.genomicPlot = genomicPlot;
    }

    public List<ChromosomePlot> getListChromosomePlots() {
        return listChromosomePlots;
    }

    public void setListChromosomePlots(List<ChromosomePlot> listChromosomePlots) {
        this.listChromosomePlots = listChromosomePlots;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Instability> getInstability() {
        return instability;
    }

    public void setInstability(List<Instability> instability) {
        this.instability = instability;
    }

    public List<QCMetric> getQcMetric() {
        return qcMetric;
    }

    public void setQcMetric(List<QCMetric> qcMetric) {
        this.qcMetric = qcMetric;
    }

    /**
     * @param sampleAnalysisId
     * @param sampleName
     * @param microarrays
     * @param genomicPlot
     * @param listChromosomePlots
     * @param listComments
     * @param instability
     * @param qcMetric
     */
    public SampleAnalysis(ObjectId sampleAnalysisId, String sampleName, List<Microarray> microarrays,
                          GenomicPlot genomicPlot, List<ChromosomePlot> listChromosomePlots, List<Comment> comments,
                          List<Instability> instability, List<QCMetric> qcMetric) {
        super();
        this.sampleAnalysisId = sampleAnalysisId;
        this.sampleName = sampleName;
        this.microarrays = microarrays;
        this.genomicPlot = genomicPlot;
        this.listChromosomePlots = listChromosomePlots;
        this.comments = comments;
        this.instability = instability;
        this.qcMetric = qcMetric;
    }


}
