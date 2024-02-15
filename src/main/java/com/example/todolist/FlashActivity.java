  package com.example.todolist;

  import android.annotation.SuppressLint;
  import android.content.Intent;
  import android.os.Bundle;
  import android.os.Handler;

  import androidx.appcompat.app.AppCompatActivity;

  public class FlashActivity extends AppCompatActivity {

      @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flash_layout);

          Handler handler=new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(FlashActivity.this,MainActivity.class));
            finish();
        },3000);


    }
}