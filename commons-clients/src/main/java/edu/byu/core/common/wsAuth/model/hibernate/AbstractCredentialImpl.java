package edu.byu.core.common.wsAuth.model.hibernate;

import edu.byu.core.common.wsAuth.api.Credential;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class AbstractCredentialImpl implements Credential, Serializable {

    private Long credentialId;
    private Date expirationDate;
    private String personId;

    @Override
    public String getPersonId() {
        return this.personId;
    }

    protected void setPersonId(String personId) {
        this.personId = personId;
    }

    @Override
    public boolean isExpired() {
        return this.expirationDate != null && this.expirationDate.before(new Date());
    }

    @Override
    public void expire() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, -60);
        this.expirationDate = c.getTime();
    }

    @Override
    public Date getExpirationDate() {
        return this.expirationDate;
    }

    public final void setExpirationDate(Date newExpirationDate) {
        this.expirationDate = newExpirationDate;
    }


    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "AbstractCredentialImpl{" +
                "credentialId=" + credentialId +
                ", expirationDate=" + dateFormat.format(expirationDate) +
                ", personId='" + personId + '\'' +
                '}';
    }

    public Long getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Long credentialId) {
        this.credentialId = credentialId;
    }
}
