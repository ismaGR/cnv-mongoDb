package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.embedded;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Field;

public class Microarray {

    @Field(value = "array_type")
    public String arrayType;

    @Field(value = "array_id")
    public String arrayId;

    @Field(value = "array_barcode")
    public String arrayBarcode;

    @Field(value = "scanner_id")
    public String scannerId;

    @Field(value = "scan_date")
    public Date scanDate;

    @Field(value = "path_to_plot")
    public String pathToPlot;

    public String getArrayType() {
        return arrayType;
    }

    public void setArrayType(String arrayType) {
        this.arrayType = arrayType;
    }

    public String getArrayId() {
        return arrayId;
    }

    public void setArrayId(String arrayId) {
        this.arrayId = arrayId;
    }

    public String getArrayBarcode() {
        return arrayBarcode;
    }

    public void setArrayBarcode(String arrayBarcode) {
        this.arrayBarcode = arrayBarcode;
    }

    public String getScannerId() {
        return scannerId;
    }

    public void setScannerId(String scannerId) {
        this.scannerId = scannerId;
    }

    public Date getScanDate() {
        return scanDate;
    }

    public void setScanDate(Date date) {
        this.scanDate = date;
    }

    public String getPathToPlot() {
        return pathToPlot;
    }

    public void setPathToPlot(String pathToPlot) {
        this.pathToPlot = pathToPlot;
    }


    public Microarray(String arrayType, String arrayId, String arrayBarcode, String scannerId, Date scanDate,
                      String pathToPlot) {
        super();
        this.arrayType = arrayType;
        this.arrayId = arrayId;
        this.arrayBarcode = arrayBarcode;
        this.scannerId = scannerId;
        this.scanDate = scanDate;
        this.pathToPlot = pathToPlot;
    }

    public Microarray() {
        super();
    }

}
