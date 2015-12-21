package com.sq.loadometer.service;

import com.sq.entity.search.MatchType;
import com.sq.entity.search.Searchable;
import com.sq.inject.annotation.BaseComponent;
import com.sq.loadometer.domain.LoadometerIndicatorDto;
import com.sq.loadometer.domain.Trade;
import com.sq.loadometer.repository.TradeDataRepository;
import com.sq.quota.domain.QuotaConsts;
import com.sq.quota.domain.QuotaInstance;
import com.sq.quota.domain.QuotaTemp;
import com.sq.quota.repository.QuotaInstanceRepository;
import com.sq.quota.repository.QuotaTempRepository;
import com.sq.service.BaseService;
import com.sq.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 地磅业务类--负责数据同步和指标生成
 * User: shuiqing
 * Date: 2015/9/15
 * Time: 10:20
 * Email: shuiqing301@gmail.com
 * GitHub: https://github.com/ShuiQing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
@Service
public class TradeDataService extends BaseService<Trade, Long> {

    private static final Logger log = LoggerFactory.getLogger(TradeDataService.class);

    @Autowired
    @BaseComponent
    private TradeDataRepository tradeDataRepository;

    @Autowired
    private QuotaTempRepository indicatorTempRepository;

    @Autowired
    private QuotaInstanceRepository indicatorInstanceRepository;

    /***
     * 生成日地磅指标
     * @param computCal 生成日期
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void generateLoadometerIndicator (Calendar computCal) {
        List<QuotaInstance> indicatorInstanceList = new ArrayList<QuotaInstance>();

        String generateDate = DateUtil.formatCalendar(computCal,DateUtil.DATE_FORMAT_DAFAULT);
        //查询地磅指标数据
        List<LoadometerIndicatorDto> loadometerIndicatorDtoList = tradeDataRepository
                .queryForLoadometerIndicator(generateDate);
        List<String> loadometerCodeList = new ArrayList<String>();
        for (LoadometerIndicatorDto loadometerIndicatorDto:loadometerIndicatorDtoList) {
            loadometerCodeList.add(loadometerIndicatorDto.getIndicatorCode());
        }

        if (!loadometerCodeList.isEmpty()) {
            //删除已经存在的当日的地磅指标数据
            Searchable removeLoadometerCodeSearchable = Searchable.newSearchable()
                    .addSearchFilter("indicatorCode", MatchType.IN, loadometerCodeList)
                    .addSearchFilter("statDateNum", MatchType.EQ, generateDate);
            indicatorInstanceRepository.deleteInBatch(indicatorInstanceRepository.findAll(removeLoadometerCodeSearchable));
        }

        //保存查询到的当日地磅指标数据
        for(LoadometerIndicatorDto loadometerIndicatorDto:loadometerIndicatorDtoList) {
            QuotaTemp indicatorTemp = indicatorTempRepository.findByIndicatorCode(loadometerIndicatorDto.getIndicatorCode());
            QuotaInstance indicatorInstance = new QuotaInstance(indicatorTemp);
            try {
                indicatorInstance.setFloatValue(Double.parseDouble(loadometerIndicatorDto.getTotalAmount()));
                indicatorInstance.setValueType(QuotaConsts.VALUE_TYPE_DOUBLE);
                indicatorInstance.setStatDateNum(Integer.parseInt(generateDate));
                indicatorInstance.setInstanceTime(DateUtil.stringToDate(generateDate, DateUtil.DATE_FORMAT_DAFAULT));
            } catch (ParseException e) {
                log.error("stringToCalendar error:", e);
            }
            indicatorInstanceList.add(indicatorInstance);
        }
        indicatorInstanceRepository.save(indicatorInstanceList);
    }
}
