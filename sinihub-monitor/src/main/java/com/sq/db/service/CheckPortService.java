package com.sq.db.service;

import com.sq.db.domain.CheckPort;
import com.sq.db.repository.CheckPortRepository;
import com.sq.inject.annotation.BaseComponent;
import com.sq.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ywj on 2016/1/8.
 * 检查端口服务类
 */
@Service("checkPortService")
public class CheckPortService extends BaseService<CheckPort,Long> {

    private static final Logger log = LoggerFactory.getLogger(CheckPortService.class);

    @BaseComponent
    @Autowired
    private CheckPortRepository checkPortRepository;

    @Autowired
    private CheckPortResultService checkPortResultService;
    /**
     * Created by ywj on 2016/1/8.
     * 检查所有的端口信息
     */
    public void checkAllPort(){
        //取出所有的检查项
        List<CheckPort> checkPortList = checkPortRepository.findAll();
        for(CheckPort c : checkPortList){
            //按顺序传入参数进行检查
            checkPortResultService.checkPortResult(c);
        }
    }
}
