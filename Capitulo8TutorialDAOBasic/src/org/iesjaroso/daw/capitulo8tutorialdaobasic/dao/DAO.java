package org.iesjaroso.daw.capitulo8tutorialdaobasic.dao;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {

    void insert(T e) throws SQLException;

    List<T> findAll()throws SQLException;

    T findByPk(int id)throws SQLException;

    void delete(T e)throws SQLException;
    //void delete(int id);

    void update(T e)throws SQLException;
}
