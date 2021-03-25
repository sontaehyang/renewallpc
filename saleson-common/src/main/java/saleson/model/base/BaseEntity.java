package saleson.model.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.SpringSecurityCoreVersion;
import saleson.common.utils.LocalDateUtils;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public abstract class BaseEntity implements Serializable {
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	@Version
	protected Long version;

    @CreatedDate
    private LocalDateTime created;

    @LastModifiedDate
    private LocalDateTime updated;

	@CreatedBy
	private Long createdBy;

	@LastModifiedBy
	private Long updatedBy;

	public String getCreatedDate() {
		return LocalDateUtils.getDate(created);
	}

	public String getCreatedDateTime() {
		return LocalDateUtils.getDateTime(created);
	}

	public String getUpdatedDate() {
		return LocalDateUtils.getDate(updated);
	}

	public String getUpdatedDateTime() {
		return LocalDateUtils.getDateTime(updated);
	}

}
