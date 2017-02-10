
package office.model.position;

import office.model.Position;

/**
 *
 * @author EvdokimovEU
 */
public class Tester implements Position{
    
    public static final String NAME = "Tester";
    
    private int wageRate;    
    private int hoursWorked;

    public Tester(int wageRate) {
        this.wageRate = wageRate;
    }
    
    @Override
    public String getName() {
        return NAME;
    }
    
    public void doTask(){
    }
    
}
