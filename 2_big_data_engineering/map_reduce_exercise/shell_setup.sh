HADOOP_EXE='/usr/bin/hadoop'
HADOOP_LIBPATH='/opt/cloudera/parcels/CDH/lib'
HADOOP_STREAMING='hadoop-mapreduce/hadoop-streaming.jar'

alias hfs="$HADOOP_EXE fs"
alias hjs="$HADOOP_EXE jar $HADOOP_LIBPATH/$HADOOP_STREAMING"

