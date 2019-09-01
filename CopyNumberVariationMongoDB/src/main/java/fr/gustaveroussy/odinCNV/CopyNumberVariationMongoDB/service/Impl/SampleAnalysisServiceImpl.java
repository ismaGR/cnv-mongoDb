package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service.Impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.SampleAnalysis;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.embedded.ChromosomePlot;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.embedded.GenomicPlot;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.embedded.Microarray;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.repository.SampleAnalysisRepository;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service.SampleAnalysisService;

@Service(value = "SampleAnalysisService")
public class SampleAnalysisServiceImpl implements SampleAnalysisService {

    private static final Logger logger = LoggerFactory.getLogger(SampleAnalysisServiceImpl.class);

    @Autowired
    private SampleAnalysisRepository sampleAnalysisRepository;

    @Override
    public Collection<SampleAnalysis> getAllSamplesAnalysis() {
        return sampleAnalysisRepository.findAll();
    }

    @Override
    public SampleAnalysis findBySampleName(String sampleName) {
        return sampleAnalysisRepository.findBySampleName(sampleName);
    }

    @Override
    public SampleAnalysis findBySampleAnalysisId(ObjectId sampleAnalysisId) {
        return sampleAnalysisRepository.findBySampleAnalysisId(sampleAnalysisId);
    }

    @Override
    public void deleteAll() {
        sampleAnalysisRepository.deleteAll();
    }

    @Override
    public void save(SampleAnalysis entity) {
        sampleAnalysisRepository.save(entity);
    }

    public void insertMicroarray(File filePath, SampleAnalysis sampleAnalysis, String microarrayPlotPath) throws IOException {
        this.insertMicroarray(filePath, "\t", sampleAnalysis, microarrayPlotPath);
    }


    /**
     * Insert Microarray information in embedded collection Microarray
     *
     * @param filePath:           path of Miscellaneous information file
     * @param separator:          example "\t"
     * @param sampleAnalysis:     specify the sample analysis object
     * @param microarrayPlotPath: path of microarray plot
     * @throws IOException
     */
    private void insertMicroarray(File filePath, String separator, SampleAnalysis sampleAnalysis, String microarrayPlotPath) throws IOException {


        logger.info("---------------------------------------------");
        logger.info("---------------- MICROARRAY -----------------");
        logger.info("---------------------------------------------");

        logger.info("Import Microarray file: " + filePath.getName());

        final List<Microarray> microarrays = new ArrayList<>();

        if (filePath.exists()) {

            BufferedReader fileBR = new BufferedReader(new FileReader(filePath));
            fileBR.readLine().split(separator);
            String line;

            while ((line = fileBR.readLine()) != null) {


                Microarray microarray = new Microarray();

                String[] value = line.split(separator);
                microarray.setArrayType(value[1]);
                microarray.setArrayId(value[2]);
                microarray.setArrayBarcode(value[3]);
                microarray.setScannerId(value[4]);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                String dateInString = value[5];

                Date date;
                try {
                    date = formatter.parse(dateInString.replaceAll("Z$", "+0000"));
                    microarray.setScanDate(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                microarray.setPathToPlot(microarrayPlotPath);
                microarrays.add(microarray);
            }
            fileBR.close();
        }

        sampleAnalysis.setMicroarrays(microarrays);
        sampleAnalysisRepository.save(sampleAnalysis);
    }

    /**
     * Insert genomic plot file in embedded collection GenomicPlot
     */
    public void insertGenomicPlot(String PathFileBAF, String PathFileL2R, String PathFileL2RBAF, SampleAnalysis sampleAnalysis) {

        /**
         * Only one plot for BAF, L2R and BAF+L2R
         * convert the arrayList in string with on element
         */

        logger.info("---------------------------------------------");
        logger.info("---------------- GENOMIC PLOT ---------------");
        logger.info("---------------------------------------------");

        logger.info("Import Genomic plots.");

        GenomicPlot genomicPlot = new GenomicPlot();

        genomicPlot.setPathFileBAF(PathFileBAF);
        genomicPlot.setPathFileL2R(PathFileL2R);
        genomicPlot.setPathFileL2RBAF(PathFileL2RBAF);

        sampleAnalysis.setGenomicPlot(genomicPlot);
        sampleAnalysisRepository.save(sampleAnalysis);
    }

    /**
     * Insert Chromosome plots in embedded collection ChromosomePlot
     */
    public void insertChromosomePlot(List<String> ChromosomesFile, SampleAnalysis sampleAnalysis) {


        logger.info("---------------------------------------------");
        logger.info("-------------- CHROMOSOMES PLOT -------------");
        logger.info("---------------------------------------------");

        logger.info("Import Chromosomes plots.");
        List<ChromosomePlot> Chromosomes = new ArrayList<>();

        for (String Chrom : ChromosomesFile) {
            if (Chrom.contains(".png")) {

                File ChrFile = new File(Chrom);
                String[] ChrName = ChrFile.getName().split(".png");

                ChromosomePlot chromosomePlot = new ChromosomePlot();

                chromosomePlot.setChrom(ChrName[0]);
                chromosomePlot.setPathFileChrom(ChrFile.toString());

                Chromosomes.add(chromosomePlot);
            }
            sampleAnalysis.setListChromosomePlots(Chromosomes);
            sampleAnalysisRepository.save(sampleAnalysis);
        }
    }

}
