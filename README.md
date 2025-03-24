# 📝 JUDO Backend

JUDO의 백엔드 서비스는 **Spring Boot**와 **Kotlin**을 활용하여 개발되었습니다.  
JWT 인증, MySQL 데이터베이스 연동, RESTful API 제공 기능을 제공합니다.  
본 프로젝트는 주류 추천 시스템의 핵심 로직을 구현하는 백엔드 서비스입니다.

---

## 🌟 주요 기능

✅ **JWT 인증 (JWT Authentication)**: 사용자 인증을 위한 JSON Web Token (JWT)을 사용하여 보안성 있는 인증 시스템 구현  
✅ **MySQL 연동 (MySQL Integration)**: MySQL 데이터베이스와 연동하여 데이터 저장 및 관리  
✅ **RESTful API**: 다양한 API 엔드포인트를 통해 프론트엔드와 데이터를 주고받는 RESTful API 제공  
✅ **Thymeleaf 템플릿**: 서버 측 HTML 렌더링을 위한 Thymeleaf 템플릿 사용

---

## 👨‍💻 개발자 소개

<div align="center">
  <img src="https://avatars.githubusercontent.com/u/67574367?s=150&v=4" alt="조승빈" width="150">
  <br>
  <strong>조승빈</strong>
  <br>
  Backend 개발
  <br>
  🔗 <a href="https://github.com/vkflco08">GitHub 프로필</a>
</div>

---

## 🚀 프로젝트 개요
- **프레임워크**: Spring Boot (Kotlin)
- **백엔드 개발 환경**: `Spring Boot 3.3.4`, `Kotlin 1.9.25`, `Java 17`
- **데이터베이스**: MySQL
- **배포 환경**: 개인 리눅스 서버
- **프론트엔드 GitHub**: [FE GitHub Repository](https://github.com/alco-lic/judo-FE)

---

## 📦 주요 라이브러리

| 라이브러리                          | 버전       | 설명                               |
|------------------------------------|------------|------------------------------------|
| `spring-boot-starter-web`          | 3.3.4      | Spring Boot 웹 애플리케이션 스타터 |
| `spring-boot-starter-security`     | 3.3.4      | 보안을 위한 Spring Security      |
| `spring-boot-starter-data-jpa`     | 3.3.4      | JPA를 사용한 데이터베이스 연동    |
| `spring-boot-starter-thymeleaf`    | 3.3.4      | Thymeleaf 템플릿 엔진             |
| `jjwt-api`                         | 0.11.5     | JWT 인증을 위한 라이브러리        |
| `mysql-connector-j`                | 8.0.29     | MySQL 데이터베이스 연결 라이브러리  |
| `spring-boot-starter-validation`   | 3.3.4      | 데이터 검증을 위한 라이브러리      |
| `lombok`                           | 1.18.24    | Lombok을 사용한 코드 간결화       |

---

## 🚀 배포 방식
- **GitHub 푸시**: 코드 변경 사항을 GitHub에 푸시하면 Webhook이 작동합니다.
- **Jenkins 실행**: 개인 리눅스 서버의 Jenkins가 Webhook을 통해 빌드를 트리거합니다.
- **Spring Boot 컨테이너 배포**: Jenkins 스크립트에서 Spring Boot 컨테이너를 생성하여 최신 빌드를 배포합니다.

---

## 📞 연락처
- **Email**: [vkflco8080@gmail.com](mailto:vkflco8080@gmail.com)
