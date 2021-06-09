from bs4 import BeautifulSoup
import requests, re, os
#import urllib.request

def get_bs_obj():
    url = "https://www.knoc.co.kr"
    result = requests.get(url)  # get html
    bs_obj = BeautifulSoup(result.content, "html.parser")
    return bs_obj

bs_obj = get_bs_obj()
cost_all = bs_obj.find("table", {"class":"tbl_domestic"})

#print(cost_all)

data = []
i = 1
for tr in cost_all.find_all('tr'):
    tds = list(tr.find_all('td'))
    #print(tds)
    print(type(tds[0]))
    data.append([tds[0]])

print(data[0])
    #for td in tds:
    #    if td.find('a'):
    #        cost = tds[0].text
    #        data.append([as])

#print(data)