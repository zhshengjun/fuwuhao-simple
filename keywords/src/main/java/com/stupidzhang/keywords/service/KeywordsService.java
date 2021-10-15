package com.stupidzhang.keywords.service;

import com.stupidzhang.keywords.mapper.KeywordsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeywordsService {

    @Autowired
    private KeywordsMapper keywordsMapper;

    public List<String> getKeys() {
        return keywordsMapper.keys();
    }

    public String getKeysReply(String keyword) {
        return keywordsMapper.getKeysReply(keyword);
    }

    public boolean match(String keyword) {
        return getKeys().contains(keyword);
    }

}
