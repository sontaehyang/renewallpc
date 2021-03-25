package saleson.shop.inquiry;

import com.onlinepowers.framework.file.service.FileService;
import com.onlinepowers.framework.notification.NotificationService;
import com.onlinepowers.framework.notification.message.OpMessage;
import com.onlinepowers.framework.notification.message.SmsMessage;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import saleson.shop.inquiry.domain.Inquiry;
import saleson.shop.inquiry.support.InquiryParam;
import saleson.shop.sendsmslog.SendSmsLogMapper;
import saleson.shop.sendsmslog.domain.SendSmsLog;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service("inquiryService")
public class InquiryServiceImpl implements InquiryService {

    @Autowired
    private SendSmsLogMapper sendSmsLogMapper;

    @Autowired
//    @Qualifier("freemelSmsService")
    private NotificationService smsService;

    @Autowired
    FileService fileService;

    @Autowired
    private InquiryMapper inquiryMapper;

    @Autowired
    SequenceService sequenceService;

    @Override
    public void insertInquiry(Inquiry inquiry) {

        inquiry.setCreatedDate(DateUtils.getToday("yyyyMMdd"));
        inquiry.setInquiryId(sequenceService.getId("OP_SHOP_INQUIRY"));

        if (inquiry.getInquiryImg() != null) {
            String uploadPath = FileUtils.getDefaultUploadPath() + File.separator + "inquiry";
            fileService.makeUploadPath(uploadPath);

            // 2. 파일명
            String defaultFileName = inquiry.getInquiryId() + "." + FileUtils.getExtension(inquiry.getInquiryImg().getOriginalFilename());    //새로운 이미지명 지정.
            inquiry.setInquiryImgName(defaultFileName);                                                                                       // 이미지명 setting
            String newFileName = FileUtils.getNewFileName(uploadPath, defaultFileName);

            // 3. 저장될 파일
            File saveFile = new File(uploadPath + File.separator + newFileName);
            inquiry.setInquiryImgName(newFileName);

            try {
                FileCopyUtils.copy(inquiry.getInquiryImg().getBytes(), saveFile);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        inquiryMapper.insertInquiry(inquiry);
    }

    @Override
    public int getInquiryCount(InquiryParam inquiryParam) {

        return inquiryMapper.getInquiryCount(inquiryParam);
    }

    @Override
    public List<Inquiry> getInquiryList(InquiryParam inquiryParam) {
        // TODO Auto-generated method stub
        return inquiryMapper.getInquiryList(inquiryParam);
    }

    @Override
    public void deleteSelectInquiry(InquiryParam inquiryParam) {
        inquiryMapper.deleteSelectInquiry(inquiryParam);
    }

    @Override
    public Inquiry getInquiryViewById(int inquiryId) {
        return inquiryMapper.getInquiryViewById(inquiryId);
    }

    @Override
    public void editAnswerFlag(Inquiry inquiry) {
        inquiryMapper.editAnswerFlag(inquiry);
    }

    @Override
    public void answerSendSms(Inquiry inquiry) {

        SendSmsLog sendSmsLog = new SendSmsLog();   // 로그기록을 위해 SMSLOG 생성

        String smsType = "sms";
        if (inquiry.getInquiryAnswer().length() > 80) {
            smsType = "mms";
        }

        OpMessage message = new SmsMessage(inquiry.getTelNumber(), inquiry.getInquiryAnswer(), "", smsType, inquiry.getSmsTitle());    // SMS를 보내기 위해 MESSAGE 생성

        // SMS 발송.
        smsService.sendMessage(message);

        sendSmsLog.setSendSmsLogId(sequenceService.getId("OP_SEND_SMS_LOG"));
        sendSmsLog.setReceiveTelNumber(inquiry.getTelNumber());
        sendSmsLog.setContent(inquiry.getInquiryAnswer());
        sendSmsLog.setSendType("INQUIRY_" + inquiry.getInquiryId());

        sendSmsLogMapper.insertSendSmsLog(sendSmsLog);     // SMS 로그기록

        inquiry.setAnswerFlag(1);                          // 답변완료 상태로 변경
        this.editAnswerFlag(inquiry);
    }

}