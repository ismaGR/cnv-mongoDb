package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.embedded.AffymetrixParameter;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.embedded.DataSummary;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.embedded.EaCoNParameter;

@Document(collection = "Miscellaneous_Information")
public class MiscellaneousInformation {

    @Id
    public ObjectId miscellaneousInfoId;

    @Field(value = "sample_analysis_id")
    private ObjectId sampleAnalysisId;

    public List<DataSummary> listDataSummary;

    public List<EaCoNParameter> listEaCoNParameter;

    public List<AffymetrixParameter> listAffymetrixParameter;

    public ObjectId getMiscellaneousInfoId() {
        return miscellaneousInfoId;
    }

    public void setMiscellaneousInfoId(ObjectId miscellaneousInfoId) {
        this.miscellaneousInfoId = miscellaneousInfoId;
    }

    public ObjectId getSampleAnalysisId() {
        return sampleAnalysisId;
    }

    public void setSampleAnalysisId(ObjectId sampleAnalysisId) {
        this.sampleAnalysisId = sampleAnalysisId;
    }

    public List<DataSummary> getListDataSummary() {
        return listDataSummary;
    }

    public void setListDataSummary(List<DataSummary> listDataSummary) {
        this.listDataSummary = listDataSummary;
    }

    public List<EaCoNParameter> getListEaCoNParameter() {
        return listEaCoNParameter;
    }

    public void setListEaCoNParameter(List<EaCoNParameter> listEaCoNParameter) {
        this.listEaCoNParameter = listEaCoNParameter;
    }

    public List<AffymetrixParameter> getListAffymetrixParameter() {
        return listAffymetrixParameter;
    }

    public void setListAffymetrixParameter(List<AffymetrixParameter> listAffymetrixParameter) {
        this.listAffymetrixParameter = listAffymetrixParameter;
    }

    public MiscellaneousInformation(ObjectId miscellaneousInfoId, ObjectId sampleAnalysisId,
                                    List<DataSummary> listDataSummary, List<EaCoNParameter> listEaCoNParameter,
                                    List<AffymetrixParameter> listAffymetrixParameter) {
        super();
        this.miscellaneousInfoId = miscellaneousInfoId;
        this.sampleAnalysisId = sampleAnalysisId;
        this.listDataSummary = listDataSummary;
        this.listEaCoNParameter = listEaCoNParameter;
        this.listAffymetrixParameter = listAffymetrixParameter;
    }

    public MiscellaneousInformation() {
        super();
    }

}
