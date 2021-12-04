package com.zyuan.boot.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

@Data
@ExcelTarget("TestExport")
public class TestEasypoi {

    private Long id;

    @Excel(name = "名称")
    private String name;

    @Excel(name = "流程")
    private String process;

    @Excel(name = "备注")
    private String remark;

    @Excel(name = "年龄")
    private Integer age;

    @Excel(name = "序号")
    private String orderNo;

}
