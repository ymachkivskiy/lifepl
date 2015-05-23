package pl.edu.agh.integr10s.persistence.db.dao;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import pl.edu.agh.integr10s.persistence.db.entities.Test;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public class TestDAO {
    private SessionFactory sessionFactory;

    public TestDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Test> getTests() {
        List<Test> result = sessionFactory.getCurrentSession().createCriteria(Test.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

        return result;
    }

    @Transactional
    public void store(Test test) {
        sessionFactory.getCurrentSession().persist(test);
    }

}
