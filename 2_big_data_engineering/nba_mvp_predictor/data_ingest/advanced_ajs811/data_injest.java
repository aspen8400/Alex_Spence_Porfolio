//Create folder in dumbo
mkdir data

//Move data files from local mac to dumbo
scp hollingerStats_raw.csv ajs811@dumbo.es.its.nyu.edu:/home/ajs811/rdba_proj/data/
scp mvp.csv ajs811@dumbo.es.its.nyu.edu:/home/ajs811/rdba_proj/data/
scp Seasons_Stats.csv ajs811@dumbo.es.its.nyu.edu:/home/ajs811/rbda_proj/data/

//Create folder in HDFS to move data to
hdfs dfs -mkdir -p rbda/project/data

//Move data from dumbo to HDFS
hdfs dfs -put data rbda/project/data