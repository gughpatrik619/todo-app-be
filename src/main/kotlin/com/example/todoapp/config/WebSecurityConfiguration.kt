package com.example.todoapp.config

import com.example.todoapp.model.ERole
import com.example.todoapp.security.AuthEntryPointJwt
import com.example.todoapp.security.AuthTokenFilter
import com.example.todoapp.service.UserDetailsServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
class WebSecurityConfiguration(
    private val userDetailsService: UserDetailsServiceImpl,
    private val unauthorizedHandler: AuthEntryPointJwt
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun authenticationJwtTokenFilter() = AuthTokenFilter()

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager = super.authenticationManagerBean()

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    override fun configure(http: HttpSecurity) {
        http
            .cors()
            .and()
            .csrf()
            .disable()
            .exceptionHandling()
            .authenticationEntryPoint(unauthorizedHandler)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/api/**")
            .hasAnyRole(
                ERole.ROLE_USER.shortName,
                ERole.ROLE_MODERATOR.shortName,
                ERole.ROLE_ADMIN.shortName
            ) // auth
            .antMatchers("/auth/test/**")
            .permitAll()
            .antMatchers("/auth/**")
            .permitAll() // swagger
            .antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/webjars/**"
            )
            .permitAll()
            .anyRequest()
            .authenticated()

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }

    @Autowired
    fun configureGlobal(authenticationManagerBuilder: AuthenticationManagerBuilder) {
        authenticationManagerBuilder.userDetailsService<UserDetailsService>(userDetailsService)
            .passwordEncoder(passwordEncoder())
    }
}