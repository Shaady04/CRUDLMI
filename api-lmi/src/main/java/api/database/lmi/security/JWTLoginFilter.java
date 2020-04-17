package api.database.lmi.security;

import api.database.lmi.model.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*Configurando o gerenciador de autenticacao*/
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    /*Obrigamos a autenticar a URL*/
    protected JWTLoginFilter(String url, AuthenticationManager authenticationManager) {

        /*obriga a autenticar a url*/
        super( new AntPathRequestMatcher(url));
        /*gerenciador de autenticacao*/
        setAuthenticationManager(authenticationManager);
    }

    /*Retorna o usuario ao processar a autenticacao*/
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException, ServletException {

        Usuario user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);

        /*retorna usuario, senha e acessos*/
        return getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getSenha()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        new JWTTokenAutenticacaoService().addAuthentication(response, authResult.getName());
    }
}
