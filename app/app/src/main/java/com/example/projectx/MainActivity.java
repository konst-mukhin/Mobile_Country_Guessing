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

public class MainActivity extends AppCompatActivity {

    private ImageView flagImage;
    private float initialY;
    private int initialWidth;
    private int initialHeight;
    private Button option1, option2, option3, option4, restartButton;
    private HashMap<String, String> countriesMap = new HashMap<>();
    private String correctAnswer;
    private Handler handler = new Handler();
    private boolean isDoubleClicked = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flagImage = findViewById(R.id.flagImage);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        restartButton = findViewById(R.id.restartButton);

        loadCountries();

        flagImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialY = event.getRawY();
                        initialWidth = flagImage.getLayoutParams().width;
                        initialHeight = flagImage.getLayoutParams().height;
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        float deltaY = initialY - event.getRawY();

                        int newHeight = (int) (initialHeight + deltaY);
                        int newWidth = (int) (initialWidth + (deltaY * (initialWidth / (float) initialHeight)));

                        if (newHeight > 100 && newHeight < 800) {
                            flagImage.getLayoutParams().height = newHeight;
                            flagImage.getLayoutParams().width = newWidth;
                            flagImage.requestLayout();
                        }
                        return true;

                    case MotionEvent.ACTION_UP:
                        return true;
                }
                return false;
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

    private void loadCountries() {
        countriesMap.put("Afghanistan", "AF");
        countriesMap.put("Albania", "AL");
        countriesMap.put("Algeria", "DZ");
        countriesMap.put("Andorra", "AD");
        countriesMap.put("Angola", "AO");
        countriesMap.put("Argentina", "AR");
        countriesMap.put("Armenia", "AM");
        countriesMap.put("Australia", "AU");
        countriesMap.put("Austria", "AT");
        countriesMap.put("Azerbaijan", "AZ");
        countriesMap.put("Bahamas", "BS");
        countriesMap.put("Bahrain", "BH");
        countriesMap.put("Bangladesh", "BD");
        countriesMap.put("Barbados", "BB");
        countriesMap.put("Belarus", "BY");
        countriesMap.put("Belgium", "BE");
        countriesMap.put("Belize", "BZ");
        countriesMap.put("Benin", "BJ");
        countriesMap.put("Bhutan", "BT");
        countriesMap.put("Bolivia", "BO");
        countriesMap.put("Bosnia and Herzegovina", "BA");
        countriesMap.put("Botswana", "BW");
        countriesMap.put("Brazil", "BR");
        countriesMap.put("Brunei", "BN");
        countriesMap.put("Bulgaria", "BG");
        countriesMap.put("Burkina Faso", "BF");
        countriesMap.put("Burundi", "BI");
        countriesMap.put("Cabo Verde", "CV");
        countriesMap.put("Cambodia", "KH");
        countriesMap.put("Cameroon", "CM");
        countriesMap.put("Canada", "CA");
        countriesMap.put("Central African Republic", "CF");
        countriesMap.put("Chad", "TD");
        countriesMap.put("Chile", "CL");
        countriesMap.put("China", "CN");
        countriesMap.put("Colombia", "CO");
        countriesMap.put("Comoros", "KM");
        countriesMap.put("Congo (Congo-Brazzaville)", "CG");
        countriesMap.put("Congo (DR Congo)", "CD");
        countriesMap.put("Costa Rica", "CR");
        countriesMap.put("Croatia", "HR");
        countriesMap.put("Cuba", "CU");
        countriesMap.put("Cyprus", "CY");
        countriesMap.put("Czechia (Czech Republic)", "CZ");
        countriesMap.put("Denmark", "DK");
        countriesMap.put("Djibouti", "DJ");
        countriesMap.put("Dominica", "DM");
        countriesMap.put("Dominican Republic", "DO");
        countriesMap.put("Ecuador", "EC");
        countriesMap.put("Egypt", "EG");
        countriesMap.put("El Salvador", "SV");
        countriesMap.put("Equatorial Guinea", "GQ");
        countriesMap.put("Eritrea", "ER");
        countriesMap.put("Estonia", "EE");
        countriesMap.put("Eswatini (Swaziland)", "SZ");
        countriesMap.put("Ethiopia", "ET");
        countriesMap.put("Fiji", "FJ");
        countriesMap.put("Finland", "FI");
        countriesMap.put("France", "FR");
        countriesMap.put("Gabon", "GA");
        countriesMap.put("Gambia", "GM");
        countriesMap.put("Georgia", "GE");
        countriesMap.put("Germany", "DE");
        countriesMap.put("Ghana", "GH");
        countriesMap.put("Greece", "GR");
        countriesMap.put("Grenada", "GD");
        countriesMap.put("Guatemala", "GT");
        countriesMap.put("Guinea", "GN");
        countriesMap.put("Guinea-Bissau", "GW");
        countriesMap.put("Guyana", "GY");
        countriesMap.put("Haiti", "HT");
        countriesMap.put("Honduras", "HN");
        countriesMap.put("Hungary", "HU");
        countriesMap.put("Iceland", "IS");
        countriesMap.put("India", "IN");
        countriesMap.put("Indonesia", "ID");
        countriesMap.put("Iran", "IR");
        countriesMap.put("Iraq", "IQ");
        countriesMap.put("Ireland", "IE");
        countriesMap.put("Israel", "IL");
        countriesMap.put("Italy", "IT");
        countriesMap.put("Jamaica", "JM");
        countriesMap.put("Japan", "JP");
        countriesMap.put("Jordan", "JO");
        countriesMap.put("Kazakhstan", "KZ");
        countriesMap.put("Kenya", "KE");
        countriesMap.put("Kiribati", "KI");
        countriesMap.put("Korea (North)", "KP");
        countriesMap.put("Korea (South)", "KR");
        countriesMap.put("Kuwait", "KW");
        countriesMap.put("Kyrgyzstan", "KG");
        countriesMap.put("Laos", "LA");
        countriesMap.put("Latvia", "LV");
        countriesMap.put("Lebanon", "LB");
        countriesMap.put("Lesotho", "LS");
        countriesMap.put("Liberia", "LR");
        countriesMap.put("Libya", "LY");
        countriesMap.put("Liechtenstein", "LI");
        countriesMap.put("Lithuania", "LT");
        countriesMap.put("Luxembourg", "LU");
        countriesMap.put("Madagascar", "MG");
        countriesMap.put("Malawi", "MW");
        countriesMap.put("Malaysia", "MY");
        countriesMap.put("Maldives", "MV");
        countriesMap.put("Mali", "ML");
        countriesMap.put("Malta", "MT");
        countriesMap.put("Marshall Islands", "MH");
        countriesMap.put("Mauritania", "MR");
        countriesMap.put("Mauritius", "MU");
        countriesMap.put("Mexico", "MX");
        countriesMap.put("Micronesia", "FM");
        countriesMap.put("Moldova", "MD");
        countriesMap.put("Monaco", "MC");
        countriesMap.put("Mongolia", "MN");
        countriesMap.put("Montenegro", "ME");
        countriesMap.put("Morocco", "MA");
        countriesMap.put("Mozambique", "MZ");
        countriesMap.put("Myanmar (Burma)", "MM");
        countriesMap.put("Namibia", "NA");
        countriesMap.put("Nauru", "NR");
        countriesMap.put("Nepal", "NP");
        countriesMap.put("Netherlands", "NL");
        countriesMap.put("New Zealand", "NZ");
        countriesMap.put("Nicaragua", "NI");
        countriesMap.put("Niger", "NE");
        countriesMap.put("Nigeria", "NG");
        countriesMap.put("North Macedonia", "MK");
        countriesMap.put("Norway", "NO");
        countriesMap.put("Oman", "OM");
        countriesMap.put("Pakistan", "PK");
        countriesMap.put("Palau", "PW");
        countriesMap.put("Panama", "PA");
        countriesMap.put("Papua New Guinea", "PG");
        countriesMap.put("Paraguay", "PY");
        countriesMap.put("Peru", "PE");
        countriesMap.put("Philippines", "PH");
        countriesMap.put("Poland", "PL");
        countriesMap.put("Portugal", "PT");
        countriesMap.put("Qatar", "QA");
        countriesMap.put("Romania", "RO");
        countriesMap.put("Russia", "RU");
        countriesMap.put("Rwanda", "RW");
        countriesMap.put("Saint Kitts and Nevis", "KN");
        countriesMap.put("Saint Lucia", "LC");
        countriesMap.put("Saint Vincent and the Grenadines", "VC");
        countriesMap.put("Samoa", "WS");
        countriesMap.put("San Marino", "SM");
        countriesMap.put("Sao Tome and Principe", "ST");
        countriesMap.put("Saudi Arabia", "SA");
        countriesMap.put("Senegal", "SN");
        countriesMap.put("Serbia", "RS");
        countriesMap.put("Seychelles", "SC");
        countriesMap.put("Sierra Leone", "SL");
        countriesMap.put("Singapore", "SG");
        countriesMap.put("Slovakia", "SK");
        countriesMap.put("Slovenia", "SI");
        countriesMap.put("Solomon Islands", "SB");
        countriesMap.put("Somalia", "SO");
        countriesMap.put("South Africa", "ZA");
        countriesMap.put("Spain", "ES");
        countriesMap.put("Sri Lanka", "LK");
        countriesMap.put("Sudan", "SD");
        countriesMap.put("Suriname", "SR");
        countriesMap.put("Sweden", "SE");
        countriesMap.put("Switzerland", "CH");
        countriesMap.put("Syria", "SY");
        countriesMap.put("Taiwan", "TW");
        countriesMap.put("Tajikistan", "TJ");
        countriesMap.put("Tanzania", "TZ");
        countriesMap.put("Thailand", "TH");
        countriesMap.put("Timor-Leste", "TL");
        countriesMap.put("Togo", "TG");
        countriesMap.put("Tonga", "TO");
        countriesMap.put("Trinidad and Tobago", "TT");
        countriesMap.put("Tunisia", "TN");
        countriesMap.put("Turkey", "TR");
        countriesMap.put("Turkmenistan", "TM");
        countriesMap.put("Tuvalu", "TV");
        countriesMap.put("Uganda", "UG");
        countriesMap.put("Ukraine", "UA");
        countriesMap.put("United Arab Emirates", "AE");
        countriesMap.put("United Kingdom", "GB");
        countriesMap.put("United States", "US");
        countriesMap.put("Uruguay", "UY");
        countriesMap.put("Uzbekistan", "UZ");
        countriesMap.put("Vanuatu", "VU");
        countriesMap.put("Venezuela", "VE");
        countriesMap.put("Vietnam", "VN");
        countriesMap.put("Yemen", "YE");
        countriesMap.put("Zambia", "ZM");
        countriesMap.put("Zimbabwe", "ZW");
    }

    private void startGame() throws JSONException {
            OkHttpClient client = new OkHttpClient();
            String tokenJsonString = Token.token;
            String tokenValue = "";
            JSONObject jsonObject = new JSONObject(tokenJsonString);
            tokenValue = jsonObject.getString("token");
            Log.d("ABOBA", "TOKEN " + Token.token);
            Request request = new Request.Builder()
                    .url("http://172.20.10.2:8080/api/v1/demo-controller")
                    .addHeader("Authorization", "Bearer " + tokenValue)
                    .get()
                    .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.e("ABOBA", "Request failed: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            Log.e("ABOBA", "Unexpected code: " + response);
                            runOnUiThread(() -> {
                                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                                startActivity(intent);
                                finish();
                            });
                            return;
                        }
                        assert response.body() != null;
                        Log.d("ABOBA", response.body().string());
                    }
                });

        List<String> countryNames = new ArrayList<>(countriesMap.keySet());
        int randomIndex = new Random().nextInt(countryNames.size());
        String selectedCountry = countryNames.get(randomIndex);
        correctAnswer = selectedCountry;

        String flagCode = countriesMap.get(selectedCountry);
        String flagUrl = "https://flagsapi.com/" + flagCode + "/flat/64.png";
        Log.d("Flag URL", flagUrl);

        Glide.with(this)
                .load(flagUrl)
                .into(flagImage);

        setAnswerOptions(countryNames, selectedCountry);

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
                    Toast.makeText(MainActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                    try {
                        startGame();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Wrong! Try again.", Toast.LENGTH_SHORT).show();
                }
            }
        };

        option1.setOnClickListener(answerListener);
        option2.setOnClickListener(answerListener);
        option3.setOnClickListener(answerListener);
        option4.setOnClickListener(answerListener);
    }
}
