import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

class Child {
	boolean reply;
	long id;

	Child(boolean reply, long id) {
		this.reply = reply;
		this.id = id;
	}
}

public class LoadingSize {
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

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("trees"));
		HashSet<Long> roots_done = new HashSet<Long>();
		String in;
		while ((in = br.readLine()) != null)
			roots_done.add(Long.parseLong(in.split(":")[1].split(",")[0]));
		br.close();
		for (int i = 0; i < id.length; i++) {
			br = new BufferedReader(new FileReader("child_list_" + i));
			BufferedWriter bw = new BufferedWriter(new FileWriter("left_" + i));
			while ((in = br.readLine()) != null) {
				String[] cur = in.split(", ");
				if (roots_done.contains(Long.parseLong(cur[0])))
					continue;
				bw.write(in + "\n");
			}
			br.close();
			bw.close();
			System.out.println("Done with child_list_" + i);
		}
		System.out.println("Done with all");
	}
}
