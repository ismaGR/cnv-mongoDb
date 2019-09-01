##########################################################
#### Data analysis


#Train data
train = read.table("train_data.txt", header = TRUE, sep ="\t")

Y_train = as.factor(train[,28104])
X_train = train[,-28104]
#Test data
test = read.table("test_data.txt", header = TRUE, sep = "\t")

#select samples based on a "correct" classification 
test_up = test[-c(3:14,16,17,22,25,26,28,37,45,47:50),]

samples_to_test = c('162430680','17H08143','17R01496','17R01920',"17P00382","18P00042","18H00583","18H00945_MN",
                    "18H00945_MX","18R00695","18H03436","18H05445","18R00978","18H02925","18H12592","18R02518","19R00088")

test_up1=test_up[c(2,5,6:8,11,13:15,17:19,21,25,27:29),]

#without Liposarcoma, well differentiated

test_up2 = test_up[c(2,5,6:8,11,14:15,17,19,21,25,27:29),]
Y_test =factor(c("Pleomorphic liposarcoma","Undifferentiated sarcoma","Undifferentiated sarcoma","Undifferentiated sarcoma","Leiomyosarcoma, NOS",
          "Leiomyosarcoma, NOS","Liposarcoma, well differentiated","Leiomyosarcoma, NOS","Leiomyosarcoma, NOS","Leiomyosarcoma, NOS","Liposarcoma, well differentiated",
          "Leiomyosarcoma, NOS","Leiomyosarcoma, NOS","Undifferentiated sarcoma","Undifferentiated sarcoma","Undifferentiated sarcoma","Pleomorphic liposarcoma"), levels=c("Dedifferentiated liposarcoma","Leiomyosarcoma, NOS","Myxoid leiomyosarcoma","Fibromyxosarcoma","Undifferentiated sarcoma","Synovial sarcoma, biphasic","Synovial sarcoma, spindle cell",
                                                                                                                                                                            "Malignant fibrous histiocytoma","Giant cell sarcoma","Synovial sarcoma, NOS","Liposarcoma, well differentiated","Malignant peripheral nerve sheath tumor","Abdominal fibromatosis","Pleomorphic liposarcoma","Aggressive fibromatosis")
)


#Filtered on two classes : "Pleomorphic liposarcoma", "Leiomyosarcoma, NOS"
library(dplyr)
class_sarcome = c("Dedifferentiated liposarcoma","Leiomyosarcoma, NOS", "Fibromyxosarcoma","Undifferentiated sarcoma")

train_filter = train %>% filter(Y %in% class_sarcome)

Y_train_filter = factor(train_filter[,28104], levels = c("Pleomorphic liposarcoma", "Undifferentiated sarcoma", "Leiomyosarcoma, NOS"))
X_train_filter = train_filter[,-c(1,28104)]

X_test_filter = test_up2[,-1]
Y_test_filter =factor(c("Pleomorphic liposarcoma","Undifferentiated sarcoma","Undifferentiated sarcoma","Undifferentiated sarcoma","Leiomyosarcoma, NOS",
                 "Leiomyosarcoma, NOS","Leiomyosarcoma, NOS","Leiomyosarcoma, NOS","Leiomyosarcoma, NOS",
                 "Leiomyosarcoma, NOS","Leiomyosarcoma, NOS","Undifferentiated sarcoma","Undifferentiated sarcoma","Undifferentiated sarcoma","Pleomorphic liposarcoma"), levels=c("Pleomorphic liposarcoma", "Undifferentiated sarcoma", "Leiomyosarcoma, NOS"))


########################################################################
#KNN
library(class)

predict1 = knn(X_train[,-1], test_up1[,-1], Y_train, k = 15, prob=TRUE)
predict2 = knn(X_train[,-1], test_up1[,-1], Y_train, k = 20, prob=TRUE)
predict3 = knn(X_train[,-1], test_up1[,-1], Y_train, k = 10, prob=TRUE)

conf_table=table(predict3,Y_test)
error=(sum(conf_table)-sum(diag(conf_table)))/sum(conf_table)*100

table(predict1)
table(predict2)
table(predict3)


k=20
predict=rep(1,k)
conf=rep(1,k)
error = rep(1,k)

for (i in 1:k) {
  predict= knn(X_train[,-1],test_up1[,-1],Y_train,k=i,prob=TRUE)
  conf=table(predict,Y_test)
  error[i]=(sum(conf)-sum(diag(conf)))/sum(conf)*100
}
#Matrice de resultat
error


##Filtered data

predict3 = knn(X_train_filter, X_test_filter, Y_train_filter, k = 10, prob=TRUE)

k=20
predict=rep(1,k)
conf=rep(1,k)
error = rep(1,k)

for (i in 1:k) {
  predict= knn(X_train_filter,X_test_filter,Y_train_filter,k=i,prob=TRUE)
  conf=table(predict,Y_test_filter)
  error[i]=(sum(conf)-sum(diag(conf)))/sum(conf)*100
}
#Matrice de resultat
error

#Meilleurs résultat avec les données filtrés

#SVM
library(e1071)
data_filter = data.frame(x = X_train_filter, y =as.factor(Y_train_filter))
data = data.frame(x = X_train, y =as.factor(Y_train))


#svm
svm_radial = svm(y~., data=as.matrix(data_filter), method ="C-classification", kernel="radial" )

#Error: protect(): protection stack overflow


library(glmnet)
reg_lasso = glmnet(as.matrix(X_train_filter),family=c("multinomial"),as.matrix(Y_train_filter),alpha=1)
#one multinomial or binomial class has fewer than 8  observations; dangerous ground

LambdaLasso = cv.glmnet(as.matrix(X_train_filter),as.matrix(Y_train_filter),family="multinomial",alpha=1)
#best
bestLambdaLasso = LambdaLasso$lambda.min



#####################################################################################################
#Suppression des gènes qui se suivent sur un même segment

rownames(train_filter)<- train_filter$X

train_filter1 = train_filter[,-c(1,28104)]
data_test = t(train_filter1)
data_test = abs(as.matrix(data_test))

test1 = vapply(1:nrow(data_test), function(x){sum(data_test[x,]*seq.int(1L,ncol(data_test)))},0.1)

sum_gene = cbind(rownames(data_test),test1)
colnames(sum_gene)<- c("gene_name","value")
library(stringr)
test2 <- lapply(1:nrow(sum_gene), function(x){str_split(sum_gene[x,1],"_")})

dd  <-  as.data.frame(matrix(unlist(test2), ncol =length(unlist(test2[1]))))
#####################################################################################################

#On recupère la liste de tout les patients
train_raw = Reduce(function(x,y) merge(x,y, by =c("Symbol", "chrom","start","end","width","strand")), l)
train_raw1 = train_raw
  
train_raw[,1] = paste(train_raw[,1],train_raw[,2],train_raw[,3],sep = "_")


row.names(train_raw)<-train_raw[,1]
train_raw = train_raw[,-1]
train_raw=t(train_raw)

write.table(data_to_analyse1, file="test_data.txt", sep = "\t", row.names = T,col.names =
              NA,quote = FALSE)

test_name = strsplit(test_name_to_parse,"_")





