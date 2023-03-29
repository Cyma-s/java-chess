# java-chess

체스 미션 저장소

## 우아한테크코스 코드리뷰

- [온라인 코드 리뷰 과정](https://github.com/woowacourse/woowacourse-docs/blob/master/maincourse/README.md)

## 요구사항

### 도메인

- 게임
    - 킹이 잡히면 게임을 종료한다.
- 보드
    - 가로 위치는 왼쪽부터 a ~ h이고, 세로는 아래부터 위로 1 ~ 8로 구현한다.
    - 말을 진영에 맞게 배치한다.
    - 흰색 진영의 말부터 움직일 수 있다.
- 말
    - 폰, 룩, 나이트, 비숍, 퀸, 킹을 갖는다.
    - 말은 검은색 혹은 흰색 진영이다.
    - 폰: 처음 이동은 앞으로 1,2칸 이동할 수 있다. 공격할 때는 대각선으로 이동할 수 있다.
    - 룩: 상하좌우 직선으로 이동할 수 있다.
    - 나이트: 정해진 8방향으로 이동할 수 있다. 가는 길에 기물이 있어도 뛰어 넘어 이동할 수 있다.
    - 비숍: 대각선으로 이동할 수 있다.
    - 퀸: 상하좌우, 대각선으로 이동할 수 있다.
    - 킹: 상하좌우, 대각선으로 이동할 수 있다. 킹이 이동할 수 있는 길이는 1이다.
- 진영
    - 검은색 진영과 흰색 진영이 존재한다.
    - 폰 8개, 룩 2개, 나이트 2개, 비숍 2개, 퀸 1개, 킹 1개가 같은 진영이 된다.
    - 상대편 킹이 잡히면 이긴다.
- 점수
    - 퀸 9점, 룩 5점, 비숍 3점, 나이트 2.5점, 폰 1점, 킹은 0점이다.
    - 같은 세로줄에 같은 색의 폰이 있는 경우 0.5점으로 계산한다.
    - 각 팀의 점수는 따로 계산한다.

### 입력

- 실행 시, `start`를 입력 받는다.
- 이동 시, `move source위치 target위치`를 입력 받는다.
- 점수, 결과를 볼 시, `status`를 입력 받는다.
- 종료 시, `end`를 입력 받는다.

### 출력

- 프로그램 시작 시, 게임 시작에 대한 메세지를 출력한다.
- `start`를 입력 받으면, 보드판을 출력한다.
- 폰은 p, 룩은 r, 나이트는 n, 비숍은 b, 퀸은 q, 킹은 k로 표시한다.
- 검은색 진영은 대문자, 흰색 진영은 소문자로 구분한다.
- 빈 공간은 . 으로 표현한다.

## DB

### 설계

```sql
create table chess.game
(
    id       int auto_increment
        primary key,
    finished tinyint(1) default 0 not null
);
```

```sql
create table chess.history
(
    source  varchar(3) null,
    target  varchar(3) not null,
    id      int auto_increment
        primary key,
    game_id int null,
    constraint game_id
        foreign key (game_id) references chess.game (id)
);
```

### 요구사항

- 이전 게임이 존재하면 게임을 불러온다.
- 말이 이동할 때마다
- `clear` 를 입력하면 진행 중인 게임을 초기화한다.
- 킹이 죽으면 게임을 종료한 상태로 변경한다. 
