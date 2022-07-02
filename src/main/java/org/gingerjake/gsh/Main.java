package org.gingerjake.gsh;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
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

                    DefaultMutableTreeNode root = new DefaultMutableTreeNode(fileRoot);
                    DefaultTreeModel model = new DefaultTreeModel(root);

                    File[] subItems = fileRoot.listFiles();
                    for (File file : Objects.requireNonNull(subItems)) {
                        if (file.isDirectory()) {
                            DefaultMutableTreeNode newDir = new DefaultMutableTreeNode(file.getName());
                            root.add(newDir);
                            model.reload();
                        } else {
                            DefaultMutableTreeNode newFile = new DefaultMutableTreeNode(file.getName());
                            root.add(newFile);
                        }
                    }
                    for (int i = 0; i < root.getChildCount(); i++) {
                        System.out.println(root.getChildAt(i).toString());
                    }


                }

                //if the user types "cd", change the directory to the one they specify
                case "cd" -> {
                    System.out.print("Enter the directory you want to change to: ");
                    String newDir = TermIn.nextLine();

                    switch (newDir) {
                        case ".." -> {
                            String currentDir = System.getProperty("user.dir");
                            String[] splitDir = currentDir.split("/");
                            StringBuilder newDirString = new StringBuilder();
                            for (int i = 0; i < splitDir.length - 1; i++) {
                                newDirString.append(splitDir[i]).append("/");
                            }
                            System.setProperty("user.dir", newDirString.toString());
                        }
                        case ".", "", " " -> {
                        }
                        case "~", "~/" -> System.setProperty("user.dir", System.getProperty("user.home"));
                        case "~/Desktop", "~/Desktop/" ->
                                System.setProperty("user.dir", System.getProperty("user.home") + "/Desktop");
                        case "~/Documents", "~/Documents/" ->
                                System.setProperty("user.dir", System.getProperty("user.home") + "/Documents");
                        case "~/Downloads", "~/Downloads/" ->
                                System.setProperty("user.dir", System.getProperty("user.home") + "/Downloads");
                        case "~/Music", "~/Music/" ->
                                System.setProperty("user.dir", System.getProperty("user.home") + "/Music");
                        case "~/Pictures", "~/Pictures/" ->
                                System.setProperty("user.dir", System.getProperty("user.home") + "/Pictures");
                        case "~/Videos", "~/Videos/" ->
                                System.setProperty("user.dir", System.getProperty("user.home") + "/Videos");
                        default -> {
                            File newFile = new File(newDir);
                            if (newFile.exists()) {
                                System.setProperty("user.dir", newDir);
                            } else {
                                System.out.println("Directory does not exist");
                            }
                        }
                    }


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

                    Scanner execOut = new Scanner(p.getInputStream());
                    while (execOut.hasNextLine()) {
                        System.out.println(execOut.nextLine());
                    }
                }

                //if the user types "ping", ping the host they specify
                case "ping" -> {
                    System.out.print("Enter the host you want to ping: ");
                    String host = TermIn.nextLine();
                    Process p = Runtime.getRuntime().exec("ping -c 6 " + host);
                    System.out.println("Pinging " + host);
                    p.waitFor();



                    Scanner pingScanner = new Scanner(p.getInputStream());
                    while (pingScanner.hasNextLine()) {
                        System.out.println(pingScanner.nextLine());
                    }

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
                    System.out.println("help - print this help menu");
                }
                case "clear", "cls" -> {
                    for (int i = 0; i < 100; i++) {
                        System.out.println(" ");
                    }
                }
                //if the user types anything else, print an error message
                default -> {
                    System.out.println(" ");
                    System.out.println("Command not recognized");
                }
            }
        }
    }
}