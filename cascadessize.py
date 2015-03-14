import json
import sys
sys.setrecursionlimit(2000)
# method to recursively browse the elements
def get_children(node):
    stack = [node]
    #print stack
    
    while stack:
        node = stack.pop()
	print node
        stack.extend(node['children'][::-1])
        yield node 
          
# open the file and parse its JSON contents
f = open('t1.json','r')
rep_dic={}
size_dic={}

for line in f:
	d = json.loads(line)
	child_dic={}
	rep=0
	for child in get_children(d):
		print child,"ch"
		if child not in child_dic.keys():
			child_dic[child["id"]]=1
			if child["reply"]==1:
				rep=rep+1
			else: 
				rep=rep+0
	print child_dic,"ch",len(child_dic)
	size=len(child_dic)
	if size not in size_dic:
		size_dic[size]=1
		rep_dic[size]=rep
	else:
		size_dic[size]=size_dic[size]+1
		rep_dic[size]=rep_dic[size]+rep
print rep_dic
print size_dic
f.close()
f1=open("cascasde_size.txt","w")
for k in sorted(size_dic.keys()):
	f1.write(str(k)+"\t"+str(size_dic[k])+"\t"+str(rep_dic[k])+"\n")
	
