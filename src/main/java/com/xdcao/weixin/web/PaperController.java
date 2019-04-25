package com.xdcao.weixin.web;

import com.google.gson.Gson;
import com.xdcao.weixin.base.ServiceResult;
import com.xdcao.weixin.service.IPaperService;
import com.xdcao.weixin.web.dto.OptionDTO;
import com.xdcao.weixin.web.dto.QuestionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: buku.ch
 * @Date: 2019-04-25 22:04
 */

@Controller
@RequestMapping("/paper")
public class PaperController {

    @Autowired
    private Gson gson;

    @Autowired
    private IPaperService paperService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PaperController.class);

    @RequestMapping("/add")
    @ResponseBody
    public ApiResponse addNewPaper(@RequestBody PaperForm paperForm) {


        LOGGER.info("receive paperForm: {}", gson.toJson(paperForm));

        ServiceResult result = paperService.addNewPaper(paperForm);

        if (result.isSuccess()) {
            return new ApiResponse(ApiResponse.Status.SUCCESS);
        } else {
            return new ApiResponse(ApiResponse.Status.BAD_REQUEST);
        }



    }


    public static void main(String[] args) {
        Gson gson = new Gson();
        PaperForm paperForm = new PaperForm();
        paperForm.setName("测试用的试卷");

        OptionDTO optionDTO1 = new OptionDTO();
        optionDTO1.setContent("选项1");
        optionDTO1.setCorrect(true);
        optionDTO1.setIndexNum(1);

        OptionDTO optionDTO2 = new OptionDTO();
        optionDTO2.setContent("选项2");
        optionDTO2.setCorrect(false);
        optionDTO2.setIndexNum(2);

        List<OptionDTO> optionDTOs = new ArrayList<>();
        optionDTOs.add(optionDTO1);
        optionDTOs.add(optionDTO2);

        QuestionDTO questionDTO1 = new QuestionDTO();
        questionDTO1.setContent("题目1");
        questionDTO1.setIndexNum(1);
        questionDTO1.setMuliti(1);
        questionDTO1.setOptionDTOS(optionDTOs);
        questionDTO1.setScore(50);

        QuestionDTO questionDTO2 = new QuestionDTO();
        questionDTO2.setContent("题目2");
        questionDTO2.setIndexNum(2);
        questionDTO2.setMuliti(1);
        questionDTO2.setOptionDTOS(optionDTOs);
        questionDTO2.setScore(50);

        List<QuestionDTO> questionDTOS = new ArrayList<>();
        questionDTOS.add(questionDTO1);
        questionDTOS.add(questionDTO2);

        paperForm.setQuestionDTOs(questionDTOS);
        System.out.println(gson.toJson(paperForm));
    }


}
