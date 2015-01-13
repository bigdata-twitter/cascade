import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

public class Isolated {
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
		for (int i = 0; i < id.length; i++) {
			HashSet<Long> h = new HashSet<Long>();
			String in;
			BufferedReader cl = new BufferedReader(new FileReader("child_list_"
					+ i));
			while ((in = cl.readLine()) != null)
				h.add(Long.parseLong(in.split(", ")[0]));
			cl.close();
			BufferedReader ind = new BufferedReader(new FileReader(
					"independent_" + i));
			BufferedWriter iso = new BufferedWriter(new FileWriter("isolated_"
					+ i));
			BufferedWriter root = new BufferedWriter(new FileWriter("roots_"
					+ i));
			while ((in = ind.readLine()) != null) {
				if (h.contains(Long.parseLong(in)))
					root.write(in + "\n");
				else
					iso.write(in + "\n");
			}
			ind.close();
			iso.close();
			root.close();
			System.out.println("Done with independent_" + i);
		}
		System.out.println("Done with all files");
	}
}
