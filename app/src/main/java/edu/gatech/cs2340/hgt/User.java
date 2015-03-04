package edu.gatech.cs2340.hgt;


import android.content.Context;

/**
 * Created by Sizhe Lin on 2/16/15.
 */
public class User {
       private String username;
       private String name;
       private String email;
       private boolean isActive;
       private boolean isMale = true;
       private String interest;
       private String shortDescription;
       private boolean hasDetail;
       private float rating;

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    /**
     *
     * @param username
     * @param name
     * @param email
     */
    public User(String username, String name, String email) {
        this.username = username;
        this.name = name;
        this.email = email;
    }


    public boolean isHasDetail() {
        return hasDetail;
    }

    public void setHasDetail(boolean hasDetail) {
        this.hasDetail = hasDetail;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getInterest() {

        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public boolean isMale() {

        return isMale;
    }

    public void setMale(boolean isMale) {
        this.isMale = isMale;
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (!username.equals(user.username)) return false;

        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    /**
     *
     * @return
     */
    public boolean isActive() {

        return isActive;
    }

    /**
     *
     * @param isActive
     */
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     *
     * @param username
     */
    public User(String username) {
        this.username = username;
    }



    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {

        this.username = username;
    }

    /**
     *
     * @return
     */
    public String getName() {

        return name;

    }

    /**
     *
     * @return
     */
    public String getUsername() {

        return username;
    }

    /**
     *
     * @return
     */
    public int getImageResource() {
        if (isMale) {
            return R.drawable.profile_icon_temp;
            //return R.drawable.ic_male
        }
        return R.drawable.profile_icon_temp;
        //return R.drawable.ic_female
    }
}
