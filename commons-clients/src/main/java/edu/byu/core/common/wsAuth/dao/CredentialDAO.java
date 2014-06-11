package edu.byu.core.common.wsAuth.dao;


import edu.byu.core.common.wsAuth.api.Credential;

import java.util.List;

public interface CredentialDAO {

    public List<Credential> getCredentialsForIdentity(String personId);

    public List<Credential> getExpiredCredentialsForIdentity(String personId);

    public List<Credential> getActiveCredentialsForIdentity(String personId);

    public List<Credential> getCredentials();

    public List<Credential> getExpiredCredentials();

    public List<Credential> getActiveCredentials();

}
