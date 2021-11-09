- <a href="#korean" >한국어/Korean</a>
- <a href="#english" >English/영어</a>  

<div id="korean"></div>

# GenshinAuto for Android 원신 자동출석체크
- 호요랩 원신 출석체크를 자동으로 진행해주는 안드로이드 앱입니다.

## 사용법
- 앱 초기 실행 시 토큰을 등록해야합니다. 토큰 등록은 <b>자동 방식</b>과 <b>수동 방식</b>이 있으며, 편한 방법을 골라 이용하실 수 있습니다.
1. 자동 등록 시, 호요랩에 로그인 한 뒤 <b>반드시 <토큰 가져오기></b> 버튼을 눌러주세요.
2. 토큰 등록 후, 정상적으로 등록되었는지 확인하기 위해 <b><수동 출석체크></b> 진행을 권장합니다.
- 수동 출석체크시 푸시 내용이 다음과 같으면 정상입니다.
```
출석체크 완료! (출석체크 성공시)
여행자, 이미 출석체크했어~ (이미 당일 출석체크가 되어있는 경우)
```
푸시 내용이 다음과 같으면 토큰이 정상적으로 등록되지 않은 경우입니다.
```
아직 로그인하지 않았습니다. (토큰 오류로 로그인에 실패한 경우)
```
3. 정상 작동이 확인되었다면 <b><자동출첵 사용></b> 버튼을 활성화해주세요. <b>서비스 작동 성공</b> 메시지가 뜨면 성공입니다.
- 자동 출석체크는 중국 표준시 (GMT+8/PRC) 기준으로 00시 00분 30초에 작동합니다.

## 민감한 정보 이용
- 이 앱은 사용자의 개인정보 (토큰, UID 등)을 호요랩 이외 외부 서버로 전달하지 않습니다.
- 토큰과 UID를 이용한 모든 작업은 클라이언트(사용자의 기기)에서만 이루어집니다.

### 토큰 데이터 처리 코드
- [Schedule.java](https://github.com/dev-by-david/GenshinAuto/blob/main/app/src/main/java/arca/dev/genshinauto/Schedule.java) - 자동/수동 출석체크 관련 코드
- [WebViewActivity.java](https://github.com/dev-by-david/GenshinAuto/blob/main/app/src/main/java/arca/dev/genshinauto/WebViewActivity.java) - 토큰 자동등록 관련 코드
- 전체 로직 보기 - [app/src/main/java/arca/dev/genshinauto](https://github.com/dev-by-david/GenshinAuto/tree/main/app/src/main/java/arca/dev/genshinauto)


<div id="english"></div>
