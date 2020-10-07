import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.util.ArrayList;
import java.lang.Math;
public class ProfileReducer
extends Reducer<Text, Text, Text, Text> {
    //@Override
    public void reduce(Text key, Iterable<Text> values, Context context)
    throws IOException, InterruptedException {
        //Initialize outputs
        double sum = 0.0;
        int i = 0;
        double max_num = 0;
        double min_num = 3000.0;
        double rang = 0.0;
        double avg = 0.0;
        int unique_num = 0;
        ArrayList<String> unique_vals = new ArrayList<String>();

        //Condition where the stat is a number
        try {
            for (Text value : values) {
                //process text value as string
                String stat = value.toString();
                double new_stat = Double.parseDouble(stat);
                sum += new_stat;
                i ++;
                if (new_stat > max_num) {
                    max_num = new_stat;
                }
                if (new_stat < min_num) {
                    min_num = new_stat;
                }
            avg = Math.round(sum / i * 100)/100;
            rang = Math.round((max_num - min_num) * 100) / 100;
            }
            String new_val =    Integer.toString(i) + "\t" + Double.toString(min_num) + "\t" + Double.toString(max_num) + "\t" +
                                Double.toString(rang) + "\t" + Double.toString(avg);
            context.write(key, new Text(new_val));

        }
        //Condition where the stat is a string
        catch (Exception e) {
            for (Text value : values) {
                i ++;
                String stat = value.toString();
                if (!unique_vals.contains(stat)){
                    unique_vals.add(stat);
                }
            }
            i++;
            String new_val = Integer.toString(i) + "\t" + Integer.toString(unique_vals.size());
            context.write(key, new Text(new_val));
        }
    }
}