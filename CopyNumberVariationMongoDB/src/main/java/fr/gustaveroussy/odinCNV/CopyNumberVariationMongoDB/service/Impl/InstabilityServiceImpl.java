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

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.Instability;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.SampleAnalysis;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.repository.InstabilityRepository;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service.InstabilityService;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service.SampleAnalysisService;

@Service(value = "InstabilityService")
public class InstabilityServiceImpl implements InstabilityService {

    private static final Logger logger = LoggerFactory.getLogger(InstabilityServiceImpl.class);

    @Autowired
    private InstabilityRepository instabilityRepository;

    @Autowired
    private SampleAnalysisService sampleAnalysisService;


    @Override
    public Collection<Instability> getAllInstability() {
        return instabilityRepository.findAll();
    }

    @Override
    public void deleteAll() {
        instabilityRepository.deleteAll();
    }

    @Override
    public void insertInstab(File filePath, SampleAnalysis sampleAnalysis) throws IOException {
        this.insertInstab(filePath, "\t", sampleAnalysis);
    }

    /**
     * Insert Instability files into collection Instability
     *
     * @param filePath:       path of instability file
     * @param separator:      example "\t"
     * @param sampleAnalysis: specify the sample analysis object
     * @throws IOException
     */
    private void insertInstab(File filePath, String separator, SampleAnalysis sampleAnalysis) throws IOException {

        logger.info("---------------------------------------------");
        logger.info("-----------  GENOMIC INSTABILITY ------------");
        logger.info("---------------------------------------------");

        logger.info("Import instability file: " + filePath.getName());
        ArrayList<Instability> instabilities = new ArrayList<>();


        if (filePath.exists()) {
            BufferedReader fileBR = new BufferedReader(new FileReader(filePath));
            fileBR.readLine().split(separator);
            String line;

            while ((line = fileBR.readLine()) != null) {

                Instability instability = new Instability();

                String[] value = line.split(separator);

                instability.setInstabilityId(new ObjectId());
                instability.setSampleName(sampleAnalysis.getSampleName());
                instability.setCNAStatus(value[0]);
                instability.setNbSegments(Integer.parseInt(value[1]));

                String[] val2 = value[2].replace(",", "").split("\t");
                instability.setTotalWidth(Long.parseLong(val2[0].trim()));

                String[] val3 = value[3].split("%");
                instability.setGenomeFraction(Double.parseDouble(val3[0].trim()));

                String[] val4 = value[4].replace(",", "").split("\t");
                instability.setMedianWidth(Long.parseLong(val4[0].trim()));
                instability.setMedianLog2Ratio(Double.parseDouble(value[5]));

                instability.setSampleAnalysisId(sampleAnalysis.getSampleAnalysisId());
                instabilityRepository.save(instability);

                instabilities.add(instability);

            }
            fileBR.close();
        }
        sampleAnalysis.setInstability(instabilities);
        sampleAnalysisService.save(sampleAnalysis);
    }

    @Override
    public Instability save(Instability entity) {
        return instabilityRepository.save(entity);
    }
}
