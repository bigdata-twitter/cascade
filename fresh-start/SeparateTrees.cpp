#include <iostream>
#include <sstream>
#include <set>
#include <fstream>
#include <cctype>
#include <cstdlib>
#include <string>
#include <vector>
using namespace std;

// to split a string and tokenise it into a vector around a given character
vector<string> split(string in, char split) {
	vector<string> array;
	istringstream ss(in);
	string token;
	while(getline(ss, token, split))
	    	array.push_back(token);
	return array;
}

int main() {
	ifstream files("parent_file");
	set<long> nodes_with_parents;
	string f;
	while (getline(files, f)) {
		ifstream br(f.c_str());
		string in;
		// get the node that has a parent and insert into a set s
		while (getline(br, in)) {
			vector<string> s = split(in, '\t');
			long long child;
			child = stoll(s[0]);
			nodes_with_parents.insert(child);
		}
		br.close();
		cerr << "Added all children nodes from " << f << endl;
	}
	files.close();
	cerr << "Added all children nodes from all files" << endl;

	ifstream tree("tree20new39.json");
	ofstream complete("complete_trees_1.json");
	ofstream incomplete("trees_to_merge_1.json");

	while (getline(tree, f)) {
		long long root = stoll(split(split(f, ',')[0], ':')[1]);
		if (nodes_with_parents.count(root) == 0) // if set does not contain root, root does not have any parent and therefore the tree obtained is complete
			complete << f << endl;
		else
			incomplete << f << endl;
	}
}
