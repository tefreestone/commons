package edu.byu.core.common;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA.
 * User: tef2
 * Date: 7/29/13
 * Time: 4:15 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:TestContext.xml",
        "classpath:AbstractBasedWSClientDAOContext.xml"
})
public class AbstractApiKeyWSClientTest {

    @Test
    public void testCase() {
//        this.makeWSCall();
    }


}
