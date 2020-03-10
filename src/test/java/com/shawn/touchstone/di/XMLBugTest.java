package com.shawn.touchstone.di;

import com.google.common.base.MoreObjects;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import org.junit.Test;

public class XMLBugTest {

    @Test
    public void bugs() {
        XStream xStream = new XStream();
        xStream.processAnnotations(BB.class);
        //will parse correctly when changing 'class' to something else like 'clazz'
        final String xml = ""//
                + "<bb class=\"counter\">"
                + "</bb>";
        xStream.processAnnotations(BB.class);
        final BB bb = (BB) xStream.fromXML(xml);
        System.out.println(bb);
    }

    @XStreamAlias("bb")
    public static class BB {

        @XStreamAsAttribute
        @XStreamAlias("class")
        //change alias to clazz and related attribute in xml, the test would pass
        public String clazz;

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("clazz", clazz)
                    .toString();
        }
    }
}
