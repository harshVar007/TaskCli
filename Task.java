import java.io.Serializable;
import java.util.List;

public class Task implements Serializable
{
    public static List<TaskCli> allTasks;

    private static final long serialVersionUID = 1L;

    private int id;
    private String task;
    private String status;

    public Task(){}

    public Task(int id ,String task, String status) {
        this.id = id;
        this.task = task;
        this.status = status;
    }

    public int getId(){
        return id;
    }

    public String getTask() {
        return task;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "id=" + id + ": task=" + task + ", status=" + status ;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    

}
