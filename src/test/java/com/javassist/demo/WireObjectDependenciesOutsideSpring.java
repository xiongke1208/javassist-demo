package com.javassist.demo;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

import javax.annotation.Resource;

import java.util.stream.Stream;
import org.junit.Test;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertNotNull;

/**
 * Created by 1234qwer on 2018/5/25.
 */
public class WireObjectDependenciesOutsideSpring {


    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /**
     * Instantiate the bean and then inject dependencies using AutoWireCapableBeanFactory.autowireBean(instance)
     * 看下输出容器内的所有bean，容器内并没有person对象。只是创建返回此对象，并对该对象内的依赖进行注入。
     */
    @Test
    public void test_create_bean_and_inject_dependencies() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        Person person = (Person) applicationContext.getAutowireCapableBeanFactory().createBean(Person.class, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, false);

        assertNotNull(person.getContext());
        Stream.of(applicationContext.getBeanDefinitionNames()).forEach(System.out::println);
        applicationContext.close();
    }

    /**
     * Instantiate the bean and then inject dependencies using AutoWireCapableBeanFactory.autowireBean(instance)
     */
    @Test
    public void test_only_inject_dependencies() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        Person person = new Person();
        assertNull(person.getContext());
        applicationContext.getAutowireCapableBeanFactory().autowireBean(person);

        assertNotNull(person.getContext());
        Stream.of(applicationContext.getBeanDefinitionNames()).forEach(System.out::println);

        applicationContext.close();
    }



    @Configuration
    static class SpringConfig {

    }

    static class Person {
        @Resource
        private ApplicationContext context;

        public ApplicationContext getContext() {
            return context;
        }

    }

    @Configuration
    @EnableSpringConfigured
    @EnableLoadTimeWeaving(aspectjWeaving = EnableLoadTimeWeaving.AspectJWeaving.ENABLED)
    static class SpringConfig2 {

    }

    @Configurable(autowire = Autowire.BY_TYPE, dependencyCheck = true)
    static class Person2 {
        @Resource
        private ApplicationContext context;

        public ApplicationContext getContext() {
            return context;
        }

    }

}
