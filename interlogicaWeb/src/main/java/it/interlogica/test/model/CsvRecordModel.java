package it.interlogica.test.model;

import javax.persistence.*;

@Entity
@Table(name="WRONG_PHONE")
public class CsvRecordModel {

    @Id
    Long id;
    String phone;
    public CsvRecordModel() {
        this.id = id;
        this.phone = phone;
    }
    public CsvRecordModel(Long id, String phone) {
        this.id = id;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public CsvRecordModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public CsvRecordModel setPhone(String phone) {
        this.phone = phone;
        return this;
    }
}

