import sys
f=open("files")
tweet_list=[]
for line in f:
	filename=line.split("\n")
	fname=filename[0]
	f1=open(fname,'r')
	for l in f1:
		l=l.split('\n')
		tup=l[0].split('\t')
		chi=long(tup[0])
		if tup[1]=="nr" and tup[4]=="no":
			if chi not in tweet_list:
				tweet_list.append(chi)
		if tup[1]=="rt" and tup[3]=="no":
			par=long(tup[2])
			if chi not in tweet_list:
				tweet_list.append(chi)
			if par not in tweet_list:
				tweet_list.append(par)
		if tup[1]=="nr" and tup[4]=="rp":
			par=long(tup[5])
			if chi not in tweet_list:
				tweet_list.append(chi)
			if par not in tweet_list:
				tweet_list.append(par)

	print("done with"+fname)
	print sys.getsizeof(tweet_list)
	f1.close()
print("Done all")
f.close()
