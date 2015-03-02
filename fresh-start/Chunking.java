import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Chunking {
	static final long id[] = { 0l, 461000000000000000l, 470750000000000000l,
			490250000000000000l, 5000000000000000000l };

	public static void main(String args[]) throws IOException {
		makeChunks();
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
				for (int i = id.length - 1; i > 0; i--)
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
