package saleson.shop.user.domain;

import saleson.common.model.DateAudit;

import javax.persistence.*;

@Entity
@Table(name="OP_MANAGER_LOGIN")
public class ManagerLogin extends DateAudit {

    @Id
    @GeneratedValue
    private Long id;

    @Column(updatable = false, nullable = false)
    private Long userId;

    @Column(length = 1000, updatable = false, nullable = false)
    private String sessionId;

    public ManagerLogin() {}

    public ManagerLogin(long userId, String sessionId) {
        this.userId = userId;
        this.sessionId = sessionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
