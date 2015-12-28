package com.sq.db.service;

import com.sq.db.component.DBUtil;
import com.sq.db.domain.ProjectPoint;
import com.sq.db.repository.ProjectPointRepository;
import com.sq.entity.BaseSerialDto;
import com.sq.entity.search.MatchType;
import com.sq.entity.search.Searchable;
import com.sq.inject.annotation.BaseComponent;
import com.sq.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by ywj on 2015/12/21
 * 建立各项目点的数据库连接
 */
@Service("projectPointService")
public class ProjectPointService extends BaseService<ProjectPoint,Long> {

    private static final Logger log = LoggerFactory.getLogger(ProjectPointService.class);

    @BaseComponent
    @Autowired
    private ProjectPointRepository projectPointRepository;

    /**
     * 外键关联t_projectPoint表取出数据
     * 建立新的远程数据库连接
     * @param projectPointId
     * @return
     * @throws Exception
     */
    public Connection instanceConnection(Long projectPointId) throws Exception{
            //获取主键为projectPointId的数据建立连接
            Searchable searchable = Searchable.newSearchable()
                    .addSearchFilter("id", MatchType.EQ, projectPointId);
            ProjectPoint pp = projectPointRepository.findOne(projectPointId.longValue());
            Connection conn = null;
            //构造url
            StringBuffer str = new StringBuffer();
            str.append(pp.getHospId()).append(":").append(pp.getPort()).append("/").append(pp.getDbName());
            //建立连接
            conn = DBUtil.getConn(str.toString(),pp.getUserName(),pp.getPassWord());
            return conn;
    }
}



