#What is basic Authentication :

This is the simplest technique to enforce access to web resources.

The basic access authentication is a method for an HTTP user agent to provide a username and password when making request.

Security : There is protection for the transmitted cedentials, they are merely encoded with base64 in transit but not encrypted or hashed in any way.

#Protocol :
	
	Server Side :
	When the server wants the client to authenticate itself, it can send a request for authentication.
	
	Request from the Server to client should be sent using the HTTP 401 Not Authorized response code containing a WWW-Authenticate HTTP header.
	
	The WWW-Authenticate header for Basic is as below :
	
	WWW-Authenticate:  Basic realm="nmrs_m7VKmomQ2YM3:"
	
	Client Side:
	When the client wants to send the server authentication credentials it may use the Authorization header.
	
	The Authorization header is as follows:
	
		1. Username and password are combined into a string "username:password"
		2. The resulting string is then encoded using Base64.
		3. The authorization method and a space i.e "Basic" is then put before the encoded string.
	
	Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==

#Project Details :
 The only change here from the previous project is as provided below.

	1. For the basic authentication, Spring provides a simple <http-basic> element to be configured in the <http> element.

	<http use-expressions="true" entry-point-ref="restAuthenticationEntryPoint">
	        <intercept-url pattern="/api/**" access="isAuthenticated()" />
	
	        <sec:http-basic/>
	    </http>

	2. And the "entry-point-ref" attribute of the <http> element has to be changed to point to the new class extending the 
BasicAuthenticationEntryPoint class from Spring framework instead of AuthenticationEntryPoint. 
	
	
	



#URL's to test:


D:\d\r-n-d\REST-master\REST-master>curl -i --header "Accept:application/json" -X GET http://localhost:8080/spring-security-rest-basic-authentication/api/foos/1
HTTP/1.1 401 Unauthorized
Server: Apache-Coyote/1.1
Set-Cookie: JSESSIONID=CD7676B5DEA9450831305E564C9F0DEE; Path=/spring-security-rest-basic-authentication/; HttpOnly
WWW-Authenticate: Basic realm="Balaji Swaroop"
Content-Length: 75
Date: Sat, 18 Apr 2015 07:28:46 GMT

HTTP Status 401 - Full authentication is required to access this resource

D:\d\r-n-d\REST-master\REST-master>curl -i --user user:userPass --header "Accept:application/json" -X GET http://localhost:8080/spring-security-rest-basic-authentication/api/foos/1
HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Set-Cookie: JSESSIONID=A903FA56D878AD0A457353B3135A4C6E; Path=/spring-security-rest-basic-authentication/; HttpOnly
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Sat, 18 Apr 2015 07:30:59 GMT

{
  "id" : 10,
  "name" : "EyuH"
}
D:\d\r-n-d\REST-master\REST-master>curl -i --user user:userPass --header "Accept:application/xml" -X GET http://localhost:8080/spring-security-rest-basic-authentication/api/foos/1
HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Set-Cookie: JSESSIONID=D6BAB89D73DAA1374D82A2FC7A4E694D; Path=/spring-security-rest-basic-authentication/; HttpOnly
Content-Type: application/xml
Transfer-Encoding: chunked
Date: Sat, 18 Apr 2015 07:31:19 GMT

<org.balaji.web.dto.Foo><id>69</id><name>jdGK</name></org.balaji.web.dto.Foo>
D:\d\r-n-d\REST-master\REST-master>
