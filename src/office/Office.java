
package office;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import office.model.Emploee;
import office.model.Task;
import office.timesheet.DefaultTimeSheet;

/**
 *
 * @author EvdokimovEU
 */
public class Office implements Emploee.NewTaskCallBack, Emploee.CompletedTaskCallBack {
    
    public static final List<Task> NEW_TASK = Collections.synchronizedList(new ArrayList<>());
    public static final List<Task> COMPLETED_TASK = Collections.synchronizedList(new ArrayList<>());
    
    private List<Emploee> emploees;

    public Office() {
        createEmloee();
    }  
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Office office = new Office();
    }

    
    @Override
    public void newTaskCall(Task task) {
        NEW_TASK.add(task);
    }

    @Override
    public void completedTaskCall(Task task) {
        COMPLETED_TASK.add(task);
    }
    
    private void createEmloee() {
        Random r = new Random();
        int emploeeCount = r.nextInt(90) + 10;
        emploees = new ArrayList<>();
        for(int i = 0; i < emploeeCount; i++){
            Emploee emploee = new Emploee(new DefaultTimeSheet());
            emploees.add(emploee);
        }
    }
}
