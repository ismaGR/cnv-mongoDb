#!/usr/bin/Rscript 
##########################################################################################
#                     Parse RDS file for microarray and QC_metric file                   #
#                            Author: Amila Malinovic                                     #
##########################################################################################

#Passing Arguments
argv <- commandArgs(TRUE)
path_dir <- argv[1]

if(length(argv)==0){
  stop("Argument must be supplied")
}

cat("path_dir =", path_dir, "\n")

#Exemple : 
#Launch this command :
#Rscript parse_RDS_file "/media/sf_shared/test2" 

##########################################################################################
###Functions

#Return qc_metric file for Oncoscan and Cytoscan RDS file
qc_metric<- function(RDS_file,path_old_qc_file,instab_file_path,path_export){
  
  r4QC = read.table(path_old_qc_file, header=TRUE)
  qc = r4QC[,c("MAPD","iqr","SNPQC","waviness.sd")]
  
  instab = read.delim(instab_file_path)
  nb_segment = instab[5,2]
  nb_breakpoint = nb_segment-length(RDS_file$data$chrs)
  
  Predicted_Gender = RDS_file$data$gender
  
  if(is.null(RDS_file$CEL$CEL1)){
    Med_raw_intensity_A = median(RDS_file$CEL$ATChannelCel$intensities)
    Med_raw_intensity_C = median(RDS_file$CEL$GCChannelCel$intensities)
    Outliers_A = length(RDS_file$CEL$ATChannelCel$outliers)
    Outliers_C = length(RDS_file$CEL$GCChannelCel$outliers)
    
    Med_raw_intensity = c(Med_raw_intensity_A,Med_raw_intensity_C)
    
    Outliers = c(Outliers_A,Outliers_C)
  }else{
    Med_raw_intensity = median(RDS_file$CEL$CEL1$intensities)
    Outliers = length(RDS_file$CEL$CEL1$outliers)
  }
  #Output file
  output = cbind(qc,Predicted_Gender,Med_raw_intensity,Outliers,nb_breakpoint)
  write.table(output, path_export ,sep = "\t",row.names=F, quote = FALSE)
}

#Return microarray information for Cytoscan and Oncoscan file
microarray_info<- function(RDS_file, path_export){
  
  Sample_Name =RDS_file$data$samples
  Array_type = RDS_file$meta$basic$type
  
  #Check if it is a file from an oncoscan
  if(is.null((RDS_file$meta$affy$CEL1))){
    
    Array_id_A = RDS_file$meta$affy$ATChannelCel$array$`affymetrix-array-id`
    Array_id_C = RDS_file$meta$affy$GCChannelCel$array$`affymetrix-array-id`
    Array_id = c(Array_id_A,Array_id_C)
    
    Array_barcode_A = RDS_file$meta$affy$ATChannelCel$array$`affymetrix-array-barcode`
    Array_barcode_C = RDS_file$meta$affy$GCChannelCel$array$`affymetrix-array-barcode`
    Array_barcode = c(Array_barcode_A,Array_barcode_C)
    
    Scanner_id_A = RDS_file$meta$affy$ATChannelCel$acquisition$`affymetrix-scanner-id`
    Scanner_id_C = RDS_file$meta$affy$GCChannelCel$acquisition$`affymetrix-scanner-id`
    Scanner_id = c(Scanner_id_A,Scanner_id_C)
    
    Scan_date_A = RDS_file$meta$affy$ATChannelCel$acquisition$`affymetrix-scan-date`
    Scan_date_C = RDS_file$meta$affy$GCChannelCel$acquisition$`affymetrix-scan-date`

    Scan_date = c(Scan_date_A,Scan_date_C)
    
    #Else, cytoscan file
    
  }else{
    Array_id = RDS_file$meta$affy$CEL1$array$`affymetrix-array-id`
    
    Array_barcode = RDS_file$meta$affy$CEL1$array$`affymetrix-array-barcode`
    Scanner_id = RDS_file$meta$affy$CEL1$acquisition$`affymetrix-scanner-id`
    Scan_date = RDS_file$meta$affy$CEL1$acquisition$`affymetrix-scan-date`
  }
  output_microarray = cbind(Sample_Name,Array_type,Array_id,Array_barcode,Scanner_id,Scan_date)
  write.table(output_microarray,path_export,sep = "\t",row.names=F,quote = FALSE)
}

