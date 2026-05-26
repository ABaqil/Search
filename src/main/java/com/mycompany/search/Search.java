package com.mycompany.search;

import java.io.File;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Search {

    static PrintWriter PR;

    public static void main(String[] args) {
        // Use a single Scanner
        Scanner SC = new Scanner(System.in);

        // Use paths that work in Docker/Linux — or pass via args
        String path1 = args.length > 0 ? args[0] : "/search1";
        String path2 = args.length > 1 ? args[1] : "/search2";

        final File F  = new File(path1);
        final File F1 = new File(path2);

        try {
            PR = new PrintWriter(new File("Log.txt"));
        } catch (Exception ex) {
            System.out.println("Could not create log file.");
        }

        while (true) {
            System.out.println("1-For Exact Name Search");
            System.out.println("2-For Contains Name Search");
            System.out.println("3-To Exit");

            try {
                int choice = SC.nextInt();
                SC.nextLine(); // ✅ flush leftover \n after nextInt()

                switch (choice) {
                    case 1:
                        System.out.println("Enter File Name:");
                        final String name = SC.nextLine(); // ✅ one Scanner

                        Thread t1 = new Thread(() -> {
                            Search(F, name);
                            System.out.println(F + " Finished");
                        });
                        Thread t2 = new Thread(() -> {
                            Search(F1, name);
                            System.out.println(F1 + " Finished");
                        });
                        t1.start(); // ✅ only once
                        t2.start();
                        break;

                    case 2:
                        System.out.println("Enter File Name:");
                        final String name1 = SC.nextLine(); // ✅ one Scanner

                        Thread t3 = new Thread(() -> {
                            Contains(F, name1);
                            System.out.println(F + " Finished");
                        });
                        Thread t4 = new Thread(() -> {
                            Contains(F1, name1);
                            System.out.println(F1 + " Finished");
                        });
                        t3.start();
                        t4.start();
                        break;

                    case 3:
                        System.out.println("Exiting...");
                        SC.close();
                        System.exit(0);

                    default:
                        System.out.println("Wrong Number");
                }

            } catch (InputMismatchException e) {
                System.out.println("Not a number, try again.");
                SC.nextLine(); // ✅ clear bad input instead of recursive main()
            }
        }
    }

    public static void Search(File file, String name) {
        File[] files = file.listFiles();
        if (files == null) return;
        for (File f : files) {
            if (f.getName().equalsIgnoreCase(name)) {
                System.out.println(f.getAbsolutePath());
            }
        }
        for (File f : files) {
            if (f.isDirectory()) Search(f, name);
        }
        System.out.println("Finished: " + file.getAbsolutePath());
    }

    public static void Contains(File file, String name) {
        File[] files = file.listFiles();
        if (files == null) return;
        try {
            for (File f : files) {
                if (f.getName().toLowerCase().contains(name.toLowerCase())) {
                    System.out.println(f.getAbsolutePath());
                }
                if (PR != null) {
                    PR.println(f.getAbsolutePath());
                    PR.flush();
                }
            }
            for (File f : files) {
                if (f.isDirectory()) Contains(f, name);
            }
        } catch (Exception e) {
            System.out.println("Error reading: " + file.getAbsolutePath());
        }
    }
}