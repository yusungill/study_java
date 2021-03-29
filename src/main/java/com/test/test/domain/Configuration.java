package com.test.test.domain;


import com.test.test.service.EventUserUpgradePolicy;
import com.test.test.service.UserLevelUpgradePolicy;
import com.test.test.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.sql.DataSource;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public UserDao userDao() {
        UserDao userDao = new UserDao();
        userDao.setDataSource(dataSource());
        return userDao;
    }

    @Bean
    public UserLevelUpgradePolicy userLevelUpgradePolicy() {
        return new EventUserUpgradePolicy();
    }

    @Bean
    public DataSource dataSource() {

        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(org.mariadb.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mariadb://localhost:3306/tobi_spring");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        return dataSource;

    }

    @Bean
    public UserServiceImpl userService() {
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        userServiceImpl.setUserDao(userDao());
        userServiceImpl.setTransactionManager(transactionManager());
        userServiceImpl.setMailSender(mailSender());

        return userServiceImpl;
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager jdbcTr = new DataSourceTransactionManager();
        jdbcTr.setDataSource(dataSource());
        return jdbcTr;

    }

    @Bean
    public JavaMailSenderImpl mailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("mail.server.com");
        return javaMailSender;
    }
}
