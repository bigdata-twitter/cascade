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
			HashSet<String> encountered = new HashSet<String>(); // parent ids
																	// that have
																	// already
																	// been seen
			String in;
			while ((in = br.readLine()) != null) {
				String s[] = in.split("\t");
				int c = 0;
				String par = null;
				for (int j = 0; j < s.length; j++)
					if (!s[j].isEmpty() && Character.isDigit(s[j].charAt(0)))
						c++;
				switch (c) { // extract the parent tweet id
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
				if (encountered.contains(par)) // already seen? we must have
												// built the childlist
												// previously, can easily
												// discard this time.
					continue;
				else {
					encountered.add(par); // add to the seen list
					String toWrite = par;
					BufferedReader bacche = new BufferedReader(new FileReader(
							"chunk_" + i));
					String kids;
					while ((kids = bacche.readLine()) != null) {
						// find all children of current parent id
						s = kids.split("\t");
						if (s[2].equals(par)) // retweet
							toWrite = toWrite.concat(", rt\t" + s[0]);
						if (s.length > 5 && s[5].equals(par)) // reply
							toWrite = toWrite.concat(", rp\t" + s[0]);
					}
					bacche.close();
					bw.write(toWrite + "\n");
				}
			}
			br.close();
			System.out.println("Done finding children in file - chunk_" + i);
			bw.close();
		}
		System.out.println("Done finding children in all chunk files");
	}

	private static void makeChunks() throws IOException {
		BufferedReader br1 = new BufferedReader(new FileReader("files")); // list
																			// of
																			// files
		BufferedWriter bw[] = new BufferedWriter[id.length];
		for (int i = 0; i < bw.length; i++)
			bw[i] = new BufferedWriter(new FileWriter("chunk_" + i)); // /chunk
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
					continue out; // independent, no tree formed
				case 2:
					if (s[1].equals("nr")) { // reply
						try {
							par = Long.parseLong(s[5]);
						} catch (NumberFormatException n) {
							bw[bw.length - 1].write(in);
							continue out;
						}
					} else { // retweet
						try {
							par = Long.parseLong(s[2]);
						} catch (NumberFormatException n) {
							bw[bw.length - 1].write(in);
							continue out;
						}
					}
					break;
				case 3:
					try { // retweet of a reply - take it as a retweet only
							// since reply will be independently considered
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
					if (par >= id[i]) { // write line in appropriate file
						bw[i].write(in + "\n");
						break;
					}
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
