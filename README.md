# Playground
> 나만의 놀이터: TMDB API를 기반으로 이것저것 적용해보고 만들어보는 앱

## 🕹️ 현재 진행 중
### feature 모듈 적용하기 (24.08.06~)
#### why?
- feature를 추가할 예정
- feauture 모듈 분리에 대한 가독성, 작업 편의성, 책임 분배 등 이점 체감

## 스크린샷
<div> 
    <img width="200" src ="https://github.com/user-attachments/assets/06e5d5db-eb96-4dee-8b04-72000f6a37af"> 
    <img width="200" src ="https://github.com/user-attachments/assets/8bf52686-5bfe-4d4f-9870-cb740b59a255"> 
</div> 

## 사용 기술
### 언어 및 아키텍처
- **Kotlin**
- **Clean Architecture/MultiModule**
: 체계적인 계층 구조과 관심사 분리를 통한 유지보수성 강화
- **MVVM**
: 뷰와 뷰를 표현하기 위한 상태, 데이터에 대한 로직 분리를 통한 관리 용이성 및 테스트 용이성 향상
### UI 및 상태 관리
- **Compose**
: 선언형 UI 프레임워크로 UI 코드를 간결하게 작성 가능 및 재사용성 용이, 상태 관리에도 적합
- **ViewModel**
: UI 관련 데이터 및 상태 관리
### 네트워크 통신 및 로컬 데이터 관리
- **Room**
: 로컬 데이터베이스 관리
- **okHttp/Retrofit**
: REST API와의 간결하고 효율적인 통신
- **kotlinxSerialization**
: Kotlin 객체를 JSON 포맷으로 직렬화/역직렬화
### 비동기 처리 및 반응형 프로그래밍
- **Coroutine**
: 비동기 작업의 간결하고 효율적인 처리
- **Flow**
: 데이터 스트림 관리 및 반응형 프로그래밍
### 의존성 주입
- **Hilt**
: 의존성 주입으로 코드 구조 개선 및 관리 용이성 향상
### 그 외
- **Coil**
: 효율적인 이미지 로딩 및 캐싱
- **Paging**
: 데이터 페이징 및 관리
- **kotlinxImmutable**
: 불변성을 보장하는 컬렉션을 제공하여 데이터 무결성 유지, 리컴포지션에 관한 성능적 이점
