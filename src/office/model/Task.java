
package office.model;

/**
 *
 * @author EvdokimovEU
 */

public enum Task{
    Prog(0, "Писать код"),    
    Sale(1, "Продавать"),
    Design(2, "Дизайн"),
    Test(3, "Тестировать");
    
    private final int taskId;
    private final String taskName;

    private Task(int taskId, String taskName) {
        this.taskId = taskId;
        this.taskName = taskName;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }
}
