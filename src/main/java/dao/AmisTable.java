package dao;

import beans.Utilisateur;
import tools.PasswordHasher;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AmisTable {

    //IMAGE PAR DEFAUT DANS LA BDD
    public static ArrayList<Utilisateur> getAmis(int id) {
        ArrayList<Utilisateur> listeAmis = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = SQLConnector.getConnection().prepareStatement("SELECT u.nom, u.prenom, u.image, u.id FROM amis a INNER JOIN utilisateur u ON a.ami2 = u.id WHERE a.ami1 = ? ");
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()) {
                Utilisateur util = new Utilisateur(result.getInt(4),result.getString(1),result.getString(2),result.getBytes(3));
                listeAmis.add(util);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return listeAmis;
    }

}
