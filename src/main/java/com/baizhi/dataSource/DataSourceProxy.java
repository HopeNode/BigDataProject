package com.baizhi.dataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DataSourceProxy extends AbstractRoutingDataSource {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceProxy.class);
    private static final AtomicInteger round = new AtomicInteger(0); //轮询初始值为0
    private String masterDBKey = "master";
    private List<String> slaveDBksys = Arrays.asList("slave-01", "slave-02");

    /**
     * 需要在这个方法种判断用户是写操作还是读的操作
     *
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        String DBkey = null;
        //在线程变量中获取操作类型
        OperType operType = OperTypeContextHolder.getOperType();
        LOGGER.debug("---determineCurrentLookupKey---：" + operType);
        //如果是写操作则 用主库
        if (operType.equals(OperType.WRITE)) {
            DBkey = masterDBKey;
        } else {
            //否则是读操作则  轮询负载均衡用从库
            int i = round.incrementAndGet();//加一操作
            //当有溢出时重置为0
            if (i < 0) round.set(0);
            //当round.get()值模上slaveDBksys的长度2时 则实现 0 1 0 1 0 1 0 1
            Integer indes = round.get() % slaveDBksys.size();
            DBkey = slaveDBksys.get(indes);
        }
        LOGGER.debug("当前的DBkey：" + DBkey);
        return masterDBKey;
    }
}
