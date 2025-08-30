package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    private static Map<String, List<Integer>> map = new HashMap<>();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        List<String> lines = loadData(args[1]);
        while (!exit) {
            printMenu();
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    System.out.println("Select a matching strategy: ALL, ANY, NONE");
                    find(scanner.nextLine(), lines, scanner.nextLine().toLowerCase());
                    break;
                case "2":
                    printLines(lines);
                    break;
                case "0":
                    exit = true;
                    break;
                default:
                    System.out.println("Incorrect option! Try again.");
            }
        }
        scanner.close();
        System.out.println("Bye!");
    }

    private static void printLines(List<String> lines) {
        System.out.println("=== List of people ===");
        for (int i = 0; i < lines.size(); i++) {
            System.out.println(lines.get(i));
        }
    }

    private static void findAll(String input, List<String> lines) {
        System.out.println("inside findall");
        String[] split = input.split(" ");
        Set<Integer> all;
        if (map.get(split[0]) != null) {
            all = new HashSet<>(map.get(split[0]));
        } else {
            all = new HashSet<>();
        }
        for (int i = 1; i < split.length; i++) {
            if (map.get(split[i]) != null) {
                all.retainAll(map.get(split[i]));
            }
        }
        if (all.size() == 0) {
            System.out.println("No matching people found.");
        } else {
            for (int i : all) {
                System.out.println(lines.get(i));
            }
        }
    }

    private static void findAny(String input, List<String> lines) {
        String[] split = input.split(" ");
        Set<Integer> any = new HashSet<>();
        for (int i = 0; i < split.length; i++) {
            any.addAll(map.get(split[i]));
        }
        if (any.size() == 0) {
            System.out.println("No matching people found.");
        } else {
            for (int i : any) {
                System.out.println(lines.get(i));
            }
        }
    }

    private static void findNone(String input, List<String> lines) {
        String[] split = input.split(" ");
        List<Integer> none = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            none.add(i);
        }
        for (int i = 0; i < split.length; i++) {
            none.removeAll(map.get(split[i]));
        }
        if (none.size() == 0) {
            System.out.println("No matching people found.");
        } else {
            for (int i : none) {
                System.out.println(lines.get(i));
            }
        }
    }

    private static void find(String choice, List<String> lines, String input) {
        switch (choice) {
            case "ALL":
                findAll(input, lines);
                break;
            case "ANY":
                findAny(input, lines);
                break;
            case "NONE":
                findNone(input, lines);
                break;
        }
    }

    private static void printMenu() {
        System.out.println("=== Menu ===\n" +
                "1. Find a person\n" +
                "2. Print all people\n" +
                "0. Exit");
    }
    
    private static List<String> loadData(String fileName) {
        List<String> list = new ArrayList<>();
        File file = new File(fileName);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                list.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        for (int i = 0; i < list.size(); i++) {
            String line = list.get(i);
            String[] split = line.split(" ");
            for (String s : split) {
                if (map.get(s.toLowerCase()) == null) {
                    List<Integer> ss = new ArrayList<>();
                    ss.add(i);
                    map.put(s.toLowerCase(),ss);
                } else {
                    map.get(s.toLowerCase()).add(i);
                }
            }
        }
        //System.out.println(map);
        return list;
    }
}
