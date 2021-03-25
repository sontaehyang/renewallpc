package saleson.shop.campaign.statistics.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatisticsInfo {

    private String key;
    private long smsSent;
    private long smsSuccess;
    private long mmsSent;
    private long mmsSuccess;
    private long kakaoSent;
    private long kakaoSuccess;
    private long pushSent;
    private long pushSuccess;
    private long pushReceive;
    private long redirection;
    private long orderCount;
    private long orderAmount;

    private long userId;
    private String phone;
    private String title;
    private String content;
    private LocalDateTime sendDate;

    public StatisticsInfo(String key) {
        this.key = key;
    }
}
