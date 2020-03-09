package com.shawn.touchstone.di;

import lombok.Data;

import java.util.List;

@Data
public class BeanDefinition {
    private String id;
    private String className;
    private List<ConstructorArg> constructorArgs;
    private Scope scope = Scope.SINGLETON;
    private boolean lazyInit = false;

    public boolean isSingleton(){
        return scope.equals(Scope.SINGLETON);
    }

    /**
     * 在下面的 ConstructorArg 类中，
     * isRef = true，arg 表示 String 类型的 refBeanId，type 不需要设置；
     * isRef = false，arg、type 都需要设置。请根据这个需求，完善 ConstructorArg 类
     */
    @Data
    public static class ConstructorArg {
        private boolean isRef;
        private Class type;
        private Object arg;
    }

    public enum Scope {
        SINGLETON,
        PROTOTYPE
    }
}
