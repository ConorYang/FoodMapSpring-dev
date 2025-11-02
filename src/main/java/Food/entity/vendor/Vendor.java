package Food.entity.vendor;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import Food.entity.account.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Vendors")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VendorID")
    private Integer vendorId;

    @Column(name = "VendorName", nullable = false, length = 50)
    private String vendorName;

    @Column(name = "TaxID")
    private Integer taxID;

    @Column(name = "OwnerName", nullable = false, length = 100)
    private String ownerName;

    @Column(name = "ContactName", nullable = false, length = 30)
    private String contactName;

    @Column(name = "ContactTitle", nullable = false, length = 30)
    private String contactTitle;

    @Column(name = "ContactTel", nullable = false, length = 30)
    private String contactTel;

    @Column(name = "ContactEmail", nullable = false, length = 30)
    private String contactEmail;

    @Column(name = "Address", nullable = false, length = 200)
    private String address;

    @Lob
    @Column(name = "MapApi")
    private String mapApi;

    @Column(name = "VD_CategoryID", nullable = false)
    private Integer vdCategoryID;

    @Lob
    @Column(name = "LogoURL")
    private String logoURL;

    @Column(name = "VD_Status", nullable = false, length = 10)
    private String vdStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AccountID", referencedColumnName = "AccountID")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "vendor", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @JsonIgnore // 前端不要直接序列化 OpeningHours
    private Set<OpeningHours> openingHours = new HashSet<>();

    @OneToOne(mappedBy = "vendor", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({ "vendor" })
    private Details details;

    // getter & setter
    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Integer getTaxID() {
        return taxID;
    }

    public void setTaxID(Integer taxID) {
        this.taxID = taxID;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactTitle() {
        return contactTitle;
    }

    public void setContactTitle(String contactTitle) {
        this.contactTitle = contactTitle;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMapApi() {
        return mapApi;
    }

    public void setMapApi(String mapApi) {
        this.mapApi = mapApi;
    }

    public Integer getVdCategoryID() {
        return vdCategoryID;
    }

    public void setVdCategoryID(Integer vdCategoryID) {
        this.vdCategoryID = vdCategoryID;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public String getVdStatus() {
        return vdStatus;
    }

    public void setVdStatus(String vdStatus) {
        this.vdStatus = vdStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<OpeningHours> getOpeningHours() {

        return openingHours;

    }

    public void setOpeningHours(Set<OpeningHours> openingHours) {

        this.openingHours = openingHours;

    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }
}