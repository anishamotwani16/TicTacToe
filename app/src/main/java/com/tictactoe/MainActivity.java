package com.tictactoe;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends ActionBarActivity {

    private TextView txtUserTurn, txtCompTurn, txtTurn;
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    private Random random = new Random();
    private Node prevPlay, root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtCompTurn = (TextView) findViewById(R.id.txtCompTurn);
        txtUserTurn = (TextView) findViewById(R.id.txtUserTurn);
        txtTurn = (TextView) findViewById(R.id.txtTurn);
        btn1 = (Button) findViewById(R.id.btn_1);
        btn2 = (Button) findViewById(R.id.btn_2);
        btn3 = (Button) findViewById(R.id.btn_3);
        btn4 = (Button) findViewById(R.id.btn_4);
        btn5 = (Button) findViewById(R.id.btn_5);
        btn6 = (Button) findViewById(R.id.btn_6);
        btn7 = (Button) findViewById(R.id.btn_7);
        btn8 = (Button) findViewById(R.id.btn_8);
        btn9 = (Button) findViewById(R.id.btn_9);
        makeTree();
        newGame(null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void makeTree() {
        root = new Node();
        root.makeTree(1);
        Log.i("Tag2",root.counter+"");
    }

    public void change(View v) {
        Button btn = (Button) v;
        if (btn.getText().toString().equals("")) {
            btn.setText(txtTurn.getText().toString());
            if (txtTurn.getText().toString().equalsIgnoreCase("x"))
                txtTurn.setText("O");
            else txtTurn.setText("X");
            computerTurn();
        } else
            Toast.makeText(this, "This is already taken. Play again", Toast.LENGTH_SHORT).show();
    }

    public void newGame(View v) {
        btn1.setText("");
        btn2.setText("");
        btn3.setText("");
        btn4.setText("");
        btn5.setText("");
        btn6.setText("");
        btn7.setText("");
        btn8.setText("");
        btn9.setText("");
        txtTurn.setText("X");//always X will play first
        enable();
        prevPlay = null;
        //choose who is X n who is O
        boolean rn = random.nextBoolean();//true=user is X, false=comp is X
        if (rn) { //user is X
            txtUserTurn.setText("X");
            txtCompTurn.setText("O");
        } else {
            txtUserTurn.setText("O");
            txtCompTurn.setText("X");
        }
        prevPlay = root;
        if (txtCompTurn.getText().toString().equalsIgnoreCase("x"))
            computerTurn();
    }

    public void computerTurn() {
        Button arr[][] = {{btn1, btn4, btn7}, {btn2, btn5, btn8}, {btn3, btn6, btn9}};
        int current[][] = new int[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String s = arr[i][j].getText().toString();
                if (s.equalsIgnoreCase("x"))
                    current[i][j] = 1;
                else if (s.equalsIgnoreCase("o"))
                    current[i][j] = -1;
                else if (s.equalsIgnoreCase(""))
                    current[i][j] = 0;
            }
        }
        Node currentPlay;
        if (prevPlay == null)
            prevPlay = root;
        currentPlay = prevPlay.find(current);
        if (currentPlay.complete) {
            Toast.makeText(this, "You Win", Toast.LENGTH_LONG).show();
            disable();
        } else if (currentPlay.draw()) {
            Toast.makeText(this, "It's a draw", Toast.LENGTH_LONG).show();
            disable();
        } else {
            prevPlay = currentPlay.best(txtCompTurn.getText().toString().equalsIgnoreCase("x") ? 1 : 0);
            convert(prevPlay);

            if (prevPlay.complete) {
                Toast.makeText(this, "Computer Wins", Toast.LENGTH_LONG).show();
                disable();
            } else if (prevPlay.draw()) {
                Toast.makeText(this, "It's a draw", Toast.LENGTH_LONG).show();
                disable();
            } else {

                if (txtTurn.getText().toString().equalsIgnoreCase("x"))
                    txtTurn.setText("O");
                else txtTurn.setText("X");
            }
        }

    }

    public void disable() {
        btn1.setEnabled(false);
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        btn4.setEnabled(false);
        btn5.setEnabled(false);
        btn6.setEnabled(false);
        btn7.setEnabled(false);
        btn8.setEnabled(false);
        btn9.setEnabled(false);
    }

    public void enable() {
        btn1.setEnabled(true);
        btn2.setEnabled(true);
        btn3.setEnabled(true);
        btn4.setEnabled(true);
        btn5.setEnabled(true);
        btn6.setEnabled(true);
        btn7.setEnabled(true);
        btn8.setEnabled(true);
        btn9.setEnabled(true);
    }


    public void convert(Node current) {
        Button arr[][] = {{btn1, btn4, btn7}, {btn2, btn5, btn8}, {btn3, btn6, btn9}};
        int[][] play = current.getBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                switch (play[i][j]) {
                    case 1:
                        arr[i][j].setText("X");
                        break;
                    case -1:
                        arr[i][j].setText("O");
                        break;
                    case 0:
                        arr[i][j].setText("");
                        break;
                }
            }
        }
    }


}
