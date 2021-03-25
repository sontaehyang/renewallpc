package saleson.shop.inquiry;

import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.FileUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import saleson.shop.inquiry.domain.Inquiry;
import saleson.shop.inquiry.support.InquiryParam;
import saleson.shop.sendsmslog.SendSmsLogService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

@Controller
@RequestMapping("/opmanager/inquiry")
@RequestProperty(title = "고객센터", template = "opmanager", layout = "default")
public class InquiryManagerController {

    @Autowired
    private InquiryService inquiryService;

    @Autowired
    private SendSmsLogService sendSmsLogService;

    private static final Logger logger = LoggerFactory
            .getLogger(InquiryManagerController.class);


    /**
     * 문의 관리 리스트
     *
     * @param model
     * @return
     */
    @RequestMapping("list")
    public String inquiryList(@ModelAttribute InquiryParam inquiryParam, Model model) {

        int inquiryCount = inquiryService.getInquiryCount(inquiryParam);

        Pagination pagination = Pagination.getInstance(inquiryCount);

        inquiryParam.setPagination(pagination);

        List<Inquiry> inquiryList = inquiryService.getInquiryList(inquiryParam);

        String today = DateUtils.getToday("yyyyMMdd");

        model.addAttribute("inquiryCount", inquiryCount);
        model.addAttribute("today", today);
        model.addAttribute("week", DateUtils.addYearMonthDay(today, 0, 0, -7));
        model.addAttribute("month1", DateUtils.addYearMonthDay(today, 0, -1, 0));
        model.addAttribute("month3", DateUtils.addYearMonthDay(today, 0, -3, 0));
        model.addAttribute("inquiryList", inquiryList);
        model.addAttribute("pagination", pagination);
        model.addAttribute("inquiryParam", inquiryParam);

        return "view";
    }

    /**
     * 비회원 문의 삭제
     *
     * @param inquiryParam
     * @return
     */
    @RequestMapping("delete")
    public String inquiryDelete(InquiryParam inquiryParam) {

        try {
            inquiryService.deleteSelectInquiry(inquiryParam);
        } catch (Exception e) {
            return ViewUtils.redirect("/opmanager/inquiry/list", "삭제 실패!");
        }

        return ViewUtils.redirect("/opmanager/inquiry/list", "삭제 되었습니다.");
    }

    /**
     * 비회원문의 상세
     *
     * @param model
     * @param inquiryId
     * @return
     */
    @RequestMapping("view/{inquiryId}")
    public String inquiryView(Model model, @PathVariable("inquiryId") int inquiryId) {

        model.addAttribute("answerList", sendSmsLogService.getSendSmsLogListBySendType("INQUIRY_" + inquiryId));
        model.addAttribute("inquiry", inquiryService.getInquiryViewById(inquiryId));

        return ViewUtils.view();
    }

    /**
     * 비회원 문의 수정
     *
     * @param inquiry
     * @return
     */
    @RequestMapping(value = "answer", method = RequestMethod.POST)
    public String inquiryAnswer(Inquiry inquiry) {
        try {
            inquiryService.answerSendSms(inquiry);
        } catch (Exception e) {
            return ViewUtils.redirect("/opmanager/inquiry/view/" + inquiry.getInquiryId(), "답변에 실패 했습니다.");
        }
        return ViewUtils.redirect("/opmanager/inquiry/list", "성공적으로 답변 되었습니다.");
    }

    @RequestMapping(value = "/download-file")
    public void downloadImage(Inquiry inquiry, HttpServletResponse response) {

        try {
            String imageUrl = "file:///" + FileUtils.getDefaultUploadPath() + File.separator + "inquiry/" + inquiry.getInquiryImgName();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = new URL(imageUrl).openStream();

            URL url = new URL(imageUrl);

            byte[] buf = new byte[1024];
            int n = 0;
            while (-1 != (n = in.read(buf))) {
                out.write(buf, 0, n);
            }
            out.close();
            in.close();

            byte fileByte[] = out.toByteArray();

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(inquiry.getInquiryImgName(), "UTF-8") + "\";");
            response.setHeader("Content-Transfer-Encoding", "binary");
            response.getOutputStream().write(fileByte);

            response.getOutputStream().flush();
            response.getOutputStream().close();

        } catch (Exception e) {
            System.out.println("DOWNLOAD ERROR : " + e.getMessage());
            throw new UserException("파일이 존재하지 않습니다.");
        }
    }
}