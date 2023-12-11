package com.example.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int notifikasi = 1;
    public String ch_id = "ch_id";
    public  String chName = "notif";
    TextView etJudul, etPesan;
    Button btnKirim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnKirim = findViewById(R.id.btnKirim);
        etJudul = findViewById(R.id.etJudul);
        etPesan = findViewById(R.id.etPesan);

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                tampilNotif(etJudul.getText().toString(), etPesan.getText().toString(),i);
            }
        });
    }

    private void tampilNotif(String judul, String pesan, Intent i) {
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, notifikasi, i, PendingIntent.FLAG_IMMUTABLE);
        NotificationManager notificationManager = (NotificationManager) MainActivity.this.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, ch_id)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setLargeIcon(BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ic_launcher_foreground))
                .setContentTitle(judul)
                .setContentText(pesan)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            NotificationChannel channel = new NotificationChannel(ch_id,chName,notificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(chName);
            builder.setChannelId(ch_id);
            if (notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }
        Notification notification = builder.build();
        if (notificationManager != null){
            notificationManager.notify(notifikasi,notification);
        }
    }

}