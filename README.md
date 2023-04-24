![Download Count](https://img.shields.io/github/downloads/AuroraCaelum/GenshinAuto/total.svg)
[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2FAuroraCaelum%2FGenshinAuto&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)

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
- 자동 출석체크는 중국 표준시 (UTC+8/PRC) 기준으로 00시 00분 10초에 작동합니다.

## 민감한 정보 이용
- 이 앱은 사용자의 개인정보 (토큰, UID 등)을 HoyoLab 이외 외부 서버로 전달하지 않습니다.
- 토큰과 UID를 이용한 모든 작업은 클라이언트(사용자의 기기)에서만 이루어집니다.
- 사용자의 토큰은 앱에서만 접근할 수 있는 앱 내부 저장소에 안전하게 저장됩니다.

### 토큰 데이터 처리 코드
- [Schedule.java](https://github.com/AuroraCaelum/GenshinAuto/blob/main/app/src/main/java/arca/dev/genshinauto/Schedule.java) - 자동/수동 출석체크 관련 코드
- [WebViewActivity.java](https://github.com/AuroraCaelum/GenshinAuto/blob/main/app/src/main/java/arca/dev/genshinauto/WebViewActivity.java) - 토큰 자동등록 관련 코드
- 전체 로직 보기 - [app/src/main/java/arca/dev/genshinauto](https://github.com/AuroraCaelum/GenshinAuto/tree/main/app/src/main/java/arca/dev/genshinauto)

## 오류 관련
- 급하게 만들어서 자잘한 오류가 있을 수 있습니다. 오류내역 이슈 보내주시면 개선하는데에 큰 도움이 됩니다.

## FAQ
>- 등록을 했는데 작동을 안해요.
- 앱 구조상 등록한 다음날부터 작동이 시작됩니다. 만약 설정한 시간이 같은날이라면, 다음날 자동출첵이 실행되는지 다시 확인해주세요.
- 위와 같은 경우가 아닐 경우, <b><자동출첵 사용></b> 버튼이 활성화 되어있지 않거나, 등록 오류가 발생했을 수 있습니다. <b><자동출첵 사용></b> 버튼을 껐다 켜 재등록 해 주세요.

>- HoyoLab 비밀번호를 바꿨더니 연동이 풀렸어요. / 앱을 재설치했는데 토큰 자동입력 메시지가 뜨지 않아요.
- 앱 데이터를 초기화하고 토큰 불러오기를 다시 시도해주세요.
- \[설정\] -> \[애플리케이션\] -> \[원신 자동출석체크\] -> \[저장공간\] -> \[데이터 삭제\] 에서 데이터를 초기화할 수 있습니다.

>- 소셜 로그인 (Google, Apple ID, Facebook, Twitter)이 작동하지 않아요.
- 해당 문제는 v4.1.2 에서 패치되었습니다. 4.1.2 이후 버전을 이용해주세요.

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
- [Schedule.java](https://github.com/AuroraCaelum/GenshinAuto/blob/main/app/src/main/java/arca/dev/genshinauto/Schedule.java) - Automatic/Manual Check-In
- [WebViewActivity.java](https://github.com/AuroraCaelum/GenshinAuto/blob/main/app/src/main/java/arca/dev/genshinauto/WebViewActivity.java) - Token value parse
- View all logics - [app/src/main/java/arca/dev/genshinauto](https://github.com/AuroraCaelum/GenshinAuto/tree/main/app/src/main/java/arca/dev/genshinauto)

## About issue
- If you found some bugs, please post an issues.

## FAQ
>- I enabled the service, but it doesn't works.
- Due to the structure of the app, the operation starts the day after enabling. If the set time is the same day, please check again if the automatic attendance check is executed the next day.
- Otherwise, the <b><Enable Service></b> button may not be activated or a registration error may have occurred. Please re-register by turning off and turning off the <b><Enable Service></b> button.

>- I changed the HoyoLab password and the account is unlinked. / I reinstalled the app, but the token auto input message does not appear.
- Please clear the app data and try to load the token again.
- You can clear the data in \[Settings\] -> \[Apps\] -> \[GenshinAuto\] -> \[Storage\] -> \[Clear data\]

>- Social Login (Google, Apple ID, Facebook, Twitter) doesn't works.
- The problem is patched on v4.1.2. Please use the latest version (>4.1.2).

<br></br>

## Special Thanks to<br>
Kim Yuhwan (Code Review)<br>
Kang Jiyoon &nbsp;|&nbsp; Ma Joyeong &nbsp;|&nbsp; Sean Kim (Test)
