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

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.SampleAnalysis;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.SegmentBAF;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.repository.SegmentBAFRepository;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service.SegmentBAFService;

@Service(value = "SegmentBAFService")
public class SegmentBAFServiceImpl implements SegmentBAFService {

    private static final Logger logger = LoggerFactory.getLogger(SegmentBAFServiceImpl.class);

    @Autowired
    private SegmentBAFRepository segmentBAFRepository;

    @Override
    public Collection<SegmentBAF> getAllSegmentBAFs() {
        return segmentBAFRepository.findAll();
    }

    public void insertSegmentBAF(File filePath, SampleAnalysis sampleAnalysis) throws IOException {
        this.insertSegmentBAF(filePath, "\t", sampleAnalysis);

    }

    /**
     * Insert BAF Segment files in collection BAFSegment
     *
     * @param filePath:       path of BAF Segment file
     * @param separator:      example "\t"
     * @param sampleAnalysis: specify the sample analysis object
     * @throws IOException
     */
    private void insertSegmentBAF(File filePath, String separator, SampleAnalysis sampleAnalysis) throws IOException {

        logger.info("----------------------------------------------");
        logger.info("------------     BAF SEGMENTS     ------------");
        logger.info("----------------------------------------------");

        SegmentBAF segmentBAF;

        logger.info("Import file: " + filePath.getName());
        if (filePath.exists()) {
            BufferedReader fileBR = new BufferedReader(new FileReader(filePath));
            fileBR.readLine().split(separator);
            String line;

            while ((line = fileBR.readLine()) != null) {
                String[] value = line.split(separator);

                segmentBAF = new SegmentBAF();
                segmentBAF.setSegmentBAFId(new ObjectId());
                segmentBAF.setChrom(value[0]);
                segmentBAF.setChr(value[1]);
                segmentBAF.setSegmentStart(Integer.parseInt(value[2]));
                segmentBAF.setSegmentEnd(Integer.parseInt(value[3]));
                segmentBAF.setSegmentWidth(Integer.parseInt(value[4]));
                segmentBAF.setBAFValue(Float.parseFloat(value[5]));
                segmentBAF.setBAFStatus(value[6]);
                segmentBAF.setSegmentStartCytoband(value[7]);
                segmentBAF.setSegmentEndCytoband(value[8]);
                segmentBAF.setNbMarkers(Integer.parseInt(value[9]));
                segmentBAF.setNbGenes(Integer.parseInt(value[10]));
                segmentBAF.setSampleName(sampleAnalysis.getSampleName());
                segmentBAF.setSampleAnalysisId(sampleAnalysis.getSampleAnalysisId());
                segmentBAF.setIsInClinicalReport(false);
                segmentBAFRepository.save(segmentBAF);

            }
            fileBR.close();
        }
    }

    @Override
    public void deleteAll() {
        segmentBAFRepository.deleteAll();
    }

    @Override
    public List<SegmentBAF> findBySampleName(String sampleName) {
        return segmentBAFRepository.findBySampleName(sampleName);
    }


}
