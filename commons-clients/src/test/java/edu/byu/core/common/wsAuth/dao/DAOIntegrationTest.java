package edu.byu.core.common.wsAuth.dao;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:TestHSQLContext.xml",
})
@TransactionConfiguration(transactionManager = "wsAuthTransactionManager")
@Transactional(value = "wsAuthTransactionManager", readOnly = true)
public class DAOIntegrationTest {
    private final Logger LOG = LoggerFactory.getLogger(DAOIntegrationTest.class);

    @Resource(name = "wsAuthSessionFactory")
    private SessionFactory sessionFactory;

    @Test
    public void testHibernateMappings() throws Exception {
        Session currentSession = sessionFactory.getCurrentSession();
        Map metaData = sessionFactory.getAllClassMetadata();
        Set entries = metaData.entrySet();
        for (Object entry : entries) {
            String entityName = (String) ((Map.Entry) entry).getKey();
            LOG.info("Trying query for entity [" + entityName + "].");
            Criteria entityCriteria = currentSession.createCriteria(entityName).setMaxResults(1);
            assertNotNull(entityCriteria.list());
            LOG.info("Query successful.");
        }
    }

}