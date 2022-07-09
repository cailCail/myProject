
package model;
/**
 * This class creates the Inhouse Part Objects. It extends the abstract Part class and adds machineid.
 *
 * @author Sonya Cail
 */

public class Inhouse extends Part {
    private int machineid;

    public Inhouse(int id, String name, double price, int stock, int min, int max, int machineid) {
        super(id, name, price, stock, min, max);
        this.machineid = machineid;
    }

    /**
     * @param machineid  the machine ID is set.
     * */
    public void setMachineid(int machineid) {
        this.machineid = machineid;
    }

    /**
     * @return the machine ID.
     * */
    public int getMachineid() {

        return machineid;
    }
}


