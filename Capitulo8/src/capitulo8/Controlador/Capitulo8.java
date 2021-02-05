package capitulo8.Controlador;

import Capitulo8.Vistas.VistaMain;
import capitulo8.DAO.EquipoDAOCSV;
import capitulo8.DAO.JugadorDAOCSV;
import capitulo8.Modelo.Equipo;
import capitulo8.Modelo.Jugador;
import capitulo8.Utils.UtilsBD;
import capitulo8.Utils.ConfigDB;
import java.sql.SQLException;

/**
 *
 * @author BILAL
 */
public class Capitulo8 {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {
//        JugadorDAOCSV.leerJugadores("jugadores.csv")
//                .forEach(j -> System.out.println(j));
//        EquipoDAOCSV.leerEquipos("equipos.csv")
//                .forEach(e -> System.out.println(e));

        System.out.println(ConfigDB.loadConfigFile("config.properties"));
//
        UtilsBD.getInstance().init();
//
//        //añadir todo los jugadores a la tabla jugadores
//        JugadorDAOCSV jugador = new JugadorDAOCSV();
//        int cant = jugador.leerJugadores("jugadores.csv").size();
//        for (int i = 0; i < cant; i++) {
//            Jugador j = jugador.leerJugadores("jugadores.csv").get(i);
//            j.setId(j.getId());
//            JugadorDAOCSV.getInstance().save(j);
//        }
//////
////        //añadir todo los jugadores a la tabla equipo
//        EquipoDAOCSV equipo = new EquipoDAOCSV();
//        int cantEq = equipo.leerEquipos("equipos.csv").size();
//        for (int i = 0; i < cantEq; i++) {
//            Equipo e = equipo.leerEquipos("equipos.csv").get(i);
//            e.setId(e.getId());
//            EquipoDAOCSV.getInstance().save(e);
//        }
//
        VistaMain.main(args);
    }

}
