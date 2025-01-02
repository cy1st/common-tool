package com.cxy.tool;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderFormatting;
import org.apache.poi.xssf.usermodel.*;

import java.util.List;
import java.util.Map;

/**
 * @author CXY
 * @className ExcelUtil
 * @description excel操作工具通用方法
 * @date 2024/12/26 09:28
 */
public class ExcelTool {
    /**
     * 获取基础样式
     *
     * @param workbook
     * @param fontSize 字体大小
     * @param isBold   字体是否加粗
     * @return
     * @author CXY
     * @date 2024/12/26 09:29
     */
    public static XSSFCellStyle getStyle(XSSFWorkbook workbook, short fontSize, boolean isBold) {
        XSSFFont headFont = workbook.createFont();
        headFont.setFontName("黑体");
        // 字体大小
        headFont.setFontHeightInPoints(fontSize);
        if (isBold) {
            // 加粗
            headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        }
        XSSFCellStyle headStyle = workbook.createCellStyle();
        headStyle.setFont(headFont);
        // 左右居中
        headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 上下居中
        headStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        headStyle.setLocked(true);
//        headStyle.setWrapText(true);
        //加边框
        headStyle.setBorderTop(BorderFormatting.BORDER_THIN);
        headStyle.setBorderRight(BorderFormatting.BORDER_THIN);
        headStyle.setBorderBottom(BorderFormatting.BORDER_THIN);
        headStyle.setBorderLeft(BorderFormatting.BORDER_THIN);
        return headStyle;
    }

    /**
     * 导出返回体统一处理
     *
     * @param response
     * @param fileName 文件名
     * @param workbook
     * @param suffix   后缀
     * @author CXY
     * @date 2024/12/26 09:45
     */
//    public static void exportDealCommon(HttpServletResponse response, String fileName, XSSFWorkbook workbook, String suffix) {
//        StringBuilder finalName = new StringBuilder(fileName);
//        checkSuffixSplicing(workbook, suffix, finalName);
//        try (ServletOutputStream stream = response.getOutputStream()) {
//            commonSet(response, finalName);
//            workbook.write(stream);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                workbook.close();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    /**
     * 可处理合并行/列添加边框样式失败问题
     *
     * @param cellStyle
     * @param row
     * @param nullIndex
     * @author CXY
     * @date 2024/12/26 17:24
     */
    public static void setNullCellStyle(XSSFCellStyle cellStyle, XSSFRow row, Integer[] nullIndex) {
        for (Integer index : nullIndex) {
            row.createCell(index).setCellStyle(cellStyle);
        }
    }

    /**
     * 导出返回体统一处理
     *
     * @param response
     * @param fileName 文件名
     * @param workbook
     * @param suffix   后缀
     * @author CXY
     * @date 2024/12/26 09:45
     */
//    public static void exportDealCommon(HttpServletResponse response, String fileName, HSSFWorkbook workbook, String suffix) {
//        StringBuilder finalName = new StringBuilder(fileName);
//        checkSuffixSplicing(workbook, suffix, finalName);
//        try (ServletOutputStream stream = response.getOutputStream()) {
//            commonSet(response, finalName);
//            workbook.write(stream);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                workbook.close();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    /**
     * 批量插入数据（当前方法仅支持 .xlsx）【要支持 .xls 可在工具重载此方法】
     *
     * @param data
     * @param startIndex    开始行索引(从0开始)
     * @param sheet
     * @param cellIndexName 列名（data元素对应的key）数据插入顺序会参考该数组元素
     * @param cellStyle     统一样式处理
     * @author CXY
     * @date 2024/12/26 10:29
     */
    public static void insertDataBatch(List<Map<String, Object>> data, int startIndex, XSSFSheet sheet, String[] cellIndexName, XSSFCellStyle cellStyle) {
        for (int i = 0; i < data.size(); i++) {
            //插入数据
            XSSFRow row_ = sheet.createRow(i + startIndex);
            row_.setHeight((short) 400);
            Map<String, Object> param = data.get(i);
            int size = cellIndexName.length;
            for (int j = 0; j < size; j++) {
                XSSFCell cell = row_.createCell(j);
                cell.setCellValue(param.get(cellIndexName[j]) + "");
                cell.setCellStyle(cellStyle);
            }
        }
    }

    private static void checkSuffixSplicing(Object workbook, String suffix, StringBuilder fileName) {
        if (!"".equals(suffix) && !suffix.contains(".")) {
            if (workbook instanceof XSSFWorkbook) {
                if (!"xlsx".equals(suffix)) {
                    throw new RuntimeException("无效的后缀名");
                }
            }
            if (workbook instanceof HSSFWorkbook) {
                if (!"xls".equals(suffix)) {
                    throw new RuntimeException("无效的后缀名");
                }
            }
            fileName.append(".").append(suffix);
        }
    }

//    private static void commonSet(HttpServletResponse response, StringBuilder fileName) throws UnsupportedEncodingException {
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/x-msdownload;charset=UTF-8");
//        response.setHeader("Content-Disposition", "attachment; filename=" + UriUtils.encodeUri(fileName.toString(), "UTF-8"));
//    }

}
