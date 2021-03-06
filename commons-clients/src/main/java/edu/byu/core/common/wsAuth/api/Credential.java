package edu.byu.core.common.wsAuth.api;

import java.util.Date;

public interface Credential {

    public String getPersonId();

    public String getId();

    public boolean isExpired();

    public void expire();

    public Date getExpirationDate();
}
