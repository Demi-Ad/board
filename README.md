# Spring Boot CRUD BOARD


## Getting Started / 어떻게 시작하나요?

> ### [BoardApplication.class](/src/main/java/com/study/board/BoardApplication.java) 
> RUN Main Class

---
### Prerequisites / 선행 조건

```
JAVA 11
```
---
### Build
Location = [gradlew](/gradlew)
```shell
gradelw build
```
> ### args = {-x test} 테스트 생략
> ### output = /build/libs/*.jar

---

### 참고 사항
> ### [application-mysql.yml](src/main/resources/application-mysql.yml)
> ### mysql 사용시 해당 프로필 이용
> ### 기본값 : 메모리 H2 데이터베이스
```shell 
java -jar -Dspring.profiles.active={profile} *.jar
```

