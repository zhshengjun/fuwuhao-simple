package com.stupidzhang.cps.mapper;


import com.stupidzhang.cps.model.TaobaoCps;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * KeywordsTabDao继承基类
 */
@Repository
public interface TaobaoCpsMapper extends MyBatisBaseMapper<TaobaoCps, Integer> {
    List<String> keys();

    String getKeysReply(String keyword);
}