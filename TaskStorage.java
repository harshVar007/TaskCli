import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class TaskStorage {
    
    private static final String file = "tasks.dat";

    //this functions loads all tasks into List
    @SuppressWarnings("unchecked")
    public static List<Task> load(){

        File f = new File(file);
        
        if(!f.exists()){
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) 
        {
            return (List<Task>) ois.readObject();
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to Load Tasks " + e);
        }
        
    }

    public static void save(List<Task> taskList){

        File f = new File(file);

        // create new file if it doesn't exist
        if (!f.exists()) {
            try {
                f.createNewFile();
                System.out.println("File created " + f.getName());
            } catch (IOException e) {
                System.out.println("Can't create new File");
                return;
            }
        }

        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f))) {

            os.writeObject(taskList);
            System.out.println("Task Written to File");

        } 
        catch (Exception e) {
            System.out.println("Can't write TaskList to file " + e);
        }

    }

    public static List<Task> reArrangeList(){

        List<Task> taskList = TaskStorage.load();
        int id = 0;
        for (Task task : taskList) 
        {

            task.setId(++id);
            //System.out.println(task);
        }

        TaskStorage.save(taskList);
        return taskList;
    }

    public static Task setStatus(List<Task> taskList, int id, String string) {
        
        if(id > taskList.size()){

            throw new IndexOutOfBoundsException();
        }

        taskList.stream().filter(task -> task.getId() == id ).forEach(task -> task.setStatus(string));
        TaskStorage.save(taskList);

        return taskList.get(id-1);
    }

    public static boolean checkList(String val){

        List<Task> taskList = TaskStorage.load();

        if(taskList.isEmpty()){

                        System.out.println("Todo List is Empty");
                        return false;
                    }

        if(Integer.parseInt(val) > taskList.size()){

                        System.out.println("No Todos At index " + val);
                        return false;
        }

        return true;
    }
}
