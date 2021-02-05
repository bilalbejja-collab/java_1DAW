/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capitulo8.DAO;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author BILAL
 * @param <T>
 */
public interface DAO<T> {
    Optional<T> get(int id);
    Optional<List<T>> getAll();    
    boolean save(T t);
    boolean update(T t);
    boolean delete(T t);
}
