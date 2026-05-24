package de.binaerraum.queneau.app

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    @Value("\${app.security.username}") private val username: String,
    @Value("\${app.security.password}") private val password: String,
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authz ->
                // Statische Ressourcen und die Login-Seite erlauben, alle anderen Requests schützen
                authz
                    .requestMatchers(
                        "/css/**",
                        "/img/**",
                        "/script/**",
                        "/js/**",
                        "/webjars/**",
                        "/favicon.ico",
                        "/login"
                    ).permitAll()
                    .anyRequest().authenticated()
            }
            // Eigene Login-Seite verwenden
            .formLogin { form ->
                // Custom login page, processing URL and default success URL to avoid first-redirect issues
                form
                    .loginPage("/login")
                    .loginProcessingUrl("/perform_login")
                    .defaultSuccessUrl("/", true)
                    .permitAll()
            }
            // Optional: Logout zurück zur Login-Seite
            .logout { logout ->
                logout.logoutSuccessUrl("/login?logout").permitAll()
            }

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun userDetailsService(): UserDetailsService {
        val user: UserDetails = User
            .withUsername(username)
            .password(passwordEncoder().encode(password))
            .roles("USER")
            .build()

        return InMemoryUserDetailsManager(user)
    }
}
