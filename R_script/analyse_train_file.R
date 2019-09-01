##################################################################################
#     DATA ANALYSE ON TRAIN FILE - TCGA DATA
##################################################################################
#Faire aussi la classification avec les fibromyxosarcoma et Undifferentiated sarcoma

##################################################################################
#There is two classification for sarcoma : 
# 1) tissu orgine 
#    Liposarcoma / Leiomysarcomes / others
# 2) cell differences 
#    DD / WD / Giant Cell / Pleomorphic


#1st step : analyse TCGA dataset 
#Split dataset in 2/3 train data and 1/3 for test
#Do this for test the algortime and the classification into the trainging set

#This, I will perform an classificiation based on tissu origin

set.seed(1234)
library(caret)

#Load the TCGA data
tcga_data = read.table("train_data.txt", header = TRUE, sep ="\t")

count = as.data.frame(table(tcga_data$Y))

library(ggpubr)
ggbarplot(count, x='Var1',y='Freq',
          color = "green",            # Set bar border colors to white
          fill = rgb(0.1,0.1,0.1,0.1),
          sort.val = "desc",          # Sort the value in dscending order
          sort.by.groups = FALSE, 
        #  x.text.angle = 90,           # Rotate vertically x axis texts
          label = TRUE, label.pos = "out",
          orientation ="horiz",
          xlab = "Classification des sarcomes",
          ylab = "Nombre d'individus",
)

####################################################################################
####################################################################################
library(dplyr)
#"Fibromyxosarcoma","Undifferentiated sarcoma"

class_sarcome = c("Dedifferentiated liposarcoma","Leiomyosarcoma, NOS","Fibromyxosarcoma","Undifferentiated sarcoma")
tcga_filter <- tcga_data %>% filter(Y %in% class_sarcome)
trainIndex <- createDataPartition(tcga_filter$Y, p=0.7,times =1, list=FALSE)

#Train data
tcga_train <- tcga_filter[trainIndex,]
Y_tcga_train <-  factor(tcga_train[,28104], levels = class_sarcome)
X_tcga_train <- tcga_train[,-c(1,28104)]

#Test data
tcga_test <- tcga_filter[-trainIndex,]
Y_tcga_test <-  factor(tcga_test[,28104], levels = class_sarcome)
X_tcga_test <- tcga_test[,-c(1,28104)]

####################################################################################
####################################################################################


#Développement d'un classifieur
train <- tcga_train
Y_train <- Y_tcga_train
X_train <- X_tcga_train

Y_test <-Y_tcga_test
X_test <-X_tcga_test

##########################################
#k-PLUS PROCHE VOISINS

library(class)
k=30
predict=rep(1,k)
conf=rep(1,k)
error = rep(1,k)

for (i in 1:k) {
  predict= knn(X_train,X_test,Y_train,k=i,prob=TRUE)
  conf=table(predict,Y_test)
  error[i]=(sum(conf)-sum(diag(conf)))/sum(conf)*100
}
#Matrice de resultat
error

# Pour deux groupes : Erreur minimale 19.23077 %  pour k=1 et k=5
# Pour trois groupes : erreur minimale 18.86792 pour k =13
# Pour quatre groupes : 36.50 pour k = 30 
##########################################
#SVM 
library(e1071)

#kernel lineaire
svm_linear = svm(y~., data=train, method ="C-classification", kernel="linear" )


##########################################
#Regression pénalisée
## Validation croisée pour le choix optimal du Lambda

library(glmnet)

LambdaLasso = cv.glmnet(as.matrix(X_train),as.matrix(Y_train), family="multinomial",alpha=1)
LambdaRidge = cv.glmnet(as.matrix(X_train),as.matrix(Y_train), family="multinomial",alpha=0)

# best lambda
bestLambdaLasso = LambdaLasso$lambda.min
bestLambdaRidge = LambdaRidge$lambda.min

#Représentation graphique
plot(LambdaLasso, main = "alpha = 1 (LASSO)")
plot(LambdaRidge, main = "alpha = 0 (Ridge)")

#Prédiction
ValSetYPredLasso = factor(predict(LambdaLasso$glmnet.fit, as.matrix(X_test), s=bestLambdaLasso, type="class"), levels = class_sarcome)
ValSetYPredRidge = factor(predict(LambdaRidge$glmnet.fit, as.matrix(X_test), s=bestLambdaRidge, type="class"), levels = class_sarcome)

confusion1=table(Y_test,ValSetYPredLasso)
erreurpredLasso=(sum(confusion1)-sum(diag(confusion1)))/sum(confusion1)*100
erreurpredLasso


