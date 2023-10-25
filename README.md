# Myongji-University-Android-Project-YongsikBrother
명지대학교 객체지향언어 강의에서 기말 프로젝트로 제작한 어플

프로젝트명 : SQ_Game_Select
소스 경로 : src/main/java/org/app/sq_game_selection


[Splashscreen]
onCreate 생명주기 시 앱의 로딩화면을 띄우고 Task 를 Clear 한다.
1초 후 SQGameselect(메인화면)으로 전환된다.

[User.java]
사용하지 않음 

[game000.java] 게임을 선정하는 화면 구성

[game005.java] - 모의 티켓팅 게임
방장의 기기에서 시작 버튼을 누르면 카운트다운이 시작되며, 10 초 안에 같은 앱에 접속한 플레이어들이 버튼을 연타하여
가장 먼저 Firebase DB에 기록을 남기는 사람이 1위부터 순번대로 순위가 기록된다.

[game006.java] - 배열을 활용한 윷놀이 게임
미구현

[music_MainActivity.java / music_ResActivity.java] - 재생되는 음악 맞추기 게임 (박세미님 제작)

[penaltygame.java] - 패한 팀 또는 개인을 선택하고 벌칙을 선정해 주는 화면 구성

[picpic_MainActivity.java / picpic_ResultActivity.java] - 사진의 인물 맞추기 게임 (조성진님 제작)

[sqgameselect.java] - 게임의 메인화면 메뉴와 일부 주요기능 구성
팀 구성
설정
팀 점수 조정
팀 점수 확인 등

[word_MainActivity.java / word_SubActivity.java] - 사자성어 이어맞추기 게임 (양동근님 제작)
