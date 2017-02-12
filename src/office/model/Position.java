
package office.model;

import java.util.concurrent.Callable;

/**
 *
 * @author EvdokimovEU
 */
public interface Position extends Callable<Task>{
    void setTask(Task task);
    Task getTask();
}
