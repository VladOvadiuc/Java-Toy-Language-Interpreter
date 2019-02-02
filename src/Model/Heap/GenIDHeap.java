package Model.Heap;

public class GenIDHeap {
    private static int number=1;
    public static  int getID(){
        return number++;
    }
}