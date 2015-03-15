import json
import sys

sys.setrecursionlimit(2000)

f = open('tree20new39.json', 'r')
offset = 0
dict = {}
for line in f:
	root = long(line.split('\n')[0].split(':')[1].split(',')[0])
	dict[root] = offset
	offset += len(line)
#print dict

def parseTree(obj):
    if len(obj["children"]) == 0:
        if obj["id"] in dict:
        	f.seek(dict[obj["id"]])
        	dict.pop(obj["id"])
        	line = f.readline()
        	subtree = json.loads(line)
		#print subtree
		obj["children"] = subtree["children"]
		#print obj["children"]
    else:
        for child in obj["children"]:
            parseTree(child)

g = open('tree0new19.json', 'r')
h = open('trees_final.json', 'w')
count = 0
for line in g:
	tree = json.loads(line)
	#print tree
	parseTree(tree)
	json.dump(tree, h)
	h.write('\n')
	count += 1
#print count
g.close()

for key, value in dict.iteritems():
	f.seek(value)
	line = f.readline()
	tree = json.loads(line)
	json.dump(tree, h)
	h.write('\n')
	count += 1
print count
f.close()
h.close()
