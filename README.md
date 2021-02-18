# 더이상 사용되지 않는 버전입니다. [이곳](https://github.com/P-JungMin/NCafeVerify)에서 새로운 버전을 이용해주세요.


# NCafeVerify2

플레이어가 카페에 가입했는지 확인을 해주는 마인크래프트 플러그인입니다.

### Commands

> `/카페인증`: 인증 단계를 시작했을 경우 게시글 확인을 진행하고 아닌 경우 도움말을 표시합니다.
>
> ##### 관리자 전용 명령어
>
> `/인증관리 확인 <닉네임>`: 닉네임에 해당하는 플레이어의 인증 시각, 네이버 아이디를 확인할 수 있습니다.
>
> `/인증관리 리로드`: config.yml파일을 다시 불러옵니다.

### config.yml

> * `cafe-url`: 사용할 카페의 주소를 입력. `(ex. https://cafe.naver.com/cafeid)`
> * `privacy-message`: 처음 1회만 보이는 개인정보 활용 경고 메시지입니다. 꼭 기본과 비슷한 형식으로 작성해주셔야 혹시라도 생길 불이익을 방지하는데 도움이 될 것입니다.
> * `already-complete`: 이미 인증을 완료한 플레이어가 명령어를 사용 시 보이는 메시지.
> * `already-process`: 인증코드를 발급받은 상태에서 명령어를 사용 시 보이는 메시지. `{code}` 부분에 발급받은 코드가 들어감.
> * `processing`: 인증 게시글을 작성하고 확인 시에 보이는 메시지.
> * `success`: 인증에 성공했을 때 뜨는 메시지
> * `fail`: 인증에 실패했을 때 뜨는 메시지
> * `help`: 도움말
> * `permission`: 일반 유저가 `/인증관리` 명령어 사용 시 뜨는 메시지
> * `start`: 인증 시작 시 뜨는 메시지. `already-process`와 마찬가지로 `{code}`사용 가능

### API

> #### 플러그인
>
> * `VerifySuccessEvent`: 인증에 성공했을 때 발생되는 이벤트.
> * `NCafeVerifyAPI.isVerified(Player)`: 플레이어의 인증 여부를 반환 (boolean)
> * `NCafeVerifyAPI.getNaverID(Player)`: 플레이어의 네이버 아이디를 반환. 만약플레이어의 인증이 완료되지 않았다면 null 반환 (String)

> #### Skript
>
> * `on verify success`: 인증에 성공했을 때 발생되는 이벤트.
> * `%player% verified`: 플레이어의 인증 여부를 반환.
> * `%player%'s naverid`: 플레이어의 네이버 아이디를 반환. (인증이 완료되지 않을 플레이어일 시 오류발생)

### 오류신고 및 문의

> #### 디스코드:  `PIGLAND#7552`

### 라이선스

> **2020 &copy; Pic_E(P-JungMin) All rights reserved.**
>
> 이 플러그인은 [CC BY-NC-ND 4.0](https://creativecommons.org/licenses/by-nc-nd/4.0/)을 따름

> ![CC](https://upload.wikimedia.org/wikipedia/commons/f/f1/Cc-by-nc-nd_icon.svg)
