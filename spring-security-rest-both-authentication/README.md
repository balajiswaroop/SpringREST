#Project Details:

	1. Since the RESTful Web Service is stateless, Spring Security provides a way to specify that no session has to be created for the request. This option is using the "create-session" attribute of the <http> element.
	2. The "entry-point-ref" still points to the same "digestEntryPoint" i.e the DigestAuthenticationFilter.
	3. Whenever we add the <http-basic> element inside the <http> element, the BasicAuthenticationFilter will be automatically added to the filter chain. So now there are both BasicAuthenticationFilter along with the BasicAuthenticationendPoint available.
	4. For an annonymos request without proper Authenticate headers, the requests will be handled by the DigestAuthenticationFilter.
	5. But, if the Authenticate headers are properly specified as BASIC/DIGEST , they will be correctly handled by the respective Authentication Filters.
	
	<http create-session="stateless" entry-point-ref="digestEntryPoint">
	        <intercept-url pattern="/api/**" access="ROLE_ADMIN" />
		
		<sec:http-basic/>
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
		
	
	    <authentication-manager alias="authenticationManager">
	        <authentication-provider>
	            <user-service id="userService">
	            	<user name="balaji" password="balaji" authorities="ROLE_ADMIN" />
	                <user name="user" password="userPass" authorities="ROLE_USER" />
	            </user-service>
	        </authentication-provider>
	    </authentication-manager>
	
#URL's to be executed :

	1. When the Url does not have a proper header it will be handled by the DigestAuthenticationFilter 

D:\d\r-n-d\REST-master\REST-master>curl -i --header "Accept:application/json" -X GET http://localhost:8080/spring-security-rest-both-authentication/api/foos/1
HTTP/1.1 401 Unauthorized
Server: Apache-Coyote/1.1
WWW-Authenticate: Digest realm="Contacts Realm via Digest Authentication", qop="auth", nonce="MTQyOTM1NzQzODkzNzpmOWYzNjY0NTQxNWZjMGE4ZjUzMjU1OWU2OTRjYjU4Ng=="
Content-Type: text/html;charset=utf-8
Content-Language: en
Content-Length: 1104
Date: Sat, 18 Apr 2015 11:38:58 GMT

<!DOCTYPE html><html><head><title>Apache Tomcat/8.0.21 - Error report</title><style type="text/css">H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;
color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}.line {height: 1px; background-color: #525D76; border: none;}</style> </head><body><h1>HTTP Status 401 - Full authentication is required to access this resource</h1><div class="line"></div><p><b>type</b> Status report</p><p><b>message</b> <u>Full authentication is required to access this resource</u></p><p><b>description</b> <u>This r
equest requires HTTP authentication.</u></p><hr class="line"><h3>Apache Tomcat/8.0.21</h3></body></html>
D:\d\r-n-d\REST-master\REST-master>


	2. When the authentication credentials are provided, its handled by the BasicAuthenticationFilter

D:\d\r-n-d\REST-master\REST-master>curl -i --user balaji:balaji --header "Accept:application/json" -X GET http://localhost:8080/spring-security-rest-both-authentication/api/foos/1
HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Sat, 18 Apr 2015 11:41:49 GMT

{
  "id" : 59,
  "name" : "JklC"
}
D:\d\r-n-d\REST-master\REST-master>

	3. When the digest header is added as part of the Authentication header, the DigestAuthentication header will be used to authenticate.

D:\d\r-n-d\REST-master\REST-master>D:\d\r-n-d\REST-master\REST-master>curl -i --digest --user balaji:balaji --header "Accept:application/json" -X GET http://localhost:8080/spring-security-rest-both-authentication/api/foos/1
'D:\d\r-n-d\REST-master\REST-master' is not recognized as an internal or external command,
operable program or batch file.

D:\d\r-n-d\REST-master\REST-master>curl -i --digest --user balaji:balaji --header "Accept:application/json" -X GET http://localhost:8080/spring-security-rest-both-authentication/api/foos/1
HTTP/1.1 401 Unauthorized
Server: Apache-Coyote/1.1
WWW-Authenticate: Digest realm="Contacts Realm via Digest Authentication", qop="auth", nonce="MTQyOTM1NzgzMjYwMjo2MWI4NTFhNDA1OGExMDAyYTUwZDIzZjBjY2I3ODZiZg=="
Content-Type: text/html;charset=utf-8
Content-Language: en
Content-Length: 1104
Date: Sat, 18 Apr 2015 11:45:32 GMT

HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Sat, 18 Apr 2015 11:45:32 GMT

{
  "id" : 25,
  "name" : "mVao"
}

D:\d\r-n-d\REST-master\REST-master
