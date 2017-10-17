package com.example.android.infs3634;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class QuizActivity extends AppCompatActivity {

    ArrayList<Question> questions = new ArrayList<Question>();

    CoordinatorLayout mQuizCoordinator;
    TextView mLessonTitle;
    TextView mLessonProgress;
    ProgressBar mProgressBar;
    TextView mQuestionNumberText;
    TextView mQuestionText;
    Button mBtnA;
    Button mBtnB;
    Button mBtnC;
    Button mBtnD;
    int questionIndex = 0;
    int totalScore = 0;
    int taskIndex;
    int totalTasks;
    ArrayList<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        getSupportActionBar().hide();

        final QuizActivity activity = this;
        mQuizCoordinator = findViewById(R.id.quizCoordinator);
        mLessonTitle = findViewById(R.id.lessonTitle);
        mLessonProgress = findViewById(R.id.lessonProgress);
        mProgressBar = findViewById(R.id.progressBar);
        mQuestionNumberText = findViewById(R.id.questionNumberText);
        mQuestionText = findViewById(R.id.questionText);
        mBtnA = findViewById(R.id.btnA);
        mBtnB = findViewById(R.id.btnB);
        mBtnC = findViewById(R.id.btnC);
        mBtnD = findViewById(R.id.btnD);

        tasks = (ArrayList<Task>) getIntent().getSerializableExtra("Tasks");
        taskIndex = (int) getIntent().getSerializableExtra("Task Index") + 1;
        totalTasks = (int) getIntent().getSerializableExtra("Total Tasks");

        int progress = Math.round(100 * taskIndex / totalTasks);
        Quiz quiz = (Quiz) tasks.get(0);
        DataService.instance.getQuestion(this, this, quiz.getQuestionIds());

        mLessonProgress.setText(taskIndex + " / " + totalTasks);
        mProgressBar.setProgress(progress);

        mBtnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(mBtnA.getText().toString(),0);
            }
        });

        mBtnB.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                checkAnswer(mBtnB.getText().toString(),1);
            }
        });

        mBtnC.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                checkAnswer(mBtnC.getText().toString(),2);
            }
        });

        mBtnD.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                checkAnswer(mBtnD.getText().toString(),3);
            }
        });
    }

    protected void renderQuiz() {
        mQuestionNumberText.setText("question " + (questionIndex + 1) + " of " + questions.size());
        mQuestionText.setText(questions.get(questionIndex).getQuestion());
        mBtnA.setText(questions.get(questionIndex).getOptions().get(0));
        mBtnB.setText(questions.get(questionIndex).getOptions().get(1));
        mBtnC.setText(questions.get(questionIndex).getOptions().get(2));
        mBtnD.setText(questions.get(questionIndex).getOptions().get(3));
    }

    protected void checkAnswer(String selected, int optionIndex) {
        int answerIndex = questions.get(questionIndex).getAnswer();
        String answer = questions.get(questionIndex).getOptions().get(answerIndex);
        String response = "";

        mBtnA.setEnabled(false);
        mBtnB.setEnabled(false);
        mBtnC.setEnabled(false);
        mBtnD.setEnabled(false);

        if (selected.equalsIgnoreCase(answer)) {
            totalScore++;
            response = "Correct";
            switch (optionIndex) {
                case 0: correctAnswer(mBtnA); break;
                case 1: correctAnswer(mBtnB); break;
                case 2: correctAnswer(mBtnC); break;
                case 3: correctAnswer(mBtnD); break;
                default: break;
            }

        } else {
            response = "Incorrect";
            switch (optionIndex) {
                case 0: incorrectAnswer(mBtnA); break;
                case 1: incorrectAnswer(mBtnB); break;
                case 2: incorrectAnswer(mBtnC); break;
                case 3: incorrectAnswer(mBtnD); break;
                default: break;
            }

            switch (answerIndex) {
                case 0: correctAnswer(mBtnA); break;
                case 1: correctAnswer(mBtnB); break;
                case 2: correctAnswer(mBtnC); break;
                case 3: correctAnswer(mBtnD); break;
                default: break;
            }
        }

        final Snackbar message = Snackbar.make(mQuizCoordinator, response, Snackbar.LENGTH_INDEFINITE);
        if (questionIndex == questions.size() - 1) {
            if (totalScore < (double) questions.size() / 2) {
                message.setAction(R.string.next, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        message.dismiss();
                        redoAlert();
                    }
                });
            } else {
                message.setAction(R.string.next, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        message.dismiss();
                        passAlert();
                    }
                });
            }
        } else {
            message.setAction(R.string.next, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    message.dismiss();
                    nextQuestion();
                }
            });
        }
        message.setActionTextColor(Color.YELLOW);
        message.show();
    }

    protected void correctAnswer(Button btn) {
        btn.setBackgroundColor(Color.parseColor("#c0ffd9"));
    }

    protected void incorrectAnswer(Button btn) {
        btn.setBackgroundColor(Color.parseColor("#ffc0c0"));
    }

    protected void nextQuestion() {
        questionIndex++;

        mBtnA.setBackgroundColor(Color.parseColor("#FAFAFA"));
        mBtnB.setBackgroundColor(Color.parseColor("#FAFAFA"));
        mBtnC.setBackgroundColor(Color.parseColor("#FAFAFA"));
        mBtnD.setBackgroundColor(Color.parseColor("#FAFAFA"));

        renderQuiz();

        mBtnA.setEnabled(true);
        mBtnB.setEnabled(true);
        mBtnC.setEnabled(true);
        mBtnD.setEnabled(true);
    }

    public void passAlert() {
        tasks.remove(0);

        String action = "";
        if (tasks.size() > 0) {
            action = "next";
        } else {
            action = "finish lesson";
        }

        AlertDialog.Builder passDialogBuilder = new AlertDialog.Builder(this);
        passDialogBuilder.setTitle("Congratulations");
        passDialogBuilder.setMessage("You got "+ totalScore + " out of " + questions.size() + " correct!\n\n You passed this quiz!");
        passDialogBuilder.setPositiveButton(action,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        if (tasks.size() > 0) {
                            if (tasks.get(0).getType().equalsIgnoreCase("quiz")) {
                                Intent intent = new Intent(QuizActivity.this, QuizActivity.class);
                                intent.putExtra("Tasks", tasks);
                                intent.putExtra("Task Index", taskIndex);
                                intent.putExtra("Total Tasks", totalTasks);
                                startActivity(intent);
                            } else if (tasks.get(0).getType().equalsIgnoreCase("video")) {
                                Intent intent = new Intent(QuizActivity.this, VideoActivity.class);
                                intent.putExtra("Tasks", tasks);
                                intent.putExtra("Task Index", taskIndex);
                                intent.putExtra("Total Tasks", totalTasks);
                                startActivity(intent);
                            }
                        } else {
                            // Results / Reward badge activity
                        }
                    }
                });

        AlertDialog redoDialog = passDialogBuilder.create();
        redoDialog.show();
    }

    public void redoAlert(){
        AlertDialog.Builder redoDialogBuilder = new AlertDialog.Builder(this);
        redoDialogBuilder.setTitle("Sorry...");
        redoDialogBuilder.setMessage("You got "+ totalScore + " out of " + questions.size() + " correct!\n\n You did not pass 50% for this quiz.");
                redoDialogBuilder.setPositiveButton(R.string.redo,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(QuizActivity.this, QuizActivity.class);
                                intent.putExtra("Tasks", tasks);
                                intent.putExtra("Task Index", taskIndex - 1);
                                intent.putExtra("Total Tasks", totalTasks);
                                dialog.cancel();
                                finish();
                                startActivity(intent);
                            }
                        });
        AlertDialog redoDialog = redoDialogBuilder.create();
        redoDialog.show();
    }
}
