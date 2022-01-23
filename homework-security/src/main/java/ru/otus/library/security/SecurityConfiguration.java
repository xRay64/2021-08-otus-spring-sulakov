package ru.otus.library.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/").permitAll()
                .and()
                .authorizeRequests().antMatchers("/authors").permitAll()
                .and()
                .authorizeRequests().antMatchers("/genres").permitAll()
                .and()
                .authorizeRequests().antMatchers("/authors/delete").hasAuthority("ROLE_ADMIN")
                .and()
                .authorizeRequests().antMatchers("/authors/*").hasAnyAuthority("ROLE_ADMIN", "ROLE_EDITOR")
                .and()
                .authorizeRequests().antMatchers("/genres/delete").hasRole("ADMIN")
                .and()
                .authorizeRequests().antMatchers("/genres/*").hasAnyRole("ADMIN", "EDITOR")
                .and()
                .authorizeRequests().antMatchers("/book/delete").hasRole("ADMIN")
                .and()
                .authorizeRequests().antMatchers("/book/comments").authenticated()
                .and()
                .authorizeRequests().antMatchers("/book/*").hasAnyRole("ADMIN", "EDITOR")
                .and()
                .authorizeRequests().antMatchers("/genres/*").authenticated()
                .and()
                .authorizeRequests().antMatchers("/**").denyAll()
                .and()
                .formLogin()
                .and()
                .rememberMe()
                .key("secret")
                .tokenValiditySeconds(60 * 60 * 24);
    }

//    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
