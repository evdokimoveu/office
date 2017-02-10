
package office.model.position;

import office.model.Position;

/**
 *
 * @author EvdokimovEU
 */
public class Designer implements Position{
    
    public static final String NAME = "Designer";
    
    private int wageRate;
    private int hoursWorked;

    public Designer(int wageRate) {
        this.wageRate = wageRate;
    }
    
    @Override
    public String getName() {
        return NAME;
    }
    
    public void doTask(){
    }

}
