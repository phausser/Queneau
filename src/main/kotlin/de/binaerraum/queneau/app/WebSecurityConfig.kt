package de.binaerraum.queneau.app

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class WebSecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests({ authz ->
                authz
                    .anyRequest().authenticated()
            })
            .httpBasic {
                it.realmName("Queneau Translation App")
            }

        return http.build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService? {
        val user: UserDetails? = User
            .withUsername("pat")
            .password("{noop}pat") // {noop} bedeutet, dass das Passwort unverschlüsselt ist
            .roles("USER")
            .build()

        return InMemoryUserDetailsManager(user)
    }
}
