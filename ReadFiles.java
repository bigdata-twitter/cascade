import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFiles {
	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("files"));
		String f;
		while ((f = br.readLine()) != null) {
			BufferedReader br1 = new BufferedReader(new FileReader(f));
			String s;
			long tot = 0;
			long rprt = 0;
			while ((s = br1.readLine()) != null) {
				String[] st = s.split("\t");
				tot++;
				int c = 0;
				for (int i = 0; i < st.length; i++)
					if (st[i].length() > 3)
						c++;
				if (c < 2)
					continue;
				rprt++;
			}
			br1.close();
			System.out.println(rprt + " " + tot + " " + (rprt / (double) tot));
		}
		br.close();
	}
}
