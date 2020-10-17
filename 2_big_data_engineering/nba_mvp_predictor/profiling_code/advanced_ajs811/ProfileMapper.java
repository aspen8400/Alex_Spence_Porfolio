import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.util.Arrays;
public class ProfileMapper
    extends Mapper<LongWritable, Text, Text, Text> { 
        public void map(LongWritable key, Text value, Context context) 
        throws IOException, InterruptedException {
            
            //Process line to be checked
            String line = value.toString();

            // Split line into an array
            String[] items = line.split("\t");

            //Setup stats list
            String[] stats = {"index", "rank", "gp", "mpg", "ts%", "ast", "to", "usg", "orr", "drr", "rebr", "per", "va", "ewa", "player", "team", "season"};

            //output each key:value as stat_name: stat
            for (int i = 0; i < stats.length; ++i) {
                context.write(new Text(stats[i]), new Text(items[i]));
            }
    }
}