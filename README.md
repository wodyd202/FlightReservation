# <a href="https://wodyd202.github.io/FlightReservation/flightReservation.html">FlightReservation(API)</a>
항공 예약 시스템을 DDD를 적용해 제작한 프로젝트
<br><br>


## Tech
- Spring Boot2
- Junit5
- JPA, Querydsl
- H2
- DDD, TDD
- jacoco

<a href="https://coveralls.io/jobs/91758654"><img src="https://s3.amazonaws.com/assets.coveralls.io/badges/coveralls_87.svg" alt="Coverage Status" /></a>
<br/><br/>

## APIs
회원<br/>
  ㄴ 회원 등록<br/>
  ㄴ 회원 수정<br/>
	ㄴ 이메일 수정<br/>
	ㄴ 여권번호 수정<br/>
	ㄴ 비밀번호 수정<br/>
  ㄴ 회원 탈퇴<br/>
  ㄴ 회원 정보 조회<br/>

항공기<br/>
  ㄴ 항공기 정보 조회<br/>

운항 정보<br/>
  ㄴ 운항 정보 상세 조회<br/>
  ㄴ 운항 정보 리스트 조회<br/>
	ㄴ 특정 지역으로 가는 운항 정보<br/>
	ㄴ 특정 일자에 도착하는 운항 정보<br/>
	ㄴ 특정 일자에 출발하는 운항 정보<br/>
	ㄴ 특정 일자에 출발해서 특정 일자에 도착하는 운항 정보<br/>
	ㄴ 지난 운항 정보 조회<br/>

예약<br/>
  ㄴ 예약<br/>
  ㄴ 예약 상세 정보 조회<br/>
  ㄴ 아직 출발하지 않은 예약 리스트 조회<br/>
  ㄴ 지난 예약 리스트 조회<br/>
  ㄴ 특정 지역으로 간 예약 리스트 조회<br/>
  ㄴ 해당 운항 정보에 대한 이미 예약된 좌석 리스트 조회<br/>

## Install
```sh
git clone https://github.com/wodyd202/FlightReservation.git
mvn clean spring-boot:run
```
