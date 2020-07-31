import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileWriter;

public class tpmms {

    public static Integer[] readFile(String filename) {
        ArrayList<File> fileList = new ArrayList<>();
        Integer[] fileContents = new Integer[100];
        int run_index = 1;
        int numFiles = 1;
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                if (run_index % 100 == 0) {
                    System.out.println(fileContents.toString());
                    Arrays.sort(fileContents);
                    writeFile(fileContents, numFiles);
                    numFiles++;
                    fileContents = new Integer[100];

                }
                String data = myReader.nextLine();
                Integer data_int = Integer.valueOf(data);
                fileContents[(run_index % 100) - 1] = data_int;
                run_index++;
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return fileContents;

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
                    myWriter.write(sortedArray[i]);
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
        Integer[] chunk = readFile(filename);

    }
}