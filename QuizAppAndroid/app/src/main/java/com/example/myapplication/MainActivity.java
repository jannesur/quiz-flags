package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

    public class MainActivity extends AppCompatActivity {
        private static final String BASE_URL = "http:/localhost:8080/";
        private List<Country> countries;
        private int currentQuestionIndex = 0;
        private int score = 0;
        private ImageView flagImageView;
        private Button[] optionButtons;
        private static final int TOTAL_QUESTIONS = 10;

        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            flagImageView = findViewById(R.id.flagImageView);
            optionButtons = new Button[]{
                    findViewById(R.id.optionButton1),
                    findViewById(R.id.optionButton2),
                    findViewById(R.id.optionButton3),
                    findViewById(R.id.optionButton4)
            };

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            CountryService countryAPI = retrofit.create(CountryService.class);
            Call<List<Country>> call = countryAPI.get4RandomCountries();
            call.enqueue(new Callback<List<Country>>() {
                @Override
                public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                    if (!response.isSuccessful()) {
                        Log.e("MainActivity", "Code: " + response.code());
                        return;
                    }

                    countries = response.body();
                    showQuestion();
                }

                @Override
                public void onFailure(Call<List<Country>> call, Throwable t) {
                    Log.e("MainActivity", "Error: " + t.getMessage());
                }
            });
        }

        private void showQuestion() {
            if (currentQuestionIndex < TOTAL_QUESTIONS) {
                Country currentCountry = countries.get(currentQuestionIndex);
                Glide.with(this).load(currentCountry.getFlag().getImageName()).into(flagImageView);

                int correctAnswerIndex = new Random().nextInt(optionButtons.length);
                optionButtons[correctAnswerIndex].setText(currentCountry.getCountryName());
                optionButtons[correctAnswerIndex].setOnClickListener(v -> {
                    Toast.makeText(MainActivity.this, "Richtig!", Toast.LENGTH_SHORT).show();
                    score++;
                    goToNextQuestion();
                });

                List<Country> wrongAnswers = countries.subList(0, countries.size() - 1);
                Collections.shuffle(wrongAnswers);
                for (int i = 0; i < optionButtons.length; i++) {
                    if (i != correctAnswerIndex) {
                        optionButtons[i].setText(wrongAnswers.get(i).getCountryName());
                        optionButtons[i].setOnClickListener(v -> {
                            Toast.makeText(MainActivity.this, "Falsch!", Toast.LENGTH_SHORT).show();
                        });
                    }
                }
            } else {
                Toast.makeText(MainActivity.this, "Spiel beendet! Score: " + score + "/" + TOTAL_QUESTIONS, Toast.LENGTH_SHORT).show();
                // Hier können Sie weitere Aktionen für das Spielende ergänzen
            }
        }

        private void goToNextQuestion() {
            currentQuestionIndex++;
            showQuestion();
        }
    }