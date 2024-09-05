# 온라인 배달 주문 프로그램

## Stacks
### Environment
<img src="https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=flat-square&logo=intellij-idea&logoColor=white"/> <img src="https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=GitHub&logoColor=ffffff"/> <img src="https://img.shields.io/badge/Git-F05032?style=flat-square&logo=Git&logoColor=ffffff"/>

### Development
<img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=flat-square&logo=openjdk&logoColor=white"/> <img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=flat-square&logo=spring&logoColor=white"/><img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=flat-square&logo=docker&logoColor=white"/> <img src="https://img.shields.io/badge/-Swagger-%23Clojure?style=flat-square&logo=swagger&logoColor=white"/> <img src="https://img.shields.io/badge/Rabbitmq-FF6600?style=flat-square&logo=rabbitmq&logoColor=white"/>
<img src=""/>

### DataBase
<img src="https://img.shields.io/badge/mysql-4479A1.svg?style=flat-square&logo=mysql&logoColor=white"/> <img src="https://img.shields.io/badge/redis-%23DD0031.svg?style=flat-square&logo=redis&logoColor=white"/>

------------
## 📌 주요 기능
### ⭐️ Main 서버
- 사용자 회원 가입
- 가게 및 메뉴 검색 (별점 순으로 정렬)
- 사용자 주문
- 사용자의 주문 내역 조회 기능 (현재 진행 중/배달 완료/특정 주문 건)
- 유저의 리뷰 (등록/조회/삭제/업데이트)

### ⭐️ Store 서버

- 가게 직원의 회원가입
- 가게 및 메뉴 등록 
- 사용자 주문 시, Push 알람 
- 주문 상태 변경 (주문 완료/주문 수락/조리 시작/배달 시작/배달 완료)
- 유저 리뷰의 사장님 댓글 (등록/조회/삭제/업데이트)

### ⭐️ Take-Out 서버

- 유저가 입력한 위치 기반으로 아래의 정보 제공 
> - 가까운 15개의 식당 이름 및 거리
> - 식당 길 안내 및 로드 뷰 URL

### ⭐️ Account 서버

- 유저/가게 직원의 로그인 및 토큰 검증
- JWT Access/Refresh 토큰 생성 및 재 발급

### ⭐️ API Gateway

- 유저/가게 직원의 요청에 따라 인증이 필요한 엔드 포인트의 경우 Account 서버를 거쳐 Main/Take-Out/Store 서버로 이동 


------------
## ✏️ 핵심 아키텍처 및 다이어그램
### 🖥  시스템 설계 아키텍처
- DB 및 각 서버에서 공통으로 사용되는 중복 요소 및 추가되는 서버를 관리하기 위해 MSA 프로젝트 설계
- 유저 인증이 필요한 엔드 포인트는 Spring Cloud Gateway를 통해 Account 서버를 거쳐 Private Zone의 서버로 전달
- Rabbit MQ를 사용하여 Main 서버와 Store 서버의 주문 내역 및 상태 중개
- 유저의 위치 및 스토어의 Map/Road-View URL 반환을 위해 카카오 API 사용
- DB 관리를 위해 MySQL, 캐싱 관리를 위해 Redis 사용
- 운영 시스템의 가상화를 위해 Docker Container 사용
  ![sys_gate](https://github.com/user-attachments/assets/11bbd69e-8f83-44c5-813c-a32115d5e4af)
### 🖥  사용자 인증
- 유저/가게 직원의 로그인 및 인증을 위해 Spring Security 사용
- 인증을 위해 JWT 토큰을 사용하고 Account 서버를 구축해 토큰을 생성/검증/재 발급하여 인증된 유저만 Gateway를 통해 Private Zone의 서비스 이용
- Access 토큰 만료 시, Refresh 토큰으로 Access 토큰을 재 발급
- 유저 정보 반환 시, DB를 조회하는 I/O 횟수를 줄이기 위해 Redis를 사용하여 메모리 캐싱
  ![user gate](https://github.com/user-attachments/assets/32faebd8-80e6-4c8e-beef-f964f9697ce7)
### 🖥  주문 Push 알람
- 유저 주문 시, Store 서버의 주문 확인 보장을 위해 RabbitMQ를 사용하여 Consumer와 Producer 간의 주문 메시지 중개
- Consumer에서 클라이언트(가게 직원)로 단방향 통신인 SSE 방식으로 주문 내역 전달
  ![rabbit](https://github.com/user-attachments/assets/f308e0b8-67c7-4da1-bfde-4986f422735f)

### 🖥  카카오 길 안내 및 로드 뷰
- 다수의 Store Entity를 등록하기 위해 공공 데이터(원주시 식당 데이터)를 DBㅔ 저장
- 정확한 주소를 입력받기 위해 카카오 우편번호 서비스 이용
- 입력 받은 주소와 가게의 거리를 구하기 위해 Haversine Formula를 사용하여 계산
- 추출된 Store 데이터는 길 안내 및 로드 뷰 URI를 제공하며 가독성을 위해 Base62로 인코딩 후 shorten URI로 제공
  ![take-out](https://github.com/user-attachments/assets/01b76ee6-5e70-4a07-8a3b-8c8581156941)

------------
## 📋 API 명세
### 📎  Main 서버
![스크린샷 2024-09-05 185445](https://github.com/user-attachments/assets/3d5a88ec-95fc-43ed-88c4-73402c75ba7d)
### 📎  Store 서버
![스크린샷 2024-09-05 185457](https://github.com/user-attachments/assets/de195f28-38db-4f89-8959-90de891e42b8)
### 📎  Account 서버
![스크린샷 2024-09-05 185508](https://github.com/user-attachments/assets/660e806a-d2c3-44b5-9bd6-13839e692c5d)
### 📎  Take-Out 서버
![스크린샷 2024-09-05 185514](https://github.com/user-attachments/assets/6ae6a07e-8a67-4b5f-9409-36a59ae40865)

------------
## 📂 디렉토리 구조
- 전체 서비스는 멀티 모듈 형태로 구성되어 있으며 각 서비스 간의 공유되는 코드를 하나의 모듈로 만들어서 사용
  ![image](https://github.com/user-attachments/assets/0b7760c8-27a1-4399-9b08-c6402e319c7b)
------------
## ✏️ ERD
![ERD](https://github.com/user-attachments/assets/e72c2d91-50a9-45e8-af84-12713cfe9493)
____________

## 📝 느낀  점
- 프로젝트를 진행하면서 이전보다 더 구조를 고민하며 코드를 작성하려고 했고, 단순한 멀티 모듈에서 점진적으로 MSA로 적용하는 과정에서 새롭게 배워가는 것이 많았다.
- 코드와 구조가 변경하는 과정에서 처음부터 설계를 잘하고 아키텍처나 클린 코드에 대한 것을 더 숙지하여 개선해야겠다는 생각이 들었다.
