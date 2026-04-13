package model;

public class Townhouse extends Property implements Rentable {

    private String unitNumber;
    private boolean hasBackyard;

    public Townhouse(String propertyId, String address, String location,
                     double marketValue, double monthlyRent,
                     String unitNumber, boolean hasBackyard) {
        super(propertyId, "Townhouse", address, location, marketValue, monthlyRent);
        this.unitNumber  = unitNumber;
        this.hasBackyard = hasBackyard;
    }

    public String getUnitNumber()  { return unitNumber; }
    public boolean hasBackyard()   { return hasBackyard; }

    @Override
    public String getDetails() {
        return "Townhouse unit " + unitNumber + " at " + getAddress()
             + " | Backyard: " + (hasBackyard ? "Yes" : "No")
             + " | Rent: N$" + getMonthlyRent();
    }

    @Override public double calculateRent()    { return getMonthlyRent(); }
    @Override public double applyLatePenalty() { return getMonthlyRent() * 0.05; }
    @Override public String generateInvoice()  {
        return "INVOICE — Townhouse " + unitNumber + " — N$" + getMonthlyRent();
    }
}
