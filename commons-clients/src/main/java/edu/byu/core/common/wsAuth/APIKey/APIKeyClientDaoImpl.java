package edu.byu.core.common.wsAuth.APIKey;

import edu.byu.core.common.wsAuth.api.Credential;
import edu.byu.core.common.wsAuth.api.CredentialClient;
import edu.byu.core.common.wsAuth.dao.SharedSecretCredentialDAO;
import edu.byu.core.common.wsAuth.model.hibernate.SharedSecretCredential;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by tefreestone on 6/12/14.
 */
@Service("apiKeyClientDao")
public class APIKeyClientDaoImpl implements CredentialClient {

    private final Logger LOG = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Autowired(required = true)
    @Qualifier("sharedSecretCredentialDAO")
    private SharedSecretCredentialDAO sharedSecretCredentialDAO;

    @Override
    public SharedSecretCredential getCredential(final String personId) {
        if (personId != null) {
            SharedSecretCredential sharedSecretCredential = sharedSecretCredentialDAO.getActiveSharedSecretCredentialForIdentity(personId);
            if (sharedSecretCredential == null) {
                throw new IllegalStateException(personId + " does not have an API Key.");
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug(personId + " using WSID : " + sharedSecretCredential.getWsId());
                }
                return sharedSecretCredential;
            }
        } else
            throw new IllegalArgumentException("personId == null");
    }

    @Override
    public void expireCredential(final Credential credential) {
        throw new NotImplementedException();
    }
}
