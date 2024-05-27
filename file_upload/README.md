파일 업로드 예제

파일 업로드 하는 경우 content-type 속성으로 multipart/form-data 를 사용한다.

Spring은 멀티파트 형식의 데이터 처리를 지원하고 있기 때문에 이 기능을 이용하여 별도처리없이 멀티파트 형식으로 전송된 파라미터와 파일 정보를 구할 수 있다.

### 1. MultipartResolver 설정

멀티파트 지원 기능을 사용하려면 먼저 MultipartResolver를 스프링 설정 파일에 등록해주어야 한다. MultipartResolver은 멀티파트 형식으로 데이터가 전송된 경우, 해당 데이터를 스프링 MVC에서 사용할 수 있또록 변환해 주는 역할을 한다.

MultipartResolver 종류
#### a. CommonsMultipartResolver
    Commons FileUpload API를 이용해서 멀티파트 데이터를 처리한다.
#### b. StardardServletMultipartResolver
    서블릿 3.0의 Part를 이용해서 멀티파트 데이터를 처리한다.

위 2개의 MultipartResolver 구현체 중 하나를 스프링 빈으로 등록해주면 된다. 주의사항은 스프링 빈의 이름은 multipartResolver 이어야 한다. DispatcherServlet은 이름이 multipartResolver인 빈을 사용하기 때문에 다른 이름을 사용할 경우 MultipartResolver로 인식하지 못한다.

#### 1-1. Commons FileUpload를 이용하기 위한 설정
    - commons-fileupload 라이브러리 추가
    - CommonsMultipartResolver를 빈으로 등록

#### 1-2. 서블릿 3의 파일 업로드 기능을 사용하기 위한 설정
    - DispatcherServlet이 서블릿 3의 Multipart를 처리하도록 설정
    - StandardServletMultipartResolver 클래스를 MultipartResolver로 설정

### 2. 업로드한 파일 접근하기

#### 2-1. MultipartFile 인터페이스 사용
- MultipartFile 인터페이스는 업로드 한 파일 정보 및 파일 데이터를 표현하기 위한 용도로 사용.
- 이 인터페이스를 이용해서 업로드한 파일 데이터를 읽을 수 있다.

#### 2-2. MultipartHttpServletRequest를 이용한 업로드 파일 접근
- MultipartHttpServletRequest 인터페이스는 스프링이 제공하는 인터페이스로, 멀티파트 요청이 들어올 때 내부적으로 HttpServletRequest 대신 사용되는 인터페이스다.