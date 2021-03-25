package saleson.shop.order.domain;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import saleson.common.enumeration.OrderLogType;
import saleson.common.enumeration.UserType;
import saleson.common.model.DateAudit;
import saleson.common.utils.InstantUtils;
import saleson.common.utils.LocalDateUtils;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name="OP_ORDER_LOG")
public class OrderLog extends DateAudit {

	@Id
	@GeneratedValue
	private Long id;

	@Column(length = 20, nullable = false)
	@Enumerated(EnumType.STRING)
	private OrderLogType logType;

	@Column(length = 50)
	private String  orderCode;

	@Column(length = 11)
	private Integer orderSequence;

	@Column(length = 11)
	private Integer itemSequence;

    @Column(length = 255)
    private String itemName;

    @Column(length = 20)
	private String orderStatus;

	@Column(length = 20)
	private String orgOrderStatus;

	@Column(length = 10)
	@Enumerated(EnumType.STRING)
	private UserType userType;

	@Column(length = 20)
	private String ip;

	@Column(length = 50)
	private String createdBy;

	@Column(length = 50)
	private String updatedBy;

	private String createdAtText;
	private String updatedAtText;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrderLogType getLogType() {
		return logType;
	}

	public void setLogType(OrderLogType logType) {
		this.logType = logType;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Integer getOrderSequence() {
		return orderSequence;
	}

	public void setOrderSequence(Integer orderSequence) {
		this.orderSequence = orderSequence;
	}

	public Integer getItemSequence() {
		return itemSequence;
	}

	public void setItemSequence(Integer itemSequence) {
		this.itemSequence = itemSequence;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrgOrderStatus() {
		return orgOrderStatus;
	}

	public void setOrgOrderStatus(String orgOrderStatus) {
		this.orgOrderStatus = orgOrderStatus;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Predicate getPredicate() {
		BooleanBuilder builder = new BooleanBuilder();
		QOrderLog orderLog = QOrderLog.orderLog;

		if (orderCode != null) {
			builder.and(orderLog.orderCode.eq(getOrderCode()));
		}

		return builder;
	}

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getCreatedAtText() {
		if (StringUtils.isEmpty(createdAtText)) {
			return LocalDateUtils.getDateTime(getCreatedAt());
		}

		return createdAtText;
	}

	public String getUpdatedAtText() {
		if (StringUtils.isEmpty(updatedAtText)) {
			return LocalDateUtils.getDateTime(getUpdatedAt());
		}

		return updatedAtText;
	}

	public void setCreatedAtText(String createdAtText) {
		this.createdAtText = createdAtText;
	}

	public void setUpdatedAtText(String updatedAtText) {
		this.updatedAtText = updatedAtText;
	}
}
