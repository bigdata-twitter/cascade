import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

class TreeNode {
	LinkedList<TreeNode> children;
	long id;
	boolean reply;

	TreeNode(long id, boolean reply) {
		this.id = id;
		this.children = new LinkedList<TreeNode>();
		this.reply = reply;
	}
}

class Child {
	boolean reply;
	long id;

	Child(boolean reply, long id) {
		this.reply = reply;
		this.id = id;
	}
}

public class TreeFormation {
	static final long id[] = { 0l, 42500000000000000l, 85000000000000000l,
			170000000000000000l, 232500000000000000l, 265000000000000000l,
			295000000000000000l, 325000000000000000l, 355000000000000000l,
			387500000000000000l, 400000000000000000l, 415000000000000000l,
			430000000000000000l, 431000000000000000l, 432000000000000000l,
			434000000000000000l, 436000000000000000l, 438000000000000000l,
			440000000000000000l, 441250000000000000l, 442500000000000000l,
			443750000000000000l, 445000000000000000l, 446250000000000000l,
			447500000000000000l, 448750000000000000l, 450000000000000000l,
			450750000000000000l, 451500000000000000l, 452250000000000000l,
			453050000000000000l, 453750000000000000l, 454500000000000000l,
			455250000000000000l, 456000000000000000l, 457300000000000000l,
			458700000000000000l, 459300000000000000l, 460000000000000000l,
			461000000000000000l, 5000000000000000000l };
	final static int MAX_LOAD = 10;
	static Hashtable<Long, LinkedList<Child>> parents = new Hashtable<Long, LinkedList<Child>>();

	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("leftover_roots"));
		int c = 0;
		String in;
		int to_at_least_load = 40, loaded = 41, cur_load = 0;
		while ((in = br.readLine()) != null) {
			c++;
			if (c % 10000 == 0)
				System.out.println(c + " roots khatam");
			long root = Long.parseLong(in);
			for (int i = id.length - 1; i >= 0; i--)
				if (root >= id[i]) {
					to_at_least_load = i;
					break;
				}
			while (loaded >= to_at_least_load) {
				if (cur_load == MAX_LOAD)
					removeData(loaded + MAX_LOAD - 1);
				loadData(--loaded);
			}
			TreeNode tree = makeTree(root, false);
			if (tree != null) {
				System.err.println("pahuch gaye yahaan");
				BufferedWriter bw = new BufferedWriter(new FileWriter(
						"tree_others", true));
				printNRemoveTree(tree, true, bw);
				bw.write("\n");
				bw.close();
			}
		}
		br.close();
	}

	private static void printNRemoveTree(TreeNode tn, boolean contained,
			BufferedWriter bw) throws IOException {
		bw.write("{\"id\":" + tn.id + ", \"contained\":" + contained
				+ ", \"reply\":" + tn.reply + ", \"children\":[");
		if (!tn.children.isEmpty()) {
			for (int i = 0; i < tn.children.size(); i++) {
				if (i == 0)
					printNRemoveTree(tn.children.getFirst(), contained, bw);
				else {
					bw.write(",");
					printNRemoveTree(tn.children.get(i), contained, bw);
				}
			}
		}
		bw.write("]}");
	}

	private static TreeNode makeTree(long root, boolean reply) {
		if (!parents.containsKey(root))
			return null;
		LinkedList<Child> cur_children = parents.get(root);
		TreeNode tn = new TreeNode(root, reply);
		if (cur_children.size() != 0) {
			Iterator<Child> iter = cur_children.iterator();
			while (iter.hasNext()) {
				Child cur_child = iter.next();
				tn.children.add(makeTree(cur_child.id, cur_child.reply));
			}
		}
		return tn;
	}

	private static void removeData(int i) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("left_" + i));
		BufferedWriter left = new BufferedWriter(new FileWriter("still_left_"
				+ i));
		String in;
		while ((in = br.readLine()) != null) {
			long par = Long.parseLong(in.split(", ")[0]);
			if (parents.containsKey(par)) {
				left.write(in + "\n");
				parents.remove(par);
			}
		}
		left.close();
		br.close();
	}

	private static void loadData(int i) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("left_" + i));
		String in;
		while ((in = br.readLine()) != null) {
			String[] cur = in.split(", ");
			long parent = Long.parseLong(cur[0]);
			LinkedList<Child> list = new LinkedList<Child>();
			for (int j = 1; j < cur.length; j++) {
				if (cur[j].split("\t")[0].equals("rp"))
					list.add(new Child(true,
							Long.parseLong(cur[j].split("\t")[1])));
				else
					list.add(new Child(false,
							Long.parseLong(cur[j].split("\t")[1])));
			}
			parents.put(parent, list);
		}
		br.close();
	}
}
