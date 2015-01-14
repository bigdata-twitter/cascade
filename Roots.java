import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

public class Roots {
	public static void main(String args[]) throws IOException {
		LinkedList<Long> list = new LinkedList<Long>();
		for (int i = 0; i <= 40; i++) {
			BufferedReader br = new BufferedReader(new FileReader("roots_" + i));
			String root;
			while ((root = br.readLine()) != null)
				list.add(Long.parseLong(root));
			br.close();
		}
		Long[] roots = list.toArray(new Long[list.size()]);
		System.out.println("Beginning sorting");
		Arrays.sort(roots);
		System.out.println("Sorting finished");
		BufferedWriter bw = new BufferedWriter(new FileWriter("roots_all"));
		for (int i = 0; i < roots.length; i++)
			bw.write(roots[i] + "\n");
		bw.close();
	}
}
