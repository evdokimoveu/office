
package office.model.position;

import office.model.Position;

/**
 *
 * @author EvdokimovEU
 */
public class SalesManager implements Position{
    
    public static final String NAME = "SalesManager";
    
    private int wageRate;

    public SalesManager(int wageRate) {
        this.wageRate = wageRate;
    }
    
    @Override
    public String getName() {
        return NAME;
    }
    
    public void doTask(){
    }


}
