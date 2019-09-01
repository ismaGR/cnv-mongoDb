package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service.Impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.Batch;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.MiscellaneousInformation;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.SampleAnalysis;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.embedded.AffymetrixParameter;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.embedded.DataSummary;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.embedded.EaCoNParameter;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.repository.MiscellaneousInformationRepository;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service.BatchService;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service.MiscellaneousInformationService;

@Service(value = "MiscellaneousInformationService")
public class MiscellaneousInformationServiceImpl implements MiscellaneousInformationService {

    private static final Logger logger = LoggerFactory.getLogger(MiscellaneousInformationServiceImpl.class);

    @Autowired
    private MiscellaneousInformationRepository misInfoRepository;

    @Autowired
    private BatchService batchService;

    @Override
    public void deleteAll() {
        misInfoRepository.deleteAll();
    }

    @Override
    public MiscellaneousInformation save(MiscellaneousInformation entity) {
        return misInfoRepository.save(entity);
    }

    @Override
    public void insertMiscellaneousInfo(File filePath, SampleAnalysis sampleAnalysis, Batch batch) throws IOException {
        this.insertMiscellaneousInfo(filePath, "\t", sampleAnalysis, batch);

    }

    /**
     * Insert Miscellaneous information files in collection MiscellaneousInformation
     *
     * @param filePath:       path of Miscellaneous information file
     * @param separator:      example "\t"
     * @param sampleAnalysis: specify the sample analysis object
     * @param batch:          specify the batch object
     * @throws IOException
     */
    private void insertMiscellaneousInfo(File filePath, String separator, SampleAnalysis sampleAnalysis, Batch batch) throws IOException {

        logger.info("---------------------------------------------");
        logger.info("--------- MISCELLANEOUS INFORMATION ---------");
        logger.info("---------------------------------------------");
        logger.info("Import miscellaneous file: " + filePath.getName());

        MiscellaneousInformation misInfo = new MiscellaneousInformation();

        List<DataSummary> listdataSummary = new ArrayList<>();
        List<EaCoNParameter> listEaCoNParam = new ArrayList<>();
        List<AffymetrixParameter> listAffymetrixParam = new ArrayList<>();

        int compteur = 1;
        Integer EaCoNStart = null;
        Integer AffymetrixStart = null;

        if (filePath.exists()) {
            BufferedReader fileBR = new BufferedReader(new FileReader(filePath));
            fileBR.readLine().split(separator);
            String line;


            //Find on which line starts the parameters list of EaCon, Affymetrix and the data summary
            while ((line = fileBR.readLine()) != null) {

                if (line.contains("EaCoN parameters")) {
                    EaCoNStart = compteur;

                }
                if (line.contains("Affymetrix Parameters")) {
                    AffymetrixStart = compteur;

                }
                if (EaCoNStart != null && AffymetrixStart != null) {
                    break;
                }
                compteur++;
            }
            fileBR.close();


            LineNumberReader reader = null;
            try {
                reader = new LineNumberReader(new FileReader(filePath));
                reader.readLine().split(separator);

                String line2;

                while ((line2 = reader.readLine()) != null) {
                    String[] value = line2.split(separator);

                    if (reader.getLineNumber() <= EaCoNStart) {
                        DataSummary dataSum = new DataSummary();
                        dataSum.setParameter(value[0]);

                        if (value.length > 1) {
                            dataSum.setParamValue(value[1]);

                            if (value[0].equals("source")) {
                                batch.setBatchTechnology(value[1]);
                                batchService.save(batch);

                            }
                            if (value[0].equals("type")) {
                                batch.setBatchInstrument(value[1]);
                                batchService.save(batch);
                            }
                            if (value[0].equals("manufacturer")) {
                                batch.setBatchDetail(value[1]);
                                batchService.save(batch);
                            }
                        }

                        listdataSummary.add(dataSum);

                    }
                    if ((reader.getLineNumber() > EaCoNStart + 1) && (reader.getLineNumber() <= AffymetrixStart)) {
                        EaCoNParameter EaCoNParam = new EaCoNParameter();
                        EaCoNParam.setParameter(value[0]);

                        if (value.length > 1) {
                            EaCoNParam.setParamValue(value[1]);
                        }

                        listEaCoNParam.add(EaCoNParam);

                    } else if (reader.getLineNumber() > AffymetrixStart + 1) {
                        AffymetrixParameter affyParam = new AffymetrixParameter();
                        affyParam.setParameter(value[0]);

                        if (value.length > 1) {
                            affyParam.setParamValue(value[1]);
                        }
                        listAffymetrixParam.add(affyParam);
                    }
                }

            } finally {
                if (reader != null)
                    reader.close();
            }

            misInfo.setSampleAnalysisId(sampleAnalysis.getSampleAnalysisId());
            misInfo.setListDataSummary(listdataSummary);
            misInfo.setListEaCoNParameter(listEaCoNParam);
            misInfo.setListAffymetrixParameter(listAffymetrixParam);
            misInfoRepository.save(misInfo);

        }
    }

    @Override
    public MiscellaneousInformation findBySampleAnalysisId(ObjectId sampleAnalysisId) {
        return misInfoRepository.findBySampleAnalysisId(sampleAnalysisId);
    }
}
