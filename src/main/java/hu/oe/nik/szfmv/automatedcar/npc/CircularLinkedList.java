package hu.oe.nik.szfmv.automatedcar.npc;

/**
 * Circular linked list implementation. Will be useful for NPC routes.
 * @param <T> The type of the list elements.
 * @author Marcell Varszegi (github: varszegimarcell)
 */
public class CircularLinkedList <T> {

    /**
     * Circular linked list element class.
     * @param <E> The type of the list elements.
     */
    private class CircularLinkedListNode<E> {
        public E data;
        public CircularLinkedListNode<E> next;
        public CircularLinkedListNode(E inputData, CircularLinkedListNode<E> nextElement){
            this.data = inputData;
            this.next = nextElement;
        }
    }

    /**
     * Head element for the circular linked list.
     */
    private CircularLinkedListNode<T> head;

    /**
     * Last element for the circular linked list.
     * It's next field will point to head always.
     */
    private CircularLinkedListNode<T> last;

    /**
     * Current element of the circular linked list.
     * As the iteration through the list is independent form the addition of the list elements,
     * it is important to store a separate reference of the next element.
     */
    private CircularLinkedListNode<T> current;


    /**
     * Gets the first element of the circular linked list.
     * @return The first element of the list.
     */
    public T getFirst() {
        return head.data;
    }

    /**
     * Gets the next element of the circular linked list.
     * On the first call, it will return the first element.
     * On any other calls, it will iterate through the list elements.
     * After the last element, it will return the first element again, starting again the iteration.
     * @return The current list element.
     */
    public T next(){
         T data = current.data;
         current = current.next;
         return data;
    }

    /**
     * Gets the last element of the circular linked list.
     * @return The last list element.
     */
    public T getLast(){
        return last.data;
    }

    /**
     * Adds a new item to the circular linked list.
     * The new element will be added after the last element of the list.
     * @param inputData The new list element.
     */
    public void add(T inputData){
        if(head == null){
            head = new CircularLinkedListNode<T>(inputData, head);
            last = head;
            current = head;
        }
        else {
            last.next = new CircularLinkedListNode<T>(inputData, head);
            last = last.next;
        }
    }

    /**
     * Removes the next element from the circular linked list.
     */
    public void removeNext(){
        if(current!=null){
            current = current.next;
        }
    }

    /**
     * Removes the head element of the circular linked list.
     * The first element will be replaced with the next element.
     */
    public void removeFirst(){
        if(head != null){
            head = head.next;
        }
    }

    /**
     * Removes the last element of the circular linked list.
     * The last element will be replaced with the previous element.
     */
    public void removeLast(){
        if(last != null){
            CircularLinkedListNode<T> prev = head;
            CircularLinkedListNode<T> item = head.next;
            while(item != last){
                prev = item;
                item = prev.next;
            }
            last = prev;
            last.next = head;
        }
    }

    /**
     * Default constructor.
     */
    public CircularLinkedList(){}
}
