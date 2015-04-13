package org.balaji.config;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan({"org.balaji.web"})
public class WebConfig extends WebMvcConfigurerAdapter{
	
	public WebConfig(){
		super();
	}
	
	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		converters.add(createXmlHttpMessageConverter());
		// TODO Auto-generated method stub
		final Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		builder.indentOutput(true).dateFormat(new SimpleDateFormat("dd-MM-yyyy hh:mm"));
		
		converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
		super.configureMessageConverters(converters);
	}
	// here we are using Xstream as the http converter.
	private HttpMessageConverter<Object> createXmlHttpMessageConverter(){
		final MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();
		
		final XStreamMarshaller xstreamMarshaller = new XStreamMarshaller();
		xmlConverter.setMarshaller(xstreamMarshaller);
		xmlConverter.setUnmarshaller(xstreamMarshaller);
		return xmlConverter;
	}

}
