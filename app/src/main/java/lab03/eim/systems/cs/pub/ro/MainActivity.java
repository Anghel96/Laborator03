package lab03.eim.systems.cs.pub.ro;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    final public static int buttonIds[] = {
            R.id.button0,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6,
            R.id.button7,
            R.id.button8,
            R.id.button9,
            R.id.button11,
            R.id.button,
            R.id.button12
    };

    private EditText phone;
    private Button button;
    private ImageButton backSpaceButton;
    private ImageButton callButton;
    private ImageButton hangupButton;

    private CallButtonListener callListener = new CallButtonListener();
    private class CallButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        1);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phone.getText().toString()));
                startActivity(intent);
            }
        }
    }

    private HangUpButton hangListener = new HangUpButton();
    private class HangUpButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    private ButtonOnClickListener buttonListener = new ButtonOnClickListener();
    private class ButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            phone.setText(phone.getText().toString() + ((Button)v).getText().toString());
        }
    }

    private BackSpaceListener backListener = new BackSpaceListener();
    private class BackSpaceListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            /*if (phone.getText().toString().length() > 0) {
                phone.setText(phone.getText().toString().substring(0, phone.getText().toString().length() - 1));
            }*/

            String phoneNumber = phone.getText().toString();
            if (phoneNumber.length() > 0) {
                phoneNumber = phoneNumber.substring(0, phoneNumber.length() - 1);
                phone.setText(phoneNumber);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        phone = (EditText)findViewById(R.id.editText);
        backSpaceButton = (ImageButton)findViewById(R.id.image_button1);
        backSpaceButton.setOnClickListener(backListener);
        callButton = (ImageButton)findViewById(R.id.image_button2);
        callButton.setOnClickListener(callListener);
        hangupButton = (ImageButton)findViewById(R.id.image_button3);
        hangupButton.setOnClickListener(hangListener);

        for (int i = 0; i < buttonIds.length; ++i) {
            button = (Button)findViewById(buttonIds[i]);
            button.setOnClickListener(buttonListener);
        }
    }
}
