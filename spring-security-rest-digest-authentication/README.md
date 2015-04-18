#What is Digest Authentication:

This is one of the agreed upon methods a web server can use to negotiate credentials such as username or password with the user's browser.

It applies a hash function to a password before sending it over the network , which is safer than the basic access authentication which sends plain text.

This typical transaction consists of the following steps:
1. The client asks for a page that requires authentication but does not provide a username and password.Typically this is because the user simply entered the address or followed a link to the page.
2. The server responds with the 401 "Unauthorized" response code, providing the authentication realm and a randomly generated, single-use value called a nonce.
3. At this point, the browser will present the authentication realm (typically a description of the computer or system being accessed) to the user and prompt for a username and password. The user may decide to cancel at this point.
4. Once a username and password have been supplied, the client re-sends the same request but adds an authentication header that includes the response code.
5. In this example, the server accepts the authentication and the page is returned. If the username is invalid and/or the password is incorrect, the server might return the "401" response code and the client would prompt the user again.

#Client request (no authentication)

GET /dir/index.html HTTP/1.0
Host: localhost

#Server response

HTTP/1.0 401 Unauthorized
Server: HTTPd/0.9
Date: Sun, 10 Apr 2014 20:26:47 GMT
WWW-Authenticate: Digest realm="testrealm@host.com",
                        qop="auth,auth-int",
                        nonce="dcd98b7102dd2f0e8b11d0f600bfb0c093",
                        opaque="5ccc069c403ebaf9f0171e9517f40e41"
Content-Type: text/html
Content-Length: 153
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Error</title>
  </head>
  <body>
    <h1>401 Unauthorized.</h1>
  </body>
</html>

#Client request (username "Mufasa", password "Circle Of Life")

GET /dir/index.html HTTP/1.0
Host: localhost
Authorization: Digest username="Mufasa",
                     realm="testrealm@host.com",
                     nonce="dcd98b7102dd2f0e8b11d0f600bfb0c093",
                     uri="/dir/index.html",
                     qop=auth,
                     nc=00000001,
                     cnonce="0a4f113b",
                     response="6629fae49393a05397450978507c4ef1",
                     opaque="5ccc069c403ebaf9f0171e9517f40e41"

#Server response

HTTP/1.0 200 OK
Server: HTTPd/0.9
Date: Sun, 10 Apr 2005 20:27:03 GMT
Content-Type: text/html
Content-Length: 7984

#Project Details :

Spring Security does not have full out of the box for digest authentication mechanism, this support is not well integrated into the name space as the Basic Authentication.

	1. So, we need to manually define the raw beans. The raw beans are as below.

	DigestAuthenticationFilter
	DigestAuthenticationEntryPoint

<http use-expressions="true" entry-point-ref="digestEntryPoint">
        <intercept-url pattern="/api/**" access="isAuthenticated()" />

        <sec:custom-filter ref="digestFilter" after="BASIC_AUTH_FILTER"/>
    </http>

	<beans:bean id="digestFilter" class="org.springframework.security.web.authentication.www.DigestAuthenticationFilter">
		<beans:property name="userDetailsService" ref="userService"/>
		<beans:property name="authenticationEntryPoint" ref="digestEntryPoint"/>
	</beans:bean>
	
	<beans:bean id="digestEntryPoint" class="org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint">
		<beans:property name="realmName" value="Contacts Realm via Digest Authentication"/>
		<beans:property name="key" value="acegi"/>
	</beans:bean>



#URL's to be executed :


D:\d\r-n-d\REST-master\REST-master>curl -i --header "Accept:application/json" -X GET http://localhost:8080/spring-security-rest-digest-authentication/api/foos/1
HTTP/1.1 401 Unauthorized
Server: Apache-Coyote/1.1
Set-Cookie: JSESSIONID=48C648A41FB58B0EF9E10A7A3F2D1BD2; Path=/spring-security-rest-digest-authentication/; HttpOnly
WWW-Authenticate: Digest realm="Contacts Realm via Digest Authentication", qop="auth", nonce="MTQyOTM1MzM3OTcxODpmMTVlMzAwMDA5MjQ3ZmQ5YmFjYTliODc1YzIxMDU3ZA=="
Content-Type: text/html;charset=utf-8
Content-Language: en
Content-Length: 1104
Date: Sat, 18 Apr 2015 10:31:19 GMT

<!DOCTYPE html><html><head><title>Apache Tomcat/8.0.21 - Error report</title><style type="text/css">H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;
color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}.line {height: 1px; background-color: #525D76; border: none;}</style> </head><body><h1>HTTP Status 401 - Full authentication is required to access this resource</h1><div class="line"></div><p><b>type</b> Status report</p><p><b>message</b> <u>Full authentication is required to access this resource</u></p><p><b>description</b> <u>This r
equest requires HTTP authentication.</u></p><hr class="line"><h3>Apache Tomcat/8.0.21</h3></body></html>


D:\d\r-n-d\REST-master\REST-master>curl -i --digest --user user:userPass --header "Accept:application/json" -X GET http://localhost:8080/spring-security-rest-digest-authentication/api/foos/1
HTTP/1.1 401 Unauthorized
Server: Apache-Coyote/1.1
Set-Cookie: JSESSIONID=F858F82856E6FDF7A1A876ED2460B40F; Path=/spring-security-rest-digest-authentication/; HttpOnly
WWW-Authenticate: Digest realm="Contacts Realm via Digest Authentication", qop="auth", nonce="MTQyOTM1MzQyNjM4MzoyZWExYTllNGVmMjdiYzkyNTIxMDJjZDMwNTVjOWI0MA=="
Content-Type: text/html;charset=utf-8
Content-Language: en
Content-Length: 1104
Date: Sat, 18 Apr 2015 10:32:06 GMT

HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Set-Cookie: JSESSIONID=1E5CFDE92F7CE5315D09D0D899563A2D; Path=/spring-security-rest-digest-authentication/; HttpOnly
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Sat, 18 Apr 2015 10:32:06 GMT

{
  "id" : 21,
  "name" : "MTuk"
}
D:\d\r-n-d\REST-master\REST-master>
