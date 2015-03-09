import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ParentListing {
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

	public static void main(String args[]) throws IOException {
		BufferedWriter bw[] = new BufferedWriter[id.length];
		for (int i = 0; i < bw.length; i++)
			bw[i] = new BufferedWriter(new FileWriter("parents_" + i));
		for (int i = 0; i < id.length; i++) {
			BufferedReader br = new BufferedReader(new FileReader("child_list_"
					+ i));
			String cur;
			while ((cur = br.readLine()) != null) {
				String children[] = cur.split(", ");
				for (int j = 1; j < children.length; j++) {
					String child = children[j].split("\t")[1];
					long ch;
					try {
						ch = Long.parseLong(child);
					} catch (NumberFormatException n) {
						bw[bw.length - 1].write(child + "\t" + children[0]
								+ '\n');
						continue;
					}
					for (int k = id.length - 1; k >= 0; k--)
						if (ch >= id[k]) { // write line in appropriate file
							bw[i].write(ch + "\t" + children[0] + '\n');
							break;
						}
				}
			}
			System.out
					.println("Done putting parents from file child_list_" + i);
			br.close();
		}
		System.out.println("Done putting parents from all files");
		for (int i = 0; i < bw.length; i++)
			bw[i].close();
	}
}
