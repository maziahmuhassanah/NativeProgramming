package com.example.mindsharpener;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView questionTextView;
    private RadioGroup levelRadioGroup;
    private Button generateButton;
    private EditText answerEditText;
    private Button checkButton;
    private TextView scoreTextView;

    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //questionTextView = findViewById(R.id.questionTextView);
        levelRadioGroup = findViewById(R.id.radioGroup);
        checkButton = findViewById(R.id.checkButton);
        answerEditText = findViewById(R.id.editText);
        checkButton = findViewById(R.id.button);
        scoreTextView = findViewById(R.id.textView8);

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateQuestion();
            }
        });

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
            }
        });
    }

    private void generateQuestion() {
        int selectedLevelId = levelRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedLevelRadioButton = findViewById(selectedLevelId);

        if (selectedLevelRadioButton != null) {
            String chosenLevel = selectedLevelRadioButton.getText().toString();

            int maxDigits = getMaxDigits(chosenLevel);
            int operand1 = generateRandomNumber(maxDigits);
            int operand2 = generateRandomNumber(maxDigits);
            int operator = generateRandomOperator();

            displayQuestion(operand1, operand2, operator);
        } else {
            Toast.makeText(this, "Please select a level", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayQuestion(int operand1, int operand2, int operator) {
        String operatorSymbol;
        switch (operator) {
            case 0:
                operatorSymbol = "+";
                break;
            case 1:
                operatorSymbol = "-";
                break;
            case 2:
                operatorSymbol = "*";
                break;
            case 3:
                operatorSymbol = "/";
                break;
            default:
                operatorSymbol = "+"; // Default to addition
        }

        String question = operand1 + " " + operatorSymbol + " " + operand2;
        questionTextView.setText(question);
    }

    private int getMaxDigits(String chosenLevel) {
        switch (chosenLevel) {
            case "i3":
                return 1;
            case "i5":
                return 2;
            case "i7":
                return 3;
            default:
                return 1; // Default to 1 digit if the level is unknown
        }
    }

    private int generateRandomNumber(int maxDigits) {
        Random random = new Random();
        int upperBound = (int) Math.pow(10, maxDigits);
        return random.nextInt(upperBound);
    }

    private int generateRandomOperator() {
        Random random = new Random();
        return random.nextInt(4); // 0 to 3 for +, -, *, /
    }

    private void checkAnswer() {
        try {
            int userAnswer = Integer.parseInt(answerEditText.getText().toString());
            String questionText = questionTextView.getText().toString();
            String[] parts = questionText.split(" ");
            if (parts.length == 3) {
                int operand1 = Integer.parseInt(parts[0]);
                int operand2 = Integer.parseInt(parts[2]);
                int operator = getOperatorCode(parts[1]);

                int correctAnswer = calculateAnswer(operand1, operand2, operator);

                if (userAnswer == correctAnswer) {
                    score++;
                    Toast.makeText(this, "Correct! Your score: " + score, Toast.LENGTH_SHORT).show();
                    updateScore();
                    generateQuestion(); // Display a new question
                } else {
                    score--;
                    Toast.makeText(this, "Incorrect! Your score: " + score, Toast.LENGTH_SHORT).show();
                    updateScore();
                }
            } else {
                Toast.makeText(this, "Invalid question format", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
        }
    }

    private int getOperatorCode(String operatorSymbol) {
        switch (operatorSymbol) {
            case "+":
                return 0;
            case "-":
                return 1;
            case "*":
                return 2;
            case "/":
                return 3;
            default:
                return 0; // Default to addition
        }
    }

    private int calculateAnswer(int operand1, int operand2, int operator) {
        switch (operator) {
            case 0:
                return operand1 + operand2;
            case 1:
                return operand1 - operand2;
            case 2:
                return operand1 * operand2;
            case 3:
                return operand1 / operand2;
            default:
                return 0; // Default to addition
        }
    }

    private void updateScore() {
        scoreTextView.setText("POINTS: " + score);
    }
}
