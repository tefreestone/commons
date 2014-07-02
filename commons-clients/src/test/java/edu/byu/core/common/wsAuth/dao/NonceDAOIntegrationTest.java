package edu.byu.core.common.wsAuth.dao;


import edu.byu.core.common.wsAuth.model.hibernate.WsNonce;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:Common-Context.xml",
        "classpath:CommonDaoHibernate-Context.xml",
        "classpath:TestOracleContext.xml",
//          "classpath:TestHSQLContext.xml",
        "classpath:TestContext.xml"
})

public class NonceDAOIntegrationTest extends AbstractTest {
    private final Logger LOG = LoggerFactory.getLogger(NonceDAOIntegrationTest.class);
    private NonceDAO nonceDAO;

    private int nonceThreshold = 2048;

    @Before
    public void setup() {
        super.before();
    }

    @After
    public void done() {
        super.done();
    }

    @Autowired
    public void setNonceDAO(NonceDAO nonceDAO) {
        this.nonceDAO = nonceDAO;
    }

    @Test
    public void testThreshold() {
        List<WsNonce> old = nonceDAO.getNonceByWSId(wsID);
        for (WsNonce tmp : old) {
            nonceDAO.consumeNonce(tmp.getNonceKey());
        }


        ArrayList<WsNonce> results = new ArrayList();
        try {
            for (int i = 0; i <= nonceThreshold + 1; i++) {
                WsNonce nonceResult = nonceDAO.getNewNonce(wsID);
                LOG.debug("i = " + i + " " + nonceResult);
                results.add(nonceResult);
            }
            Assert.assertTrue(false);
        } catch (IllegalStateException e) {
            Assert.assertTrue(true);
            LOG.debug("size : " + results.size());
            Assert.assertEquals(nonceThreshold + 1, results.size());
            for (WsNonce tmp : results) {
                nonceDAO.consumeNonce(tmp.getNonceKey());
            }

        }


    }


    @Test
    public void testGetExceptions() {
        WsNonce testNonce = nonceDAO.getNewNonce(wsID);
        WsNonce tmp = nonceDAO.getNonceByValue(null);
        Assert.assertNull(tmp);
        tmp = nonceDAO.getNonceByValue("crap");
        Assert.assertNull(tmp);
        tmp = nonceDAO.getNonceByKey(0);
        Assert.assertNull(tmp);
    }

    @Test
    public void testCrud() {
        WsNonce nonceResult;

        nonceResult = nonceDAO.getNewNonce(sessionCredential.getWsId());

        LOG.debug("runCrud nonce : " + nonceResult);
        Assert.assertNotNull(nonceResult);
        Assert.assertNotNull(nonceResult.getExpireDate());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 1);
        Assert.assertTrue(nonceResult.getExpireDate().before(cal.getTime()));

        nonceDAO.consumeNonce(nonceResult.getNonceKey());
        Assert.assertNull(nonceDAO.getNonceByKey(nonceResult.getNonceKey()));


        WsNonce nonceResult2;

        nonceResult2 = nonceDAO.getNewNonce(sessionCredential.getWsId());

        LOG.debug("runCrud nonce : " + nonceResult2);
        Assert.assertNotNull(nonceResult2);
        Assert.assertEquals(nonceResult2, nonceDAO.getNonceByValue(nonceResult2.getNonceValue()));
        nonceDAO.consumeNonce(nonceResult2.getNonceValue());
        Assert.assertNull(nonceDAO.getNonceByKey(nonceResult2.getNonceKey()));
    }

    public WsNonce getWsNonce(String wsId, String actor, String uri) {
        WsNonce result = new WsNonce();
        result.setWsId(wsId);
        result.setActor(actor);
        return result;
    }

//    private Date calcExpireDate() {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date());
//        cal.add(Calendar.MINUTE, 4);
//        return cal.getTime();
//    }

}
