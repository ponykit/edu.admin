package com.edu.admin.config.security;



import com.edu.admin.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private MemberService memberService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 탭을 iframe으로 출력할 때 보안 오류 방지
        http.headers().frameOptions().disable();

        http
                .authorizeRequests()
                // 페이지 권한 설정
                .antMatchers("/common/**").hasRole("ADMIN")
                .antMatchers("/base/**").hasRole("ADMIN")
                .antMatchers("/api/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().permitAll()
                .and() // 로그인 설정
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureHandler(customAuthenticationFailureHandler())
                .permitAll()
                .and() // 로그아웃 설정
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true);

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        //
        return new LoginFailureHandler();
    }


}
