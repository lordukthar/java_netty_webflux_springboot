package com.example.demo.filter;

import org.springframework.web.filter.OncePerRequestFilter;

public class AuthenticationFilter{

}
/*

Not used by NEtty
public class AuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws
            ServletException, IOException {
        String usrName = request.getHeader(“userName”);
        logger.info("Successfully authenticated user  " +
                userName);
        filterChain.doFilter(request, response);
    }
}

 */