import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.NullWritable;
import java.util.HashSet;

public class CleanReducer    
	extends Reducer<Text, Text, Text, NullWritable> {    

	@Override  public void reduce(Text key, Iterable<Text> values, Context context)      
		throws IOException, InterruptedException {        
		
		String [] tmp = key.toString().split("_");

		int year = Integer.parseInt(tmp[0]);
		String player = tmp[1];
		int age = Integer.parseInt(tmp[2]);

		HashSet<String> pos = new HashSet<String>();
		int fg = 0;
		int fga = 0; 
		int _3p = 0;
		int _3pa = 0;
		int _2p = 0;
		int _2pa = 0;
		int ft = 0;
		int fta = 0;
		int orb = 0;
		int drb = 0;
		int trb = 0;
		int ast = 0;
		int stl = 0;
		int blk = 0;
		int tov = 0;
		int pf = 0;
		int pts = 0;
		double fg_per,_3p_per, _2p_per, ft_per;

		// Players might switch teams mid-season, so their stats might be split
		// So iterate through values as if each player has split stats
		for(Text v : values) {
			String[] stats = v.toString().split(",");
			// Add pos to hashset, in case player switches position somehow
			pos.add(stats[0]);
			// Aggregate the following stats, which are just totals
			fg += Integer.parseInt(stats[1]);
			fga += Integer.parseInt(stats[2]);
			_3p += Integer.parseInt(stats[4]);
			_3pa += Integer.parseInt(stats[5]);
			_2p += Integer.parseInt(stats[7]);
			_2pa += Integer.parseInt(stats[8]);
			ft += Integer.parseInt(stats[10]);
			fta += Integer.parseInt(stats[11]);
			orb += Integer.parseInt(stats[13]);
			drb += Integer.parseInt(stats[14]);
			trb += Integer.parseInt(stats[15]);
			ast += Integer.parseInt(stats[16]);
			stl += Integer.parseInt(stats[17]);
			blk += Integer.parseInt(stats[18]);
			tov += Integer.parseInt(stats[19]);
			pf += Integer.parseInt(stats[20]);
			pts += Integer.parseInt(stats[21]);
		}
		// Now calculate percentage stats
		fg_per = (double)fg/fga;
		_3p_per = (double)_3p/_3pa;
		_2p_per = (double)_2p/_2pa;
		ft_per = (double)ft/fta;

		String all_pos = "";
		for (String p : pos) {
			all_pos += "_" + p;
		}
		all_pos = all_pos.substring(1);

		String emit = year + "," + player + "," + all_pos + ",";
		emit += age + "," + fg + "," + fga + "," + fg_per + ",";
		emit += _3p + "," + _3pa + "," + _3p_per + "," + _2p + "," + _2pa + "," + _2p_per + ",";
		emit += ft + "," + fta + "," + ft_per + "," + orb + "," + drb + "," + trb + ",";
		emit += ast + "," + stl + "," + blk + "," + tov + "," + pf + "," + pts + ",";
		emit += year + "_" + player;

		context.write(new Text(emit), NullWritable.get());
	}
}
