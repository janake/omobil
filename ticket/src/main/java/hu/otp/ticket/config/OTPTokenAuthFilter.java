package hu.otp.ticket.config;

import hu.otp.ticket.exception.RestException;
import hu.otp.ticket.model.core.Users;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class OTPTokenAuthFilter extends OncePerRequestFilter {

    private final String HEADER = "Authorization";
    private final String PREFIX = "User-Token ";
    private RestTemplate restTemplate;

    public OTPTokenAuthFilter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        validateTokenFormat(request);
        String token = getTokenFromRequest(request);
        Map<String, String> requestToken = Collections.singletonMap("token", token);
        validateToken(requestToken);
        setUpSpringAuthentication(requestToken);
        filterChain.doFilter(request, response);
    }

    private void validateToken(Map<String, String> requestToken) {
        String url = "http://CORE-MS/istokenvalid";
        HttpEntity<Map> request = new HttpEntity<>(requestToken);
        restTemplate.postForEntity(url, request, Boolean.class, 0);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        return request.getHeader(HEADER).replace(PREFIX, "");
    }

    private void validateTokenFormat(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(HEADER);
        if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX)) {
            throw new RestException(
                "A beérkezett kérésben szereplő felhasználó token lejárt vagy nem értelmezhető",
                "A felhasználói token lejárt vagy nem értelmezhető",
                "10051");
        }
    }

    private void setUpSpringAuthentication(Map<String, String> requestToken) {
        String url = "http://CORE-MS/getuserid";
        HttpEntity<Map> request = new HttpEntity<>(requestToken);
        ResponseEntity<Users> user = restTemplate.exchange(url, HttpMethod.POST,
                request, Users.class, 0);
        if (user.getStatusCode().equals(HttpStatus.OK)) {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    user,null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            throw new RestException(
              "invalid request",
              "invalid request",
              "500001"
            );
        }
    }
}
