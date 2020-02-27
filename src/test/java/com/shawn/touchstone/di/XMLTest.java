package com.shawn.touchstone.di;

import com.google.common.base.MoreObjects;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class XMLTest {
    private XStream xStream;

    @Before
    public void setup() {
        this.xStream = new XStream();
        xStream.alias("beans", List.class);
        xStream.processAnnotations(Bean.class);
        xStream.processAnnotations(ConstructorArg.class);
    }

    @Test
    public void shouldParseXML() {
        List<Bean> beans = (List<Bean>) xStream.fromXML(this.getClass().getResourceAsStream("beans.xml"));
        System.out.println(beans.toString());
    }

    @Test
    public void simple() {
        final String xml = ""//
                + "<u-u id = \"rateLimiter\">"
                + "  <a_Str>custom value</a_Str>"
                + "</u-u>";
        xStream.processAnnotations(U.class);
        final U u = (U) xStream.fromXML(xml);
        System.out.println(u);
    }

    @XStreamAlias("u-u")
    public static class U {

        @XStreamAsAttribute
        @XStreamAlias("id")
        public String id;

        public String a_Str;

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("id", id)
                    .add("a_Str", a_Str)
                    .toString();
        }
    }

    @XStreamAlias("bean")
    public static class Bean {
        @XStreamAsAttribute
        private String id;

        @XStreamImplicit(itemFieldName="constructor-arg")
        private List<ConstructorArg> cons;

        @XStreamAsAttribute
        @XStreamAlias("class")
        private String clazz;

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
    public static class ConstructorArg {
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
