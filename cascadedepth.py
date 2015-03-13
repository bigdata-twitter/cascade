#load in your data
import json
f=open("t.json")
height_dic={}
for line in f:
	json_tree = json.loads(line)
	depth = 0
	if json_tree:
		depth += 1

#add a root node + it's depth
	to_visit = [(json_tree, depth)] 

	max_depth = 0

	while to_visit:
		current = to_visit.pop(0)
		if current[1] > max_depth:
				max_depth = current[1]
		for child in current[0]['children']:
			to_visit.append((child, current[1] + 1))
	if max_depth not in height_dic:
		height_dic[max_depth]=1
	else: 
		height_dic[max_depth]=+height_dic[max_depth]+1
f1=open("cascasde_depth.txt","w")
for k in sorted(height_dic.keys()):
	f1.write(str(k)+"\t"+str(height_dic[k])+"\n")
