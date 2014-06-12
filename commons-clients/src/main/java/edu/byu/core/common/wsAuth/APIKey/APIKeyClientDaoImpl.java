package edu.byu.core.common.wsAuth.APIKey;

import edu.byu.core.common.wsAuth.api.Credential;
import edu.byu.core.common.wsAuth.api.CredentialClient;
import edu.byu.core.common.wsAuth.dao.SharedSecretCredentialDAO;
import edu.byu.core.common.wsAuth.model.hibernate.SharedSecretCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by tefreestone on 6/12/14.
 */
@Service("apiKeyClientDao")
public class APIKeyClientDaoImpl implements CredentialClient {

    @Autowired(required = true)
    @Qualifier("sharedSecretCredentialDAO")
    private SharedSecretCredentialDAO sharedSecretCredentialDAO;

    @Override
    public SharedSecretCredential getCredential(final String personId) {
        if (personId != null) {
            SharedSecretCredential sharedSecretCredential = sharedSecretCredentialDAO.getActiveSharedSecretCredentialForIdentity(personId);

            return sharedSecretCredential;
        } else
            throw new IllegalArgumentException("personId == null");
    }

    @Override
    public void expireCredential(final Credential credential) {
        throw new NotImplementedException();
    }
}
