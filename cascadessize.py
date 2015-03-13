import json

def get_children(node):
    for child in node['children']:
        yield child 
        for grandchild in get_children(child):
			yield grandchild
          
# open the file and parse its JSON contents
f = open('tree.txt','r')
size_dic={}
for line in f:
	d = json.loads(line)
	child_dic={}
	for child in get_children(d):
		if child not in child_dic.keys():
			child_dic[child["id"]]=1

	size=len(child_dic)+1 # root + all other children
	if size not in size_dic:
		size_dic[size]=1
	else:
		size_dic[size]=size_dic[size]+1
f.close()
f1=open("cascasde_size.txt","w")
for k in sorted(size_dic.keys()):
	f1.write(str(k)+"\t"+str(size_dic[k])+"\n")
		
