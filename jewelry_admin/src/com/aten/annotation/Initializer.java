package com.aten.annotation;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

public class Initializer implements WebBindingInitializer {

	@Override
	public void initBinder(WebDataBinder binder, WebRequest arg1) {
		binder.setAutoGrowCollectionLimit(10000);  
	}

}
