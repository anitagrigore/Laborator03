package lab03.eim.systems.cs.pub.ro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class PhoneDialerActivity extends AppCompatActivity {
    private EditText phoneNumberEditText;

    private final BasicButtonClickListener basicButtonClickListener = new BasicButtonClickListener();
    private class BasicButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            phoneNumberEditText.setText(phoneNumberEditText.getText().toString() + ((Button)view).getText().toString());
        }
    }

    private final BackspaceButtonClickListener backspaceButtonClickListener = new BackspaceButtonClickListener();
    private class BackspaceButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String phoneNumber = phoneNumberEditText.getText().toString();
            if (phoneNumber.length() > 0) {
                phoneNumber = phoneNumber.substring(0, phoneNumber.length() - 1);
                phoneNumberEditText.setText(phoneNumber);
            }
        }
    }

    private final CallButtonClickListener callButtonClickListener = new CallButtonClickListener();
    private class CallButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (ContextCompat.checkSelfPermission(PhoneDialerActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        PhoneDialerActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        Constants.PERMISSION_REQUEST_CALL_PHONE);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumberEditText.getText().toString()));
                startActivity(intent);
            }
        }
    }

    private final HangupButtonClickListener hangupButtonClickListener =  new HangupButtonClickListener();
    private class HangupButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view){
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        setContentView(R.layout.activity_phone_dialer);
        phoneNumberEditText = findViewById(R.id.phone_number_edit_text);
        ImageButton callButton = findViewById(R.id.call);
        callButton.setOnClickListener(callButtonClickListener);
        ImageButton hangupButton = findViewById(R.id.hangup);
        hangupButton.setOnClickListener(hangupButtonClickListener);
        ImageButton backspaceButton = findViewById(R.id.backspace);
        backspaceButton.setOnClickListener(backspaceButtonClickListener);

        for (int idx = 0; idx < Constants.buttonIds.length; idx++) {
            Button basicButton = findViewById(Constants.buttonIds[idx]);
            basicButton.setOnClickListener(basicButtonClickListener);
        }
    }
}