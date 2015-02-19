package csc495s15.assignment4;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import android.os.Environment;

public class MainActivity extends ActionBarActivity {

    private String fileName;
    static final int READ_BLOCK_SIZE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fileName = "assignment4.txt";
    }

    public void onClickAppend(View v) {
        EditText textBox = (EditText) findViewById(R.id.editText);
        String str = textBox.getText().toString();

        try {
            FileOutputStream fOut;

            if (useExternal()) {
                File sdCard = Environment.getExternalStorageDirectory();
                File directory = new File(sdCard.getAbsolutePath() + "/CSC495");
                directory.mkdirs();
                File file = new File(directory, fileName);
                fOut = new FileOutputStream(file, true);
            }
            else {
                fOut = openFileOutput(fileName, MODE_APPEND);
            }

            OutputStreamWriter osw = new OutputStreamWriter(fOut);

            osw.write(str);
            osw.flush();
            osw.close();

            textBox.setText("");

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void onClickOverwrite(View v) {
        EditText textBox = (EditText) findViewById(R.id.editText);
        String str = textBox.getText().toString();

        try {
            FileOutputStream fOut;

            if (useExternal()) {
                File sdCard = Environment.getExternalStorageDirectory();
                File directory = new File(sdCard.getAbsolutePath() + "/CSC495");
                directory.mkdirs();
                File file = new File(directory, fileName);
                fOut = new FileOutputStream(file);
            }
            else {
                fOut = openFileOutput(fileName, MODE_PRIVATE);
            }

            OutputStreamWriter osw = new OutputStreamWriter(fOut);

            osw.write(str);
            osw.flush();
            osw.close();

            textBox.setText("");

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void onClickDiscard(View v) {
        if (useExternal()) {
            File sdCard = Environment.getExternalStorageDirectory();
            File directory = new File(sdCard.getAbsolutePath() + "/CSC495");
            directory.mkdirs();
            File file = new File(directory, fileName);
            file.delete();
        }
        else {
            deleteFile(fileName);
        }
    }

    public void onClickDisplay(View v) {
        try {
            FileInputStream fIn;
            if (useExternal()) {
                File sdCard = Environment.getExternalStorageDirectory();
                File directory = new File(sdCard.getAbsolutePath() + "/CSC495");
                File file = new File(directory, fileName);
                fIn = new FileInputStream(file);
            }
            else {
                fIn = openFileInput(fileName);
            }
            InputStreamReader isr = new InputStreamReader(fIn);

            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            String s = "";

            int charRead;
            while((charRead = isr.read(inputBuffer))>0) {
                String readString = String.copyValueOf(inputBuffer, 0, charRead);
                s += readString;

                inputBuffer = new char[READ_BLOCK_SIZE];
            }
            Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public boolean useExternal() {
        RadioButton rb = (RadioButton) findViewById(R.id.radioButtonExternal);
        return rb.isChecked();
    }

}