package com.example.projectx;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CapitalActivity extends AppCompatActivity {

    private TextView capitalName;
    private float initialY;
    private int initialWidth;
    private int initialHeight;
    private Button option1, option2, option3, option4, restartButton, listButton, flagModeButton;
    private HashMap<String, String> capitalsMap = new HashMap<>();
    private String correctAnswer;
    private Handler handler = new Handler();
    private boolean isDoubleClicked = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capital);

        capitalName = findViewById(R.id.capitalName);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        restartButton = findViewById(R.id.restartButton);
        listButton = findViewById(R.id.listButton);
        flagModeButton = findViewById(R.id.flagsModeButton);

        loadCapitals();

        NativeLib nativeLib = new NativeLib();
        String capital = nativeLib.getCountryCapital("France");
        System.out.println("Capital: " + capital);


        capitalName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialY = event.getRawY();
                        initialWidth = capitalName.getLayoutParams().width;
                        initialHeight = capitalName.getLayoutParams().height;
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        float deltaY = initialY - event.getRawY();

                        int newHeight = (int) (initialHeight + deltaY);
                        int newWidth = (int) (initialWidth + (deltaY * (initialWidth / (float) initialHeight)));

                        if (newHeight > 100 && newHeight < 800) {
                            capitalName.getLayoutParams().height = newHeight;
                            capitalName.getLayoutParams().width = newWidth;
                            capitalName.requestLayout();
                        }
                        return true;

                    case MotionEvent.ACTION_UP:
                        return true;
                }
                return false;
            }
        });

        flagModeButton.setOnClickListener(v -> {
            Intent intent = new Intent(CapitalActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });



        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CapitalActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDoubleClicked) {
                    try {
                        startGame();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    isDoubleClicked = false;
                } else {
                    isDoubleClicked = true;
                    handler.postDelayed(() -> isDoubleClicked = false, 300);
                }
            }
        });

        try {
            startGame();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCapitals() {
        capitalsMap.put("Afghanistan", "Kabul");
        capitalsMap.put("Albania", "Tirana");
        capitalsMap.put("Algeria", "Algiers");
        capitalsMap.put("Andorra", "Andorra la Vella");
        capitalsMap.put("Angola", "Luanda");
        capitalsMap.put("Argentina", "Buenos Aires");
        capitalsMap.put("Armenia", "Yerevan");
        capitalsMap.put("Australia", "Canberra");
        capitalsMap.put("Austria", "Vienna");
        capitalsMap.put("Azerbaijan", "Baku");
        capitalsMap.put("Bahamas", "Nassau");
        capitalsMap.put("Bahrain", "Manama");
        capitalsMap.put("Bangladesh", "Dhaka");
        capitalsMap.put("Barbados", "Bridgetown");
        capitalsMap.put("Belarus", "Minsk");
        capitalsMap.put("Belgium", "Brussels");
        capitalsMap.put("Belize", "Belmopan");
        capitalsMap.put("Benin", "Porto-Novo");
        capitalsMap.put("Bhutan", "Thimphu");
        capitalsMap.put("Bolivia", "Sucre");
        capitalsMap.put("Bosnia and Herzegovina", "Sarajevo");
        capitalsMap.put("Botswana", "Gaborone");
        capitalsMap.put("Brazil", "Brasilia");
        capitalsMap.put("Brunei", "Bandar Seri Begawan");
        capitalsMap.put("Bulgaria", "Sofia");
        capitalsMap.put("Burkina Faso", "Ouagadougou");
        capitalsMap.put("Burundi", "Gitega");
        capitalsMap.put("Cabo Verde", "Praia");
        capitalsMap.put("Cambodia", "Phnom Penh");
        capitalsMap.put("Cameroon", "Yaoundé");
        capitalsMap.put("Canada", "Ottawa");
        capitalsMap.put("Central African Republic", "Bangui");
        capitalsMap.put("Chad", "N'Djamena");
        capitalsMap.put("Chile", "Santiago");
        capitalsMap.put("China", "Beijing");
        capitalsMap.put("Colombia", "Bogotá");
        capitalsMap.put("Comoros", "Moroni");
        capitalsMap.put("Congo (Congo-Brazzaville)", "Brazzaville");
        capitalsMap.put("Congo (DR Congo)", "Kinshasa");
        capitalsMap.put("Costa Rica", "San José");
        capitalsMap.put("Croatia", "Zagreb");
        capitalsMap.put("Cuba", "Havana");
        capitalsMap.put("Cyprus", "Nicosia");
        capitalsMap.put("Czechia (Czech Republic)", "Prague");
        capitalsMap.put("Denmark", "Copenhagen");
        capitalsMap.put("Djibouti", "Djibouti");
        capitalsMap.put("Dominica", "Roseau");
        capitalsMap.put("Dominican Republic", "Santo Domingo");
        capitalsMap.put("Ecuador", "Quito");
        capitalsMap.put("Egypt", "Cairo");
        capitalsMap.put("El Salvador", "San Salvador");
        capitalsMap.put("Equatorial Guinea", "Malabo");
        capitalsMap.put("Eritrea", "Asmara");
        capitalsMap.put("Estonia", "Tallinn");
        capitalsMap.put("Eswatini (Swaziland)", "Mbabane");
        capitalsMap.put("Ethiopia", "Addis Ababa");
        capitalsMap.put("Fiji", "Suva");
        capitalsMap.put("Finland", "Helsinki");
        capitalsMap.put("France", "Paris");
        capitalsMap.put("Gabon", "Libreville");
        capitalsMap.put("Gambia", "Banjul");
        capitalsMap.put("Georgia", "Tbilisi");
        capitalsMap.put("Germany", "Berlin");
        capitalsMap.put("Ghana", "Accra");
        capitalsMap.put("Greece", "Athens");
        capitalsMap.put("Grenada", "St. George's");
        capitalsMap.put("Guatemala", "Guatemala City");
        capitalsMap.put("Guinea", "Conakry");
        capitalsMap.put("Guinea-Bissau", "Bissau");
        capitalsMap.put("Guyana", "Georgetown");
        capitalsMap.put("Haiti", "Port-au-Prince");
        capitalsMap.put("Honduras", "Tegucigalpa");
        capitalsMap.put("Hungary", "Budapest");
        capitalsMap.put("Iceland", "Reykjavik");
        capitalsMap.put("India", "New Delhi");
        capitalsMap.put("Indonesia", "Jakarta");
        capitalsMap.put("Iran", "Tehran");
        capitalsMap.put("Iraq", "Baghdad");
        capitalsMap.put("Ireland", "Dublin");
        capitalsMap.put("Israel", "Jerusalem");
        capitalsMap.put("Italy", "Rome");
        capitalsMap.put("Jamaica", "Kingston");
        capitalsMap.put("Japan", "Tokyo");
        capitalsMap.put("Jordan", "Amman");
        capitalsMap.put("Kazakhstan", "Astana");
        capitalsMap.put("Kenya", "Nairobi");
        capitalsMap.put("Kiribati", "South Tarawa");
        capitalsMap.put("Korea (North)", "Pyongyang");
        capitalsMap.put("Korea (South)", "Seoul");
        capitalsMap.put("Kuwait", "Kuwait City");
        capitalsMap.put("Kyrgyzstan", "Bishkek");
        capitalsMap.put("Laos", "Vientiane");
        capitalsMap.put("Latvia", "Riga");
        capitalsMap.put("Lebanon", "Beirut");
        capitalsMap.put("Lesotho", "Maseru");
        capitalsMap.put("Liberia", "Monrovia");
        capitalsMap.put("Libya", "Tripoli");
        capitalsMap.put("Liechtenstein", "Vaduz");
        capitalsMap.put("Lithuania", "Vilnius");
        capitalsMap.put("Luxembourg", "Luxembourg");
        capitalsMap.put("Madagascar", "Antananarivo");
        capitalsMap.put("Malawi", "Lilongwe");
        capitalsMap.put("Malaysia", "Kuala Lumpur");
        capitalsMap.put("Maldives", "Malé");
        capitalsMap.put("Mali", "Bamako");
        capitalsMap.put("Malta", "Valletta");
        capitalsMap.put("Marshall Islands", "Majuro");
        capitalsMap.put("Mauritania", "Nouakchott");
        capitalsMap.put("Mauritius", "Port Louis");
        capitalsMap.put("Mexico", "Mexico City");
        capitalsMap.put("Micronesia", "Palikir");
        capitalsMap.put("Moldova", "Chisinau");
        capitalsMap.put("Monaco", "Monaco");
        capitalsMap.put("Mongolia", "Ulaanbaatar");
        capitalsMap.put("Montenegro", "Podgorica");
        capitalsMap.put("Morocco", "Rabat");
        capitalsMap.put("Mozambique", "Maputo");
        capitalsMap.put("Myanmar (Burma)", "Naypyidaw");
        capitalsMap.put("Namibia", "Windhoek");
        capitalsMap.put("Nauru", "Yaren");
        capitalsMap.put("Nepal", "Kathmandu");
        capitalsMap.put("Netherlands", "Amsterdam");
        capitalsMap.put("New Zealand", "Wellington");
        capitalsMap.put("Nicaragua", "Managua");
        capitalsMap.put("Niger", "Niamey");
        capitalsMap.put("Nigeria", "Abuja");
        capitalsMap.put("North Macedonia", "Skopje");
        capitalsMap.put("Norway", "Oslo");
        capitalsMap.put("Oman", "Muscat");
        capitalsMap.put("Pakistan", "Islamabad");
        capitalsMap.put("Palau", "Ngerulmud");
        capitalsMap.put("Panama", "Panama City");
        capitalsMap.put("Papua New Guinea", "Port Moresby");
        capitalsMap.put("Paraguay", "Asunción");
        capitalsMap.put("Peru", "Lima");
        capitalsMap.put("Philippines", "Manila");
        capitalsMap.put("Poland", "Warsaw");
        capitalsMap.put("Portugal", "Lisbon");
        capitalsMap.put("Qatar", "Doha");
        capitalsMap.put("Romania", "Bucharest");
        capitalsMap.put("Russia", "Moscow");
        capitalsMap.put("Rwanda", "Kigali");
        capitalsMap.put("Saint Kitts and Nevis", "Basseterre");
        capitalsMap.put("Saint Lucia", "Castries");
        capitalsMap.put("Saint Vincent and the Grenadines", "Kingstown");
        capitalsMap.put("Samoa", "Apia");
        capitalsMap.put("San Marino", "San Marino");
        capitalsMap.put("Sao Tome and Principe", "São Tomé");
        capitalsMap.put("Saudi Arabia", "Riyadh");
        capitalsMap.put("Senegal", "Dakar");
        capitalsMap.put("Serbia", "Belgrade");
        capitalsMap.put("Seychelles", "Victoria");
        capitalsMap.put("Sierra Leone", "Freetown");
        capitalsMap.put("Singapore", "Singapore");
        capitalsMap.put("Slovakia", "Bratislava");
        capitalsMap.put("Slovenia", "Ljubljana");
        capitalsMap.put("Solomon Islands", "Honiara");
        capitalsMap.put("Somalia", "Mogadishu");
        capitalsMap.put("South Africa", "Pretoria");
        capitalsMap.put("Spain", "Madrid");
        capitalsMap.put("Sri Lanka", "Sri Jayawardenepura Kotte");
        capitalsMap.put("Sudan", "Khartoum");
        capitalsMap.put("Suriname", "Paramaribo");
        capitalsMap.put("Sweden", "Stockholm");
        capitalsMap.put("Switzerland", "Bern");
        capitalsMap.put("Syria", "Damascus");
        capitalsMap.put("Tajikistan", "Dushanbe");
        capitalsMap.put("Tanzania", "Dodoma");
        capitalsMap.put("Thailand", "Bangkok");
        capitalsMap.put("Timor-Leste", "Dili");
        capitalsMap.put("Togo", "Lomé");
        capitalsMap.put("Tonga", "Nuku'alofa");
        capitalsMap.put("Trinidad and Tobago", "Port of Spain");
        capitalsMap.put("Tunisia", "Tunis");
        capitalsMap.put("Turkey", "Ankara");
        capitalsMap.put("Turkmenistan", "Ashgabat");
        capitalsMap.put("Tuvalu", "Funafuti");
        capitalsMap.put("Uganda", "Kampala");
        capitalsMap.put("Ukraine", "Kyiv");
        capitalsMap.put("United Arab Emirates", "Abu Dhabi");
        capitalsMap.put("United Kingdom", "London");
        capitalsMap.put("United States", "Washington, D.C.");
        capitalsMap.put("Uruguay", "Montevideo");
        capitalsMap.put("Uzbekistan", "Tashkent");
        capitalsMap.put("Vanuatu", "Port Vila");
        capitalsMap.put("Vatican City", "Vatican City");
        capitalsMap.put("Venezuela", "Caracas");
        capitalsMap.put("Vietnam", "Hanoi");
        capitalsMap.put("Yemen", "Sana'a");
        capitalsMap.put("Zambia", "Lusaka");
        capitalsMap.put("Zimbabwe", "Harare");
    }


    private void startGame() throws JSONException {
        List<String> countryNames = new ArrayList<>(capitalsMap.keySet());
        int randomIndex = new Random().nextInt(countryNames.size());
        String selectedCountry = countryNames.get(randomIndex);
        String selectedCapital = capitalsMap.get(selectedCountry);
        correctAnswer = selectedCountry;

        List<String> options = new ArrayList<>(countryNames);
        options.remove(selectedCountry);
        Collections.shuffle(options);

        List<String> answerOptions = options.subList(0, 3);
        answerOptions.add(selectedCountry);
        Collections.shuffle(answerOptions);

        runOnUiThread(() -> {
            option1.setText(answerOptions.get(0));
            option2.setText(answerOptions.get(1));
            option3.setText(answerOptions.get(2));
            option4.setText(answerOptions.get(3));
            capitalName.setText(selectedCapital);

            Toast.makeText(CapitalActivity.this, "Capital: " + selectedCapital, Toast.LENGTH_LONG).show();
        });

        setAnswerListeners();
    }

    private void setAnswerOptions(List<String> countryNames, String selectedCountry) {
        List<String> options = new ArrayList<>(countryNames);
        options.remove(selectedCountry);
        Collections.shuffle(options);

        List<String> answerOptions = options.subList(0, 3);
        answerOptions.add(selectedCountry);
        Collections.shuffle(answerOptions);

        option1.setText(answerOptions.get(0));
        option2.setText(answerOptions.get(1));
        option3.setText(answerOptions.get(2));
        option4.setText(answerOptions.get(3));
    }

    private void setAnswerListeners() {
        View.OnClickListener answerListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button clickedButton = (Button) v;
                String answer = clickedButton.getText().toString();

                if (answer.equals(correctAnswer)) {
                    Toast.makeText(CapitalActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                    try {
                        startGame();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Toast.makeText(CapitalActivity.this, "Wrong! Try again.", Toast.LENGTH_SHORT).show();
                }
            }
        };

        option1.setOnClickListener(answerListener);
        option2.setOnClickListener(answerListener);
        option3.setOnClickListener(answerListener);
        option4.setOnClickListener(answerListener);
    }
}
