import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

public class CheckCombinedLoadable {
	public static void main(String args[]) throws IOException {
		BufferedReader br1 = new BufferedReader(new FileReader("files"));
		BufferedWriter bw = new BufferedWriter(new FileWriter("large"));
		BufferedWriter bw1 = new BufferedWriter(new FileWriter("rep-and-rt"));
		String f;
		HashSet<Long> dataset = new HashSet<Long>();
		while ((f = br1.readLine()) != null) {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String in;
			out: while ((in = br.readLine()) != null) {
				String s[] = in.split("\t");
				int c = 0;
				long par = 0, chi;
				try {
					chi = Long.parseLong(s[0]);
					dataset.add(chi);
				} catch (NumberFormatException n) {
					bw.write(in);
					continue out;
				}
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
							bw.write(in);
							continue out;
						}
					} else
						try {
							par = Long.parseLong(s[2]);
						} catch (NumberFormatException n) {
							bw.write(in);
							continue out;
						}
					break;
				case 3:
					par = Long.parseLong(s[4]);
				default:
					bw1.write("Error!!\n");
					bw1.write("File: " + f + "\n");
					bw1.write("Line: " + in + "\n");
					break;
				}
				dataset.add(par);
			}
			br.close();
			System.err.println("Done adding from file " + f);
		}
		br1.close();
		bw.close();
		bw1.close();
		System.err.println("Done adding from all");
	}
}