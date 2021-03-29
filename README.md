# 리뉴올 PC

## 서버 배포 방법
### 1. Gradle Build
### 1-1. intellij
Gradle build --exclude-task test 실행하거나
CMD에서 `gradlew` 명령으로 빌드한다.

```gradle
$ ./gradlew build --exclude-task test
```

### 1-2. eclipse
- Gradle Tasks > saleson > build > build 실행

### 2. 파일 업로드
원격데스크톱으로 연결하거나 SSH로 서버에 접속하여 deploy폴더에 빌드된 파일을 업로드 한다.

#### 빌드된 파일
- Static : ~/build/distributions/saleson-static.zip
- War : ~/saleson-web/build/libs/saleson-web-3.13.0.war

#### 배포 폴더
- G:/home/saleson/deploy

### 3. 배포 실행
원격데스크톱 접속 후 배포 폴더에 deploy.bat 파일을 실행한다. 

