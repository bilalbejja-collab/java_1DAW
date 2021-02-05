/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesjaroso.daw.dao;

import java.util.List;

/**
 *
 * @author franmatias
 * @param <V>
 * @param <K>
 */
public interface DAO<V,K> {
    K get(V id);
    List<K> getAll();
    boolean insert(K item);
    boolean update(K elem);
    boolean delete(K elem);
    boolean deleteAll();
}
