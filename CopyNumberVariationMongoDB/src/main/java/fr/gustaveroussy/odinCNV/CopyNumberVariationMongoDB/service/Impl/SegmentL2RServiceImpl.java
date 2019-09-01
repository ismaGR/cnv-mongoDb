package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service.Impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.MiscellaneousInformation;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.SampleAnalysis;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.SegmentL2R;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.embedded.EaCoNParameter;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.repository.SegmentL2RRepository;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service.MiscellaneousInformationService;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service.SegmentL2RService;

@Service(value = "SegmentL2RService")
public class SegmentL2RServiceImpl implements SegmentL2RService {

    private static final Logger logger = LoggerFactory.getLogger(SegmentL2RServiceImpl.class);

    @Autowired
    private SegmentL2RRepository segmentL2RRepository;

    @Autowired
    private MiscellaneousInformationService MisInfoService;

    @Override
    public Collection<SegmentL2R> getAllSegmentL2Rs() {
        return segmentL2RRepository.findAll();
    }

    @Override
    public List<SegmentL2R> findBySampleName(String sampleName) {
        return segmentL2RRepository.findBySampleName(sampleName);
    }

    @Override
    public void deleteAll() {
        segmentL2RRepository.deleteAll();
    }

    public void insertSegmentL2R(File filePath, SampleAnalysis sampleAnalysis) throws IOException {
        this.insertSegmentL2R(filePath, "\t", sampleAnalysis);
    }


    /**
     * Insert Segment L2R files in collection SegmentL2R
     *
     * @param filePath:       path of L2R Segment file
     * @param separator:      example "\t"
     * @param sampleAnalysis: specify the sample analysis object
     * @throws IOException
     */
    public void insertSegmentL2R(File filePath, String separator, SampleAnalysis sampleAnalysis) throws IOException {

        SegmentL2R segmentL2R;


        /**
         * Get L2R segments gain and loss cutoff in Miscellaneous Information collection
         */
        MiscellaneousInformation misInfo;

        misInfo = MisInfoService.findBySampleAnalysisId(sampleAnalysis.getSampleAnalysisId());

        Float L2RGain = null;
        Float L2RLoss = null;

        for (EaCoNParameter EaCoNPara : misInfo.getListEaCoNParameter()) {
            if (EaCoNPara.getParameter().equals("L2R.segments.gain.cutoff")) {
                L2RGain = Float.parseFloat(EaCoNPara.getParamValue());
            }
            if (EaCoNPara.getParameter().equals("L2R.segments.loss.cutoff")) {
                L2RLoss = Float.parseFloat(EaCoNPara.getParamValue());
            }
        }

        logger.info("---------------------------------------------");
        logger.info("---------------  L2R SEGMENTS  --------------");
        logger.info("---------------------------------------------");

        logger.info("Import segment L2R file: " + filePath.getName());

        if (filePath.exists()) {
            BufferedReader fileCut = new BufferedReader(new FileReader(filePath));
            fileCut.readLine().split(separator);
            String line;

            while ((line = fileCut.readLine()) != null) {
                String[] value = line.split("\t");

                segmentL2R = new SegmentL2R();

                segmentL2R.setSegmentL2RId(new ObjectId());
                segmentL2R.setSampleAnalysisId(sampleAnalysis.getSampleAnalysisId());
                segmentL2R.setSampleName(sampleAnalysis.getSampleName());
                segmentL2R.setChrom(value[0]);
                segmentL2R.setChr(value[1]);
                segmentL2R.setSegmentStart(Integer.parseInt(value[2]));
                segmentL2R.setSegmentEnd(Integer.parseInt(value[3]));
                segmentL2R.setSegmentWidth(Integer.parseInt(value[4]));
                segmentL2R.setSegmentL2RValue(Float.parseFloat(value[5]));
                segmentL2R.setSegmentL2RRatio(Float.parseFloat(value[6]));
                segmentL2R.setSegmentStartCytoband(value[7]);
                segmentL2R.setSegmentEndCytoband(value[8]);
                segmentL2R.setNbMarkers(Integer.parseInt(value[9]));
                segmentL2R.setNbGenes(Integer.parseInt(value[10]));
                segmentL2R.setIsInClinicalReport(false);

                if (segmentL2R.getSegmentL2RValue() >= L2RGain && !L2RGain.equals(null)) {
                    segmentL2R.setSegmentL2RStatus("Gain");
                }
                if (segmentL2R.getSegmentL2RValue() <= L2RLoss && !L2RLoss.equals(null)) {
                    segmentL2R.setSegmentL2RStatus("Loss");
                }
                if (segmentL2R.getSegmentL2RValue() >= L2RLoss && segmentL2R.getSegmentL2RValue() <= L2RGain && !L2RGain.equals(null) && !L2RLoss.equals(null)) {
                    segmentL2R.setSegmentL2RStatus("Normal");
                }


                segmentL2RRepository.save(segmentL2R);

            }
            fileCut.close();
        }
    }
}
