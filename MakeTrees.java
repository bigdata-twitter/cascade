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
				child.parent = parent;
				done.add(child);
			}
			br.close();
			System.err.println("Done making tree from file " + f);
		}
		br1.close();
		System.err.println("Done making trees from all");
		ListIterator<Node> iter = done.listIterator();
		int count = 0;
		while (iter.hasNext()) {
			Node cur = iter.next();
			if (cur.parent == null) {
				count++;
				printTree(cur);
				System.out.println();
			}
		}
		System.err.println("Total cascades = " + count);
	}

	private static void printTree(Node cur) {
		System.out.print("{\"id\":" + cur.id + ", \"children\":[");
		if (!cur.children.isEmpty()) {
			for (int i = 0; i < cur.children.size(); i++) {
				if (i == 0)
					printTree(cur.children.getFirst());
				else {
					System.out.print(",");
					printTree(cur.children.get(i));
				}
			}
		}
		System.out.print("]}");
	}
}
