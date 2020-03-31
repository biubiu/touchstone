package com.shawn.touchstone.chainOfResponsibility;

import com.shawn.touchstone.chainOfReponsibility.chain.ArrayHandlerChain;
import com.shawn.touchstone.chainOfReponsibility.chain.HandlerChain;
import com.shawn.touchstone.chainOfReponsibility.chain.ListHandlerChain;
import com.shawn.touchstone.chainOfReponsibility.handlers.Handler;
import com.shawn.touchstone.chainOfReponsibility.handlers.HandlerA;
import com.shawn.touchstone.chainOfReponsibility.handlers.HandlerB;
import org.junit.Test;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class HandlerTest {

    @Test
    public void testHandlerShouldInvokeHandleMethod_yes() {
        Handler a = spy(HandlerA.class);
        Handler b = spy(HandlerB.class);

        HandlerChain chain = new ListHandlerChain();
        chain.addHandler(a);
        chain.addHandler(b);

        chain.handle();

        verify(a, times(1)).handle();
        verify(b, times(1)).handle();
    }
}
