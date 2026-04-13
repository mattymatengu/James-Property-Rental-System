package model;

// House extends Property = House inherits all of Property's fields and methods
// House implements Rentable = House must have calculateRent, applyLatePenalty, generateInvoice
public class House extends Property implements Rentable {

    private double plotSize; // unique to houses only

    public House(String propertyId, String address, String location,
                 double marketValue, double monthlyRent, double plotSize) {
        // super() calls Property's constructor to set the shared fields
        super(propertyId, "House", address, location, marketValue, monthlyRent);
        this.plotSize = plotSize;
    }

    public double getPlotSize() { return plotSize; }

    // Each subclass writes its OWN version of getDetails() — this is Polymorphism
    @Override
    public String getDetails() {
        return "House at " + getAddress() + " | Plot: " + plotSize + " sqm"
             + " | Rent: N$" + getMonthlyRent();
    }

    @Override
    public double calculateRent() {
        return getMonthlyRent();
    }

    @Override
    public double applyLatePenalty() {
        return getMonthlyRent() * 0.05; // 5% late fee
    }

    @Override
    public String generateInvoice() {
        return "INVOICE — House at " + getAddress() + " — N$" + getMonthlyRent();
    }
}
