package net.silica.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Locale;

public class Customer implements Serializable{
    //Gson serializable
    private int idCustomer;
    private String idFacebook="";
    private String idGoogle="";
    private String idTokenFcm="";
    private String nameFaceBook="";
    private String nameGoogle="";
    private String firstName="";
    private String lastName="";
    private String linkFacebook="";
    private String phone="";
    private String phoneFacebook="";
    private String emailFacebook="";
    private String emailGoogle="";
    private String address="";
    private String sex="";
    private String dateOfBirth="";
    private String ageRange="";
    private String fcmToken="";
    private String job="";
    private String locale;
    private String iconUrlCustomer="";
//    @Expose(serialize = false)
    private Timestamp createAtCustomer;
    @Expose(serialize = false)//=@Expose(serialize = false, deserialize = true)
    private Timestamp updateAtCustomer;

    public Customer() {
    }

    public String getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getIdFacebook() {
        return idFacebook;
    }

    public String getLinkFacebook() {
        return linkFacebook;
    }

    public void setLinkFacebook(String linkFacebook) {
        this.linkFacebook = linkFacebook;
    }

    public void setIdFacebook(String idFacebook) {
        this.idFacebook = idFacebook;
    }

    public String getIdGoogle() {
        return idGoogle;
    }

    public void setIdGoogle(String idGoogle) {
        this.idGoogle = idGoogle;
    }

    public String getNameFaceBook() {
        return nameFaceBook;
    }

    public void setNameFaceBook(String nameFaceBook) {
        this.nameFaceBook = nameFaceBook;
    }

    public String getNameGoogle() {
        return nameGoogle;
    }

    public void setNameGoogle(String nameGoogle) {
        this.nameGoogle = nameGoogle;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneFacebook() {
        return phoneFacebook;
    }

    public void setPhoneFacebook(String phoneFacebook) {
        this.phoneFacebook = phoneFacebook;
    }

    public String getEmailFacebook() {
        return emailFacebook;
    }

    public void setEmailFacebook(String emailFacebook) {
        this.emailFacebook = emailFacebook;
    }

    public String getEmailGoogle() {
        return emailGoogle;
    }

    public void setEmailGoogle(String emailGoogle) {
        this.emailGoogle = emailGoogle;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getIdTokenFcm() {
        return idTokenFcm;
    }

    public void setIdTokenFcm(String idTokenFcm) {
        this.idTokenFcm = idTokenFcm;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getLocale() {

        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getIconUrlCustomer() {
        return iconUrlCustomer;
    }

    public void setIconUrlCustomer(String iconUrlCustomer) {
        this.iconUrlCustomer = iconUrlCustomer;
    }

    public Timestamp getCreateAtCustomer() {
        return createAtCustomer;
    }

    public void setCreateAtCustomer(Timestamp createAtCustomer) {
        this.createAtCustomer = createAtCustomer;
    }

    public Timestamp getUpdateAtCustomer() {
        return updateAtCustomer;
    }

    public void setUpdateAtCustomer(Timestamp updateAtCustomer) {
        this.updateAtCustomer = updateAtCustomer;
    }

    public String toJSONNoId(){
        String json="{\"address\":\""+address+"\"," +
                "\"dateOfBirth\":\""+dateOfBirth+"\"," +
                "\"emailFacebook\":\""+emailFacebook+"\"," +
                "\"emailGoogle\":\""+emailGoogle+"\"," +
                "\"firstName\":\""+firstName+"\"," +
                "\"idFacebook\":\""+idFacebook+"\"," +
                "\"idGoogle\":\""+idGoogle+"\"," +
                "\"idTokenFcm\":\""+idTokenFcm+"\"," +
                "\"fcmToken\":\""+fcmToken+"\"," +
                "\"iconUrlCustomer\":\""+iconUrlCustomer+"\"," +
                "\"job\":\""+job+"\"," +
                "\"locale\":\""+locale+"\"," +
                "\"linkFacebook\":\""+linkFacebook+"\"," +
                "\"nameFaceBook\":\""+nameFaceBook+"\"," +
                "\"nameGoogle\":\""+nameGoogle+"\"," +
                "\"phone\":\""+phone+"\"," +
                "\"phoneFacebook\":\""+phoneFacebook+"\"," +
                "\"lastName\":\""+lastName+"\"," +
                "\"ageRange\":\""+ageRange+"\"," +
                "\"sex\":\""+sex+"\"}";
        return json;
    }
}
