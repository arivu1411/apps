package Adapter;

public class ToDoLists {
    private String task;
    private int date,time;

 /*   public ToDoLists(String task, int date, int time, int id, int status) {
        this.task = task;
        this.date = date;
        this.time = time;
        this.id = id;
        this.status = status;
    }*/

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    private int id,status;



    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
