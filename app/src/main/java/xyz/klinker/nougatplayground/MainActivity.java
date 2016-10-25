package xyz.klinker.nougatplayground;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

public class MainActivity extends AppCompatActivity implements ImageKeyboardEditText.ImageSelectedCallback {

    private Button writeShortcuts;
    private ImageKeyboardEditText editText;
    private ImageView gif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        writeShortcuts = (Button) findViewById(R.id.write_shortcuts);
        editText = (ImageKeyboardEditText) findViewById(R.id.edit_text);
        gif = (ImageView) findViewById(R.id.gif_iv);

        editText.setImageSelectedCallback(this);

        writeShortcuts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DynamicShortcutHelper(MainActivity.this)
                        .buildDynamicShortcuts(editText.getText().toString().split(", "));
            }
        });

        handleShortcutIntent(getIntent());
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleShortcutIntent(intent);
    }

    @Override
    public void onImageSelected(Uri content, String mimeType) {
        Glide.with(this).load(content).into(new GlideDrawableImageViewTarget(gif));
    }

    private void handleShortcutIntent(Intent intent) {
        if (intent.getData() != null) {
            // handle the URI with the data. You may have different logic than just
            // getting the last path segment
            String data = getIntent().getData().getLastPathSegment();
            Toast.makeText(this, "Clicked: " + data, Toast.LENGTH_SHORT).show();
        }
    }
}
