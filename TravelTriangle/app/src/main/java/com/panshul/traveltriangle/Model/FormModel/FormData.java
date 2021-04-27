package com.panshul.traveltriangle.Model.FormModel;

public class FormData {
    String id;
    String to;
    String from;
    String exploring;
    String month;
    String departure_date;
    String no_of_days;
    String hotel_category;
    String other_inclusions;
    String budget;
    String adults;
    String children;
    String infant;
    String email;
    String phone;
    String send_updates;
    String willBook;
    String age;
    String localExperiences;
    public FormData(){

    }

    public FormData(String id, String to, String from, String exploring, String month, String departure_date, String no_of_days, String hotel_category, String other_inclusions, String budget, String adults, String children, String infant, String email, String phone, String send_updates, String willBook, String age, String localExperiences) {
        this.id = id;
        this.to = to;
        this.from = from;
        this.exploring = exploring;
        this.month = month;
        this.departure_date = departure_date;
        this.no_of_days = no_of_days;
        this.hotel_category = hotel_category;
        this.other_inclusions = other_inclusions;
        this.budget = budget;
        this.adults = adults;
        this.children = children;
        this.infant = infant;
        this.email = email;
        this.phone = phone;
        this.send_updates = send_updates;
        this.willBook = willBook;
        this.age = age;
        this.localExperiences = localExperiences;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getExploring() {
        return exploring;
    }

    public void setExploring(String exploring) {
        this.exploring = exploring;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDeparture_date() {
        return departure_date;
    }

    public void setDeparture_date(String departure_date) {
        this.departure_date = departure_date;
    }

    public String getNo_of_days() {
        return no_of_days;
    }

    public void setNo_of_days(String no_of_days) {
        this.no_of_days = no_of_days;
    }

    public String getHotel_category() {
        return hotel_category;
    }

    public void setHotel_category(String hotel_category) {
        this.hotel_category = hotel_category;
    }

    public String getOther_inclusions() {
        return other_inclusions;
    }

    public void setOther_inclusions(String other_inclusions) {
        this.other_inclusions = other_inclusions;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getAdults() {
        return adults;
    }

    public void setAdults(String adults) {
        this.adults = adults;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public String getInfant() {
        return infant;
    }

    public void setInfant(String infant) {
        this.infant = infant;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSend_updates() {
        return send_updates;
    }

    public void setSend_updates(String send_updates) {
        this.send_updates = send_updates;
    }

    public String getWillBook() {
        return willBook;
    }

    public void setWillBook(String willBook) {
        this.willBook = willBook;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLocalExperiences() {
        return localExperiences;
    }

    public void setLocalExperiences(String localExperiences) {
        this.localExperiences = localExperiences;
    }
}
