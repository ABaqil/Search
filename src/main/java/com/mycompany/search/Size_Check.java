/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.search;

import java.io.File;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Size_Check {

    static PrintWriter PR;

    public static void main(String[] args) {
        File F = new File("C:/");
        final File F1 = new File("E:/");
        try {
            PR = new PrintWriter(new File("Log.txt"));
        } catch (Exception ex) {

        }
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Size_Check(F);
                System.out.println(F + "Finished ");
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Size_Check(F1);
                System.out.println(F1 + "Finished ");
            }
        });
        t1.start();
        t2.start();

    }

    public static void Size_Check(File directory) {
        if (directory == null || !directory.isDirectory()) {
            System.out.println("❌ Invalid directory: " + directory);
            return;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            System.out.println("❌ Cannot access: " + directory.getAbsolutePath());
            return;
        }

        try {
            for (File file : files) {
                if (file.isDirectory()) {
                    long bytes = getFolderSize(file); // real size, not file.length()
                    double gigabytes = (double) bytes / (1024 * 1024 * 1024);

                    if (gigabytes > 0.5) {
                        System.out.printf("📁 Folder: %s | Size: %.2f GB%n",
                                file.getAbsolutePath(), gigabytes);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("⚠ Error reading directory: " + e.getMessage());
        }
    }

// Helper method: Recursively calculates folder size
    private static long getFolderSize(File folder) {
        long length = 0;
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    length += file.length();
                } else {
                    length += getFolderSize(file);
                }
            }
        }
        return length;
    }

    public static void Contains(File File, String Name) {

        File[] Files = File.listFiles();
        try {
            for (File File1 : Files) {
                PR.println(File.getAbsolutePath());
                PR.println(File1.getName().toLowerCase());
                PR.println(File1.getName().toLowerCase().contains(Name) + "");
                PR.flush();
                if ((File1.getName().toLowerCase()).contains(Name.toLowerCase())) {
                    System.out.println(File1.getAbsolutePath());
                }
            }
            for (File File1 : Files) {
                if (File1.isDirectory()) {
                    Contains(File1, Name);
                }
            }

        } catch (Exception e) {
        }
    }
}
