package com.spring.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;

import com.spring.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


public class TokenAuthenticationService {

    private long EXPIRATIONTIME = 1000 * 60 * 60 * 24 * 10; // 10 days
    private String secret = "ThisIsASecret";
    private String tokenPrefix = "Bearer";
    private String headerString = "Authorization";
    
    private  TokenHandler tokenHandler;
    
    public void addAuthentication(HttpServletResponse response, String username)
    {
        // We generate a token now.
        String JWT = Jwts.builder()
                    .setSubject(username)
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                    .signWith(SignatureAlgorithm.HS512, secret)
                    .compact();
        response.addHeader(headerString,tokenPrefix + " "+ JWT);
    }
    
    
    public String addAuthentication( String email)
    {
        // We generate a token now.
        String JWT = Jwts.builder()
                    .setSubject(email)
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                    .signWith(SignatureAlgorithm.HS512, secret)
                    .compact();
        return JWT;
    }

    public Authentication getAuthentication(HttpServletRequest request)
    {
        String token = request.getHeader(headerString);
        if(token != null)
        {
            // parse the token.
            String email = Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject();
            if(email != null) // we managed to retrieve a user
            {
                return new AuthenticatedUser(email);
            }
        }
        return null;
    }
    
    public TokenAuthenticationService() {
    	super();
    }
    
    public TokenAuthenticationService(UserService userService) {
        tokenHandler = new TokenHandler(userService);
    }
    
    public TokenHandler getTokenHandler() {
		return tokenHandler;
	}

}
