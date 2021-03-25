package saleson.model;


import com.onlinepowers.framework.util.ValidationUtils;
import lombok.*;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartFile;
import saleson.common.config.SalesonProperty;
import saleson.common.enumeration.DataStatus;
import saleson.common.enumeration.ProcessType;
import saleson.common.utils.LocalDateUtils;
import saleson.common.utils.ShopUtils;
import saleson.model.base.BaseEntity;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDateTime;


@Entity
@Table(
        name="OP_GIFT_ITEM",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"code"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class GiftItem extends BaseEntity {


    @Id
    @GeneratedValue
    private Long id;

    // 사은품 CODE
    @Column(length = 30, updatable = false, nullable = false)
    private String code;

    // 사은품명
    @Column(length = 30, nullable = false)
    private String name;

    // 판매자 ID
    @Column(updatable = false, nullable = false)
    private long sellerId;

    // 사은품 금액
    @Column(nullable = false)
    private int price;

    // 사은품 이미지
    @Column(length = 255)
    private String image;

    // 사은품 유효 시작 기간
    @Column
    private LocalDateTime validStartDate;

    // 사은품 유효 종료 기간
    @Column
    private LocalDateTime validEndDate;

    // 데이터 상태
    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private DataStatus dataStatus;

    // form 데이터 영역

    // 이미지 파일
    @Transient
    private MultipartFile imageFile;

    // 사은품 유효 시작 기간
    @Transient
    private String startDate;

    // 사은품 유효 종료 기간
    @Transient
    private String endDate;

    // 사은품 유효 시작 시간
    @Transient
    private String startTime;

    // 사은품 유효 종료 시간
    @Transient
    private String endTime;


	@Transient
	Environment environment;


    public String getValidStartDateText() {
        return LocalDateUtils.getDateTime(getValidStartDate());
    }

    public String getValidEndDateText() {
        return LocalDateUtils.getDateTime(getValidEndDate());
    }

    public String getImageUploadPath() {

        StringBuffer sb = new StringBuffer();

        sb.append("gift-item");
        sb.append(File.separator);
        sb.append(getCode());

        return sb.toString();
    }

    public String getImageSrc() {

        if (ValidationUtils.isEmpty(getImage())) {
            return ShopUtils.getNoImagePath();
        }

        StringBuffer sb = new StringBuffer();

        sb.append(SalesonProperty.getUploadBaseFolder());
        sb.append("/");
        sb.append("gift-item");
        sb.append("/");
        sb.append(getCode());
        sb.append("/");
        sb.append(getImage());

        return sb.toString();
    }

    public ProcessType getProcessType() {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime validStartDate = getValidStartDate();
        LocalDateTime validEndDate = getValidEndDate();

        if (validStartDate == null && validEndDate == null) {

            return ProcessType.PROGRESS;

        } else if (validStartDate != null && validEndDate == null) {

            if (now.isBefore(validStartDate)) {
                return ProcessType.NOT_PROGRESS;
            } else {
                return ProcessType.PROGRESS;
            }

        } else if (validStartDate == null && validEndDate != null) {

            if (now.isAfter(validEndDate)) {
                return ProcessType.END;
            } else {
                return ProcessType.PROGRESS;
            }

        } else {

            if (now.isAfter(validStartDate) && now.isBefore(validEndDate)) {

                return ProcessType.PROGRESS;

            } else {

                if (now.isBefore(validStartDate)) {
                    return ProcessType.NOT_PROGRESS;
                }

                if (now.isAfter(validEndDate)) {
                    return ProcessType.END;
                }
            }

        }

        return null;
    }

    public String getNotProcessLabel() {

        if (DataStatus.DELETE == getDataStatus()) {
            return "삭제";
        } else {

            if (ProcessType.NOT_PROGRESS == getProcessType()) {
                return "진행전";
            }

            if (ProcessType.END == getProcessType()) {
                return "종료";
            }

        }

        return "";
    }

    public boolean isValid() {
        return getDataStatus() == DataStatus.NORMAL && getProcessType() == ProcessType.PROGRESS;
    }
}
