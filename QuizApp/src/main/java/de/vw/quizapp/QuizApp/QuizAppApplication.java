package de.vw.quizapp.QuizApp;

import de.vw.quizapp.QuizApp.country.Country;
import de.vw.quizapp.QuizApp.country.CountryRepository;
import de.vw.quizapp.QuizApp.flag.Flag;
import de.vw.quizapp.QuizApp.flag.FlagRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class QuizAppApplication {

	public QuizAppApplication(CountryRepository countryRepository, FlagRepository flagRepository) {
		this.countryRepository = countryRepository;
		this.flagRepository = flagRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(QuizAppApplication.class, args);
	}

	private static CountryRepository countryRepository;
	private static FlagRepository flagRepository;

	@Bean
	public Boolean saveAllCountrys() {
		String csvFile = "./src/main/resources/countrys.csv";
		///Users/VWEK7LG/Documents/Java/quiz-app/QuizApp/src/main/resources/countrys.csv
		List<Country> countryList = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts.length >= 2) {
					String countryCode = parts[0];
					String countryName = parts[1];
					Country country = new Country();
					country.setCountryCode(countryCode);
					country.setCountryName(countryName);
					countryList.add(country);
				}
			}
			countryRepository.saveAll(countryList);

			System.out.println("Alle Länder wurden erfolgreich erstellt.");

		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Bean
	public Boolean saveAllFlags(){
		String folderPath = "./src/main/resources/40x301 flaggen";
		List<String> uppercaseFileNames = new ArrayList<>();
		File folder = new File(folderPath);
		List<Flag> flagList = new ArrayList<>();
		List<Flag> flagListNotEqual = new ArrayList<>();
		if (folder.exists() && folder.isDirectory()) {
			for (File file : folder.listFiles()) {
				if (file.isFile() && file.getName().toLowerCase().endsWith(".png")) {
					try {
						String uppercaseFileName = file.getName().toUpperCase().replaceFirst("[.][^.]+$", "");
						uppercaseFileNames.add(uppercaseFileName);
						System.out.println(uppercaseFileName);
						byte[] fileContent = readFileToByteArray(file);
						Flag flag = new Flag(uppercaseFileName, fileContent);
						if (isFileNameValid(flag)) {
							flagList.add(flag);
						}else {
							flagListNotEqual.add(flag);
						}


					} catch (IOException e) {
						System.err.println("Fehler beim Lesen der Datei: " + file.getName());
						e.printStackTrace();
					}
				}
			}

			flagRepository.saveAll(flagList);

			System.out.println("PNG-Dateien in Großbuchstaben:");
			for (String fileName : uppercaseFileNames) {
				System.out.println(fileName);
			}
		} else {
			System.err.println("Der angegebene Pfad ist kein gültiges Verzeichnis.");
		}

		System.out.println("===================");
		for (Flag filename:flagListNotEqual) {
			System.out.println(filename.getImageName());
		}
		return true;
	}

	@Bean
	public Boolean removeCountrysWithoutFlag() {
		List<Country> countryList = countryRepository.findAll();
		List<Country> countries = new ArrayList<>();
		for (Country country : countryList) {
			if (country.getFlag() == null) {
				countries.add(country);
			}
		}
		for (Country country:countries) {
			System.out.println(country.getCountryName());
		}
		return true;
	}

	private static Boolean isFileNameValid1(String countryCode) {
		List<Flag> flagList = flagRepository.findAll();
		for (Flag flag : flagList) {
			if (flag.getImageName().equals(countryCode)) {
				return true;
			}
		}
		return false;
	}

	private static Boolean isFileNameValid(Flag flag) {
		List<Country> countryList = countryRepository.findAll();
		for (Country country:countryList) {
			if (country.getCountryCode().equals(flag.getImageName())) {
				return true;
			}
		}

		return false;
	}



	private static byte[] readFileToByteArray(File file) throws IOException {
		try (FileInputStream fis = new FileInputStream(file)) {
			byte[] data = new byte[(int) file.length()];
			fis.read(data);
			return data;
		}
	}



}
