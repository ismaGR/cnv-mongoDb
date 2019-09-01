package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.embedded;

import org.springframework.data.mongodb.core.mapping.Field;

public class GenomicPlot {

    @Field(value = "path_file_BAF")
    private String pathFileBAF;

    @Field(value = "path_file_L2R")
    private String pathFileL2R;

    @Field(value = "path_file_L2R_BAF")
    private String pathFileL2RBAF;

    public String getPathFileBAF() {
        return pathFileBAF;
    }

    public void setPathFileBAF(String pathFileBAF) {
        this.pathFileBAF = pathFileBAF;
    }

    public String getPathFileL2R() {
        return pathFileL2R;
    }

    public void setPathFileL2R(String pathFileL2R) {
        this.pathFileL2R = pathFileL2R;
    }

    public String getPathFileL2RBAF() {
        return pathFileL2RBAF;
    }

    public void setPathFileL2RBAF(String pathFileL2RBAF) {
        this.pathFileL2RBAF = pathFileL2RBAF;
    }

    public GenomicPlot() {
        super();
    }

    public GenomicPlot(String pathFileBAF, String pathFileL2R, String pathFileL2RBAF) {
        super();
        this.pathFileBAF = pathFileBAF;
        this.pathFileL2R = pathFileL2R;
        this.pathFileL2RBAF = pathFileL2RBAF;
    }

}
