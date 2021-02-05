/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capitulo8.DAO;

import capitulo8.Modelo.Equipo;
import java.util.List;

/**
 *
 * @author BILAL
 */
public interface IEquipoDAO extends DAO<Equipo> {
    public List<Equipo> leerEquipos(String fichero);
}
