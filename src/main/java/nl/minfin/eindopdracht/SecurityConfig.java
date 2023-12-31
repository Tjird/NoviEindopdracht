package nl.minfin.eindopdracht;

import nl.minfin.eindopdracht.repositories.EmployeesRepository;
import nl.minfin.eindopdracht.services.EmployeesDetailsService;
import nl.minfin.eindopdracht.services.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtService jwtService;
    private final EmployeesRepository employeesRepository;

    public SecurityConfig(JwtService service, EmployeesRepository employeesRepos) {
        this.jwtService = service;
        this.employeesRepository = employeesRepos;
    }

    public @Bean AuthenticationManager authManager(HttpSecurity http, PasswordEncoder encoder, UserDetailsService udService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(udService)
                .passwordEncoder(encoder)
                .and()
                .build();
    }

    public @Bean UserDetailsService userDetailsService() {
        return new EmployeesDetailsService(this.employeesRepository);
    }

    public @Bean PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public @Bean SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/employees").hasAuthority("ADMIN")
//                .requestMatchers(HttpMethod.GET, "/api/repairs/**/getReceipt").hasAuthority("CASHIER")
                .requestMatchers(HttpMethod.POST, "/api/inventory").hasAuthority("BACKOFFICE")
                .requestMatchers(HttpMethod.PUT, "/api/inventory").hasAuthority("BACKOFFICE")
                .requestMatchers(HttpMethod.DELETE, "/api/inventory").hasAuthority("BACKOFFICE")
                .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                .requestMatchers("/**").authenticated()
                .anyRequest().denyAll()
                .and()
                .addFilterBefore(new JwtRequestFilter(jwtService, userDetailsService()), UsernamePasswordAuthenticationFilter.class);

        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

}
