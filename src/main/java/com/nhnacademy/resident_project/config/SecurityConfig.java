package com.nhnacademy.resident_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity(debug = true)
@EnableMethodSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(req -> req.requestMatchers("/").authenticated()
                        .anyRequest()
                        .permitAll())
                .oauth2Login(o -> o.clientRegistrationRepository(clientRegistrationRepository())
                        .authorizedClientService(authorizedClientService()))
                .formLogin()
                    .and()
                .logout(l -> l.logoutUrl("/logout")
                        .invalidateHttpSession(true))
                .csrf()
                    .disable()
                .sessionManagement(s -> s.sessionFixation()
                        .none())
                .build();
    }


    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = User.withUsername("admin")
                .password("{noop}admin")
                .authorities("ROLE_ADMIN")
                .build();

        UserDetails member = User.withUsername("member")
                .password("{noop}member")
                .authorities("ROLE_MEMBER")
                .build();

        UserDetails guest = User.withUsername("guest")
                .password("{noop}guest")
                .authorities("ROLE_GUEST")
                .build();

        return new InMemoryUserDetailsManager(admin, member, guest);
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(github(), naver());
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
    }

    public ClientRegistration github() {
        return CommonOAuth2Provider.GITHUB.getBuilder("github")
                .userNameAttributeName("name")
                .clientId("c1bdfce87dbf5de8a27b")
                .clientSecret("6ba9890a56250579be8a32700b3ddf653f8df035")
                .build();
    }

    public ClientRegistration naver() {
        return ClientRegistration.withRegistrationId("naver")
                .clientId("HDVvfyB9yXd96Hl6B1V6")
                .clientSecret("ylCfvwj96I")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("name", "email", "profile_image")
                .redirectUri("{baseUrl}/{action}/oauth2/code/{registrationId}")
                .authorizationUri("https://nid.naver.com/oauth2.0/authorize")
                .tokenUri("https://nid.naver.com/oauth2.0/token")
                .userInfoUri("https://openapi.naver.com/v1/nid/me")
                .userNameAttributeName("response")
                .build();
    }

}
