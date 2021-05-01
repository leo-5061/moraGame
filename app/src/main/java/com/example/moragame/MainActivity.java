package com.example.moragame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moragame.objects.Computer;
import com.example.moragame.objects.Player;

import java.util.ConcurrentModificationException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Computer.onComputerCompletedListener {
    private ImageButton scissorsIbn;
    private ImageButton rockIbn;
    private ImageButton paperIbn;
    private Button startBtn;
    private Button quitBtn;
    private TextView counterTxt;
    private ImageView computerImg;
    private ImageView playerImg;
    private Player player;
    private Computer computer;
    private boolean isPlayerRound;
    private long gameRoundSeconds;
    private int gameRound;
    private boolean isWin;
    private int score;
    private static final String TAG = "MainActivity";
    public static final int EVEN = 0;
    public static final int PLAYER_WIN = 1;
    public static final int COMPUTER_WIN = 2;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        player = new Player();
        playerImg.setVisibility(View.INVISIBLE);
        computer = new Computer();
        computer.setOnComputerCompletedListener(this);

        computerImg.setImageResource(R.drawable.scissors);


    }

    public void findViews() {
        counterTxt = findViewById(R.id.counter_txt);
        scissorsIbn = findViewById(R.id.scissors_ibn);
        rockIbn = findViewById(R.id.rock_ibn);
        paperIbn = findViewById(R.id.paper_ibn);
        startBtn = findViewById(R.id.start_btn);
        quitBtn = findViewById(R.id.quit_btn);
        computerImg = findViewById(R.id.computer_img);
        playerImg = findViewById(R.id.player_img);
        View[] views = {scissorsIbn, rockIbn, paperIbn, startBtn, quitBtn};
        for (View v : views) {
            v.setOnClickListener(this);
        }
    }

    public void initGame() {
        if (timer != null) {
            timer.setAlive(false);
        }
        timer = new Timer(1000, true, new Timer.OnTimerListener() {
            @Override
            public void OnTick(long milliseconds) {

                sendTimerMessage(milliseconds, 3);
            }

            @Override
            public void OnTime(long milliseconds) {
                sendTimerMessage(milliseconds, 4);
            }
        });
        timer.setStepMilliseconds(50);
        timer.start();

        isPlayerRound = false;
        computer.AI();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scissors_ibn:
                if (isPlayerRound) {
                    isPlayerRound = false;
                    timer.setStop(true);
                    player.setMora(Player.SCISSORS);
                    playerImg.setImageResource(R.drawable.scissors);
                    playerImg.setVisibility(View.VISIBLE);
                    //Toast.makeText(this,getResources().getString(R.string.scissors),Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onClick: " + getResources().getString(R.string.scissors));
                    checkGameState();
                }
                break;
            case R.id.rock_ibn:
                if (isPlayerRound) {
                    isPlayerRound = false;
                    timer.setStop(true);
                    player.setMora(Player.ROCK);
                    playerImg.setImageResource(R.drawable.rock);
                    playerImg.setVisibility(View.VISIBLE);
                    //Toast.makeText(this, getResources().getString(R.string.rock), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onClick: " + getResources().getString(R.string.rock));
                    checkGameState();
                }
                break;
            case R.id.paper_ibn:
                if (isPlayerRound) {
                    isPlayerRound = false;
                    timer.setStop(true);
                    player.setMora(Player.PAPER);
                    playerImg.setImageResource(R.drawable.paper);
                    playerImg.setVisibility(View.VISIBLE);
                    //Toast.makeText(this,getResources().getString(R.string.paper),Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onClick: " + getResources().getString(R.string.paper));
                    checkGameState();
                }
                break;
            case R.id.start_btn:
                initGame();
                //Toast.makeText(this,getResources().getString(R.string.start),Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: " + getResources().getString(R.string.start));
                break;
            case R.id.quit_btn:
                //Toast.makeText(this,getResources().getString(R.string.quit),Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: " + getResources().getString(R.string.quit));
                break;
        }
    }

    @Override
    public void complete() {
        Log.d(TAG, "complete: 123123");
        int mora = computer.getMora();
        handler.sendEmptyMessage(mora);
        isPlayerRound = true;

//        switch (getWinState(player.getMora(),computer.getMora())){
//            case 0:
//                Toast.makeText(this,"even",Toast.LENGTH_SHORT).show();
//                break;
//            case 1:
//                Toast.makeText(this,"win",Toast.LENGTH_SHORT).show();
//                break;
//            case 2:
//                Toast.makeText(this, "lose", Toast.LENGTH_SHORT).show();
//                break;
//        }

//        if (mora==Player.SCISSORS ) {
//            computerImg.setImageResource(R.drawable.scissors);
//        } else if (mora==Player.ROCK) {
//            computerImg.setImageResource(R.drawable.rock);
//        }else if(mora==Player.PAPER){
//            computerImg.setImageResource(R.drawable.paper);
//        }

    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    computerImg.setImageResource(R.drawable.scissors);
                    break;
                case 1:
                    computerImg.setImageResource(R.drawable.rock);
                    break;
                case 2:
                    computerImg.setImageResource(R.drawable.paper);
                    break;
                case 3:
                    counterTxt.setText((String) msg.obj);
                    break;
                case 4:
                    isPlayerRound = false;
                    timer.setStop(true);
                    player.setMora(Player.READY);
                    counterTxt.setText((String) msg.obj);
                    checkGameState();
                    break;

            }
        }
    };

    public int getWinState(int playerMora, int computerMora) {
        Log.d(TAG, "getWinState: 456456");
        if (playerMora == Player.READY) {
            return COMPUTER_WIN;
        }
        if (playerMora == computerMora) {
            return EVEN;
        }
        if (playerMora == Player.SCISSORS && computerMora == Player.PAPER) {
            return PLAYER_WIN;
        } else if (playerMora == Player.PAPER && computerMora == Player.SCISSORS) {
            return COMPUTER_WIN;
        }
        if (playerMora > computerMora) {
            return PLAYER_WIN;
        } else {
            return COMPUTER_WIN;
        }

    }

    public void checkGameState() {
        int state = getWinState(player.getMora(), computer.getMora());
        switch (state) {
            case 0:
                Toast.makeText(this, "even", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this, "win", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this, "lose", Toast.LENGTH_SHORT).show();
                break;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                NextRound();
            }
        }).start();
    }

    public void sendTimerMessage(long milliseconds, int what) {
        int seconds = (int) (milliseconds / 1000);
        milliseconds = milliseconds % 1000;
        Message message = new Message();
        message.what = what;
        message.obj = String.format("%d:%03d", seconds, (int) milliseconds);
        handler.sendMessage(message);
    }

    public void NextRound() {
        if (isWin) {
            gameRoundSeconds = gameRoundSeconds < 300 ? 300 : gameRoundSeconds - (gameRound / 10) * 10;
            timer.setTargetMilliseconds(gameRoundSeconds);
        }
        gameRound+=1;
        isPlayerRound=false;
        playerImg.setVisibility(View.INVISIBLE);
        isWin=false;
        timer.reset();
        computer.AI();
    }

}