import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;

class CombinedNode {
	LinkedList<CombinedNode> children;
	CombinedNode parent;
	long id;
	boolean reply;

	CombinedNode(long id) {
		this.id = id;
		this.children = new LinkedList<CombinedNode>();
		this.reply = false;
	}
}

public class CombinedTree {
	public static void main(String args[]) throws IOException {
		BufferedReader br1 = new BufferedReader(new FileReader("files"));
		String f;
		HashSet<Long> h = new HashSet<Long>();
		LinkedList<CombinedNode> done = new LinkedList<CombinedNode>();
		HashSet<Long> dataset = new HashSet<Long>();
		while ((f = br1.readLine()) != null) {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String in;
			out: while ((in = br.readLine()) != null) {
				String s[] = in.split("\t");
				int c = 0;
				long par = 0;
				long chi = Long.parseLong(s[0]);
				CombinedNode child = null;
				dataset.add(chi);
				for (int i = 0; i < s.length; i++)
					if (!s[i].isEmpty() && Character.isDigit(s[i].charAt(0)))
						c++;
				switch (c) {
				case 1:
					continue out;
				case 2:
					child = new CombinedNode(chi);
					if (s[1].equals("nr")) {
						par = Long.parseLong(s[5]);
						child.reply = true;
					} else
						par = Long.parseLong(s[2]);
					break;
				default:
					System.err.println("Error!!");
					System.exit(1);
					break;
				}
				CombinedNode parent = null;
				if (h.contains(par)) {
					ListIterator<CombinedNode> iter = done.listIterator();
					while (iter.hasNext()) {
						parent = iter.next();
						if (parent.id == par)
							break;
					}
				} else {
					parent = new CombinedNode(par);
					h.add(par);
					done.add(parent);
				}
				h.add(chi);

				parent.children.add(child);
				child.parent = parent;
				done.add(child);
			}
			br.close();
			System.err.println("Done making tree from file " + f);
		}
		br1.close();
		System.err.println("Done making trees from all");
		ListIterator<CombinedNode> iter = done.listIterator();
		int count = 0;
		while (iter.hasNext()) {
			CombinedNode cur = iter.next();
			if (cur.parent == null) {
				count++;
				if (dataset.contains(cur.id))
					printTree(cur, true);
				else
					printTree(cur, false);
				System.out.println();
			}
		}
		System.err.println("Total cascades = " + count);
	}

	private static void printTree(CombinedNode cur, boolean contained) {
		System.out.print("{\"id\":" + cur.id + ", \"contained\":" + contained
				+ ", \"reply\":" + cur.reply + ", \"children\":[");
		if (!cur.children.isEmpty()) {
			for (int i = 0; i < cur.children.size(); i++) {
				if (i == 0)
					printTree(cur.children.getFirst(), contained);
				else {
					System.out.print(",");
					printTree(cur.children.get(i), contained);
				}
			}
		}
		System.out.print("]}");
	}
}
