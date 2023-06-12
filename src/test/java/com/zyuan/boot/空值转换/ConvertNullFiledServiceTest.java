package com.zyuan.boot.空值转换;

import com.zyuan.boot.entity.ConvertNullFiledInfo;
import com.zyuan.boot.entity.ConvertNullFiledInfoV2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ConvertNullFiledServiceTest {

    @Autowired
    private ConvertNullFiledService convertNullFiledService;

    @Test
    public void convertFunction01() {
        // str和info字段给null
        ConvertNullFiledInfo info = createConvertNullFiledInfo(1, 1L, null, null);
        ConvertNullFiledInfo result = convertNullFiledService.convertFunction01(info);
        System.out.println(result);
    }

    @Test
    public void convertByField() {
        // str和info字段给null
        ConvertNullFiledInfo info = createConvertNullFiledInfo(1, 1L, null, null);
        ConvertNullFiledInfo result = convertNullFiledService.convertByField(info);
        System.out.println(result);
    }

    @Test
    public void convertByFieldGeneric() {
        // 全部字段给null
        ConvertNullFiledInfo info = createConvertNullFiledInfo(null, null, null, null);
        ConvertNullFiledInfo result = (ConvertNullFiledInfo) convertNullFiledService.convertByFieldGeneric(info);
        System.out.println(result);

        // 全部字段给null
        ConvertNullFiledInfoV2 infoV2 = createConvertNullFiledInfoV2(null, null, null, null);
        ConvertNullFiledInfoV2 resultV2 = (ConvertNullFiledInfoV2) convertNullFiledService.convertByFieldGeneric(infoV2);
        System.out.println(resultV2);
    }

    /**
     * 自定义字段创建ConvertNullFiledInfo
     * @param intNum
     * @param longNum
     * @param str
     * @param info
     * @return
     */
    private ConvertNullFiledInfo createConvertNullFiledInfo(Integer intNum, Long longNum, String str, ConvertNullFiledInfo info) {
        ConvertNullFiledInfo result = new ConvertNullFiledInfo();
        result.setIntNum(intNum);
        result.setLongNum(longNum);
        result.setStr(str);
        result.setInfo(info);
        return result;
    }

    /**
     * 自定义字段创建ConvertNullFiledInfoV2
     * @param intNum
     * @param longNum
     * @param str
     * @param info
     * @return
     */
    private ConvertNullFiledInfoV2 createConvertNullFiledInfoV2(Integer intNum, Long longNum, String str, ConvertNullFiledInfoV2 info) {
        ConvertNullFiledInfoV2 result = new ConvertNullFiledInfoV2();
        result.setIntNum(intNum);
        result.setLongNum(longNum);
        result.setStr(str);
        result.setInfo(info);
        return result;
    }

}