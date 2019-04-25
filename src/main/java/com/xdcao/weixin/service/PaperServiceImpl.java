package com.xdcao.weixin.service;

import com.xdcao.weixin.base.ServiceResult;
import com.xdcao.weixin.bo.OptionBO;
import com.xdcao.weixin.bo.PaperBO;
import com.xdcao.weixin.bo.QuestionBO;
import com.xdcao.weixin.dao.OptionBOMapper;
import com.xdcao.weixin.dao.PaperBOMapper;
import com.xdcao.weixin.dao.QuestionBOMapper;
import com.xdcao.weixin.web.PaperForm;
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
    private OptionBOMapper optionBOMapper;

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
                OptionBO optionBO = new OptionBO();
                modelMapper.map(optionDTO, optionBO);
                optionBO.setPaperId(paperBO.getId());
                optionBO.setQuestionId(questionBO.getId());
                optionBO.setCreateTime(now);
                optionBO.setLastUpdateTime(now);
                optionBOMapper.insert(optionBO);
            });

        });

        return new ServiceResult(true);

    }
}
