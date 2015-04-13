package org.balaji.web.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Foo")
public class Foo {
	
	private long id;
	private String name;
	
	public Foo()
	{
		super();
	}
	
	public Foo(final long id, final String name)
	{
		super();
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
