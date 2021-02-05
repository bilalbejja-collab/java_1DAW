/**
 *
 */
package org.iesjaroso.daw.capitulo8tutorialdaobasic.cachedrowset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import org.iesjaroso.daw.capitulo8tutorialdaobasic.singleton.*;

/**
 * @author Openwebinars
 *
 */
public class EjemploCachedRowSet {

    /**
     * @param args
     */
    public static void main(String[] args) {

        //RowSetFactory myRowSetFactory = null;
        //CachedRowSet rowSet;

        try(CachedRowSet rowSet =  RowSetProvider.newFactory().createCachedRowSet()){
            //rowSet =  RowSetProvider.newFactory().createCachedRowSet();
            //myRowSetFactory = RowSetProvider.newFactory();
            //rowSet = myRowSetFactory.createCachedRowSet();

            // Podemos crear el CachedRowSet de forma id�ntica
            rowSet.setUrl(DBConnection2.JDBC_URL);
            
            rowSet.setUsername(DBConnection2.USERNAME);
            rowSet.setPassword(DBConnection2.PASSWORD);

            rowSet.setCommand("SELECT * FROM empleados");
            // Indicamos los n�meros de columnas que forman la PK
            rowSet.setKeyColumns(new int[]{1});

            // Se abre una conexi�n puntual para extraer los datos
            // y rellenar el CachedRowSet
            rowSet.execute();

            // o podr�amos usar las siguientes l�neas
//			ResultSet rs = DBConnection.getConnection().createStatement().executeQuery("SELECT * FROM empleados");
//			rowSet.populate(rs);
            // Imprimimos todos los registros
            while (rowSet.next()) {
                System.out.println(rowSet.getDate("fecha_nacimiento"));
                System.out.printf("%d %s %s\t\t (%s) - %.2f� %n", rowSet.getInt("id"),
                        rowSet.getString("nombre"), rowSet.getString("apellidos"),
                            
                        rowSet.getDate("fecha_nacimiento").toLocalDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),                        
                        rowSet.getFloat("sueldo"));
            }
            System.out.println("");

            // En este caso, vamos a subir el sueldo de todos los empleados
            rowSet.beforeFirst();
            while (rowSet.next()) {
                rowSet.updateFloat("sueldo", rowSet.getFloat("sueldo") * 1.1f);
                rowSet.updateRow();
            }

            // Imprimimos todos los registros de nuevo
            rowSet.beforeFirst();
            while (rowSet.next()) {
                System.out.printf("%d %s %s\t\t (%s) - %.2f€ %n", rowSet.getInt("id"),
                        rowSet.getString("nombre"), rowSet.getString("apellidos"),
                        rowSet.getDate("fecha_nacimiento").toLocalDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),
                        rowSet.getFloat("sueldo"));
            }
            System.out.println("");

            // �Estos cambios han sido consolidados en la base de datos?
            // NO, hasta que no ejecutamos acceptChanges.
            // �OJO! Necesitamos una conexi�n con auto-commit = false
            // para poder usar este m�todo.
            rowSet.acceptChanges(DBConnection2.getConnection());

            // Cerramos el cursor
            rowSet.close();

        } catch (SQLException e) {
            e.getMessage();
        }

    }

}
