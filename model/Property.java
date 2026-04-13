package model;

// Abstract means you can never do: new Property()
// You can only create a House, Townhouse, or Flat
// This class holds fields that ALL property types share
public abstract class Property {

    // private = only this class can access these directly
    private String propertyId;
    private String type;
    private String address;
    private String location;
    private double marketValue;
    private double monthlyRent;
    private boolean isAvailable;

    // Constructor — runs when a subclass calls super(...)
    public Property(String propertyId, String type, String address,
                    String location, double marketValue, double monthlyRent) {
        this.propertyId  = propertyId;
        this.type        = type;
        this.address     = address;
        this.location    = location;
        this.marketValue = marketValue;
        this.monthlyRent = monthlyRent;
        this.isAvailable = true; // new properties start as available
    }

    // ── Getters (how other classes READ private fields) ──────────
    public String getPropertyId()  { return propertyId; }
    public String getType()        { return type; }
    public String getAddress()     { return address; }
    public String getLocation()    { return location; }
    public double getMarketValue() { return marketValue; }
    public double getMonthlyRent() { return monthlyRent; }
    public boolean isAvailable()   { return isAvailable; }

    // ── Setters (how other classes CHANGE private fields) ────────
    public void setAvailable(boolean available) { this.isAvailable = available; }
    public void setMonthlyRent(double rent)     { this.monthlyRent = rent; }

    // Abstract method — every subclass MUST write their own version
    // This is what gives us Polymorphism
    public abstract String getDetails();

    @Override
    public String toString() {
        return propertyId + " | " + type + " | " + address;
    }
}
