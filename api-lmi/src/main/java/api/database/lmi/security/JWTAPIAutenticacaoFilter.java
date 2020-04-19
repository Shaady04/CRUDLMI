package api.database.lmi.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*Filtro onde todas as requisicoes serao capturadas para autenticar*/
public class JWTAPIAutenticacaoFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        /*estabele a autenticacao para a requisicao*/
        Authentication authentication = new JWTTokenAutenticacaoService().
                getAuthentication((HttpServletRequest) request, (HttpServletResponse) response);

        /*Coloca o processo de autenticao no spring security*/
        SecurityContextHolder.getContext().setAuthentication(authentication);

        /*Continua o processo*/
        filterChain.doFilter(request, response);
    }
}
