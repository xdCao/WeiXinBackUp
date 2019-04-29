package com.xdcao.weixin.service;

import com.xdcao.weixin.base.ServiceResult;

import java.io.File;

/**
 * @Author: buku.ch
 * @Date: 2019-04-29 21:02
 */


public interface IExcelService {

    ServiceResult<File> summaryByDepartment();

}
