import java.util.Iterator;
import java.util.NoSuchElementException;

// Deque uses 72+48N bytes of memory 
public class Deque<Item> implements Iterable<Item>{
    private Node first = null;
    private Node last = null;
    private int size = 0;
    public Deque()
    {
        first = null;
        last = null;
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

    public void addFirst(Item item)
    {
        if (item == null)
             throw new IllegalArgumentException();
        
        Node newNode = new Node(item);
        
        if (size != 0)
        {
            first.prev = newNode;
            newNode.next = first;
            first = newNode;
        }
        else
        {
            first = last = newNode;
        }
        size++;
    }
    public void addLast(Item item)
    {
        if (item == null)
             throw new IllegalArgumentException();

        Node newNode = new Node(item);

        if (size != 0)
        {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        else
        {
            first = last = newNode;
        }
        size++;
    }
    public Item removeFirst()
    {
        if (isEmpty())
            throw new NoSuchElementException();

        Item item =  first.item;
        if (first == last)
        {
            
            first = null;
            last = null;
        }
        else
        {
            first = first.next;
            first.prev = null;
        }
        size--;
        return item;
    }

    public Item removeLast()
    {
         if (isEmpty())
             throw new NoSuchElementException();

         Item item =  last.item;
         if(first == last)
         {
             first = null;
             last = null;
         }
         else
         {
             last = last.prev;
             last.next = null;
         }
         size--;
   return item;
    }
    public Iterator<Item> iterator()
    {
        return new DequeIterator();
    }
    public static void main(String args[])
    {
         Deque<Integer> D = new Deque<Integer>();
         for (int i = 1; i < 10; i++)
         {
             D.addFirst(i);
         }
         for (int i = 10; i < 20; i++)
         {
             D.addLast(i);
         }

         Iterator<Integer> ite = D.iterator();
         //Expect output: 9 8 7 6 5 4 3 2 1 10 11 12 13 14 15 16 17 18 19
         while(ite.hasNext())
         {
             System.out.print(ite.next()+" ");
         }
         System.out.print("\n");
         
         for (int i = 0; i < 5; i++)
         {
             D.removeFirst();
         }
         //Expect output: 4 3 2 1 10 11 12 13 14 15 16 17 18 19
         ite = D.iterator();
         while(ite.hasNext())
         {
             System.out.print(ite.next()+" ");
         }
         System.out.print("\n");
         
         for (int i = 0; i < 5; i++)
         {
             D.removeLast();
         }
         //Expect output: 4 3 2 1 10 11 12 13 14
         ite = D.iterator();
         while(ite.hasNext())
         {
             System.out.print(ite.next()+" ");
         }
         System.out.print("\n");
    }

    private class DequeIterator implements Iterator<Item>
    {
        private Node curr;
        DequeIterator()
        {
            curr = first;
        }
        public Item next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            
            Item item = curr.item;
            curr = curr.next;
            return item;
        }
        public boolean hasNext()
        {
            return curr != null;
        }
        public void remove()
        {
            throw new UnsupportedOperationException();   
        }
    }

    private class Node{
        Item item;
        Node next;
        Node prev;
        Node(Item item)
        {
            this.item = item;
            this.next = null;
            this.prev = null;
        }
    }
}
