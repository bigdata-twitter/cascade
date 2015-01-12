import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Independents {
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
		BufferedReader br1 = new BufferedReader(new FileReader("files")); // list
																			// of
																			// files
		BufferedWriter bw[] = new BufferedWriter[id.length];
		for (int i = 0; i < bw.length; i++)
			bw[i] = new BufferedWriter(new FileWriter("independent_" + i)); // independent
																			// files
		String f;
		while ((f = br1.readLine()) != null) {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String in;
			out: while ((in = br.readLine()) != null) {
				String s[] = in.split("\t");
				int c = 0;
				long par = 0;
				for (int i = 0; i < s.length; i++)
					if (!s[i].isEmpty() && Character.isDigit(s[i].charAt(0)))
						c++; // counter for the number of times a number occurs
								// in the line
				switch (c) {
				case 1:
					par = Long.parseLong(s[0]);
					break;
				default:
					continue out;
				}
				for (int i = id.length - 1; i >= 0; i--)
					if (par >= id[i]) { // write line in appropriate file
						bw[i].write(s[0] + "\n");
						break;
					}
			}
			br.close();
			System.out.println("Searched all independents from file " + f);
		}
		br1.close();
		System.out.println("Done searching for independents from all files");
		for (int i = 0; i < bw.length; i++)
			bw[i].close();
	}
}
