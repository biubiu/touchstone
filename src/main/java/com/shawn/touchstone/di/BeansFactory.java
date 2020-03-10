package com.shawn.touchstone.di;

import com.google.common.annotations.VisibleForTesting;
import com.shawn.touchstone.di.exceptions.BeanCreationFailureException;
import com.shawn.touchstone.di.exceptions.NoSuchBeanDefinitionException;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class BeansFactory {

    private ConcurrentHashMap<String, Object> singletonObjs = new ConcurrentHashMap<>();

    private ConcurrentHashMap<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>();

    public void addBeanDefinitions(List<BeanDefinition> beanDefinitions) {
        beanDefinitions.forEach(bean -> {
            this.beanDefinitions.putIfAbsent(bean.getId(), bean);
            if (!bean.isLazyInit() && bean.isSingleton()) {
                createBean(bean);
            }
        });
    }

    public Object get(String beanId) {
        BeanDefinition beanDefinition = beanDefinitions.get(beanId);
        if (beanDefinition == null) {
            throw new NoSuchBeanDefinitionException("Bean is not defined: " + beanId);
        }
        return createBean(beanDefinition);
    }

    @VisibleForTesting
    protected Object createBean(BeanDefinition beanDefinition) {
        if (beanDefinition.isSingleton() && singletonObjs.contains(beanDefinition.getId())) {
            return singletonObjs.get(beanDefinition.getId());
        }
        Object bean = null;
        try {
            Class beanClass = Class.forName(beanDefinition.getClassName());
            List<ConstructorArg> args = beanDefinition.getConstructorArgs();
            if (args.isEmpty()) {
                bean = beanClass.newInstance();
            } else {
                Class[] argClasses = new Class[args.size()];
                Object[] argObjs = new Object[args.size()];
                for (int i = 0; i < args.size(); i++) {
                    ConstructorArg arg = args.get(i);
                    if (!arg.isRef()) {
                        argClasses[i] = arg.getType();
                        argObjs[i] = arg.getArg();
                    } else {
                        Object refName = arg.getArg();
                        BeanDefinition refBeanDefinition = beanDefinitions.get(refName);
                        if (refBeanDefinition == null) {
                            throw new NoSuchBeanDefinitionException("Bean is not defined: " + refName);
                        }
                        argClasses[i] = Class.forName(refBeanDefinition.getClassName());
                        argObjs[i] = createBean(refBeanDefinition);
                    }
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new BeanCreationFailureException(e.toString());
        }

        if (bean != null && beanDefinition.isSingleton()) {
            singletonObjs.putIfAbsent(beanDefinition.getId(), bean);
            return singletonObjs.get(beanDefinition.getId());
        }
        return bean;
    }


}
