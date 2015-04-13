package org.balaji.web.controller;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

import org.balaji.web.dto.Foo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FooController {
	
	public FooController()
	{
		super();
	}
	
	@RequestMapping(method= RequestMethod.GET, value="/foos/{id}")
	@ResponseBody
	public Foo findById(@PathVariable final long id){
		return new Foo(Long.parseLong(randomNumeric(2)), randomAlphabetic(4));
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/foos/{id}")
	@ResponseBody
	public Foo updateFoo(@PathVariable final long id, @RequestBody final Foo foo)
	{
		System.out.println(foo);
		return foo;
	}
}
