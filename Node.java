import java.io.File;

public class Node implements Comparable<Node> {
    public Integer num;
    public File file;
    public Long fileptr;
    public Node(Integer num, File file){
        this.num = num;
        this.file = file;
        this.fileptr = Integer.toUnsignedLong(0);

    }

    @Override
    public int compareTo(Node other) {
        // TODO Auto-generated method stub
        return this.num - other.num;
    }
    
}