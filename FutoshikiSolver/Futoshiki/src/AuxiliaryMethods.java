import java.io.*;
import java.util.List;
import java.util.Scanner;

class AuxiliaryMethods {
    private static File file = new File("output.txt");

    static void writeToFile(int size, List<Integer[]> given,
                            List<Integer[]> lessThan) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new FileWriter("input.lp"));

            bufferedWriter.write("#const size = " + size + ".\n\n");

            for (Integer[] el : given) {
                bufferedWriter.write("given(" + (el[0] + 1) + "," + (el[1] + 1)
                        + "," + el[2] + ").\n");
            }
            bufferedWriter.write("\n");

            for (Integer[] el : lessThan) {
                bufferedWriter.write("lessThan(" + (el[0] + 1) + "," + (el[1] + 1)
                        + ", " + (el[2] + 1) + "," + (el[3] + 1) + ").\n");
            }

            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    static void run() {
        try {
            String[] args = new String[]{"clingo", "futoshiki.lp", "input.lp"};
            ProcessBuilder builder = new ProcessBuilder(args);
            File file = new File("output.txt");
            builder.redirectOutput(file);
            builder.redirectError(file);
            Process process = builder.start();
            InputStream is = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    static Integer[][] getInfo(int size) {
        Integer[][] toReturn = new Integer[size][size];
        try {
            Scanner scanner = new Scanner(file);

            String line, line1;
            line = "";
            line1 = "";

            while (scanner.hasNextLine()) {
                line1 = line;
                line = scanner.nextLine();
                if (line.compareTo("SATISFIABLE") == 0 ||
                        line.compareTo("UNSATISFIABLE") == 0)
                    break;
            }

            if (line.isEmpty() || line.compareTo("UNSATISFIABLE") == 0) {
                toReturn[0][0] = -1;
            } else {
                for (int i = 2; i < line1.length(); i += 9) {
                    int i1 = Integer.parseInt(line1.charAt(i) + "");
                    int j1 = Integer.parseInt(line1.charAt(i + 2) + "");
                    int val = Integer.parseInt(line1.charAt(i + 4) + "");

                    toReturn[i1 - 1][j1 - 1] = val;
                }
            }

            scanner.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return toReturn;
    }
}
