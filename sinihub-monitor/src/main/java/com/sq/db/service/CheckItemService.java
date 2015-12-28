package com.sq.db.service;

import com.sq.db.domain.CheckItem;
import com.sq.db.domain.ProjectPoint;
import com.sq.db.repository.CheckItemRepository;
import com.sq.db.repository.ProjectPointRepository;
import com.sq.entity.search.MatchType;
import com.sq.entity.search.Searchable;
import com.sq.inject.annotation.BaseComponent;
import com.sq.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ywj on 2015/12/21.
 */
@Service("checkItemService")
public class CheckItemService extends BaseService<CheckItem,Long> {

    @BaseComponent
    @Autowired
    private CheckItemRepository checkItemRepository;

    @Autowired
    private OriginalDateService originalDateService;


    private static final Logger log = LoggerFactory.getLogger(CheckItemService.class);

    /**
     * 遍历所有checkItem的数据信息
     * @throws Exception
     */
    public void checkAllCheckItem() {
        //获取数据库中所有的checkItem数据
        List<CheckItem> checkItemList = checkItemRepository.findAll();
        //遍历传入CheckItem对象

        try {
            for (CheckItem c : checkItemList) {
                originalDateService.checkOriginal(c);
            }
        } catch (Exception e) {
            log.error("checkAllCheckItem->检查项目点的通讯状态出错",e);
        }
    }
}
