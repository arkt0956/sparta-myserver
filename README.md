# sparta-myserver
## specification

https://www.notion.so/Spring-c7fe04d7c9304040ba57757101e035fc#8b1b238834f241b9af6c989e62e0051e

|name|URL|method|Request|Response|functional|logic|
|------|------|------|------|------|------|------|


1. 수정, 삭제 API의 request를 어떤 방식으로 사용하셨나요? (param, query, body)
  수정: PUT, 삭제: DELETE
  @RequestBody 어노테이션으로 게시물 등을 받아왔습니다.
  
2. 어떤 상황에 어떤 방식의 request를 써야하나요?
  조회: GET, 게시: POST
  수정: PUT, 삭제: DELETE
  
3. RESTful한 API를 설계했나요? 어떤 부분이 그런가요? 어떤 부분이 그렇지 않나요?
  Rest API에 사용하는 통신 규약을 2번과 같이 지켰습니다.

4. 적절한 관심사 분리를 적용하였나요? (Controller, Repository, Service)
  Controller, Repository, Service 모두 분리되어 있습니다.
  관계: Controller(BoardController.java) -> Service(BoardService.java) -> Repository(BoardRepository.java)
  
5. API 명세서 작성 가이드라인을 검색하여 직접 작성한 API 명세서와 비교해보세요
