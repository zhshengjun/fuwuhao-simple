package com.stupidzhang.keywords.mapper;


import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * KeywordsTabDao继承基类
 */
@Repository
public interface KeywordsMapper {

    List<String> keys();

    String getKeysReply(String keyword);
}