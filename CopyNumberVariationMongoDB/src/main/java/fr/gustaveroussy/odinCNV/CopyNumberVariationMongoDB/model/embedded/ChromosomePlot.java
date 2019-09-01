package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.embedded;

import org.springframework.data.mongodb.core.mapping.Field;

public class ChromosomePlot {

    @Field(value = "chrom")
    public String chrom;

    @Field(value = "path_file_chrom")
    public String pathFileChrom;

    public String getChrom() {
        return chrom;
    }

    public void setChrom(String chrom) {
        this.chrom = chrom;
    }

    public String getPathFileChrom() {
        return pathFileChrom;
    }

    public void setPathFileChrom(String pathFileChrom) {
        this.pathFileChrom = pathFileChrom;
    }

    public ChromosomePlot(String chrom, String pathFileChrom) {
        super();
        this.chrom = chrom;
        this.pathFileChrom = pathFileChrom;
    }

    public ChromosomePlot() {
        super();
    }
}
