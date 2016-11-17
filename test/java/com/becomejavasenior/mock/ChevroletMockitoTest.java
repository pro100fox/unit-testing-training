package com.becomejavasenior.mock;

import com.becomejavasenior.applecar.Camaro;
import com.becomejavasenior.applecar.Chevrolet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ChevroletMockitoTest {

    @Mock
    private Chevrolet mockedCamaro;
    @Mock
    private LinkedList mockedLinkedList;

    /**
     * Verify behaviour
     */
    @Test
    public void behaviourListTest() {

        Chevrolet mockedCamaro = mock(Camaro.class);

        //using mock object somewhere in our code
        //mockedCamaro.increaseSpeed(15);
        mockedCamaro.stop();

        //use somewhere mockedCamaro

        //verification
        //verify(mockedCamaro).increaseSpeed(15);
        verify(mockedCamaro).stop();
    }

    @Test
    public void stubListTest() {
        //You can mock concrete classes, not only interfaces
        LinkedList mockedList = mock(LinkedList.class);

        //stubbing
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new RuntimeException());

        //following prints "first"
        System.out.println(mockedList.get(0));

        //following throws runtime exception
        //System.out.println(mockedList.get(1));

        //following prints "null" because get(999) was not stubbed
        System.out.println(mockedList.get(999));

        //Although it is possible to verify a stubbed invocation, usually it's just redundant
        //If your code cares what get(0) returns then something else breaks (often before even verify() gets executed).
        //If your code doesn't care what get(0) returns then it should not be stubbed. Not convinced? See here.
        verify(mockedList).get(0);

        /**
         * generate real situation - add road collision
         */
        when(mockedCamaro.setSpeed(20)).thenReturn(15).thenReturn(25);


        System.out.println("speed = " + mockedCamaro.setSpeed(20));
        System.out.println("speed = "+ mockedCamaro.setSpeed(20));

        when(mockedCamaro.setSpeed(anyInt())).thenReturn(10);
        System.out.println("speed = "+ mockedCamaro.setSpeed(30));

    }

    @Test
    public void stubList2Test() {
        LinkedList mockedList = mock(LinkedList.class);

        //stubbing using built-in anyInt() argument matcher
        when(mockedList.get(anyInt())).thenReturn("element");

        //following prints "element"
        System.out.println(mockedList.get(999));

        //you can also verify using an argument matcher
        verify(mockedList).get(anyInt());
    }

    @Test
    public void invocationTimesListTest() {
        //MockitoAnnotations.initMocks(MockitoTest.class);
        //using mock
        mockedLinkedList.add("once");

        mockedLinkedList.add("twice");
        mockedLinkedList.add("twice");

        mockedLinkedList.add("three times");
        mockedLinkedList.add("three times");
        mockedLinkedList.add("three times");

        //following two verifications work exactly the same - times(1) is used by default
        verify(mockedLinkedList).add("once");
        verify(mockedLinkedList, times(1)).add("once");

        //exact number of invocations verification
        verify(mockedLinkedList, times(2)).add("twice");
        verify(mockedLinkedList, times(3)).add("three times");

        //verification using never(). never() is an alias to times(0)
        verify(mockedLinkedList, never()).add("never happened");

        //verification using atLeast()/atMost()
        verify(mockedLinkedList, atLeastOnce()).add("three times");
        //verify(mockedList, atLeast(2)).add("five times");
        verify(mockedLinkedList, atMost(5)).add("three times");

        mockedCamaro.setSpeed(20);
        mockedCamaro.setSpeed(20);

        verify(mockedCamaro, never()).setSpeed(20);


    }

    @Test
    public void invocationListTest() {

        //when( mockedCamaro.setSpeed(1000)).thenThrow(new RuntimeException());

        mockedCamaro.setSpeed(1000);

        //when(mockedLinkedList.get(0))
          //      .thenReturn("foo").thenThrow(new RuntimeException());

        //First call: prints "foo"
        System.out.println(mockedLinkedList.get(0));

        //Second call: throws runtime exception:
        mockedLinkedList.get(0);


        //Any consecutive call: prints "foo" as well (last stubbing wins).
        System.out.println(mockedLinkedList.get(0));

        //Alternative, shorter version of consecutive stubbing:
        when(mockedLinkedList.get(0))
                .thenReturn("one", "two", "three");
        System.out.println(mockedLinkedList.get(0));
        System.out.println(mockedLinkedList.get(0));
        System.out.println(mockedLinkedList.get(0));

        /**
         * Allows verifying with timeout. May be useful for testing in concurrent conditions.
         * It feels this feature should be used rarely - figure out a better way of testing your multi-threaded system.
         */
        verify(mockedLinkedList, timeout(100)).get(0);

        verify(mockedLinkedList, timeout(100).times(1)).get(0);
    }

    @Test
    public void spyListTest() {
        List list = new LinkedList();
        List spy = spy(list);

        Camaro mockedCamaro = spy(new Camaro());
        when(mockedCamaro.setSpeed(anyInt())).thenReturn(20);

        System.out.println(mockedCamaro.setSpeed(10));
        System.out.println(mockedCamaro.setSpeed(30));

        //optionally, you can stub out some methods:
        when(spy.size()).thenReturn(100);

        //using the spy calls real methods
        spy.add("one");
        spy.add("two");

        //prints "one" - the first element of a list
        System.out.println(spy.get(0));

        //size() method was stubbed - 100 is printed
        System.out.println(spy.size());

        //optionally, you can verify
        verify(spy).add("one");
        verify(spy).add("two");


    }

}
