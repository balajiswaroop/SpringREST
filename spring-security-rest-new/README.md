#Description :

In this example we will be updating the previous project Spring REST example to add Spring Security and we will see what all changes are required .

	1. Changes in web.xml 
		
		<filter>
			<filter-name>springSecurityFilterChain</filter-name>
			<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
			</filter>
			<filter-mapping>
				<filter-name>springSecurityFilterChain</filter-name>
				<url-pattern>/*</url-pattern>
			</filter-mapping>
		Spring security works as a filter, it intercepts the url's and checks whether the user is authorized for that URL.
		If not, it will return UNAUTHORIZED.
		
		There are a total of 11 filters that handle all the requests to the URL's with pattern /*, one of the 11 filters are used. For example when the URL "curl -i -X POST -d j_username=user -d j_password=userPass http://localhost:8080/spring-security-rest-new/j_spring_security_check" is fired the UsernamePasswordAuthenticationFilter class will handle the request 
		
		For Spring Security version 3.2.7.RELEASE there are 11 filter classes.
	2. webSecurityConfig.xml

	<http use-expressions="true" entry-point-ref="restAuthenticationEntryPoint">
	        <intercept-url pattern="/api/**" access="isAuthenticated()" />
	
	        <sec:form-login authentication-success-handler-ref="mySuccessHandler" authentication-failure-handler-ref="myFailureHandler" />
	
	        <logout />
	    </http>
	
	    <beans:bean id="mySuccessHandler" class="org.balaji.security.MySavedRequestAwareAuthenticationSuccessHandler" />
	    <beans:bean id="myFailureHandler" class="org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler" />
	
	    <authentication-manager alias="authenticationManager">
	        <authentication-provider>
	            <user-service>
	                <user name="temporary" password="temporary" authorities="ROLE_ADMIN" />
	                <user name="user" password="userPass" authorities="ROLE_USER" />
	            </user-service>
	        </authentication-provider>
	    </authentication-manager>
	
	The <http> element is the main container element for the HTTP security configuration. Here we are telling to intercept the url containing the pattern "/api/**" and verify whether its authenticated or not, if not authenticated fire the myFailureHandler which points to the class  "SimpleUrlAuthenticationFailureHandler" similarly if the URL is successfully authenticated, the MySavedRequestAwareAuthenticationSuccessHandler is used. 
	
	This class MySavedRequestAwareAuthenticationSuccessHandler is our custom class extending the SimpleUrlAuthenticationSuccessHandler .
	
	This class is exact same as SavedRequestAwareAuthenticationSuccessHandler from Spring Security. The original class was having redirects to login page which does not make sense in the case of REST. So our new class MySavedRequestAwareAuthenticationSuccessHandler  has the same code as the Spring Security  class with no redirects.
	
	The <form-login> element uses the UsernamePasswordAuthenticationFilter  to authenticate.
	
	3. The <authentication-manager> element we are using , here in this case we are specifying the different roles and UN/PWD to perform authentication for simplicity.
	
	For "ROLE_USER" un/pwd is user/userPass
	For "ROLE_ADMIN" un/pwd is temporary/temporary
	
	4. In a normal Web Application, when the client tries to access a URL and is not wuthenticated, he will be redirected to the login page i.e. the session is not available for him. 
	
	For the REST Web Service this does not make much sense, Authentication should be done by a request to the correct URI and all other requests should fail with 401 UNAUTHORIZED if not authenticated, no redirect here.
	
	For achieving the behaviour we use the "entry-point-ref" attribute of the <http> element and write a custom class implementing the class AuthenticationEntryPoint.


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


