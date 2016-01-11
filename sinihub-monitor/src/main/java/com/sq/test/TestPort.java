package com.sq.test;

import com.sq.db.service.CheckPortService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ywj on 2016/1/11.
 */
public class TestPort extends TestCase {

    @Autowired
    private CheckPortService checkPortService;
    @Test
    public void testPort(){
        checkPortService.checkAllPort();
    }

}
