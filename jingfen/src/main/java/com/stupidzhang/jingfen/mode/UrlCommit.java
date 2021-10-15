package com.stupidzhang.jingfen.mode;

import lombok.Data;

import java.io.Serializable;

/**
 * url_commit
 * @author 
 */
@Data
public class UrlCommit implements Serializable {
    private Long id;

    private String name;

    private String url;

    private static final long serialVersionUID = 1L;
}