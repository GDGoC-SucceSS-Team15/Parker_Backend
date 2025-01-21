---
name: "\U0001F41E Bug"
about: 버그 신고
title: "[bug]"
labels: bug
assignees: ''

---

name: 🐞 Buh
description: "버그 신고"
body:
  - type: markdown
    attributes:
      value: |
        ### 🐛 버그 설명
        아래 양식을 작성하여 문제를 상세히 보고해주세요!

  - type: input
    attributes:
      label: 제목 📝
      description: 문제를 간단히 요약해주세요!

  - type: textarea
    attributes:
      label: 문제 상황 📋
      description: 문제가 발생한 상황을 자세히 설명해주세요! (선택사항 : 스크린샷)

  - type: textarea
    attributes:
      label: 재현 방법 🔄
      description: 문제를 재현하기 위한 단계를 순서대로 작성해주세요!
      placeholder: |
        1. ...
        2. ...
        3. ...

  - type: textarea
    attributes:
      label: 기대 결과 ✅
      description: 정상적으로 동작했을 때 기대했던 결과를 적어주세요!

  - type: textarea
    attributes:
      label: 실제 결과 ❌
      description: 문제가 발생했을 때 실제로 나타난 결과를 적어주세요!

  - type: dropdown
    attributes:
      label: 발생 환경 💻
      description: 문제 발생 시 사용한 환경을 선택해주세요!
      options:
        - Windows
        - macOS
        - Linux
        - Android
        - iOS

  - type: input
    attributes:
      label: 추가 정보 🔍
      description: 문제가 발생한 경우 더 필요한 정보가 있다면 적어주세요!
