package model;

public class Task {
    private int id;
    private String title;
    private String priority;
    private String dueDate;

    public Task(int id, String title, String priority, String dueDate) {
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public int getId() { 
        return id; 
    }

    public String getTitle() { 
        return title; 
    }

    public String getPriority() { 
        return priority; 
    }

    public void setId(int id) { 
        this.id = id; 
    }

    public void setTitle(String title) { 
        this.title = title; 
    }

    public void setPriority(String priority) { 
        this.priority = priority; 
    }
    
    public String getDueDate() { 
        return dueDate; 
    }
    
    public void setDueDate(String dueDate) { 
        this.dueDate = dueDate; 
    }
}
