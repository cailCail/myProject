package model;
/**
 * This class creates the Outsource part objects. It extends the abstract Part class and adds companyName.
 */

public class Outsource extends Part {
    private String companyName;
    public Outsource(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @param companyName  sets the company name.
     * */

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return the company name.
     * */
    public String getCompanyName() {
        return companyName;
    }

}
