package edu.byu.core.common.wsAuth.model.hibernate;


import java.io.Serializable;
import java.util.Date;


public class WsNonce implements Serializable {
    private long nonceKey;

    public long getNonceKey() {
        return nonceKey;
    }

    public void setNonceKey(long nonceKey) {
        this.nonceKey = nonceKey;
    }

    private String nonceValue;

    public String getNonceValue() {
        return nonceValue;
    }

    public void setNonceValue(String nonceValue) {
        this.nonceValue = nonceValue;
    }

    private String wsId;

    public String getWsId() {
        return wsId;
    }

    public void setWsId(String wsId) {
        this.wsId = wsId;
    }

    private Date expireDate;

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    private String actor;

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    @Override
    public String toString() {
        return "WsNonce{" +
                "nonceKey=" + nonceKey +
                ", nonceValue='" + nonceValue + '\'' +
                ", wsId='" + wsId + '\'' +
                ", expireDate=" + expireDate +
                ", actor='" + actor + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WsNonce)) return false;

        WsNonce wsNonce = (WsNonce) o;

        if (nonceKey != wsNonce.nonceKey) return false;
        if (actor != null ? !actor.equals(wsNonce.actor) : wsNonce.actor != null) return false;
        if (expireDate != null ? !expireDate.equals(wsNonce.expireDate) : wsNonce.expireDate != null) return false;
        if (nonceValue != null ? !nonceValue.equals(wsNonce.nonceValue) : wsNonce.nonceValue != null) return false;
        if (wsId != null ? !wsId.equals(wsNonce.wsId) : wsNonce.wsId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (nonceKey ^ (nonceKey >>> 32));
        result = 31 * result + (nonceValue != null ? nonceValue.hashCode() : 0);
        result = 31 * result + (wsId != null ? wsId.hashCode() : 0);
        result = 31 * result + (expireDate != null ? expireDate.hashCode() : 0);
        result = 31 * result + (actor != null ? actor.hashCode() : 0);
        return result;
    }
}
