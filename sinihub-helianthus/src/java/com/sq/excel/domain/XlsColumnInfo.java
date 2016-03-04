package com.sq.excel.domain;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Excel基础的column对象.
 * User: shuiqing
 * Date: 2015/7/17
 * Time: 10:32
 * Email: shuiqing301@gmail.com
 * GitHub: https://github.com/ShuiQing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
public class XlsColumnInfo {

    private String columnName;

    private int columnIndex;

    private boolean required;

    private com.sq.core.Enum.DataType dataType;

    private String formatPattern;

    private String regularExpression;

    private DateFormat dateFormat;

    /**
     * 默认构造函数
     */
    public XlsColumnInfo() {
    }

    /**
     * 默认构造函数
     */
    public XlsColumnInfo(String columnName, int columnIndex, com.sq.core.Enum.DataType dataType, boolean required) {
        this.columnName = columnName;
        this.columnIndex = columnIndex;
        this.dataType = dataType;
        this.required = required;
        if (dataType == com.sq.core.Enum.DataType.DateTime) {
            this.formatPattern = "yyyy-MM-dd";
        }
        this.init();
    }

    /**
     * 默认构造函数
     */
    public XlsColumnInfo(String columnName, int columnIndex, com.sq.core.Enum.DataType dataType, boolean required, String expression) {
        this.columnName = columnName;
        this.columnIndex = columnIndex;
        this.dataType = dataType;
        this.required = required;
        switch (dataType) {
            case DateTime:
                this.formatPattern = expression;
                break;
            case String:
                this.regularExpression = expression;
                break;
            default:
                break;
        }
        this.init();
    }


    private void init() {
        if (this.dataType == com.sq.core.Enum.DataType.DateTime) {
            this.dateFormat = new SimpleDateFormat(this.formatPattern);
        }
    }

    public boolean validate(String value) {
        boolean result = false;
        switch (this.dataType) {
            case String: {
                if (!StringUtils.isEmpty(this.regularExpression)) {
                    result = Pattern.matches(this.regularExpression, value);
                } else {
                    result = true;
                }
                break;
            }
            case Boolean: {
                if (("true").equals(value) || ("false").equals(value) || ("是").equals(value) || ("否").equals(value)) {
                    result = true;
                }
                break;
            }
            case DateTime: {
                try {
                    if (NumberUtils.isNumber(value)) {
                        SimpleDateFormat sdf = new SimpleDateFormat(this.formatPattern);
                        double d = Double.parseDouble(value);
                        Date date = HSSFDateUtil.getJavaDate(d);
                        value = sdf.format(date);
                        result = true;
                    } else {
                        this.dateFormat.parse(value);
                        result = true;
                    }
                } catch (ParseException e) {
                    result = false;
                }
                break;
            }
            case Integer: {
                result = NumberUtils.isDigits(value);
                break;
            }
            case Double: {
                result = NumberUtils.isNumber(value);
                break;
            }
            case Float: {
                result = NumberUtils.isNumber(value);
                break;
            }
        }

        return result;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public com.sq.core.Enum.DataType getDataType() {
        return dataType;
    }

    public void setDataType(com.sq.core.Enum.DataType dataType) {
        this.dataType = dataType;
    }

    /**
     * @return the formatPattern
     */
    public String getFormatPattern() {
        return this.formatPattern;
    }

    /**
     * @param formatPattern the formatPattern to set
     */
    public void setFormatPattern(String formatPattern) {
        this.formatPattern = formatPattern;
    }

    public String getRegularExpression() {
        return this.regularExpression;
    }

    public void setRegularExpression(String regularExpression) {
        this.regularExpression = regularExpression;
    }
}

