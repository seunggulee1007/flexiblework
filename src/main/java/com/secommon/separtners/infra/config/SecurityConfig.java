package com.secommon.separtners.infra.config;

import com.secommon.separtners.infra.properties.AppProperties;
import com.secommon.separtners.infra.security.*;
import com.secommon.separtners.modules.account.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Jwt jwt;

    private final JwtTokenConfigure jwtTokenConfigure;

    private final JwtAccessDeniedHandler accessDeniedHandler;

    private final EntryPointUnauthorizedHandler unauthorizedHandler;
    private final AppProperties appProperties;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter(jwtTokenConfigure.getHeader(), jwt);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider( AccountService accountService) {
        return new JwtAuthenticationProvider(accountService);
    }

    @Override
    protected void configure ( HttpSecurity http ) throws Exception {
        http
            .httpBasic()
                .disable()                                              // rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
            .csrf()
                .disable()                                              // rest api이므로 csrf 보안이 필요없으므로 disable처리.
            .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(unauthorizedHandler)
            .and()
                .headers().frameOptions().sameOrigin()
            .and()
                .sessionManagement()
                .sessionCreationPolicy( SessionCreationPolicy.STATELESS) // jwt token으로 인증하므로 세션은 필요없으므로 생성안함.
            .and()
                .cors()
                // .configurationSource(request -> new CorsConfiguration(setCorsConfig()).applyPermitDefaultValues())
                .configurationSource(corsConfigurationSource())
            .and()
                .authorizeRequests()
                .antMatchers( "/*", "/api/sign-in", "/api/account/sign-up", "/api/account/check-email", "/api/account/check-email-token/**",
                        "/api/email-login", "/api/check-email-login", "/api/login-link", "/h2-console/**", "/api/sign/refresh-token/**", "/api/employee/positions").permitAll()
                .antMatchers( HttpMethod.GET, "/api/profile/*" ).permitAll()
                .antMatchers("/api/**")
                    .hasRole("USER")
            .and()
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins( List.of( appProperties.getHost() ) );
        config.setAllowedMethods( List.of("GET", "PUT", "DELETE", "POST", "OPTIONS", "HEAD") );
        config.setExposedHeaders( List.of("Access-Control-Allow-Headers", "Access-Control-Allow-Origin", "strict-origin-when-cross-origin") );
        config.setAllowedHeaders( List.of("*") );
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Override // ignore check swagger resource
    public void configure( WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**");
    }

}
