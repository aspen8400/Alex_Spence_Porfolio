import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.util.HashMap;

public class ProfileReducer    
	extends Reducer<IntWritable, Text, Text, Text> {    

	@Override  public void reduce(IntWritable key, Iterable<Text> values, Context context)      
		throws IOException, InterruptedException {        
		
		int index = key.get();
		String header = "null";
		int lineCount = 0;
		Object min = null;
		Object max = null;
		HashMap<String, Integer> freqCount = new HashMap<String, Integer>();
		String mostFreq = "";
		int mostFreqCount = 0;
		double sum = 0;

		for (Text t : values) {
			if (t.toString().contains("~")) {
				header = t.toString().substring(1);
			} else {
				lineCount++;
				int freq = freqCount.getOrDefault(t.toString(), 0) + 1;
				if (freq > mostFreqCount) {
					mostFreq = t.toString();
					mostFreqCount = freq;
				}
				freqCount.put(t.toString(), freq);
				min = minFunction(min, t.toString());
				max = maxFunction(max, t.toString());
				if (min instanceof Double) sum += Double.parseDouble(t.toString());
			}
		}

		context.write(new Text("Index"), new Text(index + ""));
		context.write(new Text("Header"), new Text(header));
		context.write(new Text("Number of Non-Null Lines"), new Text(lineCount + ""));
		if (min == null) return;
		context.write(new Text("Minimum value"), new Text(min.toString()));
		context.write(new Text("Maximum value"), new Text(max.toString()));
		if (min instanceof Double)
			context.write(new Text("Range"), new Text(((Double)max-(Double)min) + ""));
		context.write(new Text("Number of Distinct Values"), new Text(freqCount.size() + ""));
		context.write(new Text("Most Frequent Entry"), new Text(mostFreq + ":" + mostFreqCount));
		if (min instanceof Double)
			context.write(new Text("Average Value"), new Text((sum / lineCount)+"")); 
	}

	private Object minFunction(Object currMin, String data) {
		try {
			Double dubData = Double.parseDouble(data);
			if (currMin == null || dubData < (Double)currMin)
				return dubData;
			return currMin;
		} catch (NumberFormatException e2) {
			// Not a number, so treat as a String
			if (currMin == null || data.length() < ((String)currMin).length())
				return data;
			return currMin;
		}
	}

	private Object maxFunction(Object currMax, String data) {
		try {
			Double dubData = Double.parseDouble(data);
			if (currMax == null || dubData > (Double)currMax)
				return dubData;
			return currMax;
		} catch (NumberFormatException e2) {
			// Not a number, so treat as a String
			if (currMax == null || data.length() > ((String)currMax).length())
				return data;
			return currMax;
		}
	}
}
