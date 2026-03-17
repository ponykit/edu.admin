# 교육 플랫폼 관리자 시스템 (Edu Admin)

Spring Boot 기반의 교육 플랫폼 백오피스 관리 시스템입니다. 강의 콘텐츠 관리, 파일 업로드, 게시판 관리 등 교육 서비스 운영에 필요한 관리자 기능을 제공합니다.

---

## 기술 스택

| 분류 | 기술 |
|------|------|
| Language | Java 11 |
| Framework | Spring Boot 2.3.3 |
| Security | Spring Security 5, BCrypt |
| ORM | MyBatis 2.1.3 |
| Database | Microsoft SQL Server |
| Template | Thymeleaf |
| Build | Gradle 6.4.1 |
| Logging | Log4jdbc, Logback |
| Etc | Lombok, PageHelper, jcodec, FFmpeg |

---

## 주요 기능

### 강의 관리
- 강의 등록 / 수정 (MERGE 방식)
- 강의 목록 조회 (날짜·제목 필터, 페이지네이션)
- 강의 상세 및 단원(Unit) 관리
- 강의 노출 여부(표시/숨김) 설정
- 카테고리·배지·가격 설정

### 파일 관리
- 이미지·동영상 파일 업로드
- 동영상 썸네일 자동 생성 (jcodec 프레임 추출)
- 이미지 리사이징 및 Base64 인코딩
- 임시 파일 → 실제 파일 이관 처리
- 파일 미리보기 및 다운로드 API

### 게시판(BBS) 관리
- 게시글 목록 조회 (날짜·제목 필터, 페이지네이션)
- 게시글 등록 / 수정 (저장 프로시저 연동)
- 계층형 댓글 구조 지원

### 인증 및 권한
- Spring Security 기반 로그인/로그아웃
- BCrypt 패스워드 암호화
- Role 기반 접근 제어 (ADMIN 역할)
- 로그인 실패 핸들러

### 공통
- 카테고리 관리
- 공통 코드 관리
- 전역 예외 처리 (GlobalExceptionHandler)
- CORS 설정

---

## 프로젝트 구조

```
src/main/java/com/edu/admin/
├── config/                  # 설정 클래스
│   ├── security/            # Spring Security 설정
│   ├── CorsFilter.java
│   ├── DatabaseConfig.java
│   ├── GlobalExceptionHandler.java
│   └── WebConfig.java
├── controller/              # REST / MVC 컨트롤러
│   ├── RestAdminController.java   # 강의·게시판 REST API
│   ├── RestCommonController.java  # 파일 업로드/다운로드 API
│   ├── CourseController.java
│   ├── BbsController.java
│   └── MainController.java
├── service/                 # 비즈니스 로직
│   ├── AdminService.java
│   ├── CommonService.java
│   ├── FileStorageService.java
│   └── MemberService.java
├── dao/                     # MyBatis DAO 인터페이스
│   ├── AdminDao.java
│   └── CommonDao.java
├── model/                   # VO / DTO
│   ├── course/
│   ├── common/
│   └── security/
├── exception/               # 커스텀 예외
└── util/                    # 유틸리티 클래스

src/main/resources/
├── mapper/                  # MyBatis SQL Mapper
│   ├── admin.xml
│   ├── common.xml
│   └── PaginationMapper.xml
├── templates/               # Thymeleaf 템플릿
└── application.yml.example  # 설정 파일 예시
```

---

## API 엔드포인트

### 강의 API (`/api`)
| Method | URL | 설명 |
|--------|-----|------|
| POST | `/api/course/list` | 강의 목록 조회 (페이지네이션) |
| POST | `/api/course/insertUpdate` | 강의 등록/수정 |

### 게시판 API (`/api`)
| Method | URL | 설명 |
|--------|-----|------|
| POST | `/api/bbs/management/list` | 게시글 목록 조회 |
| POST | `/api/bbs/management/detail` | 게시글 상세 조회 |
| POST | `/api/bbs/management/save` | 게시글 등록/수정 |

### 파일 API (`/common`)
| Method | URL | 설명 |
|--------|-----|------|
| POST | `/common/file/upload` | 파일 업로드 |
| POST | `/common/file/delete` | 파일 삭제 |
| GET | `/common/filepreview/{fileName}` | 파일 미리보기 |
| GET | `/common/img/{fileName}` | 이미지 조회 |
| GET | `/common/filepath/{fileName}` | 파일 다운로드 |

---

## 시작하기

### 사전 요구사항
- Java 11
- Gradle 6.4.1
- Microsoft SQL Server

### 환경 설정

1. 설정 파일 예시를 복사합니다.
```bash
cp src/main/resources/application.yml.example src/main/resources/application.yml
```

2. `application.yml`에 데이터베이스 정보를 입력하거나 환경변수를 설정합니다.

```bash
export DB_URL=jdbc:sqlserver://YOUR_DB_HOST:1433;databaseName=YOUR_DB_NAME
export DB_USERNAME=your_username
export DB_PASSWORD=your_password
export UPLOAD_PATH=/path/to/upload
```

3. 데이터베이스 테이블을 생성합니다. (`src/main/java/com/edu/admin/sqlScript/` 참고)

### 빌드 및 실행

```bash
# 빌드
./gradlew build

# 실행
./gradlew bootRun

# WAR 패키징
./gradlew bootWar
```

서버 기본 포트: `http://localhost:9090`

---

## 데이터베이스 테이블

| 테이블명 | 설명 |
|----------|------|
| TB_ADMIN | 관리자 계정 |
| TB_COURSE | 강의 정보 |
| TB_COURSE_DETAIL | 강의 단원 정보 |
| TB_CATEGORY | 카테고리 |
| TB_COMMON_CODE | 공통 코드 |
| TB_FILES | 파일 정보 |
| TB_FILES_TEMP | 업로드 임시 파일 |
| TB_BOARD | 게시판 |

---

## 스크린샷

> 실제 운영 화면은 보안상 생략합니다.

---

## 라이선스

본 프로젝트는 포트폴리오 목적으로 공개되었습니다.
