package edu.byu.core.common.wsAuth.dao;


import edu.byu.core.common.wsAuth.model.hibernate.WsAuthenticationLog;
import edu.byu.core.common.wsAuth.model.util.WsAuthenticationLogEntryFactory;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:CommonDao-Context.xml",
        "classpath:TestContext.xml",
        "classpath:TestHSQLContext.xml"})

public class LogDAOIntegrationTest extends AbstractTest {

    private final Logger LOG = LoggerFactory.getLogger(LogDAOIntegrationTest.class);

    @Autowired
    @Qualifier("logDAO")
    private LogDAO logDAO;

    @Before
    public void before() {
        super.before();
    }

    @After
    public void done() {
        super.done();
    }


    @Test
    public void testCrud() {
        WsAuthenticationLog entry = WsAuthenticationLogEntryFactory.createValidAuthenticationLogEntry(ACTOR_ID, PERSON_ID, "BOGUS", "JUNK", URL);
        logDAO.log(entry);
        List<WsAuthenticationLog> result = logDAO.getLogEntriesByActor(entry.getActor());
        LOG.debug("result size = " + result.size());
        Assert.assertEquals(1, result.size());
        result = logDAO.getLogEntriesByPrincipal(entry.getPrincipal());
        LOG.debug("result size = " + result.size());
        Assert.assertEquals(1, result.size());
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
//        result = logDAO.getLogEntriesByPrincipal(entry.getPrincipal(), c.getTime(), new Date());
//        LOG.debug("result size = " + result.size());
//        Assert.assertEquals(1,result.size());

        logDAO.deleteLog(entry);
        result = logDAO.getLogEntriesByActor(entry.getActor());
        LOG.debug("result size = " + result.size());
        Assert.assertEquals(result.size(), 0);
    }
}
