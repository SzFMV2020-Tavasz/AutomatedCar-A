package hu.oe.nik.szfmv.automatedcar.npc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Circular linked list test")
public class CircularLinkedListTest {
    CircularLinkedList<Integer> list = new CircularLinkedList<Integer>();

    @Test
    @DisplayName("Circular linked list, test next.")
    void test_list_next(){
        // adding five integers
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        // test data
        Integer i = 0;

        //testing next
        i = list.next();
        assertEquals(1, i);
        i = list.next();
        assertEquals(2, i);
        i = list.next();
        assertEquals(3, i);
        i = list.next();
        assertEquals(4, i);
        i = list.next();
        assertEquals(5, i);

        // testing circularity
        i = list.next();
        assertEquals(1, i);
        i = list.next();
        assertEquals(2, i);
        i = list.next();
        assertEquals(3, i);
        i = list.next();
        assertEquals(4, i);
        i = list.next();
        assertEquals(5, i);
    }

    @Test
    @DisplayName("Circular linked list, test get first element")
    void test_list_first(){
        // adding five integers
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        // test data
        Integer i = list.getFirst();

        // assert
        assertEquals(1, i);
    }

    @Test
    @DisplayName("Circular linked list, test get last element")
    void test_list_last(){
        // adding five integers
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        // test data
        Integer i = list.getLast();

        // assert
        assertEquals(5, i);
    }

    @Test
    @DisplayName("Circular linked list, test remove first element")
    void test_remove_first(){
        // adding five integers
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        // deleting first element
        list.removeFirst();

        // test data
        Integer i = list.getFirst();

        // assert
        assertEquals(2, i);
    }

    @Test
    @DisplayName("Circular linked list, test remove next element")
    void test_remove_current(){
        // adding five integers
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        // deleting the 3dr element
        list.next();
        list.next();
        list.removeNext();

        // test data
        Integer i = list.next();
        assertEquals(4, i);
    }

    @Test
    @DisplayName("Circular linked list, test remove last element")
    void test_remove_last(){
        // adding five integers
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        // deleting the last element
        list.removeLast();

        // test data
        Integer i = list.getLast();
        assertEquals(4, i);

        // testing if the list is still circular
        i = list.next();
        assertEquals(1, i);
    }
}
