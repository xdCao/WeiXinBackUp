package com.xdcao.weixin.service.impl;

import com.xdcao.weixin.base.ServiceResult;
import com.xdcao.weixin.bo.AnswerBO;
import com.xdcao.weixin.bo.PaperBO;
import com.xdcao.weixin.bo.QuestionBO;
import com.xdcao.weixin.dao.AnswerBOMapper;
import com.xdcao.weixin.dao.PaperBOMapper;
import com.xdcao.weixin.dao.QuestionBOMapper;
import com.xdcao.weixin.service.IPaperService;
import com.xdcao.weixin.web.form.PaperForm;
import com.xdcao.weixin.web.dto.OptionDTO;
import com.xdcao.weixin.web.dto.QuestionDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author: buku.ch
 * @Date: 2019-04-25 22:44
 */

@Service
public class PaperServiceImpl implements IPaperService {

    @Autowired
    private PaperBOMapper paperBOMapper;

    @Autowired
    private QuestionBOMapper questionBOMapper;

    @Autowired
    private AnswerBOMapper answerBOMapper;

    @Autowired
    private ModelMapper modelMapper;



    @Override
    @Transactional
    public ServiceResult addNewPaper(PaperForm paperForm) {

        Date now = new Date();

        PaperBO paperBO = new PaperBO();
        paperBO.setName(paperForm.getName());
        paperBO.setCreateTime(now);
        paperBO.setUpdateTime(now);

        paperBOMapper.insert(paperBO);

        List<QuestionDTO> questionDTOs = paperForm.getQuestionDTOs();
        questionDTOs.forEach(questionDTO -> {
            QuestionBO questionBO = new QuestionBO();
            modelMapper.map(questionDTO, questionBO);
            questionBO.setPaperId(paperBO.getId());
            questionBO.setCreateTime(now);
            questionBO.setUpdateTime(now);
            questionBOMapper.insert(questionBO);

            List<OptionDTO> optionDTOS = questionDTO.getOptionDTOS();
            optionDTOS.forEach(optionDTO -> {
                AnswerBO answerBO = new AnswerBO();
                modelMapper.map(optionDTO, answerBO);
                answerBO.setPaperId(paperBO.getId());
                answerBO.setQuestionId(questionBO.getId());
                answerBO.setCreateTime(now);
                answerBO.setLastUpdateTime(now);
                answerBOMapper.insert(answerBO);
            });

        });

        return new ServiceResult(true);

    }
}
