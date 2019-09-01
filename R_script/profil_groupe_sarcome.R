##################################################################################
#     Profil moyen par groupe de sarcomes
##################################################################################
#Faire aussi la classification avec les fibromyxosarcoma et Undifferentiated sarcoma

tcga_data = read.table("train_data.txt", header = TRUE, sep ="\t")

#On recupère la liste de tout les patients
train_raw = Reduce(function(x,y) merge(x,y, by =c("Symbol", "chrom","start","end","width","strand")), l)

#Data preparation
data = train_raw

Id_diagnosis = SARC_clin[,c(1,6)]
#row.names(data)<-data[,1]

#Rename the gene name by adding the chromosome and the start value
data[,1]= paste(data[,1],data[,2],data[,3], sep = "_")

row.names(data)<-data[,1]
data_gene_chr = data[,c(1:6)]

data = data[,-c(1:6)]
data = t(data)

row.names(Id_diagnosis)<-Id_diagnosis[,1]

data_finale = cbind(data, Id_diagnosis)

#Remove repeated sample names
data_finale = data_finale[,-28103]

#Rename last value 
colnames(data_finale)[28103]<- "Y"


##################################################################################
#Etablissement d'un profil moyen
library(caret)
library(dplyr)


row.names(tcga_data)<-tcga_data[,1]
tcga_data = tcga_data[,-1]

data_leiomyosarcoma <- tcga_data %>% filter(Y %in% "Leiomyosarcoma, NOS")
data_dediffLipo <- tcga_data %>% filter(Y %in% "Dedifferentiated liposarcoma")
data_UndiffSar <-  tcga_data %>% filter(Y %in% "Undifferentiated sarcoma")
data_fibro <-  tcga_data %>% filter(Y %in% "Fibromyxosarcoma")


med_leiomyosarcoma <- lapply(1:ncol(data_leiomyosarcoma), function(x){median})


test = median(data_leiomyosarcoma[,2])





