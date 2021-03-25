package saleson.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import saleson.common.utils.InstantUtils;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdAt", "updatedAt"},
        allowGetters = true
)
public abstract class DateAudit implements Serializable {

	@JsonIgnore
    @CreatedDate
    @Column(updatable = false)
    private Instant createdAt = Instant.now();

	@JsonIgnore
    @LastModifiedDate
    @Column
    private Instant updatedAt = Instant.now();

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }


	public String getCreatedDate() {
		return InstantUtils.getDate(getCreatedAt());
	}
	public String getCreatedDateTime() {
		return InstantUtils.getDateTime(getCreatedAt());
	}

	public String getUpdatedDate() {
		return InstantUtils.getDate(getUpdatedAt());
	}
	public String getUpdatedDateTime() {
		return InstantUtils.getDateTime(getUpdatedAt());
	}

}
