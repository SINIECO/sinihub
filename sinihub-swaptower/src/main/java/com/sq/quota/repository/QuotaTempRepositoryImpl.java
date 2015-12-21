package com.sq.quota.repository;

import com.sq.quota.domain.QuotaTemp;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import java.util.List;

/**
 * 指标模板impl
 * User: shuiqing
 * Date: 15/12/21
 * Time: 下午3:21
 * Email: annuus.sq@gmail.com
 * GitHub: https://github.com/shuiqing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
public class QuotaTempRepositoryImpl {

    private EntityManagerFactory emf;
    private EntityManager em;
    @PersistenceUnit
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List<QuotaTemp> listQuotaTempByMpsyscode(final String sysCode){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        StringBuilder nativeSql = new StringBuilder();
        nativeSql.append(" select it.* from t_indicatortemp it, t_mesuringpoint mp ")
                .append(" where it.indicatorCode = mp.targetCode and mp.sysCode = ?1");
        Query query = em.createNativeQuery(nativeSql.toString(),QuotaTemp.class);
        query.setParameter(1, sysCode);

        List<QuotaTemp> quotaTempList = query.getResultList();
        em.getTransaction().commit();
        em.close();

        return quotaTempList;
    }
}
