
/**
 * The ObjectQueue class is an implementation of the queue data structure 
 *
 * @author Richard Stegman 
 * @version 10/18/2019
 */
public class ObjectQueue implements ObjectQueueInterface
{
    private Object[] item;
    private int front;
    private int rear;
    private int count;

    /**
     * This is the Constructor for the ObjectQueue class
     * 
     * @author Richard Stegman
     */
    public ObjectQueue() {
        item = new Object[1];
        front = 0;
        rear  = -1;
        count = 0;
    }

    /**
     * The isEmpty() returns if the queue is empty or not. 
     *
     * @return true if the queue is empty, false if the queue is not empty
     * @author Richard Stegman
     */
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * The isFull() returns if the queue is full or not. 
     *
     * @return true if the queue is full, false if the queue is not full
     * @author Richard Stegman
     */
    public boolean isFull() {
        return count == item.length;
    }

    /**
     * The clear() clears the queue
     * 
     * @author Richard Stegman
     */
    public void clear() {
        item = new Object[1];
        front = 0;
        rear  = -1;
        count = 0;
    }

    /**
     * The insert() inserts a new object into the rear of the queue. 
     * 
     * @param o, object to be inserted into the queue
     * @author Richard Stegman
     */
    public void insert(Object o) {
        if (isFull())
            resize(2 * item.length);
        rear = (rear+1) % item.length;
        item[rear] = o;
        ++count;
    }

    /**
     * The remove() removes an object from the front of the queue and returns it. 
     * 
     * @return the object that is being removed from the queue
     * @author Richard Stegman
     */
    public Object remove() {
        if (isEmpty()) {
            System.out.println("Queue Underflow");
            System.exit(1);
        }
        Object temp = item[front];
        item[front] = null;
        front = (front+1) % item.length;
        --count;
        if (count == item.length/4 && item.length != 1)
            resize(item.length/2);
        return temp;
    }

    /**
     * The query() returns the object at the front of the queue but does not remove it from the queue. 
     * 
     * @return the object at the front of the queue  
     * @author Richard Stegman
     */
    public Object query() {
        if (isEmpty()) {
            System.out.println("Queue Underflow");
            System.exit(1);
        }
        return item[front];
    }

    /**
     * The resize() resizes the queue 
     * 
     * @param size, new size of the queue
     * @author Richard Stegman
     */
    private void resize(int size) {
        Object[] temp = new Object[size];
        for (int i = 0; i < count; ++i) {
            temp[i] = item[front];
            front = (front+1) % item.length;
        }
        front = 0;
        rear = count-1;
        item = temp;
    }
}
