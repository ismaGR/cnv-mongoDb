package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.webObj;

import org.bson.types.ObjectId;

public class SampleAnalysisWebObj {

    private ObjectId sampleAnalysisId;

    public String sampleName; //in collection sample_analysis

    public SampleAnalysisWebObj() {
        super();
    }

    public SampleAnalysisWebObj(ObjectId sampleAnalysisId, String sampleName) {
        super();
        this.sampleAnalysisId = sampleAnalysisId;
        this.sampleName = sampleName;
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
}
