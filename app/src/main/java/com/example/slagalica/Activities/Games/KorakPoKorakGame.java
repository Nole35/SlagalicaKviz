package com.example.slagalica.Activities.Games;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.slagalica.Activities.MainMenu.MainActivity;
import com.example.slagalica.Activities.MainMenu.SinglePlayerActivity;
import com.example.slagalica.Connection.ConnectionController;
import com.example.slagalica.Controllers.AsocijacijeController;
import com.example.slagalica.Controllers.KorakPoKorakKontorler;
import com.example.slagalica.HelperClasses.Association;
import com.example.slagalica.HelperClasses.Column;
import com.example.slagalica.HelperClasses.DialogBuilder;
import com.example.slagalica.HelperClasses.KorakPoKorak;
import com.example.slagalica.HelperClasses.TypeOfGame;
import com.example.slagalica.R;

import java.util.ArrayList;

public class KorakPoKorakGame extends AppCompatActivity implements GameInterface {
    private static final String[] Columns = {"A"};
    private static final String[] ColumnsCyrilic = {"А"};
    private static final String[] ColumnNumbers = {"1", "2", "3", "4", "5", "6", "7"};
    public static final String gameName = "Game_1";
    private KorakPoKorak association;
    private int sumOfPoints = 0;
    private EditText mainSolution;
    private ArrayList<Button> buttons;
    private CountDownTimer timer;
    private int timeClock;
    private int clockInterval;
    private TextView textViewTimer;
    private int pointsField;
    private int pointsColumn;
    private int pointsMain;
    private final int gameId = 5;
    private KorakPoKorakKontorler controller;
    private ConnectionController connectionController;
    private boolean[] solved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_korak_po_korak);
        initializeGame();
    }

    public void initializeGame() {
        connectionController = ConnectionController.getInstance();
        controller = KorakPoKorakKontorler.getInstance();
        ConstraintLayout constraintLayout = findViewById(R.id.layoutKorakPoKorak);
        SharedPreferences preferences = getSharedPreferences(MainActivity.settingsPreferencesKey, MODE_PRIVATE);
        String color = preferences.getString(getResources().getString(R.string.backgroundColorKey), getResources().getString(R.string.color1));
        constraintLayout.setBackgroundColor(Color.parseColor(color));
        pointsField = getResources().getInteger(R.integer.pointsFieldGame6);
        pointsColumn = getResources().getInteger(R.integer.pointsColumnGame6);
        pointsMain = getResources().getInteger(R.integer.pointsMainSolutionGame6);
        clockInterval = getResources().getInteger(R.integer.clockInterval);
        timeClock = getResources().getInteger(R.integer.timeGame6);
        solved = new boolean[1];
        buttons = new ArrayList<>();
        setButtons();
        setEditTexts();
        textViewTimer = findViewById(R.id.textViewTimer);
        association = controller.getAssociation(SinglePlayerActivity.game.getKorakPoKOrak());
        timer = new CountDownTimer(timeClock * 1000, clockInterval * 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                KorakPoKorakGame.this.timeClock--;
                KorakPoKorakGame.this.textViewTimer.setText(String.valueOf(KorakPoKorakGame.this.timeClock));
            }

            @Override
            public void onFinish() {
                KorakPoKorakGame.this.endGame(true);
            }
        };
        timer.start();
    }

    @Override
    public void endGame(boolean timeOut) {
        timer.cancel();
        if (timeOut) {
            solveMain(true);
        }
    }

    private void solveColumn(int column, boolean timeOut) {
//        if (solved[column]) {
//            return;
//        }
//        solved[column] = true;
        ArrayList<String> columnWords = association.getColumn().getFields();
        for (int i = 0; i < columnWords.size(); i++) {
            Button matched = buttons.get(i);
            if (matched.isEnabled() && !timeOut) {
                SharedPreferences preferences = getSharedPreferences(MainActivity.historyPointsPreferencesKey[SinglePlayerActivity.typeOfGame.getValue()], MODE_PRIVATE);
                int points = preferences.getInt(gameName + getResources().getString(R.string.Points), 0);
                points += pointsField;
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt(gameName + getResources().getString(R.string.Points), points);
                editor.apply();
                if (SinglePlayerActivity.typeOfGame == TypeOfGame.MultiPlayer) {
                    String pointsDatabase = getResources().getString(R.string.pointsFirebase) + MainActivity.typeOfPlayer;
                    connectionController.updatePoints(this, pointsDatabase, points, String.valueOf(gameId));
                }
                sumOfPoints += pointsField;
            }
            if (!timeOut) {
                matched.setTextColor(Color.parseColor(getResources().getString(R.string.greenColor)));
            } else {
                matched.setTextColor(Color.parseColor(getResources().getString(R.string.redColor)));
            }
            matched.setText(columnWords.get(i));
            matched.setEnabled(false);
        }
        EditText editText = findViewById(R.id.editText_MainSolution);
        editText.setText(association.getMainSolution());
        if (editText.isEnabled() && !timeOut) {
            SharedPreferences preferences = getSharedPreferences(MainActivity.historyPointsPreferencesKey[SinglePlayerActivity.typeOfGame.getValue()], MODE_PRIVATE);
            int points = preferences.getInt(gameName + getResources().getString(R.string.Points), 0);
            points += pointsColumn;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(gameName + getResources().getString(R.string.Points), points);
            editor.apply();
            if (SinglePlayerActivity.typeOfGame == TypeOfGame.MultiPlayer) {
                String pointsDatabase = getResources().getString(R.string.pointsFirebase) + MainActivity.typeOfPlayer;
                connectionController.updatePoints(this, pointsDatabase, points, String.valueOf(gameId));
            }
            sumOfPoints += pointsColumn;

        }
        if (editText.isEnabled()) {
            lockEditText(editText, timeOut);
        }

    }

    private void solveMain(boolean timeOut) {
        SharedPreferences preferences = getSharedPreferences(MainActivity.historyPointsPreferencesKey[SinglePlayerActivity.typeOfGame.getValue()], MODE_PRIVATE);
        if (!timeOut) {
            int points = preferences.getInt(gameName + getResources().getString(R.string.Points), 0);
            points += pointsMain;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(gameName + getResources().getString(R.string.Points), points);
            editor.apply();
            if (SinglePlayerActivity.typeOfGame == TypeOfGame.MultiPlayer) {
                String pointsDatabase = getResources().getString(R.string.pointsFirebase) + MainActivity.typeOfPlayer;
                connectionController.updatePoints(this, pointsDatabase, points, String.valueOf(gameId));
            }
        }

//        for (int i = 0; i < association.getColumnsList().size(); i++) {
//            solveColumn(i, timeOut);
//        }
        mainSolution.setText(association.getMainSolution());
        lockEditText(mainSolution, timeOut);
        String message = getResources().getString(R.string.gameOverMessage) + pointsMain + getResources().getString(R.string.points1);
        if (timeOut) {
            message += " \n " + getResources().getString(R.string.finalSolutionGame6) + association.getMainSolution();
        }
        DialogBuilder
                .createDialogForGame(message, this)
                .show();

    }

    private void setEditTexts() {

        mainSolution = findViewById(R.id.editText_MainSolution);
        mainSolution.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String text = association.getMainSolution();
                    boolean matchedWords;
                    try {
                        matchedWords = controller.matchWords(text, mainSolution.getText().toString(), false);
                    } catch (Exception e) {
                        matchedWords = false;
                    }
                    try {
                        matchedWords = matchedWords || controller.matchWords(text, mainSolution.getText().toString(), true);
                    } catch (Exception e) {
                        // Just handle
                    }
                    if (matchedWords) {
                        KorakPoKorakGame.this.solveMain(false);
                        mainSolution.setTextColor(Color.parseColor(getResources().getString(R.string.greenColor)));

                    } else {
                        mainSolution.setTextColor(Color.parseColor(getResources().getString(R.string.redColor)));
                    }
                    if (mainSolution.isFocused()) {
                        hideSoftKeyboard(KorakPoKorakGame.this);
                    }


                }
                return false;
            }
        });
    }



    private void setButtons() {
        Button button1 = findViewById(R.id.game1);
        Button button2 = findViewById(R.id.game2);
        Button button3 = findViewById(R.id.game3);
        Button button4 = findViewById(R.id.game4);
        Button button5 = findViewById(R.id.game5);
        Button button6 = findViewById(R.id.game6);
        Button button7 = findViewById(R.id.game7);

        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        buttons.add(button5);
        buttons.add(button6);
        buttons.add(button7);
    }

    public void openField(View view) {
        int posI = -1;
        Button clicked = (Button) view;

        // Find the position of the clicked button in the buttons list
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i) == clicked) {
                posI = i;
                if(i != 0){
                    pointsMain = 20 - 2*i;
                }
                break;
            }
        }

        // Find the position of the button within its associated column
        if (posI != -1) {
            KorakPoKorak association = controller.getAssociation(posI);
            if (association != null) {
                Column column = association.getColumn();
                clicked.setText(column.getSolution());
                clicked.setEnabled(false);
                if(posI != 6) {
                    buttons.get(posI + 1).setEnabled(true);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        DialogBuilder.createAlertDialogForExit(this, timer).show();
    }

    private void lockEditText(EditText editText, boolean timeout) {
        if (timeout) {
            editText.setEnabled(false);
            editText.setTextColor(Color.parseColor(getResources().getString(R.string.redColor)));
        } else {
            editText.setEnabled(false);
            editText.setTextColor(Color.parseColor(getResources().getString(R.string.greenColor)));
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        View focusedView = activity.getCurrentFocus();
        if (focusedView != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }
}
