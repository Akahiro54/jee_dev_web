package servlets;

import beans.Utilisateur;
import com.google.gson.Gson;
import dao.DAOFactory;
import dao.UtilisateurDAO;
import tools.FormTools;
import tools.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class Amis extends HttpServlet {

    private UtilisateurDAO utilisateurDAO;


    @Override
    public void init() throws ServletException {
        this.utilisateurDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getUtilisateurDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur)session.getAttribute(Util.ATT_SESSION_USER);
        ArrayList<Utilisateur> listeamis;
        listeamis = new ArrayList<>(utilisateurDAO.getAmis(utilisateur.getId()));
        req.setAttribute("listeamis", listeamis);

        if (utilisateur.getImage() != null)
        {
            String encode = Base64.getEncoder().encodeToString(utilisateur.getImage());
            req.setAttribute("imgBase", encode);
        }

        req.getRequestDispatcher("/user-restricted/amis.jsp").forward(req,resp);


    }

    //TODO
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur)session.getAttribute(Util.ATT_SESSION_USER);
        resp.setContentType("text/plain");
        String s = (String)req.getParameter("nickSearch");
        boolean validated = false;
        try {
            FormTools.validateNickname(s);
            FormTools.validateFieldSize(s);
            validated = true;
        }catch(Exception e) {}
        if(validated) {
            List<Utilisateur> utilisateurs = utilisateurDAO.searchNonAmis(utilisateur.getId(),s);
            Gson gson = new Gson();
            String result = gson.toJson(utilisateurs);
            resp.getWriter().write(result);
        } else {
            resp.getWriter().write("error");
        }
    }
}
