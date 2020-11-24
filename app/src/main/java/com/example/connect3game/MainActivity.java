package com.example.connect3game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    //yellow:0      Red:1       Empty:2
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};
    int activePlayer = 0;
    boolean gameActive = true;
    String winner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        TextView turnTextView = (TextView) findViewById(R.id.turnTextView);

        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        if (gameState[tappedCounter] == 2 && gameActive) {
            gameState[tappedCounter] = activePlayer;
            counter.setTranslationY(-1500);

            if (activePlayer==0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
                turnTextView.setText("Red Turn");
            }
            else if(activePlayer==1) {
                counter.setImageResource(R.drawable.red);
                activePlayer=0;
                turnTextView.setText("Yellow Turn");
            }

            counter.animate().translationYBy(1500).setDuration(300);

            TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
            Button playAgainButton = (Button) findViewById(R.id.playAgainButton);

            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]]==gameState[winningPosition[1]]
                        && gameState[winningPosition[1]]==gameState[winningPosition[2]]
                        && gameState[winningPosition[0]]!=2) {
                    //Someone Won!
                    turnTextView.setVisibility(View.INVISIBLE);
                    gameActive = false;

                    if (activePlayer == 1){
                        winner = "Yellow";
                    }
                    else if(activePlayer == 0) {
                        winner = "Red";
                    }

                    winnerTextView.setText(winner + " has won!");
                    winnerTextView.setVisibility(View.VISIBLE);

                    playAgainButton.setVisibility(View.VISIBLE);
                }
                else if(gameState[winningPosition[0]]!= gameState[winningPosition[1]]
                        && gameState[winningPosition[1]]!=gameState[winningPosition[2]]
                        && gameState[winningPosition[0]]!=2)
            }
        }
    }

    public void playAgain(View view) {
        TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
        TextView turnTextView = (TextView) findViewById(R.id.turnTextView);

        winnerTextView.setVisibility(View.INVISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);

        GridLayout gameGridLayout = (GridLayout) findViewById(R.id.gameGridLayout);
        for (int i=0; i<gameGridLayout.getChildCount(); i++) {
            ImageView counter = (ImageView) gameGridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }

        for (int i=0; i<gameState.length; i++) {
            gameState[i] = 2;
        }
        activePlayer = 0;
        turnTextView.setVisibility(View.VISIBLE);
        gameActive = true;
    }
}