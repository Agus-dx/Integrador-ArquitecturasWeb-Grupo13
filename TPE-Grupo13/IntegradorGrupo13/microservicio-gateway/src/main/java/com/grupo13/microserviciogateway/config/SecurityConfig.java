/**
 * 🔒 Configuración Central de Seguridad (SecurityConfig).
 *
 * Implementa la configuración de Spring Security para el API Gateway. Es el
 * componente más crítico de seguridad en la arquitectura de microservicios.
 * * Características Clave:
 * 1. Stateless (JWT): Configura la gestión de sesiones como `STATELESS`
 * (`SessionCreationPolicy.STATELESS`), indicando que no se almacenan estados
 * de usuario en el servidor, lo que es esencial para el uso de JWT.
 * 2. JWT Filter: Agrega el `JwtFilter` **antes** del filtro estándar de
 * autenticación de Spring Security (`UsernamePasswordAuthenticationFilter`),
 * asegurando que el token se valide en cada solicitud.
 * 3. `PasswordEncoder`: Define el algoritmo de hash (`BCryptPasswordEncoder`)
 * para codificar contraseñas antes de enviarlas al Microservicio de Usuarios/Cuentas.
 * 4. Control de Autorización (CRÍTICO): Define las reglas de acceso a los
 * microservicios (`requestMatchers`) mapeando rutas a roles (`AuthotityConstant._ADMIN`,
 * `_USUARIO`, etc.). Esto centraliza la **autorización** en el Gateway.
 */
package com.grupo13.microserviciogateway.config;

import com.grupo13.microserviciogateway.security.AuthotityConstant;
import com.grupo13.microserviciogateway.security.jwt.JwtFilter;
import com.grupo13.microserviciogateway.security.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    public SecurityConfig( TokenProvider tokenProvider ) {
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain( final HttpSecurity http ) throws Exception {
        http
            .csrf( AbstractHttpConfigurer::disable );
        http
            .sessionManagement( s -> s.sessionCreationPolicy( SessionCreationPolicy.STATELESS ) );
        http
            .securityMatcher("/api/**" )
            .authorizeHttpRequests( authz -> authz
                    .requestMatchers(HttpMethod.POST, "/api/token").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/registrar").permitAll()
                    // MS-PARADAS
                    .requestMatchers(HttpMethod.GET, "/api/paradas/**").hasAnyAuthority( AuthotityConstant._USUARIO, AuthotityConstant._ADMIN )
                    .requestMatchers("/api/paradas/**").hasAuthority( AuthotityConstant._ADMIN )
                    // MS-USUARIO Y CUENTA
                    .requestMatchers("/api/usuarios").hasAuthority( AuthotityConstant._ADMIN)
                    .requestMatchers(HttpMethod.PUT, "/api/usuarios/{idUsuario}/asignar-cuenta/{idCuenta}").hasAuthority( AuthotityConstant._USUARIO)
                    .requestMatchers("/api/cuentas/**").hasAuthority( AuthotityConstant._ADMIN)
                    // MS-MONOPATINES
                    .requestMatchers("/api/monopatines/reportes-mantenimiento/{kmMaximo}").hasAuthority( AuthotityConstant._ADMIN)
                    .requestMatchers(HttpMethod.GET, "/api/monopatines/**").hasAnyAuthority( AuthotityConstant._USUARIO, AuthotityConstant._ADMIN)
                    .requestMatchers("/api/monopatines/**").hasAuthority( AuthotityConstant._ADMIN )
                    // MS-FACTURAS Y TARIFA SOLO ADMINS
                    .requestMatchers("/api/facturas/**").hasAuthority( AuthotityConstant._ADMIN )
                    .requestMatchers("/api/tarifas/**").hasAuthority( AuthotityConstant._ADMIN )
                    // MS-VIAJES
                    .requestMatchers("/api/viajes/reportes").hasAuthority( AuthotityConstant._ADMIN )
                    .requestMatchers("/api/viajes/**").hasAnyAuthority( AuthotityConstant._USUARIO, AuthotityConstant._ADMIN )
                    .anyRequest().authenticated()
            )
            .httpBasic( Customizer.withDefaults() )
            .addFilterBefore( new JwtFilter( this.tokenProvider ), UsernamePasswordAuthenticationFilter.class );
        return http.build();
    }

}
