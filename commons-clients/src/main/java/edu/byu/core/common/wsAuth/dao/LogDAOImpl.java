package edu.byu.core.common.wsAuth.dao;


import edu.byu.core.common.wsAuth.model.hibernate.WsAuthenticationLog;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Repository("logDAO")
@Transactional(value = "wsAuthTransactionManager", readOnly = true)
public class LogDAOImpl extends BaseAbstractDAO implements LogDAO {
    @Resource(name = "wsAuthSessionFactory")
    private SessionFactory sessionFactory;

    @Override
    @Transactional(value = "wsAuthTransactionManager", readOnly = false)
    public void log(WsAuthenticationLog logEntry) {
        logEntry.setLogDate(new Date());
        sessionFactory.getCurrentSession().save(logEntry);
    }

    @Override
    @Transactional(value = "wsAuthTransactionManager", readOnly = false)
    public void deleteLog(WsAuthenticationLog logEntry) {
        getSession().delete(logEntry);
    }

    @Override
    public List<WsAuthenticationLog> getLogEntriesByActor(String actor) {
        return getSession().getNamedQuery("get.log.by.actor").setString("actor", actor).list();
    }

    @Override
    public List<WsAuthenticationLog> getLogEntriesByPrincipal(String principal) {
        return getSession().getNamedQuery("get.log.by.principal").setString("principal", principal).list();
    }

    @Override
    public List<WsAuthenticationLog> getLogEntriesByPrincipal(String principal, Date startDate, Date endDate) {
        return getSession().getNamedQuery("get.log.by.principal.date.range").setString("principal", principal).setDate("startDate", startDate).setDate("endDate", endDate).list();
    }

    @Override
    public List<WsAuthenticationLog> getLogEntriesByCredentialValue(String credentialType, String credentialValue) {
        return getSession().getNamedQuery("get.log.by.credentialType.credentialValue").setString("credentialType", credentialType).setString("credentialValue", credentialValue).list();
    }

    @Override
    public List<WsAuthenticationLog> getLogEntriesByCredentialValue(String credentialType, String credentialValue, Date logDate) {
        return getSession().getNamedQuery("get.log.by.credentialType.credentialValue.logDate").setString("credentialType", credentialType).setString("credentialValue", credentialValue).setDate("logDate", logDate).list();
    }

    @Override
    public List<WsAuthenticationLog> getInvalidAuthentications(String principal) {
        return getSession().getNamedQuery("get.invalid.authentications").setString("principal", principal).list();
    }


}
