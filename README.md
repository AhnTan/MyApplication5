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

# 08-24
v0.21
QR 코드 스샷 막음
액티비티 백스택 설정
프로그래스바 액비티비 생성할때마다 시간 초기화

# 08-24
v0.12
build.gradle(app)에서 zxing 라이브러리 추가

QR코드 부분 전면 수정
- WebView를 통한 QR코드를 수정
  - 기존 1~2초 뒤 지연되서 나오던것을 안드로이드 자체적으로 만들게끔 하여 QR코드가 바로 나오게끔 함
- ClockView 비활성화, QR코드와 서버와의 관계에 대한 코드수정(send값으로만 요청하여 보내던 것을 '학번+현재시간'으로 교체)
- Intent값 수정
- (Java)서버 코드 수정(알고리즘 문제로 깃에는 올리지 않음)  

# 08-24
v0.13
->v0.12 잘못된 업로드 수정

# 08-24
v0.14
->QR코드 액티비티에서 뒤로가기시 로그에러로 인한 어플 강제종료 해결

# 08-24
v0.22
-> 쓰레드 부를때마다 줄어드는 시간 다른거 해결

# 08-29
v0.15
기존 로그인시 인텐트로 만 id값을 넘겨주어 중간에 id값이 소멸되어 null값이 되는것을
sharedreference를 이용하여 로그인시 id값을 받아 반영구적으로 저장함
id값 + 암호화값 -> split으로 토큰형식으로 값을 자름

v0.23
패턴라이브러리 추가 (짝짞짝)
스타일 변경 (toolbar-검은색, progressbar-녹색)

# 08-30
v0.43
패턴이 일치할 경우 QRcode Activity로 넘어감
App 이미지와 라벨 변경

v0.44
setting화면 하다가 집감
setting에서 리스트 클릭하면 각 액티비티 넘어가도록

# 08-31
v0.16
qrcode-xml 수정 : 현재시간 실시간으로 수정
임시적으로(최대3000초, 2970초 때부터 새값받아오는 버튼 뜨게함)
시간관련 쓰레드들 interrupt 수정

# 09-10
v0.17
3/4G로 들어갈 수 있게 host와 port 수정 ()
메인액티비티에 네트워크 접속권한 추가

v0.45
사용자가 지정한 방법(지문/패턴)으로 학생증을 불러오도록 수정
- SetLockActivity - sharedPreference
- OldFirstView - 지문/패턴 Activity 불러오기

v0.46
패턴 언어 수정
지문 - 단말기 설정화면 불러오기

v0.18, v0.25
경동 - 최초로그인시 학번과 핸드폰번호와 같이 shared로 저장시켜 타 학번으로 로그인 하지 못하게 막음
매니페스트 추가 : android.permission.READ_PHONE_STATE 추가

형우 - 서버쪽에서 로그인한 사람의 학번과 이름값을 보내 QRcode 부분에서 나오게끔 함

# 09-17
v0.47
기존회원, 신규회원 구분해서 Activity 띄우기
2. 신규회원
2-1. 로그인 후, new_first_view로 이동
2-2. 등록하기 btn 클릭 시, 팝업 띄우기
2-3. 지문등록하기 버튼 클릭 시, SetFPActivity로 이동
2-4. 패턴등록하기 버튼 클릭 시, SetPatternActivity로 이동
3. 기존회원
3-1. 로그인 후, old_first_view로 이동