package de.vw.quizapp.QuizApp.flag;

import de.vw.quizapp.QuizApp.country.Country;
import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Objects;

@Entity
public class Flag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 1300)
    private String imageName;
    @Column(length = 2500)
    private byte[] image;
    @OneToOne
    @JoinColumn(name = "imageName", referencedColumnName = "countryCode", insertable = false, updatable = false)
    private Country country;

    public Flag() {}

    public Flag(String imageName, byte[] image) {
        this.imageName = imageName;
        this.image = image;
    }

    public Flag(String imageName, byte[] image, Country country) {
        this.imageName = imageName;
        this.image = image;
        this.country = country;
    }

    public long getId() {
        return id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flag flag = (Flag) o;
        return id == flag.id && Objects.equals(imageName, flag.imageName) && Arrays.equals(image, flag.image);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, imageName);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }

    @Override
    public String toString() {
        return "Flag{" +
                "id=" + id +
                ", imageName='" + imageName + '\'' +
                ", image=" + Arrays.toString(image) +
                '}';
    }
}
