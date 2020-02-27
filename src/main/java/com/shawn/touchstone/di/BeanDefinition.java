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
