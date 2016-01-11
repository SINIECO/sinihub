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
 * Created by ywj on 2015/12/30.
 *  监测地磅数据类
 */
@Service("checkTradeItemService")
public class CheckTradeItemService {
    private static final Logger log = LoggerFactory.getLogger(CheckTradeItemService.class);

    @BaseComponent
    @Autowired
    private ProjectPointRepository projectPointRepository;

    @Autowired
    private CheckTradeDataService checkTradeDataService;
    /**
     * 遍历所有ProjectPoint的数据信息
     * @throws Exception
     */
    public void checkAllProjectPointTrade() {
        //获取数据库中所有的ProjectPoint数据
        List<ProjectPoint> projectPointList = projectPointRepository.findAll();
        //遍历传入ProjectPoint对象
        for (ProjectPoint c : projectPointList) {
            //传入对象检查地磅
            checkTradeDataService.checkTrade(c);
        }
    }
}
