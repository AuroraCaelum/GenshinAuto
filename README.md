- <a href="#korean" >한국어/Korean</a> &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp; <a href="#english" >English/영어</a>
<br></br>
- [다운로드 / Latest Release](https://github.com/dev-by-david/GenshinAuto/releases)

***

<div id="korean"></div>

# 원신 자동 출석체크 for Android
- HoyoLab 원신 출석체크를 자동으로 진행해주는 안드로이드 앱입니다.
- Android 5.0 Lollipop 이상 사용 가능합니다.

## 사용법
- 앱 초기 실행 시 토큰을 등록해야합니다. 토큰 등록은 <b>자동 방식</b>과 <b>수동 방식</b>이 있으며, 편한 방법을 골라 이용하실 수 있습니다.
1. 자동 등록 시, HoyoLab에 로그인 한 뒤 <b>반드시 <토큰 가져오기></b> 버튼을 눌러주세요.
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
- 자동 출석체크는 중국 표준시 (UTC+8/PRC) 기준으로 00시 00분 30초에 작동합니다.

## 민감한 정보 이용
- 이 앱은 사용자의 개인정보 (토큰, UID 등)을 HoyoLab 이외 외부 서버로 전달하지 않습니다.
- 토큰과 UID를 이용한 모든 작업은 클라이언트(사용자의 기기)에서만 이루어집니다.
- 사용자의 토큰은 앱에서만 접근할 수 있는 앱 내부 저장소에 안전하게 저장됩니다.

### 토큰 데이터 처리 코드
- [Schedule.java](https://github.com/dev-by-david/GenshinAuto/blob/main/app/src/main/java/arca/dev/genshinauto/Schedule.java) - 자동/수동 출석체크 관련 코드
- [WebViewActivity.java](https://github.com/dev-by-david/GenshinAuto/blob/main/app/src/main/java/arca/dev/genshinauto/WebViewActivity.java) - 토큰 자동등록 관련 코드
- 전체 로직 보기 - [app/src/main/java/arca/dev/genshinauto](https://github.com/dev-by-david/GenshinAuto/tree/main/app/src/main/java/arca/dev/genshinauto)

## 오류 관련
- 급하게 만들어서 자잘한 오류가 있을 수 있습니다. 오류내역 이슈 보내주시면 개선하는데에 큰 도움이 됩니다.

***

<div id="english"></div>

# Genshin Auto Check-In for Android
- This is an Android app that automatically proceeds with HoyoLab Genshin Daily Check-In.
- Android 5.0 Lollipop or higher is available.

## Usage
- You must register your token to use. You can <b>Automatically Register</b> or <b>Manually Register</b> your token values.
1. When automatic register, you must click the <b>Get Token</b> button after logging in HoyoLab.
2. After registering your token, click <b>Manual Check-In</b> button to validate your token values are recommended.
- If you are successfully registered, you will got the following push message.
```
Check-In Completed! (Successfully check in)
Treveler, you've already checked in today~ (Already checked in)
```
If the token values are invalid, you will got the following push message.
```
Not logged in. (Failed to login, invalid token value)
```
3. If manual check-in has successed, turn on the <b><Enable Service></b> button. Please check <b>Service started</b> message.
- Automatic Check-In will activated in UTC+8/PRC 00:00:30.

## Use of sensitive data
- This application never sends a personal datas like Token values, UID, etc. to external servers except HoyoLab.
- Every function using Token and UID will work in client(user's devices).
- User's token values are safely saved in internal storage which only local applications can access.

### Data processing codes
- [Schedule.java](https://github.com/dev-by-david/GenshinAuto/blob/main/app/src/main/java/arca/dev/genshinauto/Schedule.java) - Automatic/Manual Check-In
- [WebViewActivity.java](https://github.com/dev-by-david/GenshinAuto/blob/main/app/src/main/java/arca/dev/genshinauto/WebViewActivity.java) - Token value parse
- View all logics - [app/src/main/java/arca/dev/genshinauto](https://github.com/dev-by-david/GenshinAuto/tree/main/app/src/main/java/arca/dev/genshinauto)

## About issue
- If you found some bugs, please post an issues.

<br></br>

## Special Thanks to<br>
Kim Yuhwan (Code Review)<br>
Kang Jiyoon &nbsp;|&nbsp; Ma Joyeong &nbsp;|&nbsp; Sean Kim (Test)
