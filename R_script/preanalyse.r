#############################################
############################################
#Création de la matrice de données 

#Completer les regions des cytoband manquantes, conserver les L2R. 
#Par la suite matcher la position des segments avec la position des gènes pour compléter les données.

#Regrouper toutes les données dans une seule matrice qui seront ensuite analyser.

######################################################
#Fonction

match_gene_L2R <-function(sample_name ,path_L2R_file, path_cytoband_file, path_geneRef_file){
  
  data = read.delim(file=path_L2R_file,  sep = "\t", header=TRUE, stringsAsFactors = FALSE)
  
  cytoband = read.delim(file = path_cytoband_file, stringsAsFactors = FALSE )
  
  sample_name1 = sample_name
  
  f = data.frame(matrix(NA,ncol = 6,nrow = nrow(data)))
  colnames(f) <- c("Chrom", "Chr", "Start", "End", "Width", "L2R ratio")
  
  print("Update L2R file with extend segment...")
  #Initialize matrix f 
  #Matrix f contains modifications segment start and end.
  #Each segments are extended to cover all genome
  f[,]= data[,c(1:6)]
  f[1,3]<- 0 
  for (i in c(1 : nrow(data))) {
    
    chrom1 = f[i,2]
    chrom2 = f[i+1,2]
    start1 = f[i,3]
    end1 = f[i,4]
    
    start2 = f[i+1,3]
    end2=f[i+1,4]
    
    if(length(chrom1)!=0 && !is.na(chrom2) && chrom1==chrom2 ){
      dis = start2 - end1
      ecart = round((start2 - end1)/2)
      
      modulo_dist = dis%%2
      
      new_end1 <- ifelse(modulo_dist == 1, end1 + ecart, end1 + ecart - 1)
      new_start2 = new_end1+1
      f[i,4]=new_end1
      f[i+1,3]=new_start2
      f[i,5] = f[i,4]-f[i,3]
      
      #Set 0 on each segment start
    } else if(!is.na(chrom2)&& chrom1!=chrom2){
      f[i+1,3]<-0
    }
  }
  #Retrieve the end of each chromosome in a matrix called end_chrom
  end_chrom = data.frame(matrix(NA,ncol = 2))
  colnames(end_chrom) <- c("Chrom","End")
  
  for(i in c( 1 : nrow(cytoband) )){
    chrom1 = cytoband[i,1]
    chrom2 = cytoband[i+1,1]
    
    if(chrom1!=chrom2 && !is.na(chrom2) ){
      end_chrom[i,1]=cytoband[i,1]
      end_chrom[i,2]=cytoband[i,3]
    }
  }


  print("Update L2R file with extend segment end value...")
  end_chrom<- na.omit(end_chrom)
  
  #Change the last value of a segment with the end value of the equivalent chromsome
  for (i in c(1 : nrow(f))) {
    chrom1 = f[i,1]
    chrom2 = f[i+1,1]
    
    if(length(chrom1)!=0 && !is.na(chrom2) &&chrom1!=chrom2){
      for(j in c(1: nrow(end_chrom))){
        if(chrom1 == end_chrom[j,1])
          f[i,4]<- end_chrom[j,2]
      }
      
      #Case for the last chromosome
    }else if(is.na(chrom2)){
      for(j in c(1: nrow(end_chrom))){
        if(chrom1 == end_chrom[j,1])
          f[i,4]<-end_chrom[j,2]
      }
    }
    f[i,5] = f[i,4]-f[i,3]
    
  }
  #Import gene reference table
  print("Import gene reference table...")
  load(path_geneRef_file)
  gen.df <- gen.df[order(gen.df$chrN, gen.df$start, gen.df$end),]
  
  gene_L2R <- data.frame(matrix(NA,ncol = 7))
  colnames(gene_L2R)<-c("Symbol", "chrom","start","end","width","strand","L2R_value")
  
  #Mapping with gene reference table 
  for (i in c(1:nrow(gen.df))) {
    for(j in c(1:nrow(f))){
      
      #Check if it is the same chromosome
      if(gen.df[i,2]==f[j,1]){
        
        #Check if the gene is 
        #gen.df[i,4]>=f[j,3] && gen.df[i,4]<=f[j,4] && gen.df[i,5]>=f[j,3] && gen.df[i,5]<=f[j,4] 
        if(gen.df[i,4]<=f[j,4] && gen.df[i,5]>=f[j,3]){
          gene_L2R[i,1]<-gen.df[i,1]
          gene_L2R[i,2]<-gen.df[i,2]
          gene_L2R[i,3]<-gen.df[i,4]
          gene_L2R[i,4]<-gen.df[i,5]
          gene_L2R[i,5]<-gen.df[i,6]
          gene_L2R[i,6]<-gen.df[i,7]
          gene_L2R[i,7]<-f[j,6]
        }
      }
    }
  }
  #Matrice finale :
  gene_L2R_finale = gene_L2R
  names(gene_L2R_finale)[7]<-paste(sample_name)
#  rownames(gene_L2R_finale)<-gene_L2R_finale[,1]

  print("Created mapping file with gene and L2R value...")
  gene_L2R_finale
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

#############################################################
#Starting code 
#Find all L2R file in directory

path_dir = "C:/Users/a_malinovic/shared/data_sarcomes_EaCoN_R" 

path_cytoband_file = "Stage/ODIN_CNV/R_script/cytoBandIdeo.hg19"
path_geneRef_file = "C:/Users/a_malinovic/shared/Stage/ODIN_CNV/R_script/refGene_cl_hg19.rda"

#List all batch contain in the previous path
list_batch = list.dirs.depth.n(path_dir, n = 1)


#loop on all batch

l <- list() 
for(i in list_batch){
  list_samples = list.dirs.depth.n(i, n = 1)
  
  #loop on all samples
  for(sample in list_samples){
    L2R_file = list.files(sample,pattern = ".CytobandSegmentedL2R.txt$", recursive = TRUE)
    print(basename(sample))
    sample_name = basename(sample)
    
    #loop on L2Rfile
    for(L2R in L2R_file ){
      print(dirname(L2R))
      path_to_L2R = paste(sample,"/",L2R, sep="")
      data_result = match_gene_L2R(sample_name,path_to_L2R,path_cytoband_file,path_geneRef_file)
      l[[sample_name]]<- data_result
    }
  }
  print(i)
}
l1=l
save(l1, file = "samplesGR_L2R.Rdata")

data_to_analyse = Reduce(function(x,y) merge(x,y, by =c("Symbol", "chrom","start","end","width","strand")), l)

data_to_analyse[,1]=paste(data_to_analyse[,1],data_to_analyse[,2],data_to_analyse[,3],sep = "_")
data_to_analyse=data_to_analyse[,-c(2:6)]

row.names(data_to_analyse)<-data_to_analyse[,1]
data_to_analyse = data_to_analyse[,-1]
data_to_analyse=t(data_to_analyse)

write.table(data_to_analyse, file="test_data2.txt", sep = "\t", row.names = T,col.names =
              NA,quote = FALSE)




