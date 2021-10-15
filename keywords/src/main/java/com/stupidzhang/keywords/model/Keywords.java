package com.stupidzhang.keywords.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * keywords_tab
 *
 * @author
 */
@Data
public class Keywords implements Serializable {
    
    private Integer id;

    /**
     * 关键词
     */
    private String key;

    /**
     * 回复
     */
    private String reply;

    /**
     * 是否删除
     */
    private Boolean deleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}