#Return miscellaneous information for Oncoscan and Cytoscan file
miscellaneous_information <- function(RDS_file, path_export){
  
  #data_summary
  #Check if it is a file from an oncoscan
  if(is.null((RDS_file$meta$affy$CEL1))){
    data_summary=unlist(RDS_file$meta$basic,recursive = FALSE, use.names = TRUE)
    data_summary=t(as.data.frame(data_summary))
    
  }else{
    data_summary = t(as.data.frame(RDS_file$meta$basic))
  }
  
  #EaCoN parameters
  EaCoN_param = t(as.data.frame(RDS_file$meta$eacon))
  
  #Affymetrix parameters
  
  Affy_param=unlist(unlist(RDS_file$meta$affy,recursive = FALSE, use.names = TRUE),recursive = FALSE, use.names = TRUE)
  Affy_param=t(as.data.frame(Affy_param))
  
  miscellanous_output = rbind(c("Data summary"),data_summary,c("EaCoN parameters"),EaCoN_param, c("Affymetrix Parameters"),Affy_param)
  write.table(miscellanous_output, path_export ,sep = "\t",row.names=T, col.names = F, quote = FALSE)
  
}


# List files recursively up to a certain level
list.dirs.depth.n <- function(p, n) {
  res <- list.dirs(p, recursive = FALSE)
  if (n > 1) {
    add <- list.dirs.depth.n(res, n-1)
    c(res, add)
  } else {
    res
  }
}

##########################################################################################
#Start code

ptm <- proc.time()

#List all batch contain in the previous path
list_batch = list.dirs.depth.n(path_dir, n = 1)

cat("\n")
cat("Batchs in the directory : ", "\n")
cat(list_batch, "\n")


nb_RDS_file =0 
for (i in list_batch) {
  list_samples = list.dirs.depth.n(i, n = 1)
  
  for (j in list_samples) {
    
    RDS_file_path = list.files(j,pattern = "SEG.ASCAT.RDS$", recursive = TRUE)
    nb_RDS_file = length(RDS_file_path)
    
    #Avoid to load RDS file in directories which contain duplicated sample data (the case in SOLO and SAFIR directories)
    if(nb_RDS_file==1){
      RDS_full_path = paste(j,"/",RDS_file_path, sep="")
      RDS_file = readRDS(RDS_full_path)
      
      #Create new qc metric file
      old_qc_metric_file = list.files(j,pattern = ".qc.txt$", recursive = TRUE)
      old_qc_path = paste(j,"/",old_qc_metric_file, sep="")
      
      export_qc_path = paste(j,"/",basename(j),"_qc_metric.txt", sep="")
      
      instab_file = list.files(j, pattern = ".Instab.txt$", recursive = TRUE)
      instab_file_path = paste(j,"/",instab_file, sep="")
      
      qc_metric(RDS_file,old_qc_path,instab_file_path ,export_qc_path)
      print(paste("New qc metric file created :",export_qc_path))
      
      #Create new microarray information file
      export_microarray_path = paste(j,"/",basename(j),"_microarray.txt", sep="")
      microarray_info(RDS_file, export_microarray_path)
      print(paste("New microarray information file created :",export_microarray_path))
      
      #Create miscellaneous information file
      export_miscellaneous_info_path = paste(j,"/",basename(j),"_miscellaneous_info.txt", sep="")
      miscellaneous_information(RDS_file,export_miscellaneous_info_path)
      print(paste("New miscellaneous information file created :",export_miscellaneous_info_path))
    }  
    nb_RDS_file =0 
  }
}

proc.time() - ptm
## End(Not run)
