package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.Batch;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.Gene;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.SampleAnalysis;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service.BatchService;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service.GeneService;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service.InstabilityService;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service.MiscellaneousInformationService;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service.QCMetricService;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service.SampleAnalysisService;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service.SegmentBAFService;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service.SegmentL2RService;

@SpringBootApplication
public class CopyNumberVariationMongoDbApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CopyNumberVariationMongoDbApplication.class);

    @Autowired
    private BatchService batchService;

    @Autowired
    private SegmentBAFService segmentBAFService;

    @Autowired
    private SampleAnalysisService sampleAnalysisService;

    @Autowired
    private SegmentL2RService segmentL2RService;

    @Autowired
    private GeneService geneService;

    @Autowired
    private InstabilityService instabilityService;

    @Autowired
    private QCMetricService qcMetricService;

    @Autowired
    private MiscellaneousInformationService misInfoService;

    public static void main(String[] args) throws IOException {
        logger.info("Application launchs...");
        SpringApplication.run(CopyNumberVariationMongoDbApplication.class, args);
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory, MongoMappingContext context) {

        MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), context);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));

        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, converter);

        return mongoTemplate;
    }

    @Override
    public void run(String... args) throws Exception {

        logger.info("*********************************************");
        logger.info("***********    DROP COLLECTIONS    **********");
        logger.info("*********************************************");

        batchService.deleteAll();
        sampleAnalysisService.deleteAll();
        segmentBAFService.deleteAll();
        segmentL2RService.deleteAll();
        geneService.deleteAll();
        instabilityService.deleteAll();
        qcMetricService.deleteAll();
        misInfoService.deleteAll();

        /**
         * Import batchs and samples name in collection RunInformation
         */

        String mainDirPath = "C://Users/33753/Desktop/data_sarcomes_EaCoN_R";

        List<File> BatchFilesNames = new ArrayList<File>();

        /** Find all Batchs directory in the main directory*/
        BatchFilesNames = extractDirsFromDir(mainDirPath);

        logger.info("Batchs found :");

        BatchFilesNames.stream().forEach(BatchFileName -> logger.info(BatchFileName.toString()));

        /**
         * For each Batch create a new Batch Object
         */
        for (File f : BatchFilesNames) {
            logger.info("*******************************************************");
            logger.info("              IMPORT " + f.getName() + "");
            logger.info("*******************************************************");

            List<File> samplesDir = new ArrayList<File>();
            List<String> samplesName = new ArrayList<String>();

            /**Convert file type to string for the file path*/
            String str = f.toString();

            /**Retrieve all samples directory into a batch*/
            samplesDir = extractDirsFromDir(str);
            samplesName = convertPathToString(samplesDir);

            /**Regular expression to remove repeated file contains in solo and SAFIR directories*/
            List<String> sampleSelect = new ArrayList<String>();
            for (String sampleName : samplesName) {
                if (!sampleName.matches("(SAFIR)|(WITH_SOLO)|(With_Solo)|(with_solo)")) {
                    sampleSelect.add(sampleName);
                }
            }

            /**
             * Create and instantiated an Object Batch
             */
            Batch batch = new Batch();
            batch.setBatchId(new ObjectId());
            batch.setBatchName(f.getName());
            batchService.save(batch);

            logger.info("Repository root : " + f + " Sub-repository found :" + samplesDir);
            logger.info("Batch name: " + f.getName() + " sample(s) name : " + sampleSelect);

            /** Add sample in Sample_Analysis collection */
            List<SampleAnalysis> SampleAnalysisList = new ArrayList<>();

            for (File sampleDir : samplesDir) {

                if (!sampleDir.getName().matches("(SAFIR)|(WITH_SOLO)|(With_Solo)|(with_solo)|(.snakemake)|(logs)|(raw_data)")) {

                    SampleAnalysis sampleAnalysis = new SampleAnalysis();

                    sampleAnalysis.setSampleAnalysisId(new ObjectId());
                    sampleAnalysis.setSampleName(sampleDir.getName());
                    sampleAnalysisService.save(sampleAnalysis);

                    SampleAnalysisList.add(sampleAnalysis);
                    batch.setSampleAnalysisList(SampleAnalysisList);

                    batchService.save(batch);

                    logger.info("*********************************************");
                    logger.info("      Sample : " + sampleAnalysis.getSampleName());
                    logger.info("*********************************************");


                    /**
                     * Import Miscellaneous informations
                     */
                    String MisFileExtension = "_miscellaneous_info.txt";
                    String MisFile = sortOneFileByExt(MisFileExtension, sampleDir.toString());

                    if (!MisFile.isEmpty()) {
                        misInfoService.insertMiscellaneousInfo(new File(MisFile), sampleAnalysis, batch);
                    } else {
                        logger.error("The file" + sampleAnalysis.getSampleName() + MisFileExtension + " doesn't exist. Pleanse run R script again.");
                    }


                    /**
                     * Import BAF SEGMENTS
                     */
                    String BAFFileExtension = "CytobandSegmentedBAF.txt";
                    String BAFFile = sortOneFileByExt(BAFFileExtension, sampleDir.toString());

                    if (!BAFFile.isEmpty()) {
                        segmentBAFService.insertSegmentBAF(new File(BAFFile), sampleAnalysis);
                    } else {
                        logger.error("The file " + sampleAnalysis.getSampleName() + "." + BAFFileExtension + " doesn't exist. Please run R script again.");
                    }


                    /**
                     * Import Genomic instability file
                     */
                    String InstabFileExtension = "Instab.txt";
                    String InstabFile = sortOneFileByExt(InstabFileExtension, sampleDir.toString());

                    if (!InstabFile.isEmpty()) {
                        instabilityService.insertInstab(new File(InstabFile), sampleAnalysis);
                    } else {
                        logger.error("The file" + sampleAnalysis.getSampleName() + "." + InstabFileExtension + " is not found.");
                    }


                    /**
                     * Import targeted/truncated genes files
                     */
                    String TargetGenesFileExtension = "TargetGenes.txt";
                    String TruncatedGenesFileExtension = "TruncatedGenes.txt";

                    String TargetGeneFile = sortOneFileByExt(TargetGenesFileExtension, sampleDir.toString());
                    String TruncatedGeneFile = sortOneFileByExt(TruncatedGenesFileExtension, sampleDir.toString());

                    if (!TargetGeneFile.isEmpty()) {
                        geneService.insertGene(new File(TargetGeneFile), false, sampleAnalysis);
                    } else {
                        logger.error("The file" + sampleAnalysis.getSampleName() + "." + TargetGenesFileExtension + " is not found.");
                    }

                    if (!TruncatedGeneFile.isEmpty()) {
                        geneService.insertGene(new File(TruncatedGeneFile), true, sampleAnalysis);
                    } else {
                        logger.error("The file" + sampleAnalysis.getSampleName() + "." + TruncatedGenesFileExtension + " is not found.");
                    }


                    /**
                     * Import segments L2R file
                     */
                    String L2RFileExtension = "CytobandSegmentedL2R.txt";
                    String L2RFile = sortOneFileByExt(L2RFileExtension, sampleDir.toString());

                    if (!L2RFile.isEmpty()) {
                        segmentL2RService.insertSegmentL2R(new File(L2RFile), sampleAnalysis);
                    } else {
                        logger.error("The file" + sampleAnalysis.getSampleName() + "." + L2RFile + " doesn't exist. Please run R script again.");
                    }


                    /**
                     * Import chromosomes files
                     */
                    List<String> ChrFiles = new ArrayList<String>();
                    String ChrFileExtension = "chr";

                    ChrFiles = sortFilesByExt(ChrFileExtension, sampleDir.toString());
                    if (!ChrFiles.isEmpty()) {
                        sampleAnalysisService.insertChromosomePlot(ChrFiles, sampleAnalysis);
                    } else {
                        logger.error("Chromosomes plots files not found.");
                    }


                    /**
                     * Import microarray file
                     */
                    String MicroarrayFileExtension = "_microarray.txt";
                    String MicroarrayPlotExtension = ".INT";

                    String MicroarrayFile = sortOneFileByExt(MicroarrayFileExtension, sampleDir.toString());
                    String MicroarrayPlot = sortOneFileByExt(MicroarrayPlotExtension, sampleDir.toString());

                    if (!MicroarrayFile.isEmpty() || !MicroarrayPlot.isEmpty()) {
                        sampleAnalysisService.insertMicroarray(new File(MicroarrayFile), sampleAnalysis, MicroarrayPlot);
                    } else {
                        logger.error("The file " + sampleAnalysis.getSampleName() + MicroarrayFileExtension + " or the file " +
                                sampleAnalysis.getSampleName() + MicroarrayPlotExtension + " are not found.");
                    }


                    /**
                     * Import Genomic plots
                     */

                    String FileBAFExt = "BAF.png";
                    String FileL2RExt = "L2R.G.png";
                    String FileL2RBAFExt = "SEG.ASCAT.png";

                    String PathFileBAF = sortOneFileByExt(FileBAFExt, sampleDir.toString());
                    String PathFileL2R = sortOneFileByExt(FileL2RExt, sampleDir.toString());
                    String PathFileL2RBAF = sortOneFileByExt(FileL2RBAFExt, sampleDir.toString());

                    if (!PathFileBAF.isEmpty() || !PathFileL2R.isEmpty() || !PathFileL2RBAF.isEmpty()) {
                        sampleAnalysisService.insertGenomicPlot(PathFileBAF, PathFileL2R, PathFileL2RBAF, sampleAnalysis);
                    } else {
                        logger.error("Genomic plots files not found");
                    }


                    /**
                     * Import QC metric informations
                     */
                    String QCMetricFileExtension = "_qc_metric.txt";
                    String QCMetricFile = sortOneFileByExt(QCMetricFileExtension, sampleDir.toString());

                    if (!QCMetricFile.isEmpty()) {
                        qcMetricService.insertQCMetric(new File(QCMetricFile), sampleAnalysis, batch);
                    } else {
                        logger.error("The file" + sampleAnalysis.getSampleName() + QCMetricFileExtension + " doesn't exist. Please run R script again.");
                    }


                    System.out.println("+ + + + + + + + + + + + + ");
                    System.out.println("ID du sample analysis" + sampleAnalysis.getSampleAnalysisId());
                    System.out.println("+ + + + + + + + + + + + + ");

                    List<Gene> GeneList = new ArrayList<>();

                    GeneList = geneService.GetGenesInSegmentBAF(sampleAnalysis.getSampleAnalysisId(), "chr1", 1156131, 5368589);
                    Integer compteur = 0;
                    for (Gene gene : GeneList) {
                        compteur++;
                        System.out.println("Gene :" + compteur + " " + gene.getGeneSymbol() + " " + gene.getGeneStart() + " " + gene.getGeneEnd());

                    }

                }
            }
            logger.info("Succesfully import :" + batch.getBatchName());
        }
    }

    /**
     * Convert list of paths in list of string with the last element of a path.
     *
     * @param : list of files
     */
    public List<String> convertPathToString(List<File> ListFiles) {
        List<String> NameFile = new ArrayList<String>();
        for (File f : ListFiles) {
            String pathName = f.getName();
            NameFile.add(pathName);
        }
        return NameFile;

    }

    /**
     * Return a list of directories found in the first level of a directory path as argument
     * Method use to find all sample directories in one batch directory
     *
     * @param mainDirPath : set a directory
     * @return
     */
    public List<File> extractDirsFromDir(String mainDirPath) {
        List<File> List = new ArrayList<File>();
        File mainDir = new File(mainDirPath);
        if (mainDir.exists() && mainDir.isDirectory()) {
            File arr[] = mainDir.listFiles();
            for (File f : arr) {
                if (f.isDirectory()) {
                    List.add(f);
                }
            }
        }
        return List;
    }


    /**
     * Return a list of all files with a specific extension
     *
     * @param FileExtension : the file extension
     * @param PathToFile    : the directory where to find files
     * @return
     */
    public List<String> sortFilesByExt(String FileExtension, String PathToFile) {
        List<String> List = new ArrayList<String>();
        try (Stream<Path> walk = Files.walk(Paths.get(PathToFile))) {

            List<String> allFiles = walk.map(x -> x.toString())
                    .collect(Collectors.toList());

            for (String filename : allFiles) {
                if (filename.contains(FileExtension)) {
                    List.add(filename);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return List;
    }

    /**
     * Return one file path
     *
     * @param FileExtension : the file extension
     * @param PathToFile:   the directory where to find files
     * @return
     */
    public String sortOneFileByExt(String FileExtension, String PathToFile) {
        String file = new String();
        try (Stream<Path> walk = Files.walk(Paths.get(PathToFile))) {

            List<String> allFiles = walk.map(x -> x.toString())
                    .collect(Collectors.toList());

            for (String filename : allFiles) {
                if (filename.contains(FileExtension)) {
                    file = filename;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}