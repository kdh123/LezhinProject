## 사용 Library
* Compose
  - Android UI 라이브러리
  - 사용 목적 : UI 그리기 및 UI 코드 간결성으로 UI 그리기 편리
* Material 3
  - Android 디자인 가이드 라이브러리
  - 사용 목적 : Compose와 함께 사용함으로써 UI View 구성 편리
* ViewModel (AAC)
  - Android 데이터 캐싱 라이브러리
  - 사용 목적 : MVVM 패턴에서의 ViewModel 역할 및 데이터 캐싱
* Navigation
  - 화면 이동 라이브러리
  - 사용 목적 : Compose로 구성한 화면으로 이동
* Paging3
  - 페이징 라이브러리
  - 사용 목적 : 서버로부터 받은 데이터를 페이징 처리
* Hilt
  - Android DI 라이브러리
  - 사용 목적 : DI 구현
* Retrofit, Okhttp
  - 서버 통신 라이브러리
  - 사용 목적 : 서버 통신
* Gson
  - Google 객체 직렬화 처리 라이브러리
  - 사용 목적 : Retrofit을 통해 받아온 Json 데이터를 직렬화 처리
* Room
  - 로컬 DB 라이브러리
  - 사용 목적 : 북마크 데이터 로컬 저장
* Glide
  - 이미지 로드 라이브러리
  - 사용 목적 : 이미지 url을 통한 이미지 로드
 
## 발생할 수 있는 이슈
* 카카오에서 제공하는 이미지 데이터 속성에 ID가 따로 없어서 thumbnailUrl을 이미지 ID로 저장
* thumbnailUrl이 유니크하지 않을 수도 있기 때문에 이와 관련한 크래시가 발생할 가능성 존재
