
package office.model.position;

import office.model.Position;

/**
 *
 * @author EvdokimovEU
 */
public class Accountant implements Position{
    
    public static final String NAME = "Accountant";
    
    private int wageRate;

    public Accountant(int wageRate) {
        this.wageRate = wageRate;
    }
    
    @Override
    public String getName() {
        return NAME;
    }
    
    public void createOrder(){
    
    }
    
    public void assessmentSalary(){
    
    }

    
}
