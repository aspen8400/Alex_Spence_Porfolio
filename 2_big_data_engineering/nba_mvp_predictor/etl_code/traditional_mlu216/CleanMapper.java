import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class CleanMapper
	extends Mapper<LongWritable, Text, Text, Text> {  

	@Override  
	public void map(LongWritable key, Text value, Context context)      
		throws IOException, InterruptedException {        

		String[] line = value.toString().split(",");
		
		if (line.length < 53) return;

		try {
			int year = Integer.parseInt(line[1]);
			// Remove all rows that have year < 2003 in order
			// to match up with other dataset
			if (year < 2003) return;

			// Group player with the year he played in for Reducer
			// also include age in case there are multiple
			// players with the same name in the same year
			String player = year + "_" + line[2].replace("*","") + "_" + line[4];

			// Initialize CSV with position
			String newLine = line[3];
			for (int i = 31; i <= 52; i++) {
				// Pass unwanted column
				if (i == 40) continue;
				// Add other columns
				newLine += "," + line[i];
			}
			// Use year + player as key for Reducer
			context.write(new Text(player), new Text(newLine));
		} catch (NumberFormatException e) {
			// Header found, so ignore
			return;
		}
	}
}
