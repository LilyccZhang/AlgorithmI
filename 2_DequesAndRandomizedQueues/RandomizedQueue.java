import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

//If we use linked-list,  sample()/dequeue()/iterator() cannot meet the time requirement
//Any sequence, m randomized queue operations (starting from an empty queue) must take at most cm steps in the worst case, for some constant c.

public class RandomizedQueue<Item> implements Iterable<Item>{
    private Item[] queue;
    private int size;
 
    public RandomizedQueue()
    {
        queue = (Item[])new Object[1];
        size = 0;
    }
    public boolean isEmpty()
    {
        return (size == 0) ? true : false;
    }
    public int size()
    {
        return size;
    }
    private void resize(int capacity)
    {
        Item[] copy = (Item[])new Object[capacity];
        for (int i = 0; i < size; i++)
        {
            copy[i] = queue[i];
        }
        queue = copy;
    }
    public void enqueue(Item item)
    {
        if (item == null)
            throw new IllegalArgumentException();
        if(queue.length == size)
        {
            resize(2*size);
        }
        queue[size++] = item;
    }
    public Item dequeue()
    {
        if(isEmpty())
            throw new NoSuchElementException();
        int pos = StdRandom.uniform(size);
        int end = size -1;
        Item item = queue[pos];
  
        if (pos != end)
        {
            queue[pos] = queue[end];
        }
        queue[end] = null;
        size--;
        if (size > 0 && size == queue.length/4)
        {
            resize(queue.length/2);
        }
        return item;
    }

    public Item sample()
    {
        if(isEmpty())
            throw new NoSuchElementException();
        int pos = StdRandom.uniform(size);
        return queue[pos];
    }
    public Iterator<Item> iterator()
    {
        return new RandomizedQueueIterator();
    }

private class RandomizedQueueIterator implements Iterator<Item>{
    private int endPos;
    private Item[] privateQueue;
    RandomizedQueueIterator()
    {
        privateQueue =  (Item[])new Object[size]; 
        endPos = size - 1;
        for(int i = 0; i < size; i++)
        {
            privateQueue[i] = queue[i];
        }
    }
    public Item next()
    {
        if(hasNext() == false)
            throw new NoSuchElementException();
        int pos = StdRandom.uniform(endPos+1);
        Item item = privateQueue[pos];
        if (pos != endPos)
        {
            privateQueue[pos] = privateQueue[endPos];
            privateQueue[endPos] = null;
        }
        endPos--;
        return item;
    }

    public boolean hasNext()
    {
         return (endPos >= 0) ? true : false;
    }

    public void remove()
    {
         throw new UnsupportedOperationException();
    }
}
    public static void main(String[] args)
    {
    }
}
