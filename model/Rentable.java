package model;

// An interface is a contract — any class that implements this
// MUST have these three methods
public interface Rentable {
    double calculateRent();
    double applyLatePenalty();
    String generateInvoice();
}
