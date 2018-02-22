import edu.princeton.cs.algs4.StdIn;

public class Permutation{
    public static void main(String[] args)
    {
        RandomizedQueue<String> stringRandQueue = new RandomizedQueue<String>();
        int num = Integer.parseInt(args[0]);

        while (!StdIn.isEmpty())
        {
            stringRandQueue.enqueue(StdIn.readString());
        }

        while(num > 0)
        {
            System.out.println(stringRandQueue.dequeue());
            num--;
        }
    }
}
