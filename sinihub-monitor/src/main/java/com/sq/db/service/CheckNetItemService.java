package com.sq.db.service;

import com.sq.db.domain.CheckItem;
import com.sq.db.domain.Constant;
import com.sq.db.domain.ProjectPoint;
import com.sq.db.repository.CheckItemRepository;
import com.sq.db.repository.ProjectPointRepository;
import com.sq.inject.annotation.BaseComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ywj on 2015/12/29.
 * 监测网络连通情况类
 */
@Service("checkNetItemService")
public class CheckNetItemService  {

    @BaseComponent
    @Autowired
    private ProjectPointRepository projectPointRepository;

    @Autowired
    private CheckNetService checkNetService;

    @Autowired
    private CheckItemRepository checkItemRepository;
    private static final Logger log = LoggerFactory.getLogger(CheckNetItemService.class);

    /**
     * 遍历所有checkItem的数据信息
     * @throws Exception
     */
    public void checkAllNetCheckItem() {
        //获取数据库中所有的checkItem数据
        List<CheckItem> checkItemList = checkItemRepository.findAll();
        //遍历传入CheckItem对象
        for (CheckItem c : checkItemList) {
            //检查网络
            checkNetService.checkHospResult(c);
        }
    }
}
