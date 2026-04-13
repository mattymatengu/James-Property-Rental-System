package model;

public class Flat extends Property implements Rentable {

    private String unitNumber;
    private int    floorLevel;
    private boolean hasElevator;
    private boolean hasBackyard;

    public Flat(String propertyId, String address, String location,
                double marketValue, double monthlyRent,
                String unitNumber, int floorLevel,
                boolean hasElevator, boolean hasBackyard) {
        super(propertyId, "Flat", address, location, marketValue, monthlyRent);
        this.unitNumber  = unitNumber;
        this.floorLevel  = floorLevel;
        this.hasElevator = hasElevator;
        this.hasBackyard = hasBackyard;
    }

    public String getUnitNumber()  { return unitNumber; }
    public int getFloorLevel()     { return floorLevel; }
    public boolean hasElevator()   { return hasElevator; }
    public boolean hasBackyard()   { return hasBackyard; }

    @Override
    public String getDetails() {
        return "Flat " + unitNumber + " floor " + floorLevel + " at " + getAddress()
             + " | Elevator: " + (hasElevator ? "Yes" : "No")
             + " | Rent: N$" + getMonthlyRent();
    }

    @Override public double calculateRent()    { return getMonthlyRent(); }
    @Override public double applyLatePenalty() { return getMonthlyRent() * 0.05; }
    @Override public String generateInvoice()  {
        return "INVOICE — Flat " + unitNumber + " floor " + floorLevel + " — N$" + getMonthlyRent();
    }
}
