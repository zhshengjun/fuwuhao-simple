package com.stupidzhang.convert.service;

public interface UrlConvertService {

    String convert(String content);

    /**
     * 是否支持
     *
     * @param type 类型
     * @return omit
     */
    Boolean support(String type);

}
