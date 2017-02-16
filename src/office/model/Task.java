
package office.model;

import java.math.BigDecimal;
import office.model.position.Designer;
import office.model.position.Programmer;
import office.model.position.SalesManager;
import office.model.position.Tester;

/**
 *
 * @author EvdokimovEU
 */

public enum Task{
    Prog(Programmer.NAME, "Писать код"),    
    Sale(SalesManager.NAME, "Продавать услуги"),
    Design(Designer.NAME, "Рисовать макет"),
    Test(Tester.NAME, "Тестировать программу");
    
    private final String positionName;
    private final String taskName;
    private String taskPerformer;
    private int wageRate;
    private BigDecimal deadline;

    private Task(String positionName, String taskName) {
        this.positionName = positionName;
        this.taskName = taskName;
    }

    public BigDecimal getTaskPayment(){
        return deadline.multiply(BigDecimal.valueOf(wageRate));
    }
    
    public String getPositionName() {
        return positionName;
    }

    public String getTaskName() {
        return taskName;
    }   
    
    public String getTaskPerformer() {
        return taskPerformer;
    }

    public void setTaskPerformer(String taskPerformer) {
        this.taskPerformer = taskPerformer;
    }

    public int getWageRate() {
        return wageRate;
    }

    public void setWageRate(int wageRate) {
        this.wageRate = wageRate;
    }

    public BigDecimal getDeadline() {
        return deadline;
    }

    public void setDeadline(double deadline) {
        this.deadline = BigDecimal.valueOf(deadline);
    }
    
}
