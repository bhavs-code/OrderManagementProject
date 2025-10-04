package com.mywork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mywork.model.props.Props;

@RestController
public class TestProps {
	
	@Autowired
	Props props;
	
	@GetMapping("/props")
	public Props getProps() {
		return props;
	}

}
 