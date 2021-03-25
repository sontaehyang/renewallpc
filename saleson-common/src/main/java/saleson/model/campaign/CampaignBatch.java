package saleson.model.campaign;

import lombok.*;
import saleson.model.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "OP_CAMPAIGN_BATCH")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CampaignBatch extends BaseEntity {

    // 사용자 ID
    @Id
    private Long userId;

    @Column(nullable = false, length = 60)
    private String loginId;

    @Column(length = 50)
    private String userName;

    // 회원 그룹 코드
    @Column(length = 10)
    private String groupCode;

    // 회원 등급 ID
    @Column(length = 14)
    private int levelId;

    // SMS 수신여부
    @Column(length = 1)
    private String receiveSms;

    // 푸시 수신여부
    @Column(length = 1)
    private String receivePush;

    // 가입이후 총 구매액
    @Column(length = 10)
    private int orderAmount1;

    // 최근 3개월간 총 구매액
    @Column(length = 10)
    private int orderAmount2;

    // 최종 로그인 일자
    @Column(length = 14)
    private String lastLoginDate;

    // 장바구니 개수
    @Column(length = 10)
    private int cartCount;

    // 장바구니 총액
    @Column(length = 10)
    private int cartAmount;
    
    // 회원 보유 포인트
    @Column(length = 10)
    private int point;

}
