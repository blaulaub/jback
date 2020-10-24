package ch.patchcode.jback.api;

import ch.patchcode.jback.presentation.ApiAuthority;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        // who can access what (the order here is important)
        http.authorizeRequests()

                .antMatchers(HttpMethod.GET, "/api/v1/session").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/session/roles").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/session/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/session/logout").authenticated()

                .antMatchers(HttpMethod.POST, "/api/v1/registration").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/registration/????????-????-????-????-????????????").permitAll()

                .antMatchers(HttpMethod.GET, "/api/v1/persons/????????-????-????-????-????????????").authenticated()
                .antMatchers(HttpMethod.POST, "/api/v1/persons").hasAuthority(ApiAuthority.CAN_CREATE_CLIENT_PERSON.toString())
                .antMatchers(HttpMethod.POST, "/api/v1/persons/with-password").hasAuthority(ApiAuthority.CAN_CREATE_OWN_PERSON.toString())

                .antMatchers(HttpMethod.GET, "/api/v1/clubs").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/clubs").hasAuthority(ApiAuthority.CAN_CREATE_CLUB.toString())
                .antMatchers(HttpMethod.GET, "/api/v1/clubs/????????-????-????-????-????????????").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/clubs/????????-????-????-????-????????????/members").hasAuthority(ApiAuthority.CAN_ASSIGN_MEMBER.toString())
                .antMatchers(HttpMethod.PUT, "/api/v1/clubs/????????-????-????-????-????????????/admins").hasAuthority(ApiAuthority.CAN_ASSIGN_ADMIN.toString())

                // API remainder is closed to anybody else
                .antMatchers("/api/v1/**/*").denyAll()

                // swagger is open (that looks like wide open)
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/*").permitAll()
                .antMatchers("/webjars/springfox-swagger-ui/*").permitAll()
                .antMatchers("/v2/api-docs/*").permitAll()

                // otherwise the Angular SPA takes over
                .anyRequest().permitAll();

        // how to login
        http.formLogin().disable();
    }
}
