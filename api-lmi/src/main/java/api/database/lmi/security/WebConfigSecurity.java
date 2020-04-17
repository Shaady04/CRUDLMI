package api.database.lmi.security;

import api.database.lmi.service.ImplementacaoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


/*Mapeia URL, enderecos, autoriza ou bloqueia acessos a URL*/
@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private ImplementacaoUserDetailsService implementacaoUserDetailsService;

    /*Configuracao as solicitacoes de acesso por Http*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /*Ativando a protecao contra usuário que não estão validandos por token*/
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

        /*Ativando a permissao para acesso a pagina inicial do sistema*/
        .disable().authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/index").permitAll()

        /*URL de Logout - redireciona apos o user deslogar do sistema*/
        .anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")

        /*Mapeia URL de Logout e invalida o usuario*/
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

        /*Filtra requisicoes de login para autenticação*/
        .and().addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
                UsernamePasswordAuthenticationFilter.class)

        /*Filtra demais requisições para verificar a presenca de TOKE JWT no HEADER HTTP*/
        .addFilterBefore(new JWTAPIAutenticacaoFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        /*Service que irá consultar o usuario no banco de dados*/
        auth.userDetailsService(implementacaoUserDetailsService).
                /*padrao de condificacao de senha*/
                passwordEncoder(new BCryptPasswordEncoder());
    }
}
