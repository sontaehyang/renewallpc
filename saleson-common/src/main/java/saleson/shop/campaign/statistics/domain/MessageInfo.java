package saleson.shop.campaign.statistics.domain;

import lombok.Data;

@Data
public class MessageInfo {

    private String sentType;
    private String title;
    private String content;
    private String sendDate;
    private String phoneNumber;

    private long smsSent;
    private long smsSuccess;
    private long mmsSent;
    private long mmsSuccess;
    private long kakaoSent;
    private long kakaoSuccess;
    private long pushSent;
    private long pushSuccess;
    private long pushReceive;
}
