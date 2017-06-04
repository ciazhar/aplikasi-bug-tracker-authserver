package com.ciazhar.bugtracker.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 * Created by ciazhar on 12/02/17.
 */

@EnableWebSecurity(debug = true)
public class KonfigurasiSecurity extends WebSecurityConfigurerAdapter{

    private static final String SQL_LOGIN = "select username, password, enabled "
            + "from user where username = ?";
    private static final String SQL_PERMISSION =
            "select u.username as username, p.nama_permission as authority "
                    + "from user u "
                    + "inner join user_role ur on u.id_user = ur.id_user "
                    + "inner join role r on ur.id_role = r.id_role "
                    + "inner join role_permission rp on rp.id_role = r.id_role "
                    + "inner join permission p on rp.id_permission = p.id_permission "
                    + "where u.username = ?";

    @Autowired
    private DataSource dataSource;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(
        );
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
          auth
                  .jdbcAuthentication().dataSource(dataSource)
                  .usersByUsernameQuery(SQL_LOGIN)
                  .authoritiesByUsernameQuery(SQL_PERMISSION)
                  .passwordEncoder(passwordEncoder());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/css/**").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").permitAll()
                .and().logout().permitAll();
    }
}
