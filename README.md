# Forrrest AppManagementService 기획서

## 1. 모듈 개요

* **모듈명**: forrrest-appmanagementservice
* **주요 역할**: 외부·내부 애플리케이션을 위한 Nonce 토큰 등록 및 인증
* **형태**: 독립 실행형 마이크로서비스

## 2. 현재 구현 기능

* **Client CRUD**

  * `POST /apps`: 애플리케이션 등록
  * `GET /apps/{id}`: 상세 조회
  * `PUT /apps/{id}`: 정보 수정
  * `DELETE /apps/{id}`: 삭제
* **Client ID & Secret 관리**: 생성 및 안전한 저장
* **Swagger UI**: API 문서 자동 생성 및 확인

## 3. 향후 구현 예정 기능

* **Scopes 관리**: 리소스별 권한 정의 및 할당
* **Secret 자동 회전**: 주기별·수동 재생성, Grace Period 지원
* **OAuth2 Grant 지원**: Authorization Code, PKCE, Device Code
* **Token Introspection Endpoint**: 토큰 상태 및 클레임 조회
* **트래픽 모니터링**: 요청량·오류율·레이턴시 지표 수집
* **승인 워크플로우**: 클라이언트 등록 승인·거절 프로세스
* **Webhooks/Callbacks**: 상태 변경 이벤트 알림
* **보안 강화**: IP 화이트리스트, Secret 노출 감지

## 4. 서비스 설명

Forrrest AppManagementService는 외부 및 내부 애플리케이션이 Forrrest API를 안전하게 호출할 수 있도록 JWT 토큰 Nonce scope 기반 인증·인가 기능을 제공합니다. 클라이언트 등록, Secret 발급·회전, 권한 관리, 이벤트 알림 등 전체 라이프사이클을 일관되게 지원합니다.

## 5. 목표

* **완전한 OAuth2 클라이언트 관리** 제공
* **Secret 회전 자동화**로 운영 부담 최소화
* **세분화된 권한 모델**로 세밀한 접근 제어
* **실시간 모니터링**을 통한 SLA 준수

## 6. 핵심 기능 및 우선순위

| 우선순위 | 기능                  | 설명                                    |
| :--: | ------------------- | ------------------------------------- |
|   1  | Client CRUD         | 클라이언트 등록, 조회, 수정, 삭제                  |
|   2  | Secret 자동 회전 PoC    | Secret 교체 및 Grace Period 지원           |
|   3  | Scopes 관리           | 리소스별 권한 정의 및 할당                       |
|   4  | OAuth2 Grant 지원     | Authorization Code, PKCE, Device Code |
|   5  | 트래픽 모니터링            | 요청량·오류율·레이턴시 지표 수집                    |
|   6  | 승인 워크플로우 UI         | 등록 승인/거절 처리 인터페이스 제공                  |
|   7  | Webhooks/Callbacks  | 상태 변경 이벤트 알림                          |
|   8  | 보안 기능               | IP 화이트리스트, Secret 노출 감지               |
|   9  | Token Introspection | JWT 토큰 상태 및 클레임 조회                    |

---
