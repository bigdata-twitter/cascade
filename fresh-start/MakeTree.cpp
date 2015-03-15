#include <iostream>
#include <sstream>
#include <map>
#include <set>
#include <fstream>
#include <list>
#include <iterator>
#include <cctype>
#include <cstdlib>
#include <string>
#include <vector>
#include <stdexcept>
using namespace std;

class CombinedNode {
	public: 
		list<CombinedNode*> children;
		bool parent; // the root node will not have a parent, all other nodes will
		long id;
		bool reply;

		CombinedNode(long l) {
			id = l;
			reply = false;
			parent = false;
		}
};

// to split a string and tokenise it into a vector around a given character
vector<string> split(string in, char split) {
	vector<string> array;
	istringstream ss(in);
	string token;
	while(getline(ss, token, split))
	    	array.push_back(token);
	return array;
}

// removed the idea of boolean contained from the trees because it doesn't make any sense
void printTree(CombinedNode* cur) {
	cout << "{\"id\":" << cur->id << ", \"reply\":" << cur->reply << ", \"children\":[";
	if (!cur->children.empty()) {
		int i = 0;
		for (auto it = cur->children.begin(); it != cur->children.end(); it++) {
			if (i == 0)
				printTree(*it);
			else {
				cout << ",";
				printTree(*it);
			}
			i++;
		}
	}
	cout << "]}";
}

int main() {
	ifstream files("files");
	ofstream large("large");
	string f;
	map<long long, CombinedNode*> m;
	while (getline(files, f)) {
		ifstream br(f.c_str());
		string in;
		int line = 0;
		out: while (getline(br, in)) {
			// in will be of the form xxx, rt yyy, rp zzz ...
			vector<string> s = split(in, ',');
			long long par;
			try {
				par = stoll(s[0]); // contains the parent tweet id
			} catch (const out_of_range& oor) {
    				large << in << endl;
    				goto out;
    			}
			CombinedNode *parent;
			if (m.count(par) != 0) // check if the parent is already in the map. if yes, set parent node to that otherwise create a new node
				parent = m.at(par);
			else {
				parent = new CombinedNode(par);
				m[par] = parent;
			}
			inner: for (int i = 1; i < s.size(); i++) {
				vector<string> this_one = split(s[i], '\t'); // split each child into rt/rp and the tweet id
				long long chi;
				CombinedNode *child;
				try {
					chi = stoll(this_one[1]); // tweet id
				} catch (const out_of_range& oor) {
					large << s[0] << "," << s[i] << endl;
					goto inner;
				}
				if (m.count(chi) != 0) // check if the id is in the map and do similar stuff as above
					child = m.at(chi);
				else {
					child = new CombinedNode(chi);
					m[chi] = child;
				}
				if (this_one[0].compare(" rp") == 0) // set the reply flag to true
					child->reply = true;
				child->parent = true; // for each child, the parent flag will be true since it has a parent
				parent->children.push_front(child); // add the child to the list of children of the parent
			}
		}
		br.close();
		cerr << "Done making tree from file " << f << endl;
	}
	files.close();
	large.close();
	cerr << "Done making trees from all" << endl;
	int count = 0;
	// iterate through the entire map of nodes. if the node under consideration doesn't have a parent, it must be root. so print the tree from there
	for (map<long, CombinedNode*>::iterator it=m.begin(); it!=m.end(); ++it) {
		CombinedNode* cur = it->second;
		if (cur->parent == false) {
			count++;
			if (count%10000 == 0)
				cerr << count << " cascades found" << endl;
			printTree(cur);
			cout << endl;
		}
	}
	cerr << "Total cascades = " << count << endl;
}
