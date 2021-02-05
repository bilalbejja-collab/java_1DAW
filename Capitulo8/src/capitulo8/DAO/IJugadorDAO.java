/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capitulo8.DAO;

import capitulo8.Modelo.Jugador;
import java.util.List;

/**
 *
 * @author BILAL
 */
public interface IJugadorDAO extends DAO<Jugador> {
    public List<Jugador> leerJugadores(String fichero);
}
