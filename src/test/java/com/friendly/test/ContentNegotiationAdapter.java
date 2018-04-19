package com.friendly.test;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ContentNegotiationAdapter extends WebMvcConfigurerAdapter {
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(false).favorParameter(true).ignoreAcceptHeader(true).useJaf(false)
				.defaultContentTypeStrategy(new ContentNegotiationStrategy() {

					@Override
					public List<MediaType> resolveMediaTypes(NativeWebRequest nativeWebRequest)
							throws HttpMediaTypeNotAcceptableException {
						if (nativeWebRequest.getDescription(false).endsWith("/getEmpty")) {
							return Collections.singletonList(MediaType.TEXT_PLAIN);
						} else {
							return Collections.singletonList(MediaType.APPLICATION_JSON);
						}
					}
				});
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
	}
}