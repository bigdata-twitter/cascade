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
	CombinedNode parent;
	long id;
	boolean reply;

	TreeNode(long id, boolean reply) {
		this.id = id;
		this.children = new LinkedList<TreeNode>();
		this.reply = reply;
	}
}

public class TwoSize {
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
	static Hashtable<Long, LinkedList<Child>> parents;
	final static int MAX_LOAD = 8;

	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("roots_all"));
		BufferedWriter left = new BufferedWriter(new FileWriter(
				"leftover_roots"));
		BufferedWriter oor = new BufferedWriter(new FileWriter("out_of_range"));
		BufferedWriter trees = new BufferedWriter(new FileWriter("trees"));
		LinkedList<Long> roots = new LinkedList<Long>();
		parents = new Hashtable<Long, LinkedList<Child>>();
		String in;
		while ((in = br.readLine()) != null)
			roots.add(Long.parseLong(in));
		br.close();
		int to_load = 40, cur_load = 0, cur_loaded = 41;
		Iterator<Long> iter = roots.iterator();
		while (iter.hasNext()) {
			long cur_root = iter.next();
			for (int i = id.length; i >= 0; i--)
				if (cur_root >= id[i]) {
					to_load = i;
					break;
				}
			while (cur_loaded >= to_load) {
				if (cur_load == MAX_LOAD)
					removeData(cur_loaded + MAX_LOAD - 1);
				loadData(--cur_loaded);
			}
			if (!parents.containsKey(cur_root))
				left.write(cur_root + "\n");
			else {
				LinkedList<Child> children = parents.get(cur_root);
				if (children.size() != 1)
					continue;
				Child child = children.getFirst();
				if (child.id < id[cur_loaded + MAX_LOAD]) {
					if (!parents.containsKey(child.id)) {
						TreeNode tn = new TreeNode(cur_root, false);
						tn.children.add(new TreeNode(child.id, child.reply));
						printTree(tn, true);
						parents.remove(cur_root);
					}
				} else
					oor.write(cur_root + ", " + (child.reply ? "rp\t" : "rt\t")
							+ child.id + "\n");
			}
		}
		left.close();
		oor.close();
		trees.close();
	}

	private static void printTree(TreeNode tn, boolean contained) {
		System.out.print("{\"id\":" + tn.id + ", \"contained\":" + contained
				+ ", \"reply\":" + tn.reply + ", \"children\":[");
		if (!tn.children.isEmpty()) {
			for (int i = 0; i < tn.children.size(); i++) {
				if (i == 0)
					printTree(tn.children.getFirst(), contained);
				else {
					System.out.print(",");
					printTree(tn.children.get(i), contained);
				}
			}
		}
		System.out.print("]}");
	}

	private static void loadData(int i) throws IOException {
		BufferedReader br = new BufferedReader(
				new FileReader("child_list_" + i));
		String in;
		while ((in = br.readLine()) != null) {
			String[] cur = in.split(", ");
			LinkedList<Child> list = new LinkedList<Child>();
			for (int j = 1; j < cur.length; j++) {
				if (cur[j].split("\t")[0].equals("rp"))
					list.add(new Child(true,
							Long.parseLong(cur[j].split("\t")[0])));
				else
					list.add(new Child(false,
							Long.parseLong(cur[j].split("\t")[0])));
			}
			parents.put(Long.parseLong(cur[0]), list);
		}
		br.close();
	}

	private static void removeData(int loaded) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("child_list_"
				+ loaded));
		BufferedWriter left = new BufferedWriter(new FileWriter("left_"
				+ loaded));
		String in;
		while ((in = br.readLine()) != null) {
			parents.remove(Long.parseLong(in.split(", ")[0]));
			left.write(in + "\n");
		}
		left.close();
		br.close();
	}
}
