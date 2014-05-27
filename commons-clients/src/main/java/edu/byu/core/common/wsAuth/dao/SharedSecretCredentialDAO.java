package edu.byu.core.common.wsAuth.dao;


import edu.byu.core.common.wsAuth.model.hibernate.SharedSecretCredential;

import java.util.List;


public interface SharedSecretCredentialDAO {

    public List<SharedSecretCredential> getSharedSecretCredentialsForIdentity(String personId);

    public List<SharedSecretCredential> getExpiredSharedSecretCredentialsForIdentity(String personId);

    public SharedSecretCredential getActiveSharedSecretCredentialForIdentity(String personId);

    public SharedSecretCredential getActiveSharedSecretCredentialByWsId(String wsId);

    public SharedSecretCredential getSharedSecretCredentialByWsId(String wsId);

    public List<SharedSecretCredential> getSharedSecretCredentials();

    public List<SharedSecretCredential> getExpiredSharedSecretCredentials();

    public List<SharedSecretCredential> getActiveSharedSecretCredentials();

    public SharedSecretCredential saveSharedSecretCredential(SharedSecretCredential sharedSecretCredential);

    public SharedSecretCredential updateSharedSecretCredential(SharedSecretCredential sharedSecretCredential);
}
