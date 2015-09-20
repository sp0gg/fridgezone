package net.sp0gg.fridgezone.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by sp0gg on 9/14/15.
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication().withUser("nelson").password("gandalf529***").roles("USER").and()
        .withUser("ATDD_USER").password("OUf?lJ?of|%Cz8F&N.rd").roles("USER");
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().and().httpBasic().and().csrf().disable();
        http.authorizeRequests().anyRequest().authenticated();
    }
}
