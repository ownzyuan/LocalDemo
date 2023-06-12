package com.zyuan.boot.空值转换;

import com.zyuan.boot.entity.ConvertNullFiledInfo;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
public class ConvertNullFiledService<T> {

    /**
     * 写死代码的方式一个一个字段来转换
     * @param info
     * @return
     */
    public ConvertNullFiledInfo convertFunction01(ConvertNullFiledInfo info) {
        info.setIntNum(info.getIntNum() == null ? -1 : info.getIntNum());
        info.setLongNum(info.getLongNum() == null ? -1L : info.getLongNum());
        info.setStr(info.getStr() == null ? "" : info.getStr());
        info.setInfo(info.getInfo() == null ? new ConvertNullFiledInfo() : info.getInfo());
        return info;
    }

    /**
     * 遍历field的方式一个一个字段来转换
     * @param info
     * @return
     */
    public ConvertNullFiledInfo convertByField(ConvertNullFiledInfo info) {
        try {
            Field[] fields = info.getClass().getDeclaredFields();
            for (Field field : fields) {
                // 设置可访问私有变量
                field.setAccessible(true);
                // 获取当前字段值
                Object value = field.get(info);
                // value不为空就跳过
                if (value != null) {
                    continue;
                }
                // 获取当前字段类型
                Class<?> type = field.getType();
                if (type == Integer.class) {
                    // Integer类型就设置为-1
                    field.set(info, -1);
                } else if (type == Long.class) {
                    // Long类型就设置为-1L
                    field.set(info, -1L);
                } else if (type == String.class) {
                    // String类型就设置为“”
                    field.set(info, "");
                } else if (type == ConvertNullFiledInfo.class) {
                    // ConvertNullFiledInfo类型就设置为新对象
                    field.set(info, new ConvertNullFiledInfo());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 使用泛型，遍历field的方式一个一个字段来转换
     * @param object
     * @return
     */
    public T convertByFieldGeneric(T object) {
        try {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                // 设置可访问私有变量
                field.setAccessible(true);
                // 获取当前字段值
                Object value = field.get(object);
                // value不为空就跳过
                if (value != null) {
                    continue;
                }
                // 获取当前字段类型
                Class<?> type = field.getType();
                if (type == Integer.class) {
                    // Integer类型就设置为-1
                    field.set(object, -1);
                } else if (type == Long.class) {
                    // Long类型就设置为-1L
                    field.set(object, -1L);
                } else if (type == String.class) {
                    // String类型就设置为“”
                    field.set(object, "");
                } else if (type == object.getClass()) {
                    // T类型就设置为新对象
                    Object newObj = object.getClass().newInstance();
                    field.set(object, newObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

}
