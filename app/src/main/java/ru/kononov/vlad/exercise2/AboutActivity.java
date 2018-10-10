package ru.kononov.vlad.exercise2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);

        Button sendBtn = findViewById(R.id.send_message_btn);

        sendBtn.setOnClickListener(v -> {
            String message = ((EditText) findViewById(R.id.message_text)).getText().toString();

            if (message.isEmpty()) {
                Toast.makeText(getBaseContext(), getResources().getString(R.string.message_text_empty), Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL, "test@test.ru");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Hello");
            intent.putExtra(Intent.EXTRA_TEXT, message);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(getBaseContext(), getResources().getString(R.string.no_email_client_error), Toast.LENGTH_SHORT).show();
            }
        });

        addImageUrl(R.id.img_instagram, "https://instagram.com");
        addImageUrl(R.id.img_facebook, "https://facebook.com");
        addImageUrl(R.id.img_linkedin, "https://linkedin.com");
    }

    void addImageUrl(int resId, final String url) {
        ImageView img = findViewById(resId);
        img.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });
    }
}
