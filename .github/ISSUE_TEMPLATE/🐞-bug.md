name: "Bug Report"
description: "버그 보고 또는 문제 상황"
title: "[Bug] "

body:
  - type: textarea
    id: "problem_description"
    attributes:
      label: "📄 문제 설명"
      placeholder: |
        문제가 발생한 상황을 자세히 설명해 주세요.
        예) 사용자 인증 API 호출 시 500 Internal Server Error가 발생합니다.

  - type: textarea
    id: "reproduction_steps"
    attributes:
      label: "🔄 재현 방법"
      placeholder: |
        문제가 발생하는 과정을 단계별로 작성해 주세요.
        예)
        1. /api/auth/login API를 호출
        2. 유효한 사용자 이메일과 비밀번호를 전송
        3. 서버에서 500 Internal Server Error 반환

  - type: textarea
    id: "actual_result"
    attributes:
      label: "❌ 실제 결과"
      placeholder: |
        문제가 발생했을 때 나타나는 결과를 작성해 주세요.
        예) 인증 요청 시 500 에러가 발생하며 로그에 “Database Connection Timeout” 메시지가 출력됩니다.

  - type: textarea
    id: "expected_result"
    attributes:
      label: "✅ 기대 결과"
      placeholder: |
        정상적으로 동작했을 때 기대했던 결과를 작성해 주세요.
        예) 로그인 성공 시 JWT 토큰과 사용자 정보가 반환됩니다.

  - type: input
    id: "environment"
    attributes:
      label: "💻 발생 환경"
      placeholder: |
        문제가 발생한 환경 (OS, 브라우저 등)을 작성해 주세요.
        예) Ubuntu 22.04, Java 17, Spring Boot 3.1, MySQL 8.0
