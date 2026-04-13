package model;

public class Tenant {

    private String tenantId;
    private String firstName;
    private String lastName;
    private String idNumber;
    private String phone;
    private String email;
    private String status; // "Active" or "Blacklisted"

    public Tenant(String tenantId, String firstName, String lastName,
                  String idNumber, String phone, String email, String status) {
        this.tenantId  = tenantId;
        this.firstName = firstName;
        this.lastName  = lastName;
        this.idNumber  = idNumber;
        this.phone     = phone;
        this.email     = email;
        this.status    = status;
    }

    // Getters
    public String getTenantId()  { return tenantId; }
    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName; }
    public String getIdNumber()  { return idNumber; }
    public String getPhone()     { return phone; }
    public String getEmail()     { return email; }
    public String getStatus()    { return status; }

    // Setters
    public void setPhone(String phone)   { this.phone = phone; }
    public void setEmail(String email)   { this.email = email; }
    public void setStatus(String status) { this.status = status; }

    public String getFullName() { return firstName + " " + lastName; }

    @Override
    public String toString() { return tenantId + " | " + getFullName() + " | " + status; }
}
