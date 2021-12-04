package com.zyuan.boot.easypoi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.zyuan.boot.entity.TestEasypoi;
import com.zyuan.boot.mapper.TestEasypoiMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TestExportByEasypoi implements ITestExportByEasypoi{

    @Autowired
    private TestEasypoiMapper testEasypoiMapper;

    @Override
    public void testExport() {
        List<TestEasypoi> allData = testEasypoiMapper.selectAllData();
        Workbook workbook = getSheets(allData);
        if (ObjectUtils.isEmpty(workbook)) {
            return;
        }
        String excelName = "第一个导出的Excel";

    }

    private Workbook getSheets(List<TestEasypoi> allData) {
        ExportParams params = new ExportParams();
        // 设置sheet的名称
        params.setSheetName("单sheet");
        // sheet使用的map
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("title",params);
        dataMap.put("entity",TestEasypoi.class);
        dataMap.put("data", allData);
        List<Map<String,Object>> sheetsList = new ArrayList<>();
        sheetsList.add(dataMap);
        Workbook workbook = ExcelExportUtil.exportExcel(sheetsList, ExcelType.HSSF);
        return workbook;
    }
}
