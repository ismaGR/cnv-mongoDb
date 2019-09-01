#!/usr/bin/env Rscript
##########################################################################################
#                     Match cytoband with segments BAF and L2R                           #
#                            Author: Amila Malinovic                                     #
##########################################################################################

library(foreach)

#Passing Arguments
argv <- commandArgs(TRUE)
path_dir <- argv[1]
cytobandIdeo_path <- argv[2]
ref_gene <- argv[3]

if(length(argv)==0){
  stop("Three arguments must be supplied")
}else if(length(argv)==1){
  stop("Missing two arguments")
}else if(length(argv)==2){
  stop("Missing one arguments")
}

cat("path_dir =", path_dir, "\n")
cat("cytobandIdeo_path =", cytobandIdeo_path, "\n")
cat("ref_gene =",ref_gene,"\n")
#Exemple : 
#Set a path
#path_dir = "test"   #Path which contains all batchs directories
#cytobandIdeo_path = "cytoBandIdeo.hg19"  #Path for the cytoband Ideogram file

#Launch this command :
#Rscript match_cytoband_segment "test2" "/media/sf_shared/Stage/ODIN_CNV/R_script/cytoBandIdeo.hg19" "/media/sf_shared/Stage/ODIN_CNV/R_script/refGene_cl_hg19.rda"

##########################################################################################
###Functions

##Match cytoband with BAF segment files
match_cytoband_BAF <- function(path_genomic_cytoband,path_segmentBAF,ref_gene,path_export){
 
  #Import cytoband
  genomic_cytoband=read.delim(file = path_genomic_cytoband)
  
  #Import reference gene file
  rg.df <- load(file = ref_gene)
  gen.df <- gen.df[order(gen.df$chrN, gen.df$start, gen.df$end),]
  
  #Import BAF file
  segmentBAF = read.delim(file =path_segmentBAF)
  
  #Number of segment contains in BAF file
  row = nrow(segmentBAF)
  
  #Data frame 
  f <- data.frame(matrix(NA,ncol = 10,nrow = row))
  colnames(f) <- c("Chrom","Chr","Start","End","Width","BAF Value","BAF Status","Start Cytoband","End Cytoband","# Markers")
  #Loop on segmentBAF file
  for(i in c(1: nrow(segmentBAF))){
    
    #Loop on cytoband file
    for(j  in c(1: nrow(genomic_cytoband))){
      #Check if it is in same chromosome
      segmentBAF[,1] <- factor(segmentBAF[,1], levels=levels(genomic_cytoband[,1]))
      if (segmentBAF[i,1]==genomic_cytoband[j,1]){
        #Start segment BAF
        if(segmentBAF[i,3]>= genomic_cytoband[j,2] && segmentBAF[i,3]<=genomic_cytoband[j,3]){ 
          #  print(paste("BAF start : ",segmentBAF[i, 3],"Cytoband Start :",segmentBAF[i, 2],genomic_cytoband[j,4]))
          f[i,1]=as.character(genomic_cytoband[j,1])
          
          if(segmentBAF[i,2]==23){
            f[i,2]="X"
          }else if(segmentBAF[i,2]==24){
            f[i,2]="Y"
          }else{
            f[i,2]=segmentBAF[i,2]
          }
          
          f[i,3]=segmentBAF[i,3]
          f[i,5]=segmentBAF[i,5]
          f[i,6]=segmentBAF[i,6]
          f[i,7]=as.character(segmentBAF[i,7])
          f[i,8]=paste(f[i,2],genomic_cytoband[j,4],sep="")
          f[i,10]=segmentBAF[i,12]
        }
        
        #End segment BAF
        if(segmentBAF[i,4]>= genomic_cytoband[j,2] &&segmentBAF[i,4]<=genomic_cytoband[j,3]){
          #  print(paste("BAF end",segmentBAF[i, 4],"Cytoband End :",segmentBAF[i, 2],genomic_cytoband[j,4]))
          f[i,4]=segmentBAF[i,4]
          f[i,9]=paste(f[i,2],genomic_cytoband[j,4],sep="")
        }
      }
    }
  }
  Genes = c(rep(0,nrow(f)))
  Genes <- foreach::foreach(seg = 1:nrow(f), .combine = "rbind") %do% {
    ingenz <- gen.df$symbol[gen.df$chrom == f$Chrom[seg] & gen.df$start <= f$End[seg] & gen.df$end >= f$Start[seg]]
    Genes =length(ingenz)
  }
  
  f_final = cbind(f,Genes)
  names(f_final)[11]<-"# Genes"
  
  #Export data 
  write.table(f_final,path_export,sep = "\t",row.names=F, quote = FALSE)
}

