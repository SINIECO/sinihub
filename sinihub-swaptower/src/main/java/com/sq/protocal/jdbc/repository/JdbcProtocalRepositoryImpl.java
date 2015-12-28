package com.sq.protocal.jdbc.repository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * User: shuiqing
 * Date: 15/12/21
 * Time: 上午11:16
 * Email: annuus.sq@gmail.com
 * GitHub: https://github.com/shuiqing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
public class JdbcProtocalRepositoryImpl {

    private EntityManagerFactory emf;

    @PersistenceUnit
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

}
