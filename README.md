# 📄 Spring Boot + Redis 조회수 성능 개선 프로젝트
#### Notion : 
https://curious-cinema-a83.notion.site/Spring-Boot-Redis-320d9939f2898057a432ca8b862d76b8?source=copy_link

***
### ✋🏻 문제 정의
기존 조회수 시스템은 DB 기반으로 다음과 같이 동작한다.
~~~
조회수 증가 요청 → DB 조회 → +1 업데이트 → Commit
~~~
하지만 인기 있는 장소의 경우, 다수의 사용자가 동시에 요청을 보내게 된다.
***
### ⚠️ 문제 상황
#### 1. DB 부하 증가 <br/>
1000명이 동시에 요청할 경우:
~~~
DB connection 획득
→ 조회
→ +1 update
→ commit
→ connection 반환
~~~
이 과정이 1000번 반복됨 <br/>
→ DB I/O 증가, 응답 지연 발생
<br/><br/>
#### 2. 동시성 문제 (Lost Update)
~~~
T1: 조회수 10 읽음
T2: 조회수 10 읽음

T1: 11 저장
T2: 11 저장  ← 덮어쓰기
~~~
→ 최종 결과: 12가 아닌 11

***

### 🚀 해결 전략: Redis 도입
* 핵심 아이디어
  * 조회수 증가 → Redis에서 처리
  * DB 반영 → Scheduler로 주기적 처리
<br/><br/>
* Redis 사용 이유 <br/>
Redis는 다음 특징을 가진다:
  * 메모리 기반 → 빠른 처리
  * INCR 연산 → Atomic 보장
  * 시간복잡도 → O(1)
  ~~~
  INCR place:view:{id}
  ~~~
    → 동시 요청에도 안전하게 증가

***

### ⚙️ 시스템 구조
~~~
[사용자 요청]
   ↓
Redis INCR
   ↓
(Scheduler)
   ↓
DB 반영
~~~

***

### 📊 성능 테스트: k6
#### 1️⃣ 1차 결과 → 비효율 
* DB.txt
    ~~~
    HTTP
    http_req_duration..............: avg=55.11ms min=1.06ms med=37.34ms max=1.41s p(90)=125.05ms p(95)=173.02ms
      { expected_response:true }...: avg=55.11ms min=1.06ms med=37.34ms max=1.41s p(90)=125.05ms p(95)=173.02ms
    http_req_failed................: 0.00%  0 out of 54371
    http_reqs......................: 54371  1810.470742/s
    ~~~
* Redis.txt
  ~~~
   HTTP
    http_req_duration..............: avg=76.58ms min=1.56ms med=60.4ms  max=731.75ms p(90)=159.74ms p(95)=210.75ms
      { expected_response:true }...: avg=76.58ms min=1.56ms med=60.4ms  max=731.75ms p(90)=159.74ms p(95)=210.75ms
    http_req_failed................: 0.00%  0 out of 39144
    http_reqs......................: 39144  1302.687085/s
  ~~~
→ DB가 더 빠르다. 
<br/><br/>
#### 🔍 원인 분석
~~~
DB existsById() + Redis INCR
~~~
Redis를 사용했지만 <br/>
→ 매 요청마다 DB 조회 발생

결국: <br/>
DB + Redis 둘 다 사용함으로써 성능 저하 ↓

<br/><br/>

#### 2️⃣ 2차 결과 (개선 후)
* DB.txt
    ~~~
    HTTP
        http_req_duration..............: avg=23.19ms min=372µs   med=14.35ms max=458.95ms p(90)=51.04ms p(95)=79.08ms
          { expected_response:true }...: avg=23.19ms min=372µs   med=14.35ms max=458.95ms p(90)=51.04ms p(95)=79.08ms
        http_req_failed................: 0.00%  0 out of 129122
        http_reqs......................: 129122 4302.541129/s
    ~~~
* Redis.txt
  ~~~
  HTTP
    http_req_duration..............: avg=9.49ms min=274µs    med=8.23ms max=266.55ms p(90)=18.25ms p(95)=23.4ms 
      { expected_response:true }...: avg=9.49ms min=274µs    med=8.23ms max=266.55ms p(90)=18.25ms p(95)=23.4ms 
    http_req_failed................: 0.00%  0 out of 314556
    http_reqs......................: 314556 10482.932891/s
  ~~~
→ Redis가 압도적으로 빠른 것을 확인해 볼 수 있다. 

***

### 📖 배운점
* Redis의 Atomic 연산의 중요성 
* DB vs Redis의 역활 차이 
* k6 기반 성능 테스트 경험 

***
