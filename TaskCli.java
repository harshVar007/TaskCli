// import java.io.BufferedReader;
// import java.io.BufferedWriter;
// import java.io.File;
// import java.io.FileInputStream;
// import java.io.FileOutputStream;
// import java.io.FileReader;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.io.ObjectInputStream;
// import java.io.ObjectOutputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskCli {

    public static List<Task> list = new ArrayList<>();

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Usage: add <value> | delete <id> | setStatus <id> <status> | filter <status> | help | getAll");
            return;
        }

        List<Task> taskList = TaskStorage.load();

        switch (args[0]) {

            case "add": {

                try {
                    taskList.add(new Task(nextId(taskList), args[1], "Todo"));
                    TaskStorage.save(taskList);
                } catch (IndexOutOfBoundsException i) {

                    System.out.println("Todo is Empty.Please provide a value");

                }

            }
                break;

            case "delete": {
                try {

                    if (TaskStorage.checkList(args[1])) {
                        taskList.removeIf(task -> task.getId() == Integer.parseInt(args[1]));

                        TaskStorage.save(taskList);
                        System.out.println("Task With id : " + args[1] + " removed");

                        taskList = TaskStorage.reArrangeList();

                        for (Task t : taskList) {
                            System.out.println(t);
                        }

                    }

                } catch (NumberFormatException nfe) {
                    System.out.println("Please Enter a numeric Id");
                }
            }
                break;

            case "setStatus": {

                try {
                    if (TaskStorage.checkList(args[1])) {
                        Task t = TaskStorage.setStatus(taskList, Integer.parseInt(args[1]), args[2]);
                        System.out.println("Status Changed " + t);
                    }
                } catch (NumberFormatException nfe) {
                    System.err.println("Please Enter a numeric Id");
                }

            }
                break;

            case "getAll": {

                for (Task task2 : taskList) {
                    System.out.println(task2);
                }

            }
                break;

            case "filter": {

                String status = args[1];
                List<Task> filteredList = taskList.stream()
                        .filter(task -> task.getStatus().toLowerCase().equals(status.toLowerCase()))
                        .collect(Collectors.toList());

                for (Task t : filteredList) {

                    System.out.println(t);
                    // return;

                }

                if (filteredList.isEmpty()) {
                    System.out.println("Couldn't find any Todo with status : " + args[1]);
                }

                // for(Task t : filteredList){

                // System.out.println(t);
                // return;

                // }
            }
                break;

            default: {

                System.out
                        .println("Usage: add <value> | delete <id>| setStatus <id> <value> | filter <status> | help | getAll");
                return;

            }
        }

    }

    private static int nextId(List<Task> taskList) {

        return taskList.stream().mapToInt(Task::getId).max().orElse(0) + 1;
    }

}