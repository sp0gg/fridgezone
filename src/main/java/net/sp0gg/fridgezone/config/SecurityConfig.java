package net.sp0gg.fridgezone.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

/**
 * Created by sp0gg on 9/14/15.
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource fridgezoneDataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication().dataSource(fridgezoneDataSource);
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().and().httpBasic().and().rememberMe()
                .and().csrf().disable();
        http.authorizeRequests().anyRequest().authenticated();
    }
}
