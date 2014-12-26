import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class CheckLoadable {
	public static void main(String args[]) throws IOException {
		BufferedReader br1 = new BufferedReader(new FileReader("files"));
		String f;
		HashSet<Long> h = new HashSet<Long>();
		while ((f = br1.readLine()) != null) {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String in;
			while ((in = br.readLine()) != null) {
				String s[] = in.split("\t");
				if (!h.contains(Long.parseLong(s[0])))
					h.add(Long.parseLong(s[0]));
				if (!h.contains(Long.parseLong(s[2])))
					h.add(Long.parseLong(s[2]));
			}
			br.close();
			System.out.println("Done " + f);
		}
		br1.close();
		System.out.println("Done all");
	}
}
