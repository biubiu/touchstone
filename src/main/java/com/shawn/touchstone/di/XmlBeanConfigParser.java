package com.shawn.touchstone.di;

import com.google.common.base.MoreObjects;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Class.forName;

public class XmlBeanConfigParser implements BeanConfigParser {
    private XStream xStream;

    public XmlBeanConfigParser() {
        this.xStream = new XStream();
        this.xStream.processAnnotations(RawBean.class);
        this.xStream.processAnnotations(RawConstructorArg.class);
    }

    @Override
    public List<BeanDefinition> parse(InputStream inputStream) {
        List<RawBean> rawObjs = (List<RawBean>)xStream.fromXML(this.getClass().getResourceAsStream("beans.xml"));
        return convert(rawObjs);
    }

    private List<BeanDefinition> convert(List<RawBean> raws) {
        return raws.stream().map(this::convert).collect(Collectors.toList());
    }

    private BeanDefinition convert(RawBean raw) {
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setClassName(raw.clazz);
        beanDefinition.setId(raw.id);
        beanDefinition.setScope(BeanDefinition.Scope.SINGLETON);
        beanDefinition.setLazyInit(to(raw.lazyInit));
        List<BeanDefinition.ConstructorArg> args = new ArrayList<>();
        raw.cons.forEach(r -> {
            BeanDefinition.ConstructorArg arg = new BeanDefinition.ConstructorArg();
            arg.setArg(r.value);
            arg.setRef(to(r.ref));
            try {
                arg.setType(forName(r.type));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            args.add(arg);
        });
        beanDefinition.setConstructorArgs(args);
        return beanDefinition;
    }

    private Boolean to(String val) {
        return StringUtils.isEmpty(val) &&
                val.equalsIgnoreCase("true") ? true : false;
    }
    @XStreamAlias("bean")
    public static class RawBean {
        @XStreamAsAttribute
        private String id;

        @XStreamImplicit(itemFieldName="constructor-arg")
        private List<RawConstructorArg> cons;

        @XStreamAsAttribute
        @XStreamAlias("class")
        private String clazz;

        @XStreamAsAttribute
        @XStreamAlias("lazy-init")
        private String lazyInit;

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("id", id)
                    .add("clazz", clazz)
                    .add("cons", cons)
                    .toString();
        }
    }

    @XStreamAlias("constructor-arg")
    public static class RawConstructorArg {
        @XStreamAsAttribute
        private String ref;
        @XStreamAsAttribute
        private String type;
        @XStreamAsAttribute
        private String value;

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("ref", ref)
                    .add("type", type)
                    .add("value", value)
                    .toString();
        }
    }
}
