import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;

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
		BufferedWriter bw = new BufferedWriter(new FileWriter("large"));
		BufferedWriter bw1 = new BufferedWriter(new FileWriter("rep-and-rt"));
		String f;
		Hashtable<Long, CombinedNode> h = new Hashtable<Long, CombinedNode>();
		HashSet<Long> dataset = new HashSet<Long>();
		while ((f = br1.readLine()) != null) {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String in;
			long line = 0;
			out: while ((in = br.readLine()) != null) {
				line++;
				String s[] = in.split("\t");
				int c = 0;
				long par = 0, chi;
				try {
					chi = Long.parseLong(s[0]);
				} catch (NumberFormatException n) {
					bw.write(in);
					continue out;
				}
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
						try {
							par = Long.parseLong(s[5]);
						} catch (NumberFormatException n) {
							bw.write(in);
							continue out;
						}
						child.reply = true;
					} else
						try {
							par = Long.parseLong(s[2]);
						} catch (NumberFormatException n) {
							bw.write(in);
							continue out;
						}
					break;
				case 3:
					child = new CombinedNode(chi);
					par = Long.parseLong(s[4]);
					child.reply = true;
				default:
					bw1.write("Error!!\n");
					bw1.write("File: " + f + "\n");
					bw1.write("Line number: " + line + "\n");
					bw1.write("Line: " + in + "\n");
					break;
				}
				CombinedNode parent = null;
				if (h.containsKey(par))
					parent = h.get(par);
				else {
					parent = new CombinedNode(par);
					h.put(par, parent);
				}
				parent.children.add(child);
				child.parent = parent;
				h.put(chi, child);
			}
			br.close();
			System.err.println("Done making tree from file " + f);
		}
		br1.close();
		bw.close();
		bw1.close();
		System.err.println("Done making trees from all");
		Enumeration<CombinedNode> e = h.elements();
		int count = 0;
		while (e.hasMoreElements()) {
			CombinedNode cur = e.nextElement();
			if (cur.parent == null) {
				count++;
				printTree(cur, dataset.contains(cur.id));
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