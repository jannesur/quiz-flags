package de.vw.quizapp.QuizApp.country;

import de.vw.quizapp.QuizApp.flag.Flag;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CountryService {
    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> get4RandomCountries() {
        List<Country> allCountries = countryRepository.findAll(); // Hole alle Länder aus der Datenbank
        Collections.shuffle(allCountries); // Mische die Liste

        // Wähle die ersten vier Länder aus der gemischten Liste aus
        List<Country> randomCountries = new ArrayList<>();
        for (int i = 0; i < 4 && i < allCountries.size(); i++) {
            randomCountries.add(allCountries.get(i));
        }

        return randomCountries;
    }

    public Country getOneCountry(long id) {
        Optional<Country> countryOptional = countryRepository.findById(id);
        Country country = countryOptional.get();
        return country;
    }

    public Flag getFlagFromCountry(long id) {
        Optional<Country> countryOptional = countryRepository.findById(id);
        Country country = countryOptional.get();
        return country.getFlag();
    }
}