confusionMatrix(ValSetYPredLasso, Y_test, positive = class_sarcome)


confusion2=table(Y_test,ValSetYPredRidge)
erreurpredRidge=(sum(confusion2)-sum(diag(confusion2)))/sum(confusion2)*100
erreurpredRidge

confusionMatrix(ValSetYPredRidge, Y_test, positive = class_sarcome)

#Pour deux groupes : lasso : 9.615385% et RIDGE : 23.07692 %
#Pour trois groupe : lasso  11.32075 % et RIDGE 22.64151%
#Pour quatre groupes : 

#Elastic net

al = seq(0.1,0.90,by=0.1)
for (i in al) {
  print(i)
  Reg = cv.glmnet(as.matrix(X_train),as.matrix(Y_train), family="multinomial",alpha=i)
   
  bestlambda = Reg$lambda.min
 
  ValSetYPred = factor(predict(Reg$glmnet.fit, as.matrix(X_test), s=bestlambda, type="class"), levels = class_sarcome)
  conf=table(Y_test,ValSetYPred)
  error=(sum(conf)-sum(diag(conf)))/sum(conf)*100
  print(error)
}

#Pour deux groupes : 
# [1] 0.1
# [1] 13.46154
# [1] 0.2
# [1] 11.53846
# [1] 0.3
# [1] 11.53846
# [1] 0.4
# [1] 9.615385
# [1] 0.5
# [1] 9.615385
# [1] 0.6
# [1] 11.53846
# [1] 0.7
# [1] 9.615385
# [1] 0.8
# [1] 9.615385
# [1] 0.9
# [1] 9.615385

#Pour trois groupes:
# [1] 0.1
# [1] 16.98113
# [1] 0.2
# [1] 15.09434
# [1] 0.3
# [1] 16.98113
# [1] 0.4
# [1] 15.09434
# [1] 0.5
# [1] 13.20755
# [1] 0.6
# [1] 16.98113
# [1] 0.7
# [1] 13.20755
# [1] 0.8
# [1] 15.09434
# [1] 0.9
# [1] 15.09434

#Pour 4 groupes:
# [1] 0.1
# [1] 25.39683
# [1] 0.2
# [1] 25.39683
# [1] 0.3
# [1] 23.80952
# [1] 0.4
# [1] 28.57143
# [1] 0.5
# [1] 28.57143
# [1] 0.6
# [1] 30.15873
# [1] 0.7
# [1] 30.15873
# [1] 0.8
# [1] 30.15873
# [1] 0.9
# [1] 28.57143

##########################################
#Spherical k-means
install.packages("skmeans")

library("skmeans")
party <-skmeans(x =as.matrix(X_train), k=4 ,control = list(verbose = TRUE))

require("cluster")
plot(silhouette(party))

ids <- abbreviate(Y_train)

conf= table(party$cluster, ids)
error=(sum(conf)-sum(diag(conf)))/sum(conf)*100
error 


# erreur avec deux groupes 38.09524 %
# erreur avec trois groupes 34.375 %
##########################################
#Méthode des arbres
library(DAAG)
library(rpart)
library(ipred)
library(rpart.plot)
library(datasets)

set.seed(123)
library(randomForest)
fit <-randomForest(Y~.,data=train)

tcga_rpart = rpart(formula = Y~.,data=train,method="class",control=rpart.control(cp=0.0001))
printcp(digit_rpart) #resumé des erreurs
plotcp(digit_rpart)
printcp(digit_rpart)
prp(digit_rpart,extra=1)

#Arbre élagués
pruned.tree = prune(tcga_rpart,cp=0.00032803)
prp(tcga_rpart,extra=1)



##############################################################################################
#Suppression des gènes qui ont la même valeur 

train1 <- train[,-c(1,28104)]
train1 <- t(train1)
train1 <- abs(as.matrix(train1))
saveRDS(train1,file="train1b.RDS",compress = "xz")

train1_list <- vapply(1:nrow(train1), function(x){sum(train1[x,]*seq.int(1L,ncol(train1)))},0.1)

sum_gene <- cbind(rownames(train1),train1_list)
colnames(sum_gene)<- c("gene_name","value")
library(stringr)


#Appliquer la fonction !duplicated pour enlever les duplicats





#test <-str_split("AGPAT2_chr9_139567594" ,"_")

#test2 <- lapply(1:nrow(sum_gene), function(x){str_split(sum_gene[x,])})

