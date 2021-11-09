# GenshinAuto for Android
- 호요랩 원신 출석체크를 자동으로 진행해주는 안드로이드 앱입니다.

## 사용법
- 앱 초기 실행 시 토큰을 등록해야합니다. 토큰 등록은 자동 방식과 수동 방식이 있으며, 편한 방법을 골라 이용하실 수 있습니다.
- 토큰 등록 후, 정상적으로 등록되었는지 확인하기 위해 수동 출첵 진행을 권장합니다.
- 수동 출석체크시 푸시 내용이 다음과 같으면 정상입니다.
```
출석체크 성공! (출석체크 완료 시)
여행자, 이미 출석체크했어~ (당일 출석체크가 되어있을 경우)
```
푸시 내용이 다음과 같으면 토큰이 정상적으로 등록되지 않은 경우입니다.
```
Not logged in (토큰 오류로 로그인에 실패 한 경우)
```
- 자동 출석체크는 중국 표준시 (GMT+8/PRC) 기준으로 00시 00분 30초에 작동합니다.

## 민감한 정보 이용
- 이 앱은 사용자의 개인정보 (토큰, UID 등)을 호요랩 이외 외부 서버로 전달하지 않습니다.
- 토큰과 UID를 이용한 모든 작업은 클라이언트(사용자의 기기)에서만 이루어집니다.

### 토큰 데이터 처리 코드
- [Schedule.java](https://github.com/dev-by-david/GenshinAuto/blob/main/app/src/main/java/arca/dev/genshinauto/Schedule.java) - 자동/수동 출석체크 관련 코드
- [WebViewActivity.java](https://github.com/dev-by-david/GenshinAuto/blob/main/app/src/main/java/arca/dev/genshinauto/WebViewActivity.java) - 토큰 자동등록 관련 코드
- 전체 로직 보기 - [app/src/main/java/arca/dev/genshinauto](https://github.com/dev-by-david/GenshinAuto/tree/main/app/src/main/java/arca/dev/genshinauto)
