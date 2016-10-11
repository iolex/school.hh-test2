package com.company;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        //Sequence test = new Sequence("34235");
        //Sequence test = new Sequence("9101115");
        //Sequence test = new Sequence("6789");
        /*String result = test.calculate();
        System.out.println(result);
        System.exit(1);*/

        Scanner scanner = new Scanner(System.in);
        String inputPattern = "[1-9][0-9]{0,49}";
        scanner.useDelimiter("\\n");

        List<Sequence> sequenceList = new ArrayList<>();

        while ((scanner.hasNext(inputPattern))) {
            Sequence currentSequence = new Sequence(scanner.next(inputPattern));
            sequenceList.add(currentSequence);
        }
        scanner.close();

        for (int i = 0; i < sequenceList.size(); i++) {
            System.out.println(sequenceList.get(i).calculate());
        }

    }

}
