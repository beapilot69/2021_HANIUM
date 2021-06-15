from bs4 import BeautifulSoup   # HTML과 XML문서를 파싱하기 위한 패키지
import requests, re, os         # HTTP 요청을 처리하는 패키지

# URL 주소로부터 페이지 소스를 파싱
def parsing():
    url = "https://www.knoc.co.kr"  # 한국석유공사 홈페이지 주소
    result = requests.get(url)      # get type의 HTTP요청을 수행
    bs_obj = BeautifulSoup(result.content, "html.parser")   # contect 속성을 통해 바이너리 원문을 얻음
    return bs_obj
    
# 페이지 소스로부터 필요한 정보가 들어있는 테이블 선택
parse_obj = parsing()
cost_all = parse_obj.find("table", {"class":"tbl_domestic"})

# 가격을 저장할 빈 리스트 생성
data = []

# 선택한 테이블 안에서 필요한 정보가 속한 속성에서 가격만 리스트에 저장
for tr in cost_all.find_all('tr'):
    tds =. ist(tr.find_all('td'))
    data.append(tds[0].text)    # Get costs of Gasoline, Diesel
        # text 속성을 통해 UTF-8로 인코딩된 문자열을 얻을 수 있음

costOfGasoline = float(data[0]) # 리스트의 원소는 문자열이므로 실수형으로 형 변환 수행
costOfDiesel = float(data[1])   # 형변환 이유 : 결제된 금액만큼의 유량을 얻기 위해 곱/나누기 연산 필요

# 출력단
print("\n오늘 날짜 : ", datetime.today().strftime("%Y/%m/%d")) # YYYY/mm/dd형태의 날짜 출력
print("\n\n휘발유 가격은", costOfGasoline,"원 입니다.")
print("경유 가격은", costOfDiesel,"원 입니다.")
print("\n\n\n       by https://www.knoc.co.kr") # 소스의 출처 표기
