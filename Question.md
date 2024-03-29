# Questions
해당 문서는 RESTHotel을 설계하는 과정에서 발생하는 궁금증과 그에 알맞은 답을 정리한 문서.

---
#### 1. Room Status로 무엇을 정의해야 하는가?
* Check In
* Check Out
* Leaving
* Cleaning
    * Cleaning의 Status를 나눌 필요가 있는가?(Before, Current, After)
  

#### 2. Resource의 CRUD 외의 API 요청들은 어떤 HTTP Method를 사용해야 하는가?
* Check In, Out API
  * Room Status를 변경하는 API이기 때문에 Patch를 써야 하는가?  
    -> Patch를 사용하는 쪽이 조금 더 명확하고, 바뀌는 상태 정보는 Body에 포함시켜 요청한다.  
    [참고 : Rest uri design for changing the status for resource](https://stackoverflow.com/questions/18233632/rest-uri-design-for-changing-the-status-for-resource)
* Room Service API
  * 음식을 주문하는 요청은 결국 주문과 비슷한 개념이기 때문에 POST를 사용한다.
* Cleaning Service API
  * 방 청소에 대한 요청 또한 청소라는 주문이 새로 생성된 것이기 때문에 POST를 사용한다.
* ETC...

#### 3. Room Status 변경에 따라서 처리해야 되는 추가 로직은 어디에 작성해야 하는가?
* CheckOut 시 Room의 상태는 'Leaving' or 'Required Cleaning'으로 변경 될 텐데, 해당 시점에서 Cleang Service의 add room 메서드가 호출되어야 한다.  
-> RepositoryEventHandler class의 @HandleAfterSave를 적용한다. 직접 add room 메서드를 호출하지 않고, ApplicationEvent를 사용하는 것이 좋다.
[참고 : @RepositoryEventHandler](https://www.baeldung.com/spring-data-rest-events)
* 추가로 @RepositoryRestResource사용 시 custom Api가 필요하면 @RepositoryRestController를 사용하여 Controller를 생성하면 된다.
