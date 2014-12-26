import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;

class Node {
	LinkedList<Node> children;
	Node parent;
	long id;

	Node(long id) {
		this.id = id;
		this.children = new LinkedList<Node>();
	}
}

public class MakeTrees {
	public static void main(String args[]) throws IOException {
		BufferedReader br1 = new BufferedReader(new FileReader("files"));
		String f;
		HashSet<Long> h = new HashSet<Long>();
		LinkedList<Node> done = new LinkedList<Node>();
		while ((f = br1.readLine()) != null) {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String in;
			while ((in = br.readLine()) != null) {
				String s[] = in.split("\t");
				Node parent = null;
				if (h.contains(Long.parseLong(s[2]))) {
					ListIterator<Node> iter = done.listIterator();
					while (iter.hasNext()) {
						parent = iter.next();
						if (parent.id == Long.parseLong(s[2]))
							break;
					}
				} else {
					parent = new Node(Long.parseLong(s[2]));
					h.add(Long.parseLong(s[2]));
					done.add(parent);
				}
				h.add(Long.parseLong(s[0]));
				Node child = new Node(Long.parseLong(s[0]));
				parent.children.add(child);
				done.add(child);
			}
			br.close();
			System.out.println("Done " + f);
		}
		br1.close();
		System.out.println("Done all");
	}
}
