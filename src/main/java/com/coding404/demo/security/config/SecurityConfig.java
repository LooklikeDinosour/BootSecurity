package com.coding404.demo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //설정파일
@EnableWebSecurity //이 설정파일을 시큐리티 필터에 추가(필수)
public class SecurityConfig {


	@Bean
	public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {
		
		//csrf토큰 x
		http.csrf().disable();
		
		//권한설정 모든페이지 접근 허가
		//http.authorizeRequests( authorize -> authorize.anyRequest().permitAll());
		
		//모든페이지에 대해서 거부
		//http.authorizeRequests( authorize -> authorize.anyRequest().denyAll());		
		
		//user페이지에 대해서 인증이 필요
//		http.authorizeHttpRequests( authorize -> authorize.
//												 antMatchers("/user/**").
//												 authenticated());
		
		//user페이지는 user권한이 필요, admin페이지는 admin 권한이 필요
//		http.authorizeHttpRequests(authorize -> authorize.antMatchers("/user/**").hasRole("USER")
//														 .antMatchers("/admin/**").hasRole("ADMIN"));	
		
		
//		http.authorizeHttpRequests (authorize -> authorize.antMatchers("/all").authenticated() //all 인증필요
//														  .antMatchers("/user/**").hasRole("USER") //user는 user 권한
//														  .antMatchers("/admin/**").hasRole("ADMIN") //admin페이지는 admin 권한
//														  .anyRequest().permitAll()); // 그외에는 모든 페이지는 접근이 가능하다.

		http.authorizeHttpRequests (authorize -> authorize.antMatchers("/all").authenticated() //all 인증필요
														  .antMatchers("/user/**").hasAnyRole("USER","ADMIN", "TESTER") // 셋중 1개의 권한을 가지면 됨
														  .antMatchers("/admin/**").hasRole("ADMIN") //admin페이지는 admin 권한
														  .anyRequest().permitAll()); // 그외에는 모든 페이지는 접근이 가능하다.

		//시큐리티 설정파일 만들면, 시큐리티가 제공하는 기본 로그인페이지가 보이지 않게 됩니다.
		//시큐리티가 사용하는 기본로그인페이지를 사용함
		//권한 or 인증이 되지 않으면 기본으로 선언된 로그인페이지를 보여주게 됩니다.
		//http.formLogin(Customizer.withDefaults()); //기본로그인페이지 사용
		
		//사용자가 제공하는 폼기반 로그인기능을 사용할 수 있습니다.
		http.formLogin().loginPage("/login");
		
		
		
		http.formLogin(Customizer.withDefaults()); //기본로그인 페이지사용
		
		
		return http.build();
	}
}
