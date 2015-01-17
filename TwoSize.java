import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

public class TwoSize {
	static final long id[] = { 0l, 42500000000000000l, 85000000000000000l,
			170000000000000000l, 232500000000000000l, 265000000000000000l,
			295000000000000000l, 325000000000000000l, 355000000000000000l,
			387500000000000000l, 400000000000000000l, 415000000000000000l,
			430000000000000000l, 431000000000000000l, 432000000000000000l,
			434000000000000000l, 436000000000000000l, 438000000000000000l,
			440000000000000000l, 441250000000000000l, 442500000000000000l,
			443750000000000000l, 445000000000000000l, 446250000000000000l,
			447500000000000000l, 448750000000000000l, 450000000000000000l,
			450750000000000000l, 451500000000000000l, 452250000000000000l,
			453050000000000000l, 453750000000000000l, 454500000000000000l,
			455250000000000000l, 456000000000000000l, 457300000000000000l,
			458700000000000000l, 459300000000000000l, 460000000000000000l,
			461000000000000000l, 5000000000000000000l };
	static Hashtable<Long, LinkedList<Long>> h;
	final static int MAX_LOAD = 8;

	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("roots_all"));
		BufferedWriter bw = new BufferedWriter(new FileWriter("leftover_roots"));
		
		LinkedList<Long> roots = new LinkedList<Long>();
		h = new Hashtable<Long, LinkedList<Long>>();
		String in;
		while ((in = br.readLine()) != null)
			roots.add(Long.parseLong(in));
		br.close();
		int to_load = 40, cur_load = 0, cur_loaded = 41;
		Iterator<Long> iter = roots.iterator();
		while (iter.hasNext()) {
			long cur_root = iter.next();
			for (int i = id.length; i >= 0; i--)
				if (cur_root >= id[i])
					to_load = i;
			while (cur_loaded >= to_load) {
				if (cur_load == MAX_LOAD)
					removeData(cur_loaded + MAX_LOAD - 1);
				loadData(--cur_loaded);
			}
			if (!h.containsKey(cur_root))
				bw.write(cur_root + "\n");
			else {
				
			}
		}
		bw.close();
	}

	private static void loadData(int i) {
		// TODO Auto-generated method stub

	}

	private static void removeData(int loaded) {
		// TODO Auto-generated method stub

	}
}
