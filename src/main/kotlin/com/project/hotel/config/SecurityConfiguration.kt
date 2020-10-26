package com.project.hotel.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.session.data.mongo.config.annotation.web.reactive.EnableMongoWebSession

@Configuration
@EnableReactiveMethodSecurity
class SecurityConfiguration {
    @Bean
    open fun passwordEncoderAndMatcher(): PasswordEncoder {
        return object : PasswordEncoder {
            override fun encode(rawPassword: CharSequence?): String {
                return BCryptPasswordEncoder().encode(rawPassword)
            }
            override fun matches(rawPassword: CharSequence?, encodedPassword: String?): Boolean {
                return BCryptPasswordEncoder().matches(rawPassword, encodedPassword)
            }
        }
    }
    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity,
                               jwtAuthenticationManager: ReactiveAuthenticationManager,
                               jwtAuthenticationConverter: ServerAuthenticationConverter): SecurityWebFilterChain {
        val authenticationWebFilter = AuthenticationWebFilter(jwtAuthenticationManager)
        authenticationWebFilter.setServerAuthenticationConverter(jwtAuthenticationConverter)

        return http

                .authorizeExchange()
                .pathMatchers("/**")
//                .pathMatchers("/api/auth", "/api/activate/**", "/api/refresh", "/api/registration")
                    .permitAll()
                .pathMatchers("/api/user", "/api/animal/**")
                    .authenticated().and()
                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .formLogin()
                .loginPage("/api/auth")
                .and()
                .httpBasic()
                .disable()
                .csrf()
                .disable()
                .formLogin()
                .disable()
                .logout()
                .disable()
                .build()
    }
}