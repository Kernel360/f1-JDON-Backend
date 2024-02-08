# 💡 JDON

원티드 JD, 인프런 강의, 그리고 네트워킹을 한 곳에서!

## 📌 프로젝트 소개

원티드 JD 기반의 인기있는 기술스택과 관련 인프런 강의 추천 서비스

자유로운 커피챗 커뮤니티 서비스


### 🔍️ JDON 미리 보기

[JDON 바로가기](https://jdon.kr) 👉🏻 https://jdon.kr

| 메인페이지                                                                                                                                 | 기술 스택 검색 페이지                                                                                                        |
|---------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------|
| <img width="1792" src="https://github.com/Kernel360/f1-JDON-Backend/blob/develop/docs/images/jdon_main_page.png">                     | <img width="1792" src="https://github.com/Kernel360/f1-JDON-Backend/blob/develop/docs/images/jdon_search_page.png"> |
| 원티드에서 크롤링 해온 기술스택 키워드를 기반으로 <br> 관련있는 인프런 인기 강의와 해당 기술에 관심을 가진 회사의 Job Description을 제공합니다. <br/> JDON의 회원이 아니어도 해당 서비스를 제공받을 수 있습니다.  | 검색한 기술 키워드와 관련된 인프런 인기 강의와 해당 기술에 관심을 가진 회사의 Job Description을 제공합니다. <br/> JDON의 회원이 아니어도 해당 서비스를 제공받을 수 있습니다.      |

| 커피챗 페이지                                                                                                       | 커피챗 상세 조회 페이지                                                                                                             |
|---------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------|
| <img width="1792" src="https://github.com/Kernel360/f1-JDON-Backend/blob/develop/docs/images/jdon_coffeechat_list.png"> | <img width="1792" src="https://github.com/Kernel360/f1-JDON-Backend/blob/develop/docs/images/jdon_coffeechat_detail.png"> |
| JDON 회원이 생성한 커피챗 목록을 확인할 수 있습니다. | JDON의 회원은 커피챗을 자유롭게 오픈할 수 있습니다. <br> 커피챗을 오픈하기 위해서는 커피챗 진행 날짜, 시간, 모임 인원 그리고 커피챗 제목과 내용을 작성해야합니다.                         |

| 로그인 페이지                                                                                     | 마이페이지                                                                                     |
|---------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------|
| <img width="1792" src="https://github.com/Kernel360/f1-JDON-Backend/blob/develop/docs/images/jdon_login_page.png"> | <img width="1792" src="https://github.com/Kernel360/f1-JDON-Backend/blob/develop/docs/images/jdon_my_page.png"> |
| JDON은 카카오와 깃헙을 통해서 로그인하실 수 있습니다.                                                            | 마이페이지에서는 내가 찜한 강의 목록, 신청한 커피챗 목록, 오픈한 커피챗 목록을 확인할 수 있습니다.                                 |


## 🗃️ 개발 기록
### ✨ 기술 스택
<div style="display:flex; flex-direction:column; align-items:flex-start;">
    <p><strong>Backend</strong></p>
    <div>
        <img src="https://img.shields.io/badge/Java_17-007396?style=for-the-badge&logo=java&logoColor=white"> 
        <img src="https://img.shields.io/badge/Spring_Boot_3.2-6DB33F?style=for-the-badge&logo=spring boot&logoColor=white">
        <img src="https://img.shields.io/badge/Spring_Security_6.2-6DB33F?style=for-the-badge&logo=spring security&logoColor=white">
    </div>
    <p><strong>Database</strong></p>
    <div>
        <img src="https://img.shields.io/badge/Mysql_8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
        <img src="https://img.shields.io/badge/Spring_Data_JPA_3.2-6DB33F?style=for-the-badge&logo=spring data jpa&logoColor=white">
        <img src="https://img.shields.io/badge/Querydsl-4479A1?style=for-the-badge&logo=&logoColor=white">
    </div>
    <p><strong>Others</strong></p>
    <div>
        <img src="https://img.shields.io/badge/AWS_EC2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white">
        <img src="https://img.shields.io/badge/AWS_route_53-8C4FFF?style=for-the-badge&logo=amazonroute53&logoColor=white">
    </div>
</div>

### 📦️ ERD

![](docs/images/jdon_erd.png)


### 🏗️ Architecture 

![architecture](docs/images/jdon_architecture.png)

### 📚️ 개발 기록
- [프로젝트 구조](docs/structure.md)
- [API 명세서](docs/api.md)
- trouble shooting
  - [멀티 모듈 도입하기]()
  - [oauth2 적용하기]()
  - [커피챗 신청에 동시성 고려하기]()
  - [인프런 크롤러 똑똑하게 만들기]()


## 👥 Contributors

<table>
  <tbody>
    <tr>
    <td align="center">
        <a href="https://github.com/yoonseon12">
          Leader <br>
          <img src="https://avatars.githubusercontent.com/u/59242594?v=4" width="130px;" alt=""/>
          <br /> <sub><b>yoonseon12</b><br></sub>
        </a>
    </td>
    <td align="center">
        <a href="https://github.com/aqrms">
          <br>
          <img src="https://avatars.githubusercontent.com/u/111513287?v=4" width="130px;" alt=""/>
          <br /><sub><b>aqrms</b></sub>
        </a>
        <br />
    </td>
    <td align="center">
        <a href="https://github.com/aacara">
          <br>
          <img src="https://avatars.githubusercontent.com/u/86637372?v=4" width="130px;" alt=""/>
          <br /><sub><b>aacara</b><br></sub>
        </a>
    </td>
    <td align="center">
        <a href="https://github.com/anso33">
          <br>
          <img src="https://avatars.githubusercontent.com/u/68376744?v=4" width="130px;" alt=""/>
          <br /><sub><b>anso33</b></sub>
        </a>
        <br />
    </td>
    </tr>
    <tr>
        <td align="center">
        <sub><b>원티드 크롤링</b></sub>
        </td>
        <td align="center">
        <sub><b>커피챗 서비스</b></sub>
        </td>
        <td align="center">
        <sub><b>인프런 크롤링</b></sub>
        </td>
        <td align="center">
        <sub><b>로그인 서비스</b></sub>
        </td>
    </tr>
  </tbody>
</table>


