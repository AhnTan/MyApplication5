# 08-18

# 08-18
리드미 첫 테스트

# 08-19 
v0.11 
포트넘버수정, clockview 크기 수정 및 확인 제거, 
QRcode 불필요한 코드 삭제

# 08-21
v0.41
패턴 코드 삭제, 설정 연결

#08-21
v0.42
모든 액티비티에 padding 7dp 추가,
디자인 수정(아이콘)
[해야 하는 것]
1. 로그아웃
2. 버튼 디자인
3. 다른 생체인식 기능 추가하기 -> 설정 보완
4. 전체적인 디자인 다듬기
5. QR코드
6. 최초에 학생증 등록시 경고창 띄우기

#08-24
v0.21
QR 코드 스샷 막음
액티비티 백스택 설정
프로그래스바 액비티비 생성할때마다 시간 초기화

#08-24
v0.12
build.gradle(app)에서 zxing 라이브러리 추가

QR코드 부분 전면 수정
- WebView를 통한 QR코드를 수정
  - 기존 1~2초 뒤 지연되서 나오던것을 안드로이드 자체적으로 만들게끔 하여 QR코드가 바로 나오게끔 함
- ClockView 비활성화, QR코드와 서버와의 관계에 대한 코드수정(send값으로만 요청하여 보내던 것을 '학번+현재시간'으로 교체)
- Intent값 수정
- (Java)서버 코드 수정(알고리즘 문제로 깃에는 올리지 않음)  