package net.sp0gg.fridgezone.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@ComponentScan
public class TestDataConfig {
	
	String schemaLocation = "classpath:/schema.sql";
	String testDataLocation = "classpath:test-data.sql";

	@Profile("local")
	@Bean
	public DataSource testFridgezoneDataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
				.addScript(schemaLocation)
				.addScript(testDataLocation).build();
	}
}
