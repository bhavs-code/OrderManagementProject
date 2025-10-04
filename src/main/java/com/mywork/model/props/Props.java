package com.mywork.model.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "spring.info")
public class Props {

	public String name;
	
	public String password;
}
