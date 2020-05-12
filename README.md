# example
스프링 배치 reader, writer, process 확인용
간단 소스 입니다.

해당소스는 intellij로 작성하였습니다. 

1. spring을 실행하기전에 meta-spring batch 테이블을 생성해야합니다.
(구글에서 spring batch schema로 검색하시면 해당 생성 스크립트를 확인 하실수 있습니다.)

2. db 접속정보는 프로퍼티 파일을 참고하시면 됩니다.

3. 실행방법은 Demo4Application을 런하시면 됩니다.
(단 실행성공 시 program arguments에서 값을 아무값을 넣어서 성공마다 값을 변경해야됨)
ex)test=1 -> test=2 성공시 값을 늘리는방식으로
