import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;

class CombinedNode {
	LinkedList<CombinedNode> children;
	CombinedNode parent;
	long id;
	boolean reply;

	CombinedNode(String par) {
		this.id = Long.parseLong(par);
		this.children = new LinkedList<CombinedNode>();
		this.reply = false;
	}
}

class TrieNode {
	TrieNode[] children;
	CombinedNode c;

	TrieNode() {
		children = new TrieNode[10];
		c = null;
	}
}

class Trie {
	TrieNode root;

	Trie() {
		root = new TrieNode();
	}

	boolean contains(String s) {
		TrieNode cur = root;
		for (int i = 0; i < s.length(); i++) {
			if (cur.children[s.charAt(i) - 48] == null)
				return false;
			else
				cur = cur.children[s.charAt(i) - 48];
		}
		return true;
	}

	CombinedNode getNode(String s, TrieNode n) {
		if (s.length() == 0)
			return n.c;
		else
			return getNode(new String(s.substring(1)),
					n.children[s.charAt(0) - 48]);
	}

	void add(String s, CombinedNode n) {
		TrieNode cur = root;
		for (int i = 0; i < s.length(); i++) {
			if (cur.children[s.charAt(i) - 48] == null)
				cur.children[s.charAt(i) - 48] = new TrieNode();
			cur = cur.children[s.charAt(i) - 48];
		}
		cur.c = n;
	}
}

public class CombinedTree {
	static HashSet<Long> dataset = new HashSet<Long>();
	static int count;

	public static void main(String args[]) throws IOException {
		BufferedReader br1 = new BufferedReader(new FileReader("files"));
		String f;
		Trie t = new Trie();
		while ((f = br1.readLine()) != null) {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String in;
			out: while ((in = br.readLine()) != null) {
				String s[] = in.split("\t");
				int c = 0;
				String par = null;
				String chi = s[0];
				CombinedNode child = null;
				dataset.add(Long.parseLong(chi));
				for (int i = 0; i < s.length; i++)
					if (!s[i].isEmpty() && Character.isDigit(s[i].charAt(0)))
						c++;
				switch (c) {
				case 1:
					continue out;
				case 2:
					child = new CombinedNode(chi);
					if (s[1].equals("nr")) {
						par = s[5];
						child.reply = true;
					} else
						par = s[2];
					break;
				default:
					System.err.println("Error!!");
					System.exit(1);
					break;
				}
				CombinedNode parent = null;
				if (t.contains(par))
					parent = t.getNode(par, t.root);
				else {
					parent = new CombinedNode(par);
					t.add(par, parent);
				}

				parent.children.add(child);
				child.parent = parent;
				t.add(chi, child);
			}
			br.close();
			System.err.println("Done making tree from file " + f);
		}
		br1.close();
		System.err.println("Done making trees from all");
		count = 0;
		printAllTrees(t.root);
		System.err.println("Total cascades = " + count);
	}

	private static void printAllTrees(TrieNode n) {
		if (n.c != null && n.c.parent == null) {
			printTree(n.c, dataset.contains(n.c.id));
			System.out.println();
			count++;
		}
		for (int i = 0; i < 10; i++)
			if (n.children[i] != null)
				printAllTrees(n.children[i]);
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
