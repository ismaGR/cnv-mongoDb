package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service.Impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.Batch;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.QCMetric;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.SampleAnalysis;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.repository.QCMetricRepository;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service.QCMetricService;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service.SampleAnalysisService;

@Service(value = "QCMetricService")
public class QCMetricServiceImpl implements QCMetricService {

    private static final Logger logger = LoggerFactory.getLogger(QCMetricServiceImpl.class);

    @Autowired
    private QCMetricRepository qcMetricRepository;

    @Autowired
    private SampleAnalysisService sampleAnalysisService;

    @Override
    public Collection<QCMetric> getAllQCMetric() {
        return qcMetricRepository.findAll();
    }

    @Override
    public void deleteAll() {
        qcMetricRepository.deleteAll();
    }

    @Override
    public QCMetric save(QCMetric entity) {
        return qcMetricRepository.save(entity);
    }

    @Override
    public void insertQCMetric(File filePath, SampleAnalysis sampleAnalysis, Batch batch) throws IOException {
        this.insertQCMetric(filePath, "\t", sampleAnalysis, batch);
    }

    /**
     * Insert QC Metric files in collection QCMetric
     *
     * @param filePath:       path of Miscellaneous information file
     * @param separator:      example "\t"
     * @param sampleAnalysis: specify the sample analysis object
     * @param batch:          specify the batch object
     * @throws IOException
     */
    private void insertQCMetric(File filePath, String separator, SampleAnalysis sampleAnalysis, Batch batch) throws IOException {


        logger.info("---------------------------------------------");
        logger.info("----------------- QC METRIC -----------------");
        logger.info("---------------------------------------------");

        logger.info("Import QC metric file: " + filePath.getName());

        ArrayList<QCMetric> qcMetrics = new ArrayList<>();

        if (filePath.exists()) {
            BufferedReader fileBR = new BufferedReader(new FileReader(filePath));
            fileBR.readLine().split(separator);
            String line;

            while ((line = fileBR.readLine()) != null) {
                QCMetric qcMetric = new QCMetric();

                String[] value = line.split(separator);

                qcMetric.setQcMetricId(new ObjectId());
                qcMetric.setMapd(Float.parseFloat(value[0]));
                qcMetric.setIqr(Float.parseFloat(value[1]));
                qcMetric.setSnpqc(Float.parseFloat(value[2]));
                qcMetric.setWavinessSD(Float.parseFloat(value[3]));
                qcMetric.setPredictedGender(value[4]);
                qcMetric.setMedRawIntensity(Integer.parseInt(value[5]));
                qcMetric.setOutlier(Integer.parseInt(value[6]));
                qcMetric.setNbBreakpoint(Integer.parseInt(value[7]));
                qcMetric.setSampleAnalysisId(sampleAnalysis.getSampleAnalysisId());
                qcMetric.setSampleName(sampleAnalysis.getSampleName());
                qcMetric.setBatchName(batch.getBatchName());
                qcMetricRepository.save(qcMetric);

                qcMetrics.add(qcMetric);
            }
            fileBR.close();
        }

        sampleAnalysis.setQcMetric(qcMetrics);
        sampleAnalysisService.save(sampleAnalysis);

    }
}
