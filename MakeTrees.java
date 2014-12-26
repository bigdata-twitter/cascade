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
	}
}

public class MakeTrees {
	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(
				new FileReader(
						"D:\\Study Material\\8th Sem\\CSD750\\akhil\\reply-data_0_1.txt"));
		HashSet<String> h = new HashSet<String>();
		LinkedList<Node> done = new LinkedList<Node>();
		String in;
		while ((in = br.readLine()) != null) {
			String s[] = in.split("\t");
			Node parent = null;
			if (h.contains(s[2])) {
				ListIterator<Node> iter = done.listIterator();
				while (iter.hasNext()) {
					parent = iter.next();
					if (parent.id == Long.parseLong(s[2]))
						break;
				}
			} else {
				parent = new Node(Long.parseLong(s[2]));
				h.add(s[2]);
				done.add(parent);
			}
			h.add(s[0]);
			Node child = new Node(Long.parseLong(s[0]));
			parent.children.add(child);
			done.add(child);
		}
		br.close();
	}
}
