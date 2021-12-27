package com.example.springbootinit.Utils;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.example.springbootinit.Exception.BussinessException;

import java.io.File;
import java.io.InputStream;

import java.util.List;

public class DataHandle {

    private static final String PARSE_FAILED = "文件解析错误!";

    /**
     * excel解析
     *
     * @param is
     * @param clazz
     * @return
     * @throws Exception
     */
    public static List<?> parseExcel(InputStream is, Class<?> clazz) throws BussinessException {
        try {
            ImportParams importParams = new ImportParams();
            return ExcelImportUtil.importExcelMore(is, clazz, importParams).getList();
        }catch (Exception e) {
            throw new BussinessException(PARSE_FAILED);
        }
    }

    /**
     * 删除本地临时文件
     *
     * @param file
     */
    public static void delteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            del.delete();
        }
    }
}
