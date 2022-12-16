<img src="https://user-images.githubusercontent.com/65756225/208087196-0b24f1b3-143a-4f6e-93c4-8feb1cff57df.png">

# 📍 프로젝트 소개
> **오프라인 장보기 도우미 서비스 "가물"**
- 동국대학교 2022 캡스톤디자인2, 5팀 가물가물
- 프로젝트기간 : 2022.09.15 ~ 2022.12.14 (3개월)

- 오프라인 농수산물 등의 신선식품의 물가에 어두운 사회초년생을 위한 물가 정보 제공
  - 상품 사진을 찍으면 최근 물가 동향을 그래프로 제공!
  - 마트를 선택하면 인근 마트와 함께 최신 가격 정보를 테이블로 제공!

- [~~Demo link~~](gamul.shop) (😢현재 배포 중단😢)
---
# 📄 아키텍쳐 다이어그램
<img src="https://user-images.githubusercontent.com/65756225/208094525-620749b4-add0-417a-97a5-363c91b0a619.jpg">

- 사용한 Open API : [서울시 생필품 농수축산물 가격 정보](https://data.seoul.go.kr/dataList/OA-1170/S/1/datasetView.do)

---
# 📚 개발 환경

> ## Spring Stack <br>
> ![img](https://img.shields.io/badge/java-11-orange)
> ![img](https://img.shields.io/badge/springboot-2.7.4-green)
> ![img](https://img.shields.io/badge/postgresql-42.5.1-blue)

> ## 전체 프로젝트 Stack <br>
> <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> 
> <img src="https://img.shields.io/badge/react-61DAFB?style=for-the-badge&logo=react&logoColor=black"> 
> <img src="https://img.shields.io/badge/node.js-339933?style=for-the-badge&logo=Node.js&logoColor=white">
> <img src="https://img.shields.io/badge/redux-764ABC?style=for-the-badge&logo=redux&logoColor=black"> 
> <br>
> <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
> <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> 
> <img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">
> <img src="https://img.shields.io/badge/junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white">
> <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
> <img src="https://img.shields.io/badge/postgresql-4169E1?style=for-the-badge&logo=postgresql&logoColor=white">
> <br>
> <img src="https://img.shields.io/badge/python-3776AB?style=for-the-badge&logo=python&logoColor=white">
> <img src="https://img.shields.io/badge/django-092E20?style=for-the-badge&logo=django&logoColor=white">
> <img src="https://img.shields.io/badge/yolo-00FFFF?style=for-the-badge&logo=yolo&logoColor=white">
> <br>
> <img src="https://img.shields.io/badge/amazonaws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white">
> <img src="https://img.shields.io/badge/nginx-009639?style=for-the-badge&logo=nginx&logoColor=white">
> <img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
---
# 🔍 사용 방법

| 회원가입 | 로그인 | 북마크 기능| 상품 물가 조회| 마트 물가 조회 |
|:---:|:---:|:---:|:---:|:---:|
|<img src="https://user-images.githubusercontent.com/65756225/208087539-f3530b10-fe68-4b51-b20c-a818ae7f84e8.gif" width=200px>|<img src="https://user-images.githubusercontent.com/65756225/208087524-413588e1-68e7-4615-925a-cfb3dbdb8870.gif" width=200px>|<img src="https://user-images.githubusercontent.com/65756225/208087535-edca0d61-5d22-4a81-9ac3-14d719001ddd.gif" width=200px>|<img src="https://user-images.githubusercontent.com/65756225/208087537-3d93c0c0-9fd4-443a-bced-4cbd15e843a3.gif" width=200px>|<img src="https://user-images.githubusercontent.com/65756225/208087531-6e559c8e-ff99-4e70-b68e-186d6911c5f9.gif" width=200px>|
|.|.|북마크에 마트를 추가한 뒤에 기준이 되는 시장 or 마트 설정해야 다른 기능 사용 가능!| 상품을 촬영한 뒤에 설정했던 시장 or 마트의 물가 동향을 그래프 제공!| 설정한 마트와 가까운 두 곳의 시장 or 마트의 최신 가격들을 테이블로 제공!|

---
# ⚙️ Build.gradle
```gradle
plugins {
	id 'org.springframework.boot' version '2.7.4'
	id 'io.spring.dependency-management' version '1.0.14.RELEASE'
	id 'java'
}

group = 'com.gamul'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '11'

configurations {
	compileOnly {
		extendsFrom annotationProcessor
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-security'
	implementation 'org.springframework.boot:spring-boot-starter-validation'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'

	compileOnly 'org.projectlombok:lombok'
	developmentOnly 'org.springframework.boot:spring-boot-devtools'

	runtimeOnly 'org.postgresql:postgresql'
	implementation group: 'org.postgresql', name: 'postgresql', version: '42.5.1'

	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testImplementation 'org.springframework.security:spring-security-test'

	//jwt
	implementation 'io.jsonwebtoken:jjwt-api:0.11.5'
	implementation 'io.jsonwebtoken:jjwt-impl:0.11.5'
	implementation 'io.jsonwebtoken:jjwt-jackson:0.11.5'

	//json
	implementation 'com.googlecode.json-simple:json-simple:1.1.1'
}

tasks.named('test') {
	useJUnitPlatform()
}
```
---
# 🛠️ API specification
[API specification](https://shynnn.notion.site/a12ae0a2071241e0a1a606b32ca9612a?v=8fe37c2453d947639765419575f97c54)

---
# 👥 멤버
| 신예진 | 신현철 | 이지영 | 김정현 |
|:---:|:---------:|:---:|:---:|
| <img src="https://user-images.githubusercontent.com/65756225/208081193-b340e86a-eb8e-431c-a4d1-0716b368d01f.png" width="200px" /> | <img src="https://user-images.githubusercontent.com/65756225/208077417-befedafa-7bc9-475d-88d0-edfd8ceb6de3.jpeg" width="200px" /> | <img src="https://user-images.githubusercontent.com/65756225/208081475-0b5e5188-bef9-4ace-9b02-48360988f57f.png" width="200px" /> | <img src="https://user-images.githubusercontent.com/65756225/208081903-7d75816e-acde-48a4-b476-645e28f2dba4.png" width="200px" /> |
|[shyjnnn](https://github.com/shyjinnn)|[moonn6pence](https://github.com/moonn6pence) | [jiyoungzero](https://github.com/jiyoungzero) |[CaliSeoul](https://github.com/CaliSeoul)|
| *React* | *SpringBoot*, *PostgreSQL* | *Django* | *AI(YOLOv3)* |
