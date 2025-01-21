---
name: "\U0001F680 Deploy"
about: 배포 관련 작업
title: "[deploy]"
labels: ''
assignees: ''

---

name: "🚀 Deploy"
description: "배포 관련 작업"
labels: ["deploy"]
body:
  - type: markdown
    attributes:
      value: |
        ### 🛠️ 배포 작업
        아래 내용을 작성하여 배포 프로세스를 원활하게 진행할 수 있도록 해주세요!

  - type: input
    attributes:
      label: 제목 📝
      description: 배포 작업의 제목을 입력해주세요!

  - type: textarea
    attributes:
      label: 작업 내용 📋
      description: 배포와 관련된 작업 내용을 자세히 작성해주세요!
      placeholder: |
        - 배포 대상(예: 서버, 환경)
        - 변경 사항 요약
        - 예상 영향 범위
    validations:
      required: true

  - type: textarea
    attributes:
      label: 점검 사항 ✅
      description: 배포 전에 확인해야 할 점검 사항을 작성해주세요!
      placeholder: |
        - 테스트 여부
        - 로그 확인
        - 백업 상태 등
    validations:
      required: true

  - type: textarea
    attributes:
      label: 배포 완료 후 작업 🔄
      description: 배포 완료 후 필요한 후속 작업을 작성해주세요! (예: 알림, 문서 업데이트 등)
      placeholder: 예: 배포 완료 알림, 로그 확인 등
