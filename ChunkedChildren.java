import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

class Child {
	boolean reply;
	long id;

	Child(boolean reply, long id) {
		this.reply = reply;
		this.id = id;
	}
}

public class ChunkedChildren {
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

	public static void main(String args[]) throws IOException {
		for (int i = 0; i < id.length; i++) {
			BufferedReader br = new BufferedReader(new FileReader("chunk_" + i));
			Hashtable<Long, LinkedList<Child>> chunk = new Hashtable<Long, LinkedList<Child>>(); // dictionary
																									// for
																									// the
																									// data
																									// chunk
			String in;
			while ((in = br.readLine()) != null) {
				String s[] = in.split("\t");
				int c = 0;
				boolean reply = false;
				long par = 0;
				for (int j = 0; j < s.length; j++)
					if (!s[j].isEmpty() && Character.isDigit(s[j].charAt(0)))
						c++;
				switch (c) { // extract the parent tweet id
				case 2:
					if (s[1].equals("nr")) {
						par = Long.parseLong(s[5]);
						reply = true;
					} else
						par = Long.parseLong(s[2]);
					break;
				case 3:
					par = Long.parseLong(s[2]);
					break;
				}
				Child cur = new Child(reply, Long.parseLong(s[0]));
				if (!chunk.containsKey(par)) // new parent, create child list
					chunk.put(par, new LinkedList<Child>());
				chunk.get(par).add(cur);
			}
			br.close();
			System.out.println("Done finding children in file - chunk_" + i);
			BufferedWriter bw = new BufferedWriter(new FileWriter("child_list_"
					+ i));
			Enumeration<Long> e = chunk.keys();
			while (e.hasMoreElements()) {
				long parent = e.nextElement();
				LinkedList<Child> children = chunk.get(parent);
				String toWrite = parent + "";
				Iterator<Child> iter = children.iterator();
				while (iter.hasNext()) {
					Child cur = iter.next();
					if (cur.reply)
						toWrite = toWrite.concat(", rp\t" + cur.id);
					else
						toWrite = toWrite.concat(", rt\t" + cur.id);
				}
				bw.write(toWrite + '\n');
			}
			bw.close();
		}
		System.out.println("Done finding children in all chunk files");
	}
}
