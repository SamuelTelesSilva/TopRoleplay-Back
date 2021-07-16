package com.samaniasoft.toproleplay.infra.security;

import com.samaniasoft.toproleplay.cors.CorsConfig;
import com.samaniasoft.toproleplay.infra.security.jwt.JwtAuthenticationFilter;
import com.samaniasoft.toproleplay.infra.security.jwt.JwtAuthorizationFilter;
import com.samaniasoft.toproleplay.infra.security.jwt.handler.AccessDeniedHandler;
import com.samaniasoft.toproleplay.infra.security.jwt.handler.UnauthorizedHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private UnauthorizedHandler unauthorizedHandler;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        AuthenticationManager authManager = authenticationManager();

        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/login", "/api/streamer/**", "/api/clipe","/api/usuarios", "/api/grupo", "/api/cidade", "/api/noticia").permitAll()
                .antMatchers(HttpMethod.GET, "/api/usuarios", "/api/streamer/**","/api/clipe", "/api/grupo", "/api/cidade", "/api/noticia").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/streamer/**", "/api/usuarios/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/usuarios/**", "/api/streamer/**").permitAll()
                .antMatchers("/**/*").denyAll()
                .anyRequest().authenticated()
                .and().cors()
                .and().csrf().disable()
                .addFilter(new CorsConfig())
                .addFilter(new JwtAuthenticationFilter(authManager))
                .addFilter(new JwtAuthorizationFilter(authManager, userDetailsService))
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

}