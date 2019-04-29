package com.xdcao.weixin.service;

import com.xdcao.weixin.base.DepartmentExcelElement;
import com.xdcao.weixin.base.Departments;
import com.xdcao.weixin.base.ServiceResult;
import com.xdcao.weixin.bo.UserBO;
import com.xdcao.weixin.dao.UserBOMapper;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

/**
 * @Author: buku.ch
 * @Date: 2019-04-29 21:06
 */

@Service
public class ExcelServiceImpl implements IExcelService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelServiceImpl.class);

    @Autowired
    private IUserService userService;

    @Override
    public ServiceResult<File> summaryByDepartment() {

        List<DepartmentExcelElement> departmentExcelElements = new ArrayList<>();

        for (Departments departments : Departments.values()) {

            if (departments.getValue() == Departments.OTHERS.getValue()) {
                continue;
            }

            DepartmentExcelElement excelElement = new DepartmentExcelElement();
            String departName = departments.getName();
            excelElement.setDepartName(departName);
            int departmentsValue = departments.getValue();
            List<UserBO> users = userService.findUsersByDepartment(departmentsValue);
            if (users == null || users.isEmpty()) {
                excelElement.setAvaScore(0);
                excelElement.setTotalScore(0);
                excelElement.setUserNum(0);
                departmentExcelElements.add(excelElement);
                continue;
            }

            excelElement.setUserNum(users.size());
            int sum = users.stream().mapToInt(UserBO::getTotalScore).sum();
            excelElement.setTotalScore(sum);
            OptionalDouble average = users.stream().mapToDouble(UserBO::getTotalScore).average();
            excelElement.setAvaScore(average.isPresent() ? average.getAsDouble() : 0);
            departmentExcelElements.add(excelElement);
        }

        File file = exportInfo(departmentExcelElements);

        if (file == null) {
            return new ServiceResult<>(false,"创建excel文件失败");
        }

        return new ServiceResult<>(true,"ok",file);
    }


    public File exportInfo(List<DepartmentExcelElement> departmentExcelElements) {
        try {

            //web对象
            HSSFWorkbook wb = new HSSFWorkbook();

            //创建表头等
            HSSFSheet sheet = wb.createSheet("各科室积分统计");
            // 用于格式化单元格的数据
            HSSFDataFormat format = wb.createDataFormat();
            // 设置字体 (上方标题用 填写内容)
            HSSFFont font = wb.createFont();
            font.setFontHeightInPoints((short) 11); //字体高度
            font.setColor(HSSFFont.COLOR_NORMAL); //字体颜色
            font.setFontName("宋体"); //字体

            //表头主数据，加粗
            HSSFCellStyle cellStyle = wb.createCellStyle();
            cellStyle.setFont(font);
            cellStyle.setAlignment(HorizontalAlignment.CENTER); //水平布局：居中
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//单元格垂直居中
            cellStyle.setWrapText(true);//换行

            // 设置字体 (上方标题用 导航头加粗)
            HSSFFont fontBold = wb.createFont();
            fontBold.setFontHeightInPoints((short) 11); //字体高度
            //setHSSFPalette("#3366FF",7,wb);//自定义设置字体颜色
            fontBold.setColor(HSSFFont.COLOR_NORMAL); //字体颜色
            fontBold.setFontName("宋体"); //字体;

            // 设置单元格类型
            HSSFCellStyle cellStyleBold = wb.createCellStyle();
            cellStyleBold.setFont(fontBold);
            cellStyleBold.setAlignment(HorizontalAlignment.CENTER); //水平布局：居中
            cellStyleBold.setVerticalAlignment(VerticalAlignment.CENTER);//单元格垂直居中
            cellStyleBold.setWrapText(true);//换行


            //rowName
            String[] rowsName = new String[]{"科室名称", "科室成员数量", "总分", "平均分"};
            sheet.setDefaultColumnWidth(15);//设置列默认的宽度
            HSSFRow row = null;

            row = sheet.createRow(0);

            row.setHeightInPoints(17);//设置字体大小

            for (int c = 0; c < rowsName.length; c++) {
                HSSFCell cell = row.createCell(c);
                cell.setCellValue(rowsName[c]);
//                if (i == 2) {
//                    cell.setCellStyle(getStyleTitle(wb));
//                } else {
//                    if (c % 2 == 0) {
//                        cell.setCellStyle(cellStyleBold);
//                    } else {
//                        cell.setCellStyle(cellStyle);
//                    }
//                }
            }
            sheet.setColumnWidth((short) 1, (short) (256 * 20)); //设置行宽度
            sheet.setColumnWidth((short) 2, (short) (256 * 20)); //设置行宽度
            //==================表头信息 结束=========================



            for (int i = 0; i < departmentExcelElements.size(); i++) {
                HSSFRow cellsRow = sheet.createRow(i + 1);
                DepartmentExcelElement objsRow = departmentExcelElements.get(i);
                int length = 0;

                HSSFCell nameCell = cellsRow.createCell(0);
                nameCell.setCellValue(objsRow.getDepartName());
                nameCell.setCellStyle(getStyle(wb));

                HSSFCell userNumCell = cellsRow.createCell(1);
                userNumCell.setCellValue(objsRow.getUserNum());
                userNumCell.setCellStyle(getStyle(wb));

                HSSFCell totalScoreCell = cellsRow.createCell(2);
                totalScoreCell.setCellValue(objsRow.getTotalScore());
                totalScoreCell.setCellStyle(getStyle(wb));

                HSSFCell avaScoreCell = cellsRow.createCell(3);
                avaScoreCell.setCellValue(objsRow.getAvaScore());
                avaScoreCell.setCellStyle(getStyle(wb));
            }

//            HttpServletResponse response = ServletActionContext.getResponse();
//            response.reset();
//            OutputStream output = getRepsonse().getOutputStream();

            File file = new File("excel.xls");
            OutputStream output = new FileOutputStream(file);
//            response.setHeader("Content-disposition", "attachment; filename=Info.xls");
//            response.setContentType("application/msexcel");
            wb.write(output);
            output.close();
            LOGGER.info("成功创建excel文件");

            return file;

        } catch (Exception e) {
            LOGGER.error("创建excel文件失败", e);
            return null;
        }
    }

    /**
     * 列表数据格式
     *
     * @param workbook
     * @return
     * @author 邓成波
     * @date 创建时间 2017年3月21日 下午4:32:13
     */
    public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体名字
        font.setFontName("宋体"); //字体
        //设置字体大小
        font.setFontHeightInPoints((short) 11);
        //字体加粗
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(BorderStyle.THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(BorderStyle.THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(BorderStyle.THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(BorderStyle.THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(true);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    /**
     * 列表表头用   数据格式表头
     *
     * @param workbook
     * @return
     * @author 邓成波
     * @date 创建时间 2017年3月21日下午4:32:13
     */
    public HSSFCellStyle getStyleTitle(HSSFWorkbook workbook) {

        //设置字体
        HSSFFont font = workbook.createFont();
        //设置字体名字
        font.setFontName("宋体"); //字体
        //设置字体大小
        font.setFontHeightInPoints((short) 10);
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();

        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.RED.getIndex());
        style.setFillBackgroundColor(IndexedColors.RED.getIndex());

        //设置底边框;
        style.setBorderBottom(BorderStyle.THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(BorderStyle.THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(BorderStyle.THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(BorderStyle.THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    /**
     * 设定自定义字体颜色信息
     *
     * @param str   颜色
     * @param index 编号
     * @param wb    execl对象
     * @author 邓成波
     * @date 创建时间 2017年3月21日 下午12:01:09
     */
    public void setHSSFPalette(String str, int index, HSSFWorkbook wb) {
        //处理把它转换成十六进制并放入一个数
        int[] color = new int[3];
        color[0] = Integer.parseInt(str.substring(1, 3), 16);
        color[1] = Integer.parseInt(str.substring(3, 5), 16);
        color[2] = Integer.parseInt(str.substring(5, 7), 16);
        //自定义颜色
        HSSFPalette palette = wb.getCustomPalette();
        //设置自定义颜色的下标，接下来会用到
        palette.setColorAtIndex(((short) index), (byte) color[0], (byte) color[1], (byte) color[2]);
    }


}
