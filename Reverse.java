import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class Reverse {
	public static void main() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("leftover_roots"));
		HashSet<Long> roots = new HashSet<Long>();
		String in;
		while ((in = br.readLine()) != null)
			roots.add(Long.parseLong(in));
		br.close();
		br = new BufferedReader(new FileReader("roots_all"));
		while ((in = br.readLine()) != null)
			if (!roots.contains(Long.parseLong(in)))
				System.out.println(in);
		br.close();
	}
}
