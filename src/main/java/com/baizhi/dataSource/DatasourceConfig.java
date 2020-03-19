package com.baizhi.dataSource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DatasourceConfig {
    /*
     * @program: BigDataProject
     * @description: 配置数据自定义数据源
     * @author: 周
     * @create date: 2020-03-19 15:50
     **/
    @Bean //Master
    @ConfigurationProperties("spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean //Slave1
    @ConfigurationProperties("spring.datasource.slave1")
    public DataSource slave1DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean //Slave2
    @ConfigurationProperties("spring.datasource.slave2")
    public DataSource slave2DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public DataSource proxyDataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
                                      @Qualifier("slave1DataSource") DataSource slave1DataSource,
                                      @Qualifier("slave2DataSource") DataSource slave2DataSource) {
        DataSourceProxy dataSourceProxy = new DataSourceProxy();
        //需要设置设置数据源
        dataSourceProxy.setDefaultTargetDataSource(masterDataSource);//默认数据源
        Map<Object, Object> mappedDatasources = new HashMap<Object, Object>();
        mappedDatasources.put("master", masterDataSource);
        mappedDatasources.put("slave-01", slave1DataSource);
        mappedDatasources.put("slave-02", slave2DataSource);
        dataSourceProxy.setTargetDataSources(mappedDatasources);
        return dataSourceProxy;
    }

    /**
     * 用户定义数据源 需要覆盖SqlSessionFactoty工厂
     *
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("proxyDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);//构建一个datasource
        sqlSessionFactoryBean.setTypeAliasesPackage("com.baizhi.entities");
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mappers/*.xml"));
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
        return sqlSessionFactory;
    }


    /**
     * 当自定义数据源，用户必须覆盖SqlSessionTemplate,开启BATCH处理模式
     *
     * @param sqlSessionFactory
     * @return
     */
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
    }

    /***
     * 当自定义数据源，用户必须注入，否则事务控制不生效
     * @param dataSource
     * @return
     */
    @Bean
    public PlatformTransactionManager platformTransactionManager(@Qualifier("proxyDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
