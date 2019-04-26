package com.xdcao.weixin.service;

import com.xdcao.weixin.base.ServiceResult;
import com.xdcao.weixin.web.form.PaperForm;

/**
 * @Author: buku.ch
 * @Date: 2019-04-25 22:42
 */


public interface IPaperService {

    ServiceResult addNewPaper(PaperForm paperForm);

}
