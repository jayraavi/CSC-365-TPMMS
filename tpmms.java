import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Collections;

public class tpmms {

    public static ArrayList<File> readFile(final String filename) {
        final ArrayList<File> fileList = new ArrayList<>();
        ArrayList<Integer> fileContents = new ArrayList<>();
        int run_index = 0;
        int numFiles = 1;
        try {
            final File myObj = new File(filename);
            final Scanner myReader = new Scanner(myObj);
            String data = myReader.nextLine();
            while (data != null) {
                if (run_index != 0 && run_index % 100 == 0) {
                    Collections.sort(fileContents);
                    fileList.add(writeFile(fileContents, numFiles));
                    numFiles++;
                    fileContents = new ArrayList<>();

                }
                final Integer data_int = Integer.valueOf(data);
                fileContents.add(data_int);
                try {
                    data = myReader.nextLine();
                } catch (final NoSuchElementException e) {
                    System.out.println(e.getMessage());
                    break;
                }
                run_index++;
                // System.out.println(data);
            }
            if (fileContents.size() > 0) {
                Collections.sort(fileContents);
                fileList.add(writeFile(fileContents, numFiles));
            }
            myReader.close();
        } catch (final FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return fileList;

    }

    public static File writeFile(final ArrayList<Integer> sortedArray, final Integer numFiles) {

        try {
            final File myObj = new File("run" + numFiles.toString() + ".txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }

            try {
                final FileWriter myWriter = new FileWriter(myObj.getName());
                int i = 0;
                while (i < sortedArray.size()) {
                    System.out.println(sortedArray.get(i));
                    myWriter.write(Integer.toString(sortedArray.get(i)));
                    myWriter.write('\n');
                    i++;
                }
                myWriter.close();

            } catch (final IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            return myObj;

        } catch (final IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Node> initialBuffer(final ArrayList<File> files) {
        final ArrayList<Node> buffer = new ArrayList<>(100);
        for (File file : files){
            try{
                final Scanner myReader = new Scanner(file);
                for (int i = 0; i<5; i++){
                    final String data = myReader.nextLine();
                    final Integer data_int = Integer.valueOf(data);
                    final Node numNode = new Node(data_int,file);
                    buffer.add(numNode);
                }
                myReader.close();
            } catch (final FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }    
        }
        Collections.sort(buffer);
        for(Node node: buffer){
            System.out.println(node.num);
        } 
        return buffer;    
    }

    public static void writeFromBuffer(ArrayList<Node> buffer, ArrayList<File> files) {
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
                    for (int x = 0; x < 5; x++) {
                        if (buffer.size() < x) {
                            continue;
                        }
                        Node curFile  = buffer.remove(x);
                        myWriter.write(curFile.num);
                        Node fromFile = writeToBuffer(curFile.file);
                        if(fromFile == null){
                            continue;
                        }
                        buffer.add(fromFile);
                    }
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

    public static Node writeToBuffer(File file){
        try{

            final Scanner myReader = new Scanner(file);
            final String data = myReader.nextLine();
            if (data != null){
                final Integer data_int = Integer.valueOf(data);
                Node newNode = new Node(data_int,file);
                myReader.close();
                return newNode;
            }
            myReader.close();
            
        } catch (final FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
        
    }

    public static void main(final String[] args) {
        final String filename = args[0];
        final ArrayList<File> chunk = readFile(filename);
        System.out.println(chunk);
        final ArrayList<Node> initialBuffer = initialBuffer(chunk);
        writeFromBuffer(initialBuffer, chunk);
        // try {
        //     final File myObj = new File("output.txt");
        //     if (myObj.createNewFile()) {
        //         System.out.println("File created: " + myObj.getName());
        //     } else {
        //         System.out.println("File already exists.");
        //     }

        //     try {
        //         final FileWriter myWriter = new FileWriter(myObj.getName());
        //         int i = 0;
        //         while (i < sortedNums.size()) {
        //             System.out.println(sortedNums.get(i));
        //             myWriter.write(Integer.toString(sortedNums.get(i)));
        //             myWriter.write('\n');
        //             i++;
        //         }
        //         myWriter.close();

        //     } catch (final IOException e) {
        //         System.out.println("An error occurred.");
        //         e.printStackTrace();
        //     }

        // } catch (final IOException e) {
        //     System.out.println("An error occurred.");
        //     e.printStackTrace();
        // }
    }

}