package com.sq.test;

import com.sq.db.service.CheckItemService;
import com.sq.db.service.CheckNetItemService;
import com.sq.db.service.CheckPortService;
import com.sq.db.service.CheckTradeItemService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ywj on 2016/1/11.
 */
public class TestPort extends TestCase {

    @Autowired
    private CheckNetItemService checkNetItemService;
    @Autowired
    private CheckTradeItemService checkTradeItemService;
    @Autowired
    private CheckItemService checkItemService;
    @Autowired
    private CheckPortService checkPortService;
    @Test
    public void testPort(){
        checkNetItemService.checkAllNetCheckItem();
        for(int i=0;i<1;i++){
            //checkPortService.checkAllPort();
          // checkItemService.checkAllCheckItem();

           checkTradeItemService.checkAllProjectPointTrade();
        }


    }

}
