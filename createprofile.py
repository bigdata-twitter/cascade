import sys
import io
import json
f1=open("foldername.txt","r")
fprog=open("/mnt/filer01/round2/progress.txt","w")
for line in f1:
	lines=line.split('\n')
	folder=lines[0]
	fprog.write(str(folder)+'\n')
	txtfile = "tweets"+folder.split('_')[0]+".txt";
	path = "/mnt/filer01/round2/tmp/"+folder+"/mnt/"+txtfile;
	f=io.open(path,"r",encoding='utf8')
	fw=io.open("/mnt/filer01/round2/twitter/"+folder+"/mnt/userprofile_"+folder+".txt","w",encoding='utf8')
	fuid=io.open("/mnt/filer01/round2/twitter/"+folder+"/mnt/tid_uid_fc_"+folder+".txt","w",encoding='utf8')
	fment=io.open("/mnt/filer01/round2/twitter/"+folder+"/mnt/users_ment_"+folder+".txt","w",encoding='utf8')
	frt=io.open("/mnt/filer01/round2/twitter/"+folder+"/mnt/retweet-data_"+folder+".txt","w",encoding='utf8')
	frp=io.open("/mnt/filer01/round2/twitter/"+folder+"/mnt/reply-data_"+folder+".txt","w",encoding='utf8')
	fresp=io.open("/mnt/filer01/round2/twitter/"+folder+"/mnt/rt-rep-data"+folder+".txt","w",encoding='utf8')
	hash=0;
	tot_tweets=0;
	for line in f:
		try:
			data = json.loads(line);
			
			fw.write(unicode(data['author'])+'\t');
			fw.write(data['tweets'][0]['user']['screen_name']+'\t');
			fw.write(unicode(data['tweets'][0]['user']['followers_count'])+'\t');
			fw.write(unicode(data['tweets'][0]['user']['friends_count'])+'\t');
			fw.write(unicode(data['tweets'][0]['user']['listed_count'])+'\t');
			fw.write(unicode(data['tweets'][0]['user']['statuses_count'])+'\t');
			fw.write(unicode(data['tweets'][0]['user']['created_at'])+'\t');
			fw.write(unicode(data['tweets'][0]['user']['lang']));
			fw.write(unicode('\n'));
	
			for i in range(0,len(data['tweets'])):
				#print("Tweet "+str(i+1)+" done")
				tweet_text = unicode(data['tweets'][i]['text']).replace("\n","\\n")
				fuid.write(unicode(data['tweets'][i]["id_str"])+'\t'+unicode(data['tweets'][i]['user']['id_str'])+'\t'+tweet_text+'\t'+unicode(data["tweets"][i]["created_at"])+'\t'+unicode(data["tweets"][i]["favorite_count"]));
				tot_tweets=tot_tweets+1;
				if data["tweets"][i]["coordinates"]!=None:
					fuid.write('\t'+unicode(data['tweets'][i]["coordinates"]["coordinates"][0]))
					fuid.write('\t'+unicode(data['tweets'][i]["coordinates"]["coordinates"][1]))
				else:
					fuid.write('\t'+unicode('nc')+'\t'+unicode('nc'))
				if (len(data['tweets'][i]["entities"]["hashtags"]) != 0):
					hash = hash+1;
				for j in range(0,len(data['tweets'][i]["entities"]["hashtags"])):
					fuid.write('\t'+unicode(data['tweets'][i]["entities"]["hashtags"][j]["text"]))
				fuid.write(unicode('\n'))
				if (len(data['tweets'][i]["entities"]["user_mentions"])!=0):
					fment.write(unicode(data['tweets'][i]["id_str"])+'\t')
				for j in range(0,len(data['tweets'][i]["entities"]["user_mentions"])):
					fment.write(unicode(data["tweets"][i]["entities"]["user_mentions"][j]["id"])+',')
				if (len(data['tweets'][i]["entities"]["user_mentions"])!=0):
					fment.write(unicode('\n'));
				fresp.write(unicode(data['tweets'][i]["id_str"])+'\t')
				if 'retweeted_status' in data['tweets'][i]:
					retweet_parent=unicode(data['tweets'][i]['retweeted_status']['id'])
					fresp.write('rt\t'+retweet_parent+'\t')
					frt.write(unicode(data['tweets'][i]["id_str"])+'\t'+'rt\t'+retweet_parent+unicode('\n'))
				else: 
					fresp.write(unicode('nr\t'+'\t'+'\t'))
				if data["tweets"][i]["in_reply_to_status_id"]!=None:
					reply_parent=unicode(data["tweets"][i]["in_reply_to_status_id"])
					fresp.write('rp\t'+reply_parent+unicode('\n'))
					frp.write(unicode(data['tweets'][i]["id_str"])+'\t'+'rp\t'+reply_parent+unicode('\n'))
				else:
					fresp.write(unicode('no\t'+'\t'+'\n'))

		except:
			continue;
	fw.close()
	fuid.close()
	fment.close()
	frt.close()
	frp.close()
	f.close()
	fresp.close()
print("Hashtagged="+hash+'\nTotal='+tot_tweets)