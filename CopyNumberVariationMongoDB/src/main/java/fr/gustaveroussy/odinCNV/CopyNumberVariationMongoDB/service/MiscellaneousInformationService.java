package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service;

import java.io.File;
import java.io.IOException;

import org.bson.types.ObjectId;

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.Batch;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.MiscellaneousInformation;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.SampleAnalysis;

public interface MiscellaneousInformationService {
    void deleteAll();

    public MiscellaneousInformation save(MiscellaneousInformation entity);

    public MiscellaneousInformation findBySampleAnalysisId(ObjectId sampleAnalysisId);

    void insertMiscellaneousInfo(File filePath, SampleAnalysis sampleAnalysis, Batch batch) throws IOException;
}
