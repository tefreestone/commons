package edu.byu.core.common.wsAuth.dao;


import edu.byu.core.common.wsAuth.model.hibernate.SharedSecretCredential;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("sharedSecretCredentialDAO")
@Transactional(value = "wsAuthTransactionManager", readOnly = true)
public class SharedSecretCredentialDAOImpl extends BaseAbstractDAO implements SharedSecretCredentialDAO {


    @Override
    public List<SharedSecretCredential> getSharedSecretCredentialsForIdentity(String personId) {
        return getSession().getNamedQuery("list.sharedSecretCredential.by.personId").setString("personId", personId).list();
    }

    @Override
    public List<SharedSecretCredential> getExpiredSharedSecretCredentialsForIdentity(String personId) {
        return getSession().getNamedQuery("list.sharedSecretCredential.expired.by.personId").setString("personId", personId).list();
    }

    @Override
    public SharedSecretCredential getActiveSharedSecretCredentialForIdentity(String personId) {
        return (SharedSecretCredential) getSession().getNamedQuery("get.sharedSecretCredential.active.by.personId").setString("personId", personId).uniqueResult();
    }

    @Override
    public SharedSecretCredential getActiveSharedSecretCredentialByWsId(String wsId) {
        return (SharedSecretCredential) getSession().getNamedQuery("get.sharedSecretCredential.active.by.wsId").setString("wsId", wsId).uniqueResult();
    }

    @Override
    public SharedSecretCredential getSharedSecretCredentialByWsId(String wsId) {
        return (SharedSecretCredential) getSession().getNamedQuery("get.sharedSecretCredential.by.wsId").setString("wsId", wsId).uniqueResult();
    }

    @Override
    public List<SharedSecretCredential> getSharedSecretCredentials() {
        return getSession().getNamedQuery("list.sharedSecretCredential").list();
    }

    @Override
    public List<SharedSecretCredential> getExpiredSharedSecretCredentials() {
        return getSession().getNamedQuery("list.sharedSecretCredential.expired").list();
    }

    @Override
    public List<SharedSecretCredential> getActiveSharedSecretCredentials() {
        return getSession().getNamedQuery("list.sharedSecretCredential.active").list();
    }

    @Override
    @Transactional(value = "wsAuthTransactionManager", readOnly = false)
    public SharedSecretCredential saveSharedSecretCredential(SharedSecretCredential sharedSecretCredential) {
        getSession().save(sharedSecretCredential);
        return sharedSecretCredential;
    }

    @Override
    @Transactional(value = "wsAuthTransactionManager", readOnly = false)
    public SharedSecretCredential updateSharedSecretCredential(SharedSecretCredential sharedSecretCredential) {
        getSession().update(sharedSecretCredential);
        return sharedSecretCredential;
    }
}
