package de.vw.quizapp.QuizApp.country;

import de.vw.quizapp.QuizApp.flag.Flag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/country")
@CrossOrigin("*")
public class CountryController {
    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public ResponseEntity<List<Country>> get4RandomCountries() {
        List<Country> countries = countryService.get4RandomCountries();
        return  ResponseEntity.ok().body(countries);
    }

    @GetMapping("/{id}")
    public Country getOneCountry(@PathVariable(value = "id") long id) {
        return countryService.getOneCountry(id);
    }

    @GetMapping("/flag/{id}")
    public Flag getFlagByCountry(@PathVariable(value = "id") long id) {
        return countryService.getFlagFromCountry(id);
    }
}
