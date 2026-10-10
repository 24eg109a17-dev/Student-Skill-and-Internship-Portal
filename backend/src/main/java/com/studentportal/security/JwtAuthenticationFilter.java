package com.studentportal.security;
import jakarta.servlet.*; import jakarta.servlet.http.*; import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.security.core.userdetails.UserDetails; import org.springframework.security.web.authentication.WebAuthenticationDetailsSource; import org.springframework.stereotype.Component; import org.springframework.web.filter.OncePerRequestFilter;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
 private final JwtService jwt; private final CustomUserDetailsService uds;
 public JwtAuthenticationFilter(JwtService jwt,CustomUserDetailsService uds){this.jwt=jwt;this.uds=uds;}
 protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain)throws java.io.IOException,ServletException{
  String h=req.getHeader("Authorization");
  if(h!=null&&h.startsWith("Bearer ")){String t=h.substring(7); if(jwt.valid(t)){try{String email=jwt.username(t); UserDetails u=uds.loadUserByUsername(email); var a=new UsernamePasswordAuthenticationToken(u,null,u.getAuthorities()); a.setDetails(new WebAuthenticationDetailsSource().buildDetails(req)); SecurityContextHolder.getContext().setAuthentication(a);}catch(Exception ignored){}}}
  chain.doFilter(req,res);
 }
}
