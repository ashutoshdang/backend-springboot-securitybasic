package com.asu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.asu.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class BasicAuthConfiguration extends WebSecurityConfigurerAdapter{

	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Bean
	public UserDetailsService mongoUserDetails() {
	    return new CustomUserDetailsService();
	}
	
	/**
	 * this method is used to return authentication object which spring security uses to
	 * authenticate a user . Here AuthenticationBuilder returns an Authentication Provider. 
	 * SecurityBuilder used to create an AuthenticationManager. Allows for easily building in memory authentication,
	 * LDAP authentication, JDBC based authentication, adding UserDetailsService, and adding AuthenticationProviders.
	 */
	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("configureglobal called");
	    UserDetailsService userDetailsService = mongoUserDetails();
	    auth
	        .userDetailsService(userDetailsService)
	        .passwordEncoder(bCryptPasswordEncoder);


	}
	/**
	 * we are overriding this method from WebSecurityConfigurerAdapter
	 *  to configure  spring WebSecurity  to ignore certain requests to certain paths/urls
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	        .ignoring()
	        .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}
	
	/**
	 *  this method from WebSecurityConfigurerAdapter is overridden here to
	 *  configure spring websecurity to authorize/restricting access or allowing access 
	 *  to certain users having certain roles . roles are nothing but a set of authorities 
	 *  Authority is nothing but access right to certain urls 
	 *  so when it is said that a user has certain roles means the user has certain set of 
	 *  granted authorities  or the user has access right to a certain set of urls
	 */
	@Override
    protected void configure(HttpSecurity http) 
      throws Exception {
        /*http.csrf().disable()
          .authorizeRequests()
          .antMatchers("/","/login","/user","/createUser").permitAll()
          .anyRequest()
          .authenticated()
          .and()
          .httpBasic();*/
	http
		.cors()
		.and()
	    .httpBasic().and()
	    .authorizeRequests()
        .antMatchers("/","/login","/createUser","/photos/add").permitAll()
        .anyRequest()
        .authenticated().and().csrf().disable()
        .sessionManagement().maximumSessions(1);
        
        
       
    }
	
	/*@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}*/
}
