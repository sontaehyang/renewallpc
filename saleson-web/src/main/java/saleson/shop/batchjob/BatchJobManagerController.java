package saleson.shop.batchjob;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import saleson.batch.BatchJobService;
import saleson.batch.domain.BatchJob;
import saleson.batch.support.BatchJobListParam;
import saleson.batch.support.BatchJobParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/opmanager/batch-job")
@RequestProperty(title="batchJob", layout="default", template="opmanager")
public class BatchJobManagerController {

    private static final Logger log = LoggerFactory.getLogger(BatchJobManagerController.class);

    @Autowired
    BatchJobService batchJobService;

    @Autowired
    SequenceService sequenceService;

    /**	배치 작업 조회
     * @param param
     * @param model
     * @return
     */
    @GetMapping(value="list")
    public String batchJobList(@ModelAttribute BatchJobParam param, Model model) {

        List<BatchJob> batchJobList = batchJobService.getBatchJobList(param);

        log.debug("**** batchJobList.size() **** :", batchJobList.size());

        // 사용하는 곳은 어디인가?  skc
        //HttpSession session = RequestContextUtils.getSession();
        //session.setAttribute("reCustomerParam", param);

        int totalCount = 1;

        model.addAttribute("batchJobParam", param);
        model.addAttribute("batchJobList", batchJobList);
        model.addAttribute("pagination", param.getPagination());
        model.addAttribute("totalCount", totalCount);

        return "view:/batch-job/list";
    }

    /**
     * 배치 작업 등록 form
     * @param batchJob
     */
    @GetMapping("/create")
    @RequestProperty(layout = "base")
    public String getBatchJobCreate(@ModelAttribute("batchJob") BatchJob batchJob) {
        return "view:/batch-job/form";
    }

    /**
     * 배치 작업 등록 form
     * @param batchJob
     */
    @PostMapping("/create")
    public JsonView postBatchJobCreate(@ModelAttribute("batchJob") BatchJob batchJob, RequestContext requestContext) {
        if (!requestContext.isAjaxRequest()) {
            throw new NotAjaxRequestException();
        }

        batchJob.setBatchJobId(String.valueOf(sequenceService.getId("OP_BATCH_JOB")));
        batchJobService.insertBatchJob(batchJob);

        return JsonViewUtils.success();
    }



    /**
     * 배치 작업 상세 조회
     * @param model
     * @param batchJob
     * @return
     */
    @GetMapping("detail")
    @RequestProperty(layout = "base")
    public String sechedulDetailList(Model model, BatchJob batchJob) {
        BatchJob batchJobDetail = batchJobService.getBatchJobDetailList(batchJob);

        log.debug("##### batchJobParam : ", batchJob.getBatchJobId());

        // 어디서 사용하지?
        //HttpSession session = RequestContextUtils.getSession();
        //session.setAttribute("reCustomerParam", batchJob);


        int totalCount = 1;

        model.addAttribute("batchJob", batchJobDetail);
        model.addAttribute("totalCount", totalCount);

        return "view:/batch-job/form";
    }

    /**
     * 배치 작업 데이터 수정
     * @param batchJob
     * @return
     */
    @PostMapping("update")
    public JsonView sechedulUpdate(@ModelAttribute("batchJob") BatchJob batchJob, RequestContext requestContext) {
        if (!requestContext.isAjaxRequest()) {
            throw new NotAjaxRequestException();
        }

        batchJobService.updateBatchJob(batchJob);
        log.debug("##### batchJobParam : ", batchJob.getBatchJobId());

        return JsonViewUtils.success();
    }

    /**
     * 배치 작업 데이터 삭제
     * @param batchJobListParam
     * @return
     */
    @PostMapping(value="delete")
    public JsonView deleteListData(BatchJobListParam batchJobListParam) {

        Map<String,Object> params = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        String[] jobIdArray = batchJobListParam.getBatchJobId();
        log.debug("#### jobIdArray.length :  {}", jobIdArray.length);

        for(int i=0; i<jobIdArray.length; i++){


            BatchJob batchJob = batchJobListParam.getBatchJobId(i);
            log.debug("#### jbatch.getBatchJobId() : " + batchJob.getBatchJobId());
            Map<String, Object> item = new HashMap<>();
            item.put("batchJobId", batchJob.getBatchJobId());
            list.add(item);
        }

        params.put("list", list);
        batchJobService.deleteBatchJob(list);

        return JsonViewUtils.success();
    }
}
