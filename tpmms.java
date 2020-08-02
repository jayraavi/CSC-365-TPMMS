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
                } catch (NoSuchElementException e) {
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
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return fileList;

    }

    public static File writeFile(ArrayList<Integer> sortedArray, Integer numFiles) {

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
                    System.out.println(sortedArray.get(i));
                    myWriter.write(Integer.toString(sortedArray.get(i)));
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