
package office.model.position;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
    private List<Integer> salaries;

    public Accountant(int wageRate) {
        this.wageRate = wageRate;
        this.salaries = new ArrayList<>();
    }

    public void assessmentSalary(List<Emploee> emploees){
        int salary;
        salaries.add(wageRate);
        for(Emploee emploee : emploees){
            salary = 0;
            List<Task> tasks = emploee.getTasksCompleted();
            for(Task task : tasks){
                salary = salary + task.getTaskPayment();
            }
            Map<String, Position> mapPosition = emploee.getPositions();
            if(mapPosition.containsKey(Director.NAME)){
                Director director = (Director) mapPosition.get(Director.NAME);
                salary = salary + director.getWageRate();
                emploee.addSalaries(salary);
            }
        }        
    }
    
    public void createReport(List<Emploee> emploees, List<Task> freelance) throws FileNotFoundException{
        StringBuilder strBuilder = new StringBuilder();
        PrintWriter writeReport = new PrintWriter("report.txt");
        
        strBuilder
                .append("                    Отчет о выполненной работе                      ")
                .append("--------------------------------------------------------------------")
                .append("|           Имя            |  Выполненно заданий  | Начисленно з/п |");
        
        writeReport.println(strBuilder.toString());
                       
        emploees.forEach((emploee) -> {
            int taskCount = emploee.getTasksCompleted().size();
            int sumSalary = 0;
            strBuilder.setLength(0);
            for(Integer salary : emploee.getSalaries()){
                    sumSalary = sumSalary + salary;
            }
            
            strBuilder.append("|                          |                      |                |");                
            strBuilder
                    .replace(2, emploee.getName().length(), emploee.getName())
                    .replace(29, String.valueOf(taskCount).length(), String.valueOf(taskCount))
                    .replace(29, String.valueOf(sumSalary).length(), String.valueOf(sumSalary));
            writeReport.println(strBuilder.toString());
        });
        writeReport.println(" Отдано заданий фрилансерам : " + freelance.size());
        writeReport.close();
    }    
}
