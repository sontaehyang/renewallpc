package saleson.shop.config.domain;

import saleson.common.hibenate.converter.BooleanYnConverter;
import saleson.common.model.DateAudit;
import saleson.common.enumeration.LogType;

import javax.persistence.*;

@Entity
@Table(name="OP_CONFIG_LOG")
public class ConfigLog extends DateAudit {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 15, updatable = false, nullable = false)
    private LogType logType;

    @Column(name="`KEY`", length = 30, updatable = false, nullable = false)
    private String key;

    @Column(length = 50)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(length=1)
    @Convert(converter = BooleanYnConverter.class)
    private boolean used;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LogType getLogType() {
        return logType;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
