package org.gingerjake.gsh;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("GingerShell, version 0.1 (c) 2022 GingerJake");
        System.out.println("Type 'help' for more information.");

        while (true) {
            System.out.println(" ");
            Scanner TermIn = new Scanner(System.in); // Create a Scanner object
            System.out.print(System.getProperty("user.name")); // Print the user's name
            System.out.print("@" + InetAddress.getLocalHost().getHostName() + " " + System.getProperty("user.dir") + ": "); //Print the rest of the start of the line, and prompt the user
            String TermOut = TermIn.nextLine(); // Read a line of text from the user


            switch (TermOut) {
                case "ls" -> {
                    File fileRoot = new File(System.getProperty("user.dir"));

                    DefaultMutableTreeNode root = new DefaultMutableTreeNode(fileRoot);
                    DefaultTreeModel model = new DefaultTreeModel(root);

                    File[] subItems = fileRoot.listFiles();
                    for (File file : Objects.requireNonNull(subItems)) {
                        if (file.isDirectory()) {
                            DefaultMutableTreeNode newDir = new DefaultMutableTreeNode(file.getName() + "/");
                            root.add(newDir);
                            model.reload();
                        } else {
                            DefaultMutableTreeNode newFile = new DefaultMutableTreeNode(file.getName());
                            root.add(newFile);
                        }
                    }
                    System.out.println(" ");
                    for (int i = 0; i < root.getChildCount(); i++) {
                        System.out.println(root.getChildAt(i).toString());
                    }


                }
                case "cd", "cd " -> {
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
                            if (newFile.isDirectory()) {
                                System.setProperty("user.dir", newDir);
                            } else {
                                System.out.println(" ");
                                System.out.println("Not a directory");
                            }
                        }
                    }


                }
                case "pwd" -> {
                    System.out.println(" ");
                    System.out.println(System.getProperty("user.dir"));
                }
                case "exit" -> System.exit(0);
                case "exec" -> {
                    System.out.print("Enter the program you want to execute: ");
                    String exec = TermIn.nextLine();

                    try {
                        Process p = Runtime.getRuntime().exec(exec);
                        p.waitFor();
                        System.out.println(" ");
                        Scanner execOut = new Scanner(p.getInputStream());
                        while (execOut.hasNextLine()) {
                            System.out.println(execOut.nextLine());
                        }

                        Scanner execErr = new Scanner(p.getErrorStream());
                        while (execErr.hasNextLine()) {
                            System.out.println(execErr.nextLine());
                        }

                        p.waitFor();
                        p.destroy();
                    } catch (FileNotFoundException e) {
                        System.out.println(" ");
                        System.out.println("File not found");
                    } catch (IOException e) {
                        System.out.println(" ");
                        System.out.println("IOException");
                    } catch (InterruptedException e) {
                        System.out.println(" ");
                        System.out.println("InterruptedException");
                    }


                }
                case "ping" -> {
                    System.out.print("Enter the host you want to ping: ");
                    String host = TermIn.nextLine();
                    Process p = Runtime.getRuntime().exec("ping -c 6 " + host);
                    System.out.println("Pinging " + host);

                    System.out.println(" ");
                    Scanner pingScanner = new Scanner(p.getInputStream());
                    while (pingScanner.hasNextLine()) {
                        System.out.println(pingScanner.nextLine());
                    }

                }
                case "help" -> {
                    System.out.println(" ");
                    System.out.println("ls - list the contents of the current directory");
                    System.out.println("cd - change the current directory");
                    System.out.println("pwd - print the current directory");
                    System.out.println("exit - exit the application");
                    System.out.println("exec - execute a program");
                    System.out.println("ping - ping a host");
                    System.out.println("help - print this help menu");
                    System.out.println("cat - print the contents of a file");
                    System.out.println("echo - print a line of text");
                    System.out.println("touch - create a file");
                    System.out.println("rm - remove a file or directory");
                    System.out.println("mkdir - create a directory");
                    System.out.println("clear - clear the screen");
                    System.out.println("cls - also clears the screen");

                }
                case "clear", "cls" -> {
                    for (int i = 0; i < 100; i++) {
                        System.out.println(" ");
                    }
                }
                case "echo" -> {
                    System.out.print("Enter the text you want to echo: ");
                    String echo = TermIn.nextLine();
                    System.out.println(" ");
                    System.out.println(echo);
                }
                case "cat" -> {
                    System.out.print("Enter the file you want to read: ");
                    String file = TermIn.nextLine();
                    File f = new File(System.getProperty("user.dir") + "/" + file);
                    try {
                        System.out.println(" ");
                        Scanner fileScanner = new Scanner(f);

                        while (fileScanner.hasNextLine()) {
                            System.out.println(fileScanner.nextLine());
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println("File not found or is a directory");
                    }
                }
                case "touch" -> {
                    System.out.print("Enter the file you want to create: ");
                    String file = TermIn.nextLine();
                    File f = new File(System.getProperty("user.dir") + "/" + file);

                    if (f.exists()) {
                        System.out.println(" ");
                        System.out.println("File already exists");
                    } else {
                        //noinspection ResultOfMethodCallIgnored
                        f.createNewFile();
                        System.out.println(" ");
                        System.out.println("File created");
                    }

                }
                case "rm" -> {
                    System.out.print("Enter the file or directory you want to delete: ");
                    String file = TermIn.nextLine();
                    File f = new File(System.getProperty("user.dir") + "/" + file);
                    if (f.exists()) {
                        //noinspection ResultOfMethodCallIgnored
                        f.delete();
                    } else {
                        System.out.println(" ");
                        System.out.println("File or directory does not exist");
                    }
                }
                case "mkdir" -> {
                    System.out.print("Enter the directory you want to create: ");
                    String dir = TermIn.nextLine();
                    File f = new File(System.getProperty("user.dir") + "/" + dir);
                    if (f.exists()) {
                        System.out.println(" ");
                        System.out.println("Directory already exists");
                    } else {
                        //noinspection ResultOfMethodCallIgnored
                        f.mkdir();
                        System.out.println(" ");
                        System.out.println("Directory created");
                    }
                }
                default -> {
                    System.out.println(" ");
                    System.out.println("Command not recognized");
                }
            }
        }
    }
}