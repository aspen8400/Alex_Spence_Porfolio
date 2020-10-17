readme.txt

This readme assumes that you are running the analytic on the cleaned data.

Profiling and cleaning code and files are located in the following folders:
- Input data can be found in the "input data" folder.  These files are the raw datasets which were used as inputs during the cleaning and profiling process.
- Commands used to move files over from local to DUMBO to HDFS are found in the data_injest folder.  Note that the project evolved over time, so exact file paths/names may not be updated.
- Profiling for the advanced and traditional datasets are shown in the "profiling_code" folder.  This may be helpful if the user would like to get a better understanding of the data inputs and their values.
-The Map and/or MapReduce codes for the cleaning scripts are located in the "etl_code" folder

All of the cleaned data is located in the "cleaned_data" folder.

There are three datasets in the "cleaned_data" folder:
- advanced
- mvp
- traditional

It is recommended to move each of these files to DUMBO and then to HDFS.

Once this is completed, the analytic can be run by copy and pasting each command in the .impala document in the "app_code" folder in an impala shell, while logged into DUMBO.  Make sure to update the file paths.


Some additional information:

- Sample screenshots of the analytic running in impala are saved to the "screenshots" folder.
- The report will be saved in the "report" folder at a later date.
- The PPT presentation is saved in the "presentation" folder.
