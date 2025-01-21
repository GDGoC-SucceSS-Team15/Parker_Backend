---
name: "\U0001F6E0️ Refactor"
about: 리팩토링
title: "[refactor]"
labels: enhancement
assignees: ''

---

name: "🛠️ Refactor"
description: "리팩토링 작업"
labels: ["refactor"]
body:
  - type: markdown
    attributes:
      value: |
        ### 🔄 리팩토링 작업
        코드 품질 향상, 중복 코드 제거, 성능 최적화 등을 위해 아래 내용을 작성해주세요!

  - type: input
    attributes:
      label: 제목 📝
      description: 리팩토링 작업의 제목을 입력해주세요!

  - type: textarea
    attributes:
      label: 작업 내용 📋
      description: 리팩토링 대상과 작업 내용을 자세히 작성해주세요!
      placeholder: |
        - 변경해야 할 코드 영역
        - 이유와 목적
        - 예상 결과
    validations:
      required: true

  - type: textarea
    attributes:
      label: 기대 효과 ✅
      description: 리팩토링 후 예상되는 효과를 작성해주세요!
      placeholder: 예: 코드 가독성 향상, 성능 최적화 등

  - type: input
    attributes:
      label: 추가 참고 자료 🔗
      description: 참고할 자료(문서, 링크 등)가 있다면 작성해주세요. (선택 사항)
