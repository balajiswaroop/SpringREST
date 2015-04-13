#Testing URL's

curl -i -X POST -d j_username=user -d j_password=userPass -c D:\d\r-n-d\Spring_preparation\SpringREST\spring-security-rest-new\cookies.txt http://localhost:8080/spring-security-rest-new/j_spring_security_check

Output :

HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Set-Cookie: JSESSIONID=EE6093E9251E391DDB58B255A306E0D8; Path=/spring-security-rest-new/; HttpOnly
Content-Length: 0
Date: Mon, 13 Apr 2015 10:20:19 GMT

curl -i --header "Accept:application/json" -X GET -b D:\d\r-n-d\Spring_preparation\SpringREST\spring-security-rest-new\cookies.txt http://localhost:8080/spring-security-rest-new/api/foos/1

Output :

HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Mon, 13 Apr 2015 10:21:55 GMT

{
  "id" : 1,
  "name" : "ONKu"
}


