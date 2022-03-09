package androidev.main_app;


public class ProfileCompletionModel {
    private String fname, lname, postcode, interests, politicalInterests, ethnicity,age, phoneNumber;


    public ProfileCompletionModel(String age, String phoneNumber, String fname, String lname, String postcode, String interests, String politicalInterests, String ethnicity) {
        this.fname = fname;
        this.lname = lname;
        this.interests = interests;
        this.postcode = postcode;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.politicalInterests = politicalInterests;
        this.postcode = postcode;
        this.ethnicity = ethnicity;


    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getPoliticalInterests() {
        return politicalInterests;
    }

    public void setPoliticalInterests(String politicalInterests) {
        this.politicalInterests = politicalInterests;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
