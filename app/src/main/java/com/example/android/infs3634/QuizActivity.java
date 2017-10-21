package com.example.android.infs3634;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;

// Activity to view quiz task in lesson
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
    Week week;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        getSupportActionBar().hide();

        // Set status bar colour
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

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

        // Get list of tasks for lesson
        tasks = (ArrayList<Task>) getIntent().getSerializableExtra("Tasks");
        taskIndex = (int) getIntent().getSerializableExtra("Task Index") + 1;
        totalTasks = (int) getIntent().getSerializableExtra("Total Tasks");
        week = (Week) getIntent().getSerializableExtra("Week");
        mLessonTitle.setText("Week " + week.getWeekNumber() + ": " + week.getWeekTopic());
        int progress = Math.round(100 * taskIndex / totalTasks);

        // Get current quiz from list of tasks
        Quiz quiz = (Quiz) tasks.get(0);

        // Get questions for quiz from firebase database
        DataService.instance.getQuestion(this, this, quiz.getQuestionIds());

        mLessonProgress.setText(taskIndex + " / " + totalTasks);
        mProgressBar.setProgress(progress);

        // On-click listeners to select answer for quiz question
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

    // Method to render question text and question options
    protected void renderQuiz() {
        mQuestionNumberText.setText("question " + (questionIndex + 1) + " of " + questions.size());
        mQuestionText.setText(questions.get(questionIndex).getQuestion());
        mBtnA.setText(questions.get(questionIndex).getOptions().get(0));
        mBtnB.setText(questions.get(questionIndex).getOptions().get(1));
        mBtnC.setText(questions.get(questionIndex).getOptions().get(2));
        mBtnD.setText(questions.get(questionIndex).getOptions().get(3));
    }

    // Method to check answer
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

        // Snackbar to notify logged-in user if answer is correct or incorrect
        // https://developer.android.com/training/snackbar/showing.html
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

        // Customise snackbar
        int snackbarTextId = android.support.design.R.id.snackbar_text;
        TextView textView = message.getView().findViewById(snackbarTextId);
        textView.setTextColor(getResources().getColor(R.color.primaryBlack));
        message.getView().setBackgroundColor(ContextCompat.getColor(QuizActivity.this, R.color.white));
        message.setActionTextColor(getResources().getColor(R.color.colorPrimary));
        message.show();
    }

    // Method to highlight correct answer
    protected void correctAnswer(Button btn) {
        btn.setBackgroundColor(Color.parseColor("#64D677"));
        btn.setTextColor(Color.WHITE);
    }

    // Method to highlight logged-in user's incorrect answer
    protected void incorrectAnswer(Button btn) {
        btn.setBackgroundColor(Color.parseColor("#EB4A4A"));
        btn.setTextColor(Color.WHITE);
    }

    // Method to render quiz with next question
    protected void nextQuestion() {
        questionIndex++;

        mBtnA.setBackgroundColor(Color.parseColor("#FAFAFA"));
        mBtnB.setBackgroundColor(Color.parseColor("#FAFAFA"));
        mBtnC.setBackgroundColor(Color.parseColor("#FAFAFA"));
        mBtnD.setBackgroundColor(Color.parseColor("#FAFAFA"));

        mBtnA.setTextColor(getResources().getColor(R.color.primaryBlack));
        mBtnB.setTextColor(getResources().getColor(R.color.primaryBlack));
        mBtnC.setTextColor(getResources().getColor(R.color.primaryBlack));
        mBtnD.setTextColor(getResources().getColor(R.color.primaryBlack));


        renderQuiz();

        mBtnA.setEnabled(true);
        mBtnB.setEnabled(true);
        mBtnC.setEnabled(true);
        mBtnD.setEnabled(true);
    }

    // Method to alert logged-in users upon passing a quiz
    // https://developer.android.com/guide/topics/ui/dialogs.html
    public void passAlert() {
        // remove current quiz from list of tasks for lesson
        tasks.remove(0);

        // Set action text for alert dialog
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
                        // Determine if there are any more tasks left for lesson
                        dialog.cancel();
                        if (tasks.size() > 0) {
                            Intent intent;
                            if (tasks.get(0).getType().equalsIgnoreCase("quiz")) {
                                // if next task is a quiz create intent for another Quiz Activity
                                intent = new Intent(QuizActivity.this, QuizActivity.class);
                            } else {
                                // if next task is a video task create intent for Video Activity
                                intent = new Intent(QuizActivity.this, VideoActivity.class);
                            }

                            // Pass remaining task list to next task activity
                            intent.putExtra("Tasks", tasks);
                            intent.putExtra("Task Index", taskIndex);
                            intent.putExtra("Total Tasks", totalTasks);
                            intent.putExtra("Week", week);
                            startActivity(intent);
                        } else {
                            // If there are no more tasks left for lesson, show result activity
                            Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                            intent.putExtra("Week", week);
                            startActivity(intent);
                        }
                    }
                });

        AlertDialog redoDialog = passDialogBuilder.create();
        redoDialog.show();
    }

    // Method to alert logged-in users upon failing a quiz
    // https://developer.android.com/guide/topics/ui/dialogs.html
    public void redoAlert(){
        AlertDialog.Builder redoDialogBuilder = new AlertDialog.Builder(this);
        redoDialogBuilder.setTitle("Sorry...");
        redoDialogBuilder.setMessage("You got "+ totalScore + " out of " + questions.size() + " correct!\n\n You did not pass 50% for this quiz.");
                redoDialogBuilder.setPositiveButton(R.string.redo,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // restart activity for logged-in user to restart quiz
                                Intent intent = new Intent(QuizActivity.this, QuizActivity.class);
                                intent.putExtra("Tasks", tasks);
                                intent.putExtra("Task Index", taskIndex - 1);
                                intent.putExtra("Total Tasks", totalTasks);
                                intent.putExtra("Week", week);
                                dialog.cancel();
                                finish();
                                startActivity(intent);
                            }
                        });
        AlertDialog redoDialog = redoDialogBuilder.create();
        redoDialog.show();
    }
}
