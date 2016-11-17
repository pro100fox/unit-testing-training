package com.damir.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.damir.dao","com.damir.domain"})
public class SpringConfiguration {
	
	@Bean
	public DataSource dataSource() throws Exception {        
		Context ctx = new InitialContext();
		DataSource dataSource = (DataSource) ctx.lookup("java:/datasources/universitySchedule");        
        return dataSource;
	}
}