package com.cxy.tool;

import org.apache.poi.xwpf.usermodel.*;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author CXY
 * @className exportWord
 * @description 导出word
 * @date 2024/12/19 14:48
 */
public class FillWordTool {

    /**
     * 调用此方法记得关闭XWPFDocument流！！！
     *
     * @param file 模板文件
     * @return
     * @author CXY
     * @date 2024/12/19 16:42
     */
    public static XWPFDocument getXWPFDocument(File file) {
        FileInputStream fis = null;
        XWPFDocument document = null;
        try {
            fis = new FileInputStream(file);
            document = new XWPFDocument(fis);
        } catch (IOException e) {
            System.out.println("===获取模板失败===");
            e.fillInStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return document;
    }

    /**
     * 文档填充
     *
     * @param document
     * @param param
     * @param isTable
     * @author CXY
     * @date 2024/12/19 16:12
     */
    public static void fillContent(XWPFDocument document, Map<String, Object> param, boolean isTable) {
        if (!param.isEmpty()) {
            Iterator<Map.Entry<String, Object>> iterator = param.entrySet().iterator();
            if (isTable) {
                List<XWPFTable> tables = document.getTables();
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> next = iterator.next();
                    String key = next.getKey();
                    Object value = next.getValue();
                    fillTable(tables, key, formatString(value));
                }
            } else {
                List<XWPFParagraph> paragraphs = document.getParagraphs();
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> next = iterator.next();
                    String key = next.getKey();
                    Object value = next.getValue();
                    fillTxt(paragraphs, key, formatString(value));
                }
            }
        }
    }

    /**
     * 填装文档基本文本信息
     *
     * @param paragraphs
     * @param key
     * @param value
     * @author CXY
     * @date 2024/12/19 15:57
     */
    public static void fillTxt(List<XWPFParagraph> paragraphs, String key, String value) {
        key = "${" + key + "}";
        if (!paragraphs.isEmpty()) {
            for (XWPFParagraph paragraph : paragraphs) {
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    if (text != null && text.contains(key)) {
                        text = text.replace(key, value);
                        run.setText(text, 0);
                    }
                }
            }
        }
    }

    /**
     * 填装表格基本数据
     *
     * @param tables
     * @param key
     * @param value
     * @author CXY
     * @date 2024/12/19 15:55
     */
    public static void fillTable(List<XWPFTable> tables, String key, String value) {
        if (!tables.isEmpty()) {
            for (XWPFTable table : tables) {
                for (XWPFTableRow row : table.getRows()) {
                    tableCommon(row, key, value);
                }
            }
        }
    }

    /**
     * 填装表格批量数据
     *
     * @param tables
     * @param params
     * @param index
     * @author CXY
     * @date 2024/12/19 15:57
     */
    public static void fillTableBatch(List<XWPFTable> tables, List<Map<String, Object>> params, int index) {
        if (!params.isEmpty()) {
            for (Map<String, Object> param : params) {
                for (Map.Entry<String, Object> next : param.entrySet()) {
                    String key = next.getKey();
                    Object value = next.getValue();
                    if (!tables.isEmpty()) {
                        for (XWPFTable table : tables) {
                            List<XWPFTableRow> rows = table.getRows();
                            XWPFTableRow row = rows.get(index);
                            tableCommon(row, key, formatString(value));
                        }
                    }
                }
                index++;
            }
        }
    }

    private static String formatString(Object str) {
        if (str == null) {
            return "";
        }
        if (str instanceof BigDecimal) {
            BigDecimal res = (BigDecimal) str;
            return res.toPlainString();
        } else if (str instanceof Integer) {
            Integer res = (Integer) str;
            return res.toString();
        } else if (str instanceof Double) {
            Double res = (Double) str;
            return res.toString();
        }
        return (String) str;
    }

    /**
     * 公共方法
     *
     * @param row
     * @param key
     * @param value
     * @author CXY
     * @date 2024/12/19 16:14
     */
    private static void tableCommon(XWPFTableRow row, String key, String value) {
        key = "${" + key + "}";
        for (XWPFTableCell cell : row.getTableCells()) {
            String text = cell.getText();
            if (text != null && text.contains(key)) {
                text = text.replace(key, value);
                cell.removeParagraph(0);
                cell.setText(text);
            }
        }
    }

    /**
     * 公共导出方法（.docx文件）
     * @param response
     * @param fileName
     * @param doc
     * @author CXY
     * @date 2024/12/24 10:42
     */
//    public static void commonExport(HttpServletResponse response, String fileName, XWPFDocument doc) {
//        try (ServletOutputStream outputStream = response.getOutputStream()) {
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/x-msdownload;charset=UTF-8");
//            if (!fileName.contains(".")) {
//                fileName += ".docx";
//            }
//            response.setHeader("Content-Disposition", "attachment; filename=" + UriUtils.encodeUri(fileName, "UTF-8"));
//            doc.write(outputStream);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                doc.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

}
