package saleson.common.notification.domain;

import com.onlinepowers.framework.notification.message.OpMessage;

import java.util.Collection;

public class UmsMessage implements OpMessage {

    private String title;
    private String message;

    private String[] receivers = new String[1];

    private String callbackNumber;

    private String smsType;

    public UmsMessage(String title, String message, String phoneNumber, String callbackNumber, String smsType) {

        this.title = title;
        this.message = message;

        this.callbackNumber = callbackNumber;
        this.receivers[0] = phoneNumber;

        this.smsType = smsType;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public Collection<String> getMessageReceivers() {
        return null;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String[] getReceivers() {
        return this.receivers;
    }

    @Override
    public String getFrom() {
        return this.callbackNumber;
    }

    @Override
    public String getContent() {
        return null;
    }

    @Override
    public boolean isHTML() {
        return false;
    }

    @Override
    public String[] getCc() {
        return new String[0];
    }

    @Override
    public String[] getBcc() {
        return new String[0];
    }

    @Override
    public String getSmsType() {
        return this.smsType;
    }
}
