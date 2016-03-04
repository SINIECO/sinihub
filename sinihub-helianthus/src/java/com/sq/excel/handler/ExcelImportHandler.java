package com.sq.excel.handler;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: shuiqing
 * Date: 2015/7/17
 * Time: 11:53
 * Email: shuiqing301@gmail.com
 * GitHub: https://github.com/ShuiQing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
public class ExcelImportHandler {

    private static Logger log = LoggerFactory.getLogger(ExcelImportHandler.class);

    /*
	 * 解析来源文件时是否需要检查列头的名称是否正确
	 */
    private boolean checkHeader = false;

    /**
     * @return the checkHeader
     */
    public boolean isCheckHeader() {
        return this.checkHeader;
    }

    /**
     * @param checkHeader the checkHeader to set
     */
    public void setCheckHeader(boolean checkHeader) {
        this.checkHeader = checkHeader;
    }

    /**
     * 检查导入文件的列头是否和指定的列模型的列头匹配
     */
    public boolean checkHeader() {
        return true;
    }

    public static Map<Integer, String[]> getDataFromXls(File file, int rowIndex) {
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
            HSSFWorkbook workbook = new HSSFWorkbook(fs);
            int num = workbook.getNumberOfSheets();
            if (num > 1) {
                String error = "模板的sheet数不能超过1。 文件名称: " + file.getName();
                log.error(error);
                return null;
            }
            Map<Integer, String[]> results = new HashMap<Integer, String[]>();
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);
            int rowNum = sheet.getLastRowNum();
            Row header = sheet.getRow(rowIndex);
            int totalCol = header.getLastCellNum();// 获取总列数
            for (int i = rowIndex + 1/*i = 1*/; i <= rowNum; i++) {
                Row row = sheet.getRow(i);
                if (row == null || isBlankRow(row)) {
                    continue;
                }
                try {
                    results.put(i, getXlsRowData(row, totalCol));
                } catch (Exception e) {
                    String error = "解析导入文件出错! 文件名称: " + file.getName();
                    log.error(error, e);
                    return null;
                }
            }
            return results;
        } catch (Exception e) {
            String error = "解析导入文件出错! 文件名称: " + file.getName();
            log.error(error, e);
            return null;
        }
    }

    /**
     * 获得cvs格式文件中的数据
     * @param file
     * @return
     */
    public static Map<Integer, String[]> getDataFromCsv(File file, int rowIndex) {
        try {
            List<String> lines = FileUtils.readLines(file, "GBK");
            for(String str:lines) {
                System.out.println(str);
            }

            Map<Integer, String[]> results = new HashMap<Integer, String[]>();
            int rowNum = rowIndex;
            if (lines != null) {
                for (int i = rowIndex + 1/*i = 1*/; i < lines.size(); i++) {
                    if (isBlankLine(lines.get(i))) {
                        continue;
                    }
                    try {
                        results.put(rowNum, getCvsRowData(lines.get(i)));
                    } catch (Exception e) {
                        String error = "解析导入文件出错! 文件名称: " + file.getName();
                        log.error(error);
                        return null;
                    }
                    rowNum++;
                }
            }
            return results;
        } catch (Exception e) {
            String error = "解析导入文件出错! 文件名称: " + file.getName();
            log.error(error,e);
            return null;
        }
    }

    /**
     * 获得cvs文件每行数据
     * @param line
     * @return
     */
    private static String[] getCvsRowData(String line) {
        try {
            String[] rowDatas = line.split(",");
            for (int i = 0; i < rowDatas.length; i++) {
                String s = rowDatas[i];
                rowDatas[i] = s == null ? s : s.trim();
                if (s != null && s.startsWith("\"") && s.endsWith("\"")) {
                    rowDatas[i] = s.substring(1, s.length() - 1);
                    System.out.println(rowDatas[i]);
                }
            }
            return rowDatas;
        } catch (Exception e) {
            String error = "获得cvs文件数据出错!";
            log.error(error, e);
            throw new com.sq.core.exception.OperationFailtureException(error + " " + e.getMessage());
        }
    }

    /**
     * 获得Xls表的每行数据
     * @param row
     * @param totalCol 总列数
     * @return
     */
    private static String[] getXlsRowData(Row row, int totalCol) {
        try {
            String[] rowDatas = new String[totalCol];
            for (int i = 0; i < rowDatas.length; i++) {
                org.apache.poi.ss.usermodel.Cell cell = row.getCell(i);
                if (cell != null) {
                    int cellType = cell.getCellType();
                    if (cellType == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC) {
                        DecimalFormat formatter = new DecimalFormat("0.##########");
                        rowDatas[i] = formatter.format(cell.getNumericCellValue());
                    } else {
                        rowDatas[i] = cell.getStringCellValue();
                    }
                } else {
                    rowDatas[i] = "";
                }
                if (rowDatas[i] != null) {// 删除空格字符
                    String aa = rowDatas[i].trim();
                    int pos = aa.indexOf(' ');
                    while (pos != -1) {
                        aa = aa.substring(0, pos) + aa.substring(pos + 1);
                        pos = aa.indexOf(' ');
                    }
                    rowDatas[i] = aa;
                }
            }
            return rowDatas;
        } catch (Exception e) {
            String error = "获得exl一行数据出错!";
            log.error(error, e);
            throw new com.sq.core.exception.OperationFailtureException(error + " " + e.getMessage());
        }
    }

    /**
     * 空白数据判断
     * @param line
     * @return
     */
    private static boolean isBlankLine(String line) {
        if (empty(line))
            return true;
        String[] args = line.split(",");
        if (args.length == 0)
            return false;
        boolean blank = true;
        for (int i = 0; i < args.length; i++) {
            if (!empty(args[i])) {
                blank = false;
                break;
            }
        }
        return blank;
    }

    /**
     * 空白数据判断
     * @param row
     * @return
     */
    private static boolean isBlankRow(Row row) {
        if (row.getLastCellNum() <= 0) {
            return true;
        }
        String[] args = new String[row.getLastCellNum()];
        if (args.length == 0)
            return true;
        for (int i = 0; i < args.length; i++) {
            org.apache.poi.ss.usermodel.Cell cell = row.getCell(i);
            if (cell != null) {
                int cellType = cell.getCellType();
                if (cellType == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC) {
                    args[i] = cell.getNumericCellValue() + "";
                } else {
                    args[i] = cell.getStringCellValue();
                }
            } else {
                args[i] = "";
            }
        }
        boolean blank = true;
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null && args[i].trim().length() != 0) {
                blank = false;
                break;
            }
        }
        return blank;
    }

    /**
     * 简单判断文件是否存在，用户可以根据自己的业务需要自行覆盖
     */
    public com.sq.excel.domain.HandleResult doConfirmSource(Object dataSource) {
        com.sq.excel.domain.HandleResult result = new com.sq.excel.domain.HandleResult(false, null);
        try {
            File file = (File) dataSource;
            if (file.isFile()) {
                result.setSuccess(true);
            }
            return result;
        } catch (Exception e) {
            log.error("导入文件无效", e);
            return result;
        }
    }

    /**
     * 判断字符串是否为空
     */
    public static boolean empty(String str) {
        return str == null || str.length() == 0;
    }
}
