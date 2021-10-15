package com.stupidzhang.jingfen.mapper;

import com.stupidzhang.jingfen.mode.UrlCommit;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlCommitMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UrlCommit record);

    int insertSelective(UrlCommit record);

    UrlCommit selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UrlCommit record);

    int updateByPrimaryKey(UrlCommit record);

    List<UrlCommit> selectAll();
}