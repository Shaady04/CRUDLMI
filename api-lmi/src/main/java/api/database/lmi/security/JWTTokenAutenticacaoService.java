package api.database.lmi.security;

import api.database.lmi.ApplicationContextLoad;
import api.database.lmi.model.Usuario;
import api.database.lmi.repository.UsuarioRepository;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Service
@Component
public class JWTTokenAutenticacaoService {

    /*tempo de validade do token: 2 dias*/
    private static final long EXPIRATION_TIME = 172800000;//milisegundos

    /*Uma senha unica para compor a autenticacao e ajudar na seguranca*/
    private static final String SECRET = "*SenhaExtremamenteSecreta";

    /*Prefixo padrao de token*/
    private static final String TOKEN_PREFIX = "Bearer";

    /**/
    private static final String HEADER_STRING = "Authorization";

    /*Gerando token de autenticacao e adicionando o cabecalho e resposta Http*/
    public void addAuthentication(HttpServletResponse response, String username) throws IOException {

        /*MOntagem do token*/
        String JWT = Jwts.builder(). /*chama o gerador de toke*/
                setSubject(username)./*add o usuario*/
                setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))./*tempo de expiracao*/
                signWith(SignatureAlgorithm.HS512, SECRET).compact();/*compactacao e algoritmo de geracao de senha*/

        /*Junta token com o prefixo*/
        String token = TOKEN_PREFIX + " " + JWT; /*Bearer 074ds32043ds2094c343c0932cc2093*/

        /*Adiciona no cabecalho http*/
        response.addHeader(HEADER_STRING, token); /*Authorization: Bearer 074ds32043ds2094c343c0932cc2093 */

        /*Escreve token como resposta no corpo http*/
        response.getWriter().write("{\"Authorization\": \""+token+"\"}");
    }

    /*Retorna o usuario validado com token ou caso nao seja valido retorna null*/
    public Authentication getAuthentication(HttpServletRequest request) {

        /*Pega o token enviado no cabealho http*/

        String token = request.getHeader(HEADER_STRING);

        if(token != null) {
            /*Faz a validacao do tokne do usuario na requisiccao*/
            String user = Jwts.parser().setSigningKey(SECRET) /*Bearer 074ds32043ds2094c343c0932cc2093*/
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, "")) /* 074ds32043ds2094c343c0932cc2093*/
                    .getBody().getSubject();/*Caio Assuncao*/
            if(user != null) {

                Usuario usuario = ApplicationContextLoad.getApplicationContext()
                        .getBean(UsuarioRepository.class).findUserByLogin(user);

                if(usuario != null) {
                    return new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getSenha(), usuario.getAuthorities());
                } else{
                    return null; //nao autorizado
                }
            }else {
                return null; //nao autorizado
            }
        }else {
            return null; // nao autorizado
        }

    }
}
