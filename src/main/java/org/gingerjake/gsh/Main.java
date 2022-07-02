package org.gingerjake.gsh;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("GingerShell, version 0.1 (c) 2022 GingerJake");
        System.out.println("Type 'help' for list of commands");

        while (true) {
            System.out.println(" ");
            Scanner TermIn = new Scanner(System.in); // Create a Scanner object
            System.out.print(System.getProperty("user.name")); // Print the user's name
            System.out.print("@" + InetAddress.getLocalHost().getHostName() + " " + System.getProperty("user.dir") + ": "); //Print the rest of the start of the line, and prompt the user
            String TermOut = TermIn.nextLine(); // Read a line of text from the user


            switch (TermOut) {
                //index files in current directory and print it to the screen
                case "ls" -> {
                    File fileRoot = new File(System.getProperty("user.dir"));

                    File[] files = fileRoot.listFiles();
                    DefaultMutableTreeNode node;
                    for (File file : Objects.requireNonNull(files)) {
                        node = new DefaultMutableTreeNode(file.getName());
                        System.out.println(node);

                    }


                }

                //if the user types "cd", change the directory to the one they specify
                case "cd" -> {
                    System.out.print("Enter the directory you want to change to: ");
                    String newDir = TermIn.nextLine();
                    System.setProperty("user.dir", newDir);
                }
                //if the user types "pwd", print the current directory
                case "pwd" -> System.out.println(System.getProperty("user.dir"));

                //if the user types "exit", exit the application
                case "exit" -> System.exit(0);


                //if the user types "exec", execute the program they specify
                case "exec" -> {
                    System.out.print("Enter the program you want to execute: ");
                    String exec = TermIn.nextLine();
                    Process p = Runtime.getRuntime().exec(exec);
                    p.waitFor();
                }

                //if the user types "ping", ping the host they specify
                case "ping" -> {
                    System.out.print("Enter the host you want to ping: ");
                    String host = TermIn.nextLine();
                    Process p = Runtime.getRuntime().exec("ping -c 1 " + host);
                    p.waitFor();
                }

                //if the user types "netstat", print the network status
                case "netstat" -> {
                    Process p = Runtime.getRuntime().exec("netstat -an");
                    p.waitFor();
                }

                //if the user types "help", print a help menu
                case "help" -> {
                    System.out.println(" ");
                    System.out.println("ls - list the contents of the current directory");
                    System.out.println("cd - change the current directory");
                    System.out.println("pwd - print the current directory");
                    System.out.println("exit - exit the application");
                    System.out.println("exec - execute a program");
                    System.out.println("ping - ping a host");
                    System.out.println("netstat - print the network status");
                    System.out.println("help - print this help menu");
                }
                //if the user types anything else, print an error message
                default -> {
                    System.out.println(" ");
                    System.out.println("Command not recognized");
                    System.out.println(" ");
                }
            }
        }
    }
}