##Match cytoband with L2R segment files
match_cytoband_L2R <- function(path_genomic_cytoband,path_segmentL2R,ref_gene,path_export){

  #Import cytoband
  genomic_cytoband=read.delim(file = path_genomic_cytoband)
  
  #Import reference gene file
  rg.df <- load(file = ref_gene)
  gen.df <- gen.df[order(gen.df$chrN, gen.df$start, gen.df$end),]
  
  #Import BAF file
  segmentL2R = read.delim(file =path_segmentL2R)
  
  #Number of segment contains in BAF file
  row = nrow(segmentL2R)
  
  f <- data.frame(matrix(NA,ncol = 10,nrow = row))
  colnames(f) <- c("Chrom","Chr","Start","End","Width","L2R","ratio","Start Cytoband","End Cytoband","# Markers")
  
  #Loop on segment L2R file
  for (i in c(1: nrow(segmentL2R))) {
    #Loop on cytoband file
    #  segmentBAF[,1] <- factor(segmentBAF[,1], levels=levels(genomic_cytoband[,1]))
    for (j in c(1: nrow(genomic_cytoband))){
      if(segmentL2R[i,2]==23){
        chr = "X"
        #Field for "chr"+ chromosome number
        chrom = paste("chr",chr, sep="")
      }else if(segmentL2R[i,2]==24){
        chr="Y"
        chrom = paste("chr",chr, sep="")
      }else{
        chr=segmentL2R[i,2]
        chrom = paste("chr",segmentL2R[i,2], sep="")
      }
      #Check if it is in the same chromosome
      if(chrom==genomic_cytoband[j,1]){
        if(segmentL2R[i,3]>=genomic_cytoband[j,2] && segmentL2R[i,3]<=genomic_cytoband[j,3]){
          # print(paste("Segment L2R start :",segmentL2R[i,3], "Cytoband start", segmentL2R[i,2],genomic_cytoband[j,4]))
          
          f[i,1]=chrom
          f[i,2]=chr
          f[i,3]=segmentL2R[i,3]
          f[i,6]=segmentL2R[i,6]
          f[i,7]=2^(segmentL2R[i,6])
          f[i,8]=paste(chr,genomic_cytoband[j,4], sep="")
          f[i,10]=segmentL2R[i,5]
        }
        if(segmentL2R[i,4]>=genomic_cytoband[j,2] && segmentL2R[i,3]<=genomic_cytoband[j,3]){
          f[i,4]=segmentL2R[i,4]
          f[i,5]=f[i,4]-f[i,3] +1
          f[i,9]=paste(chr,genomic_cytoband[j,4], sep="")
        }
      }
    }
  }
  Genes = c(rep(0,nrow(f)))
  Genes <- foreach::foreach(seg = 1:nrow(f), .combine = "rbind") %do% {
    ingenz <- gen.df$symbol[gen.df$chrom == f$Chrom[seg] & gen.df$start <= f$End[seg] & gen.df$end >= f$Start[seg]]
    Genes =length(ingenz)
  }
  
  f_final = cbind(f,Genes)
  names(f_final)[11]<-"# Genes"
  
  #Export data 
  write.table(f_final,path_export,sep = "\t",row.names=F, quote = FALSE)
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
#Starting code

#List all batch contain in the previous path
list_batch = list.dirs.depth.n(path_dir, n = 1)

cat("\n")
cat("Batchs in the directory : ", "\n")
cat(list_batch, "\n")


#Loop on all batchs
for (i in list_batch) {
  list_samples = list.dirs.depth.n(i, n = 1)
  #Loop on all samples
  for(j in list_samples){
    segment_BAF_file = list.files(j,pattern = ".SegmentedBAF.txt$", recursive = TRUE)
    segment_L2R_file  =list.files(j,pattern = ".NoCut.cbs$", recursive = TRUE)
    #Loop on all BAF files contain in sample file
    for (BAF in segment_BAF_file) {
      #Check if a BAF file directory exists
      if(length(BAF)!=0){ 
        path_to_BAF = paste(j,"/",BAF, sep="")
        export_BAF_path = paste(j,"/",dirname(BAF),"/",basename(j),".CytobandSegmentedBAF.txt", sep="")
        
        #Execute function
        match_cytoband_BAF(cytobandIdeo_path,path_to_BAF,ref_gene,export_BAF_path)
        print(paste("New Cytoband - BAF file created : ",export_BAF_path))
      }
    }
    for (L2R in segment_L2R_file) {
      if(length(L2R)!=0){
        path_to_L2R = paste(j,"/",L2R, sep="")
        export_L2R_path = paste(j,"/",dirname(L2R),"/",basename(j),".CytobandSegmentedL2R.txt", sep="")
        
        #Execute function
        match_cytoband_L2R(cytobandIdeo_path,path_to_L2R,ref_gene,export_L2R_path)
        print(paste("New Cytoband - L2R file created : ",export_L2R_path))
      }
    }
  }
}
