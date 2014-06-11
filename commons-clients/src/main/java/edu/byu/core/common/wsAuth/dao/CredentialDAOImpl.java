package edu.byu.core.common.wsAuth.dao;


import edu.byu.core.common.wsAuth.api.Credential;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("credentialDAO")
@Transactional(value = "wsAuthTransactionManager", readOnly = true)
public class CredentialDAOImpl extends BaseAbstractDAO implements CredentialDAO {


    @Override
    public List<Credential> getCredentialsForIdentity(String personId) {
        return getSession().getNamedQuery("list.credential.by.personId").setString("personId", personId).list();
    }

    @Override
    public List<Credential> getExpiredCredentialsForIdentity(String personId) {
        return getSession().getNamedQuery("list.credential.expired.by.personId").setString("personId", personId).list();
    }

    @Override
    public List<Credential> getActiveCredentialsForIdentity(String personId) {
        return getSession().getNamedQuery("list.credential.active.by.personId").setString("personId", personId).list();
    }

    @Override
    public List<Credential> getCredentials() {
        return getSession().getNamedQuery("list.credential").list();
    }

    @Override
    public List<Credential> getExpiredCredentials() {
        return getSession().getNamedQuery("list.credential.expired").list();
    }

    @Override
    public List<Credential> getActiveCredentials() {
        return getSession().getNamedQuery("list.credential.active").list();
    }

}
