package com.shawn.touchstone.chainOfResponsibility;

import com.google.common.collect.Lists;
import com.google.common.net.InternetDomainName;
import com.shawn.touchstone.chainOfReponsibility.chain.HandlerChain;
import com.shawn.touchstone.chainOfReponsibility.chain.ListHandlerChain;
import com.shawn.touchstone.chainOfReponsibility.handlers.Handler;
import com.shawn.touchstone.chainOfReponsibility.handlers.HandlerA;
import com.shawn.touchstone.chainOfReponsibility.handlers.HandlerB;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.Test;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
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

    @Test
    public void testFormat() throws ParseException {
        String dateString = "2020/07/01 17:26:00";
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        Date date = format.parse(dateString);
        dateString.contains("+");
        dateString.split("/+");
        System.out.printf(date.toLocaleString());
    }

    @Test
    public void testIndx() {
        int[] arr = new int[]{1, 2, 3, 4, 5};
        System.out.printf(Arrays.toString(Arrays.copyOfRange(arr, 1, 4)));
        System.out.printf("" + Integer.MAX_VALUE);
    }

    @Test
    public void str() {
        String email = "test.email+alex@leetcode.com";

            String[] arr = email.split("@");
            String local = arr[0];
            if (local.contains("+")) {
                local = local.split("\\+")[0];
            }
            local = local.replace(".", "");
            local.replace("-", "");
        System.out.println("4-".toUpperCase());
        String a = "absbd";
        Map<Character, Integer> map = new HashMap<>();
        map.merge('c', 1, (x, y) -> x - y);

        System.out.println(local);
    }

    @Test
    public void time() {
        System.out.printf(String.format("%02d:%02d", 1, 45));
        String[] words = new String[]{};
        Arrays.stream(words).filter(s -> s.equals("a")).count();

        PriorityQueue<Character> pq = new PriorityQueue<Character>(
            (o1, o2) -> o1.charValue() - o2.charValue());

    }

    @Test
    public void pqTest() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(2);
        pq.add(10);
        assertThat(pq.poll(), is(1));
        assertThat(pq.poll(), is(2));
        assertThat(pq.poll(), is(10));
        pq = new PriorityQueue<>((a, b) -> b -a);
        pq.add(1);
        pq.add(2);
        pq.add(10);
        assertThat(pq.poll(), is(10));
        assertThat(pq.poll(), is(2));
        assertThat(pq.poll(), is(1));
    }

    @Test
    public void domainName() throws MalformedURLException {
        final String urlString = "http://www.google.com/bask";
        final URL url = new URL(urlString);
        final String host = url.getHost();
        url.getHost();

        System.out.println(urlString);
        System.out.println(host);
        System.out.println(url.getProtocol() + "://" + url.getHost());
        System.out.println(urlString.substring(0, urlString.indexOf('/', 7)));

        URL urll = new URL("http://test.example.com/{application}/test.html");
        String baseUrl = urll.getProtocol() + "://" + urll.getHost();
        System.out.println(baseUrl);
    }

    @Test
    public void testIsValid() {
        threeNumberSum(new int[]{12, 3, 1, 2, -6, 5, -8, 6},  0);

    }
    public static List<Integer[]> threeNumberSum(int[] array, int targetSum) {
        // Write your code here.
        Arrays.sort(array);
        List<Integer[]> result = new ArrayList<>();
        for (int i = 0; i < array.length - 2; i++) {
            int target = targetSum - array[i];
            int left = i + 1, right = array.length - 1;
            while (left < right) {
                int subSum = array[left] + array[right];
                if (subSum == target) {
                    result.add(new Integer[]{array[i], array[left], array[right]});
                    left++;
                    right--;
                } else if (subSum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return result;
    }
}
