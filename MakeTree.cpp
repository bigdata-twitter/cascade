#include <iostream>
#include <forward_list>
#include <iterator>
#include <cctype>
#include <string>
#include <vector>
using namespace std;

class CombinedNode {
	public: 
		forward_list<CombinedNode> children;
		bool parent;
		long id;
		bool reply;

		CombinedNode(long l) {
			id = l;
			reply = false;
			parent = false;
		}
};

vector<string> split(string in, char split) {
	vector<string> array;
	istringstream ss(in);
	string token;
	while(getline(ss, token, split))
    	array.push_back(token);
    return array;
}

int main() {
	ifstream files("files");
	ofstream large("large");
	string f;
	int line = 0;
	map<long, CombinedNode> m;
	set<long> dataset;
	while (getline(files, f)) {
		ifstream br(f);
		string in;
		out: while (getline(br, in)) {
			line++;
			vector<string> s = split(in, '\t');
			int c = 0;
			long long par = 0, chi;
			try {
				chi = stoll(s[0]);
			} catch (const out_of_range& oor) {
    			large << in << endl;
    			continue out;
    		}
    		CombinedNode child;
			dataset.insert(chi);
			for (int i = 0; i < s.size(); i++)
				if (!s[i].empty() && isdigit(s[i][0]))
					c++;
			switch (c) {
			case 1:
				continue out;
			case 2:
				child = new CombinedNode(chi);
				if (s[1].compare("nr") == 0) {
					try {
						par = stoll(s[5]);
					} catch (const out_of_range& oor) {
						large << in << endl;
						continue out;
					}
					child.reply = true;
				} else
					try {
						par = stoll(s[2]);
					} catch (const out_of_range& oor) {
						large << in << endl;
						continue out;
					}
				break;
			case 3:
				child = new CombinedNode(chi);
				try {
					par = stoll(s[5]);
				} catch (const out_of_range& oor) {
					large << in << endl;
					continue out;
				}
				break;
			default:
				cerr << "Error!!\nFile: " << f << "\nLine number: " << line << "\nLine: " << in << endl;
			}
			CombinedNode parent;
			if (m.count(par) != 0)
				parent = m.at(par);
			else {
				parent = new CombinedNode(par);
				m[par] = parent;
			}
			parent.children.push_front(child);
			child.parent = true;
			m[chi] = child;
		}
		br.close();
		cerr << "Done making tree from file " << f << endl;
	}
	files.close();
	large.close();
	cerr << "Done making trees from all" << endl;
	int count = 0;
	for (map<long, CombinedNode>::iterator it=mymap.begin(); it!=mymap.end(); ++it) {
		CombinedNode cur = it->second;
		if (cur.parent == false) {
			count++;
			printTree(cur, dataset.contains(cur.id));
			cout << endl;
		}
	}
	cerr << "Total cascades = " << count << endl;
}

void printTree(CombinedNode cur, bool contained) {
	cout << "{\"id\":" << cur.id << ", \"contained\":" << contained << ", \"reply\":" << cur.reply << ", \"children\":[";
	if (!cur.children.empty()) {
		for (int i = 0, auto it = cur.children.begin(); it != cur.children.end(); i++, it++) {
			if (i == 0)
				printTree(*it, contained);
			else {
				cout << ",";
				printTree(*it, contained);
			}
		}
	}
	cout << "]}";
}