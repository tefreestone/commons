package edu.byu.core.common.wsAuth.model.hibernate;


import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: tef2
 * Date: 2/22/11
 * Time: 4:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class WsAuthenticationLog implements Serializable {
    public static String VALID_AUTHENTICATION = "Y";
    public static String INVALID_AUTHENTICATION = "N";

    private long logKey;
    private String userAgent;

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public long getLogKey() {
        return logKey;
    }

    public void setLogKey(long logKey) {
        this.logKey = logKey;
    }

    private String credentialType;

    public String getCredentialType() {
        return credentialType;
    }

    public void setCredentialType(String credentialType) {
        this.credentialType = credentialType;
    }

    private String credentialValue;

    public String getCredentialValue() {
        return credentialValue;
    }

    public void setCredentialValue(String credentialValue) {
        this.credentialValue = credentialValue;
    }

    private String principal;

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    private Date logDate;

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    private String uri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    private String validAuthentication;

    public String getValidAuthentication() {
        return validAuthentication;
    }

    public void setValidAuthentication(String validAuthentication) {
        this.validAuthentication = validAuthentication;
    }

    private String actor;

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WsAuthenticationLog)) return false;

        WsAuthenticationLog that = (WsAuthenticationLog) o;

        if (logKey != that.logKey) return false;
        if (actor != null ? !actor.equals(that.actor) : that.actor != null) return false;
        if (credentialType != null ? !credentialType.equals(that.credentialType) : that.credentialType != null)
            return false;
        if (credentialValue != null ? !credentialValue.equals(that.credentialValue) : that.credentialValue != null)
            return false;
        if (logDate != null ? !logDate.equals(that.logDate) : that.logDate != null) return false;
        if (principal != null ? !principal.equals(that.principal) : that.principal != null) return false;
        if (uri != null ? !uri.equals(that.uri) : that.uri != null) return false;
        if (userAgent != null ? !userAgent.equals(that.userAgent) : that.userAgent != null) return false;
        if (validAuthentication != null ? !validAuthentication.equals(that.validAuthentication) : that.validAuthentication != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (logKey ^ (logKey >>> 32));
        result = 31 * result + (userAgent != null ? userAgent.hashCode() : 0);
        result = 31 * result + (credentialType != null ? credentialType.hashCode() : 0);
        result = 31 * result + (credentialValue != null ? credentialValue.hashCode() : 0);
        result = 31 * result + (principal != null ? principal.hashCode() : 0);
        result = 31 * result + (logDate != null ? logDate.hashCode() : 0);
        result = 31 * result + (uri != null ? uri.hashCode() : 0);
        result = 31 * result + (validAuthentication != null ? validAuthentication.hashCode() : 0);
        result = 31 * result + (actor != null ? actor.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WsAuthenticationLog{" +
                "logKey=" + logKey +
                ", userAgent='" + userAgent + '\'' +
                ", credentialType='" + credentialType + '\'' +
                ", credentialValue='" + credentialValue + '\'' +
                ", principal='" + principal + '\'' +
                ", logDate=" + logDate +
                ", uri='" + uri + '\'' +
                ", validAuthentication='" + validAuthentication + '\'' +
                ", actor='" + actor + '\'' +
                '}';
    }
}
