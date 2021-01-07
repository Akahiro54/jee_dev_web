package dao;

import beans.Lieu;
import java.util.List;


public interface LieuDAO {

    boolean placeExistsById(int idPlace);

    boolean placeExistsByName(String namePlace);

    List<Lieu> getAllPlaces();

    boolean add(Lieu place);

    boolean update(Lieu place, Object... data);

    boolean delete(int idPlace);

    Lieu get(int idPlace);

}
