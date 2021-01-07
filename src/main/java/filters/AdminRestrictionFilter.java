package filters;

import beans.TypeUtilisateur;
import beans.Utilisateur;
import tools.Util;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminRestrictionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur)session.getAttribute(Util.ATT_SESSION_USER);

        if(utilisateur == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } else {
            if(utilisateur.getRole() == TypeUtilisateur.ADMIN) {
                filterChain.doFilter(request,response);
            } else {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }

        }
    }

    @Override
    public void destroy() {

    }
}
