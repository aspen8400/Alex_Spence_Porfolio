import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ProfileMapper
	extends Mapper<LongWritable, Text, IntWritable, Text> {  

	@Override  
	public void map(LongWritable key, Text value, Context context)      
		throws IOException, InterruptedException {        

		String[] line = value.toString().split(",");
		String mod = "";
		if (line[0].isEmpty()) {
			mod = "~";
		}   
		
		for (int i = 0; i < line.length; i++) {
			if (!line[i].isEmpty()) {
				context.write(new IntWritable(i), new Text(mod + line[i]));
			}
		}
	}
}
