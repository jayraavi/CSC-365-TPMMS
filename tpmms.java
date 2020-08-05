import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Collections;

public class tpmms {



    public static ArrayList<File> readFile( String filename) {
        ArrayList<File> fileList = new ArrayList<>();
        ArrayList<Integer> fileContents = new ArrayList<>();
        int run_index = 0;
        int numFiles = 1;
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            String data = myReader.nextLine();
            while (data != null) {
                if (run_index != 0 && run_index % 100 == 0) {
                    Collections.sort(fileContents);
                    fileList.add(writeFile(fileContents, numFiles));
                    numFiles++;
                    fileContents = new ArrayList<>();

                }
                Integer data_int = Integer.valueOf(data);
                fileContents.add(data_int);
                try {
                    data = myReader.nextLine();
                } catch ( NoSuchElementException e) {
                    System.out.println(e.getMessage());
                    break;
                }
                run_index++;
                
            }
            if (fileContents.size() > 0) {
                Collections.sort(fileContents);
                fileList.add(writeFile(fileContents, numFiles));
            }
            myReader.close();
        } catch ( FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return fileList;

    }

    public static File writeFile( ArrayList<Integer> sortedArray,  Integer numFiles) {

        try {
            File myObj = new File("run" + numFiles.toString() + ".txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }

            try {
                FileWriter myWriter = new FileWriter(myObj.getName());
                int i = 0;
                while (i < sortedArray.size()) {
                    myWriter.write(Integer.toString(sortedArray.get(i)));
                    myWriter.write('\n');
                    i++;
                }
                myWriter.close();

            } catch ( IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            return myObj;

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Node> initialBuffer(ArrayList<File> files) {
        ArrayList<Node> buffer = new ArrayList<>(100);
        long fileptr = 0;
        for (File file : files) {
            try {
                RandomAccessFile myReader = new RandomAccessFile(file, "r");
                // Scanner myReader = new Scanner(file);
                Integer bufferSize = 100 / files.size(); 
                for (int i = 0; i < bufferSize; i++) {
                    String data = myReader.readLine();
                    if (data != null){
                        Integer data_int = Integer.valueOf(data);
                        Node numNode = new Node(data_int, file);
                        buffer.add(numNode);
                    }
                    
                }
                fileptr = myReader.getFilePointer();
                myReader.close();
            } catch (Exception e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        Node first = new Node(null,null);
        first.fileptr = fileptr;
        buffer.add(0, first);
        return buffer;
    }

    public static void writeFromBuffer(ArrayList<Node> buffer, ArrayList<File> files) {
        HashMap<File, Long> filePtrs = new HashMap<>();
        Node ptrNode = buffer.remove(0);
        Collections.sort(buffer);
        
        Integer fileptr = Math.toIntExact(ptrNode.fileptr);
        try {
            File myObj = new File("final_results.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
            try {
                FileWriter myWriter = new FileWriter("final_results.txt");
                while (!buffer.isEmpty()) {
                    Node curNode = buffer.remove(0);
                    myWriter.write(Integer.toString(curNode.num));
                    myWriter.write("\n");
                    if (!filePtrs.containsKey(curNode.file)) {
                        filePtrs.put(curNode.file, Integer.toUnsignedLong(fileptr));
                    }
                    Node newNode = readFromFile(curNode.file, filePtrs.get(curNode.file));
                    if (newNode == null) {
                        continue;
                    }

                    filePtrs.replace(newNode.file, newNode.fileptr);

                    buffer.add(newNode);
                    Collections.sort(buffer);
                }
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public static Node readFromFile(File filePath, Long position) throws IOException {
        RandomAccessFile file = new RandomAccessFile(filePath, "r");
        file.seek(position);
        try {
            try {
                Integer num = Integer.parseInt(file.readLine());
                Node curNode = new Node(num, filePath);
                curNode.fileptr = file.getFilePointer();
                file.close();
                return curNode;
            } catch (NumberFormatException ex) {
                return null;
            }
        } catch (EOFException ex) {
            file.close();
            return null;
        }
    }

    public static void main(String[] args) {
        String filename = args[0];
        ArrayList<File> chunk = readFile(filename);
        ArrayList<Node> initialBuffer = initialBuffer(chunk);
        writeFromBuffer(initialBuffer, chunk);
    
    }

}