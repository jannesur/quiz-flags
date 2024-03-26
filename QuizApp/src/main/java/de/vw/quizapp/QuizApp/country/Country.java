package de.vw.quizapp.QuizApp.country;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.vw.quizapp.QuizApp.flag.Flag;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String countryCode;
    private String countryName;

    @OneToOne(mappedBy = "country", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Flag flag;

    public Country(String countryCode, String countryName, Flag flag) {
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.flag = flag;
    }

    public Country(String countryCode, String countryName) {
        this.countryCode = countryCode;
        this.countryName = countryName;
    }

    public Country(){}

    public long getId() {
        return id;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Flag getFlag() {
        return flag;
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return id == country.id && Objects.equals(countryCode, country.countryCode) && Objects.equals(countryName, country.countryName) && Objects.equals(flag, country.flag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, countryCode, countryName, flag);
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", countryCode='" + countryCode + '\'' +
                ", countryName='" + countryName + '\'' +
                ", flag=" + flag +
                '}';
    }
}
