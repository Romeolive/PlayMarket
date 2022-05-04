package com.example.playmarket;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.games.PlayGames;
import com.google.android.gms.games.PlayGamesSdk;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button sign,leaders,satisfy;
    TextView score;
    private int record = 0;
    private final int RC_LEADERBOARD_UI = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sign = findViewById(R.id.sign);
        leaders = findViewById(R.id.leaders);
        satisfy = findViewById(R.id.sutisfy_me);
        score = findViewById(R.id.record_text);

        satisfy.setOnClickListener(view -> clickReward());
        sign.setOnClickListener(view -> SignIn());
        leaders.setOnClickListener(view -> {
            showLeaderBoards();
        });
        PlayGames.getGamesSignInClient(this).isAuthenticated().addOnCompleteListener(task -> {
            boolean allIsGood =
                    task.isSuccessful() && task.getResult().isAuthenticated();
            if (allIsGood){
                Toast.makeText(this, R.string.succesful, Toast.LENGTH_LONG).show();
            }
            else{
                //если неуспешно

            }
        });

    }
    private void SignIn() {
        PlayGames.getGamesSignInClient(this).signIn().addOnCompleteListener(task -> {
            Toast.makeText(this,"Все плохо!",Toast.LENGTH_LONG).show();
        });
    }
    private void clickReward(){

        score.setText(String.valueOf(++record));
        PlayGames.getLeaderboardsClient(this).submitScore(getString(R.string.leader_board),record);
    }
    private void showLeaderBoards(){
        PlayGames.getLeaderboardsClient(this)
                .getLeaderboardIntent(getString(R.string.leader_board))
                .addOnSuccessListener(intent -> startActivityForResult(intent,RC_LEADERBOARD_UI));
    }
}