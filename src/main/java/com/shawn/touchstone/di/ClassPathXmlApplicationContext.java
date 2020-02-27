package com.shawn.touchstone.di;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ClassPathXmlApplicationContext implements ApplicationContext {

    private BeansFactory beansFactory;

    private BeanConfigParser beanConfigParser;

    public ClassPathXmlApplicationContext(String configPath) {
        this.beansFactory = new BeansFactory();
        this.beanConfigParser = new XmlBeanConfigParser();
        loadBeanDefinition(configPath);
    }

    private void loadBeanDefinition(String configPath) {
        InputStream in = null;
        try {
            in = this.getClass().getResourceAsStream("/" + configPath);
            if (in == null) {
                throw new RuntimeException("cannot find config file " + configPath);
            }
            List<BeanDefinition> beanDefinitions = beanConfigParser.parse(in);
            beansFactory.addBeanDefinitions(beanDefinitions);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                System.err.println(String.format("configPath = %s, with error closing %s", configPath, e));
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object getBean(String beanId) {
        return beansFactory.get(beanId);
    }
}
