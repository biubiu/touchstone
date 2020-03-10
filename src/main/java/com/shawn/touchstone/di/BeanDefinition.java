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

    public enum Scope {
        SINGLETON,
        PROTOTYPE
    }
}
