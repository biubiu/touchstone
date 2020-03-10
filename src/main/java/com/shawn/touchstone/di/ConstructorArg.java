package com.shawn.touchstone.di;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import static java.lang.Class.forName;

/**
 * 在下面的 ConstructorArg 类中，
 * isRef = true，arg 表示 String 类型的 refBeanId，type 不需要设置；
 * isRef = false，arg、type 都需要设置。请根据这个需求，完善 ConstructorArg 类
 */
@Data
public class ConstructorArg {
    private boolean isRef;
    private Class type;
    private Object arg;

    private ConstructorArg(){}
    private ConstructorArg(Builder builder){
        this.isRef = builder.isRef;
        this.type = builder.type;
        this.arg = builder.arg;
    }

    public static class Builder {

        private boolean isRef;

        private Class type;

        private Object arg;

        public ConstructorArg build() {
            if (!isRef && (type == null || arg == null)) {
                throw new IllegalArgumentException("not ref type but not set type and arg");
            }
            if (isRef && (type != null || arg != null)) {
                throw new IllegalArgumentException("is ref type but not set type and arg");
            }
            return new ConstructorArg(this);
        }

        public Builder setIsRef(boolean isRef) {
            this.isRef = isRef;
            return this;
        }

        public Builder setType(String type) {
            try {
                if (StringUtils.isNoneEmpty(type)) {
                    this.type = forName(type);
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("class not found " + type);
            }
            return this;
        }

        public Builder setArg(Object arg) {
            this.arg = arg;
            return this;
        }
    }
}
