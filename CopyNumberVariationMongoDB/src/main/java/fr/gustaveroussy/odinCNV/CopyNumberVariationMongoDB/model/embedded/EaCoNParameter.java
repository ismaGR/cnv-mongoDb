package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.embedded;

import org.springframework.data.mongodb.core.mapping.Field;

public class EaCoNParameter {

    @Field(value = "parameter")
    private String parameter;

    @Field(value = "value")
    private String paramValue;

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public EaCoNParameter(String parameter, String paramValue) {
        super();
        this.parameter = parameter;
        this.paramValue = paramValue;
    }

    public EaCoNParameter() {
        super();
    }

}
