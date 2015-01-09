import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

public class Chunking {
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
		makeChunks();
		findChildren();
	}

	private static void findChildren() throws IOException {
		for (int i = 0; i < id.length; i++) {
			BufferedReader br = new BufferedReader(new FileReader("chunk_" + i));
			BufferedWriter bw = new BufferedWriter(new FileWriter("child_list_"
					+ i));
			HashSet<String> encountered = new HashSet<String>();
			String in;
			while ((in = br.readLine()) != null) {
				String s[] = in.split("\t");
				int c = 0;
				String par = null;
				for (int j = 0; j < s.length; j++)
					if (!s[j].isEmpty() && Character.isDigit(s[j].charAt(0)))
						c++;
				switch (c) {
				case 2:
					if (s[1].equals("nr"))
						par = s[5];
					else
						par = s[2];
					break;
				case 3:
					par = s[2];
					break;
				}
				if (encountered.contains(par))
					continue;
				else {
					encountered.add(par);
					String toWrite = par;
					BufferedReader bacche = new BufferedReader(new FileReader(
							"chunk_" + i));
					String kids;
					while ((kids = bacche.readLine()) != null) {
						s = kids.split("\t");
						for (int j = 1; j < s.length; j++)
							if (s[j].equals(par))
								toWrite = toWrite.concat("," + s[0]);
					}
					bacche.close();
					bw.write(toWrite);
				}
			}
			br.close();
			System.out.println("Done finding children in file - chunk_" + i);
			bw.close();
		}
		System.out.println("Done finding children in all chunk files");
	}

	private static void makeChunks() throws IOException {
		BufferedReader br1 = new BufferedReader(new FileReader("files"));
		BufferedWriter bw[] = new BufferedWriter[id.length];
		for (int i = 0; i < bw.length; i++)
			bw[i] = new BufferedWriter(new FileWriter("chunk_" + i));
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
						c++;
				switch (c) {
				case 1:
					continue out;
				case 2:
					if (s[1].equals("nr")) {
						try {
							par = Long.parseLong(s[5]);
						} catch (NumberFormatException n) {
							bw[bw.length - 1].write(in);
							continue out;
						}
					} else {
						try {
							par = Long.parseLong(s[2]);
						} catch (NumberFormatException n) {
							bw[bw.length - 1].write(in);
							continue out;
						}
					}
					break;
				case 3:
					try {
						par = Long.parseLong(s[2]);
					} catch (NumberFormatException n) {
						bw[bw.length - 1].write(in);
						continue out;
					}
					break;
				default:
					System.out.println("Error!");
				}
				for (int i = id.length - 1; i >= 0; i--)
					if (par >= id[i])
						bw[i].write(in);
			}
			br.close();
			System.out.println("Done chunking from file " + f);
		}
		br1.close();
		System.out.println("Done chunking from all files");
		for (int i = 0; i < bw.length; i++)
			bw[i].close();
	}
}
