import json
import sys
sys.setrecursionlimit(2000)

def annotateTree(t,l=0):
	t["level"] = l
	t["number_of_children"] = len(t["children"])
	if t["level"] in level_dic:
		level_dic[t["level"]]=level_dic[t["level"]]+t["number_of_children"]
	else:
		level_dic[t["level"]]=t["number_of_children"]
	#print t["level"],"level"
	#print t["number_of_children"],"no"
    	for child in t["children"]:
		annotateTree(child, l+1)
     
f=open("t1.json","r")
no_tree_dic={}
no_child_dic={}

for line in f:
	t=json.loads(line)
	level_dic={}
	annotateTree(t)
	print level_dic
	for k in sorted(level_dic.keys()):
		if k in no_tree_dic.keys():
			no_tree_dic[k]=no_tree_dic[k]+1
			no_child_dic[k]=no_child_dic[k]+level_dic[k]
		else:
			no_tree_dic[k]=1
			no_child_dic[k]=level_dic[k]
f1=open("average_children_level.txt","w")
for k in sorted(no_tree_dic.keys()):
	f1.write(str(k)+"\t"+str(no_tree_dic[k])+"\t"+str(no_child_dic[k])+"\n")			
