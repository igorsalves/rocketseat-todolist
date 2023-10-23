package br.com.igorsalves.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.igorsalves.todolist.user.IUserRepository;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

  @Autowired
  private IUserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
      var servletPath = request.getServletPath();

      if(servletPath.equals("/tasks")) {
        var authorization = request.getHeader("Authorization");
      
        if (authorization == null) {
          response.sendError(401);
        } else {
          var authEncoded = authorization.substring("Basic".length()).trim();
          byte[] authDecoded = Base64.getDecoder().decode(authEncoded);

          String authString = new String(authDecoded);

          String[] credtentials = authString.split(":");
          String username = credtentials[0];
          String password = credtentials[1];

          var user = userRepository.findByUsername(username);

          if (user == null) {
            response.sendError(401);
          } else {
            var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
            
            if (passwordVerify.verified) {
              request.setAttribute("idUser", user.getId());
              filterChain.doFilter(request, response);
            }
          }
        }
      } else {
        filterChain.doFilter(request, response);
      }
    }
}
