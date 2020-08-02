import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.io.FileWriter;
import java.util.Collections;

public class tpmms {

    public static ArrayList<File> readFile(String filename) {
        ArrayList<File> fileList = new ArrayList<>();
        Integer[] fileContents = new Integer[100];
        int run_index = 0;
        int numFiles = 1;
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            String data = myReader.nextLine();
            while (data != null) {
                if (run_index != 0 && run_index % 100 == 0) {
                    Arrays.sort(fileContents);
                    for (int x = 0; x < fileContents.length; x++) {

                        System.out.print(fileContents[x]);
                        System.out.print(" ");
                        System.out.println();
                    }
                    fileList.add(writeFile(fileContents, numFiles));
                    numFiles++;
                    fileContents = new Integer[100];

                }
                Integer data_int = Integer.valueOf(data);
                fileContents[run_index % 100] = data_int;
                try {
                    data = myReader.nextLine();
                } catch (NoSuchElementException e) {
                    System.out.println(e.getMessage());
                    break;
                }
                run_index++;
                // System.out.println(data);
            }
            if (fileContents.length > 0) {
                List<Integer> newList = Arrays.asList(fileContents);
                System.out.println(newList.toString());
                Collections.sort(newList);
                fileList.add(writeFile(fileContents, numFiles));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return fileList;

    }

    public static File writeFile(Integer[] sortedArray, Integer numFiles) {

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
                while (i < sortedArray.length) {
                    System.out.println(sortedArray[i]);
                    myWriter.write(Integer.toString(sortedArray[i]));
                    myWriter.write('\n');
                    i++;
                }
                myWriter.close();

            } catch (IOException e) {
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

    public static void main(String[] args) {
        System.out.println("This will be printed");

        String filename = args[0];
        ArrayList<File> chunk = readFile(filename);

    }

}