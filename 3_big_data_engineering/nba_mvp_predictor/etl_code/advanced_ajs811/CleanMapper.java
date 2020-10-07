import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
public class CleanMapper 
    extends Mapper<LongWritable, Text, IntWritable, Text> { 
        public void map(LongWritable key, Text value, Context context) 
        throws IOException, InterruptedException {

            //Process key to be checked 
            //If it's the first line (i.e. header row, don't output anything)
            Long line_idx = key.get();
            Long zero = new Long(0);
            if (line_idx.equals(zero)) {
                String placeholder = "nothing";
            }
            //If it's not the header row, clean and output the data
            else {
                //Process line to be checked
                String line = value.toString();

                //Remove quotes from line if they exist
                line = line.replace("\"", "");

                // Split line into an array
                String[] items = line.split(",");

                //Get key (first entry of line)
                int new_key = Integer.parseInt(items[0]);

                //Get season and convert to YYYY
                int season = Integer.parseInt(items[16].substring(0,4)) + 1;

                //Convert back to string
                String season_str = String.valueOf(season);

                //Change season item in the array
                items[16] = season_str;

                //Get length of array
                int len = items.length; 

                //Convert items to a list (drop the original index)
                List new_value_ls = new ArrayList(Arrays.asList(Arrays.copyOfRange(items,1,len)));

                //Add year_name key to list
                new_value_ls.add(16, items[16] + "_" + items[14]);

                //Generate new value to be outputted
                String new_value = String.join("\t", new_value_ls);

                //Output new values
                context.write(new IntWritable(new_key), new Text(new_value));

            }

            }
        }
    