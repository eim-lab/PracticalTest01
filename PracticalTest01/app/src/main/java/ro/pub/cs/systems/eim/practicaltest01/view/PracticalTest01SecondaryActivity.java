package ro.pub.cs.systems.eim.practicaltest01.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import ro.pub.cs.systems.eim.practicaltest01.R;
import ro.pub.cs.systems.eim.practicaltest01.general.Constants;

public class PracticalTest01SecondaryActivity extends AppCompatActivity {

    private final ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.ok_button) {
                setResult(RESULT_OK, null);
            } else if (id == R.id.cancel_button) {
                setResult(RESULT_CANCELED, null);
            }

            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);

        TextView numberOfClicksTextView = findViewById(R.id.number_of_clicks_text_view);
        Intent intent = getIntent();
        if (intent != null && Objects.requireNonNull(intent.getExtras()).containsKey(Constants.NUMBER_OF_CLICKS)) {
            int numberOfClicks = intent.getIntExtra(Constants.NUMBER_OF_CLICKS, -1);
            numberOfClicksTextView.setText(String.valueOf(numberOfClicks));
        }

        Button okButton = findViewById(R.id.ok_button);
        okButton.setOnClickListener(buttonClickListener);
        Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(buttonClickListener);
    }
}
