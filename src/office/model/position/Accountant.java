
package office.model.position;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import office.model.Emploee;
import office.model.Position;
import office.model.Task;

/**
 *
 * @author EvdokimovEU
 */
public class Accountant {
    
    public static final String NAME = "Accountant";
    
    private final int wageRate;

    public Accountant(int wageRate) {
        this.wageRate = wageRate;
    }

    /**
     * Assessment salaries in last week's day 
     * @param emploees 
     */
    public void assessmentSalary(List<Emploee> emploees){
        BigDecimal salary;
        for(Emploee emploee : emploees){
            salary = new BigDecimal(0);
            List<Task> tasks = emploee.getTasksCompleted();            
            for(Task task : tasks){
                salary = salary.add(task.getTaskPayment());
            }            
            Map<String, Position> mapPosition = emploee.getPositions();            
            
            //Add fixed wage rate to salary
            if(mapPosition.containsKey(Director.NAME)){                
                Director director = (Director) mapPosition.get(Director.NAME);
                salary = salary.add(BigDecimal.valueOf(director.getWageRate()));
            }
            
            //Add fixed wage rate to salary
            if(mapPosition.containsKey(SalesManager.NAME)){
                SalesManager manager = (SalesManager) mapPosition.get(SalesManager.NAME);
                salary = salary.add(BigDecimal.valueOf(manager.getWageRate()));
            }
            emploee.addSalaries(salary);
        }        
    }
    
    /**
     * Create report in last day of month
     * @param emploees
     * @param freelance
     * @throws FileNotFoundException 
     */
    public void createReport(List<Emploee> emploees, List<Task> freelance) throws FileNotFoundException{
        
        StringBuilder strBuilder = new StringBuilder();
        PrintWriter writeReport = new PrintWriter("report.txt");
                
        writeReport.println("                    Отчет о выполненной работе                      ");
        writeReport.println("--------------------------------------------------------------------");
        writeReport.println("|           Имя            |  Выполненно заданий  | Начисленно з/п |");
        
        writeReport.println(strBuilder.toString());
        
        emploees.forEach((emploee) -> {            
            int taskCount = emploee.getTasksCompleted().size();
            BigDecimal sumSalary = new BigDecimal(0);
            strBuilder.setLength(0);
            for(BigDecimal salary : emploee.getSalaries()){                
                sumSalary = sumSalary.add(salary);
            }
            strBuilder.append("|                          |                      |                |");
            strBuilder
                    .replace(2, emploee.getName().length()+2, emploee.getName())
                    .replace(29, String.valueOf(taskCount).length()+29, String.valueOf(taskCount))
                    .replace(52, String.valueOf(sumSalary).length()+52, String.valueOf(sumSalary));
            writeReport.println(strBuilder.toString());
        });
        
        writeReport.println("| Бухгалтер                |                      | " + wageRate + "           |");
        writeReport.println("--------------------------------------------------------------------");
        writeReport.println("     Отдано заданий фрилансерам : " + freelance.size());
        writeReport.close();
    }    
}
