package com.ant.test;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ant.pojo.User;
/**
 * @描述 JUnit测试<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-9
 */
@ContextConfiguration(locations = { "classpath:applicationContext.xml" ,"classpath:spring-mvc.xml"})  
@RunWith(SpringJUnit4ClassRunner.class)  
 public class ProjectTest extends AbstractJUnit4SpringContextTests {
	@Autowired
	private SessionFactory sessionFactory;
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	
	@Test
	public void testAddUser(){
		for(int i=1;i<10;i++){
			User user = new User();
			user.setCreateTime("2017-05-0"+i);
			user.setReferenceAccount("1500000000"+i);
			user.setReferenceId(i);
			user.setUserAccount("1835371012"+i);
			user.setUserName("user"+i);
			user.setUserPassword("userpassword");
			user.setIsDel(0);
			user.setLoginState(String.valueOf(System.currentTimeMillis()));
			user.setUserRole(0);
			sessionFactory.openSession().save(user);
			System.out.println("---------------------"+i);
		}
	}
	
	
	@After
	public void after(){
		sessionFactory.close();
	}
 }