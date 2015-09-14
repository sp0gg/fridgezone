package net.sp0gg.fridgezone.config;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.jndi.JndiObjectFactoryBean;

@Configuration
@ComponentScan
public class RootConfig {
	
	@Profile(value={"dev", "test", "prod"})
	@Bean
	public DataSource fridgezoneDataSource() throws IllegalArgumentException, NamingException{
		JndiObjectFactoryBean jndiObjectFB = new JndiObjectFactoryBean();
		jndiObjectFB.setJndiName("java:comp/env/jdbc/fridgezone");
		jndiObjectFB.setResourceRef(true);
		jndiObjectFB.setProxyInterface(javax.sql.DataSource.class);
		jndiObjectFB.afterPropertiesSet();
		DataSource ds = (DataSource) jndiObjectFB.getObject();
		return ds;
	}
}
