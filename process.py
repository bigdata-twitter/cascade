import sys
import os
import traceback
import json
import pdb

class Node:
	def __init__ (self,tweet_id, word, child_list):
		self.tweet_id = tweet_id;
		self.word = word;
		self.child_list = child_list;


def dfs(tweet_id,parent_tag):
	global id_line
	global lines
	global visited
	global line_list
	global tree

	if not(id_line.has_key(tweet_id)):
		return None

	if visited.has_key(tweet_id):
		return None

	visited[tweet_id] = True

	line_no = id_line[tweet_id]
	line_list.append(line_no+1)

	items = lines[line_no].strip("\n").split("\t")
	assert len(items)==4
	assert items[0]==tweet_id

	tag = items[0]+","+items[1]
	if parent_tag!="":
		if not(tree.has_key(parent_tag)):
			tree[parent_tag] = []
		tree[parent_tag].append(tag)

	if items[3]=="":
		return []
	else:
		child_list = items[3].strip("\'").split("\', \'")
		for_ret = []
		for child in child_list:
			dfs_ret = dfs(child,tag)


if __name__=="__main__":
	id_line = {}
	infile = open("cascadesremloc1to10.txt","r")
	for line in infile:
		items = line.strip().split("\t")
		if id_line.has_key(items[0]):
			print "id %d duplication in id line mapping" %(items[0])
			sys.exit(0)
		id_line[items[0]] = int(items[1])-1
	infile.close()

	infile = open("cascadesrem1to10.txt","r")
	lines = infile.readlines()
	infile.close()

	f = open("trees1to10.txt",'w');
	ft = open('linesread1to10.txt','w');

	line_list = []
	visited = {}
	tree = {}
	for i in range(len(lines)):
		if i%10000==0:
			print "%d" %(i)
		visited.clear()
		del line_list[:]
		tree.clear()
		line = lines[i]
		items = line.strip("\n").split("\t")
		assert len(items)==4
		if items[2]=="NP":
			dfs(items[0],"")	
			print >>ft, "\n".join(map(lambda x: str(x),line_list))
			f.write(json.dumps(tree,sort_keys=True)+'\n');


"""
ctr = 0
for line in lines:
	if ctr%1000==0:
		print ctr

	ctr += 1
	items = line.strip("\n").split("\t")

	if len(items)!=4:
		print "length of line %d not 4" %(ctr)
		sys.exit(0)

	if not(id_line.has_key(items[0])):
		print "id %s not found in dict" %(items[0])
		sys.exit(0)

	if items[3]!="":
		children = items[3].strip("\'").split("\', \'")
		for child in children:
			if not(id_line.has_key(child)):
				print "child id %s not found in dict" %(child)
				sys.exit(0)

infile.close()
"""
