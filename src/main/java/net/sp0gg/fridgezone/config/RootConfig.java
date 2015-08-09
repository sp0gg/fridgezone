package net.sp0gg.fridgezone.config;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jndi.JndiObjectFactoryBean;

@Configuration
@ComponentScan
public class RootConfig {
	
	@Profile(value={"test", "prod"})
	@Bean
	public DataSource fridgezoneDataSource() throws IllegalArgumentException, NamingException{
		JndiObjectFactoryBean jndiObjectFB = new JndiObjectFactoryBean();
		jndiObjectFB.setJndiName("java:comp/env/jdbc/fridgezone");
		jndiObjectFB.setResourceRef(true);
		jndiObjectFB.setProxyInterface(javax.sql.DataSource.class);
		jndiObjectFB.afterPropertiesSet();
		DataSource ds = (DataSource) jndiObjectFB.getObject();
		System.out.println("datasource is: " + ds.toString());
		return ds;
	}

/*
@Bean
public JndiObjectFactoryBean fridgezoneDataSource(){
JndiObjectFactoryBean jndiObjectFB = new JndiObjectFactoryBean();
jndiObjectFB.setJndiName("jdbc/fridgezone");
jndiObjectFB.setResourceRef(true);
jndiObjectFB.setProxyInterface(javax.sql.DataSource.class);
return jndiObjectFB;
}
*/

}
