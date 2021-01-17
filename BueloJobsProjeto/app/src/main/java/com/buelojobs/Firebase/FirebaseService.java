package com.buelojobs.Firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.buelojobs.MainActivity;
import com.buelojobs.NotificacaoActivity;
import com.buelojobs.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Picasso;

public class FirebaseService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage notificacao) {

        if(notificacao.getData().size() >0){

            String msg =  notificacao.getData().get("mensagem");
            String titulo =  notificacao.getData().get("titulo");
           // String nome =  notificacao.getData().get("nome");
            String urlimagem =  notificacao.getData().get("urlimagem");

            String mensagem = msg;

            sendNotification_2(titulo,mensagem,urlimagem);

        }

      else  if(notificacao.getNotification()!= null){

            //Log.d("Jaaffapp.com",notificacao.getNotification().getTitle());
           // Log.d("Jaaffapp.com",notificacao.getNotification().getBody());

        String titulo= notificacao.getNotification().getTitle();
        String msg = notificacao.getNotification().getBody();
        sendNotification_1(titulo,msg);
        }



    }

    private void sendNotification_1(String titulo, String msg){

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        String canal = getString(R.string.default_notification_channel_id);
        Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this,canal)
                .setSmallIcon(R.mipmap.ic_buelo_launcher)
                .setContentTitle(titulo)
                .setContentText(msg)
                .setSound(som)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(canal,"canal",NotificationManager.IMPORTANCE_DEFAULT);

            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0,notification.build());


    }


    private void sendNotification_2(String titulo, String msg,String url){

        int id = (int) (System.currentTimeMillis()/1000);


        Glide.with(getBaseContext()).asBitmap().load(url).listener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap bitmap, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                Intent intent = new Intent(getBaseContext(), NotificacaoActivity.class);

                intent.putExtra("url",url);
                intent.putExtra("mensagem",msg);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),id,intent,PendingIntent.FLAG_ONE_SHOT);
                String canal = getString(R.string.default_notification_channel_id);
                Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notification = new NotificationCompat.Builder(getBaseContext(),canal)
                        .setSmallIcon(R.mipmap.ic_buelo_launcher)
                        .setContentTitle(titulo)
                        .setContentText(msg)
                        .setSound(som)
                        .setAutoCancel(true)
                        .setLargeIcon(bitmap)
                        //imagem grande
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                        //texto grande
                        //.setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                        .setContentIntent(pendingIntent);

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
                    NotificationChannel channel = new NotificationChannel(canal,"canal",NotificationManager.IMPORTANCE_DEFAULT);

                    notificationManager.createNotificationChannel(channel);
                }

                notificationManager.notify(id,notification.build());
                return false;
            }
        }).submit();









    }






        @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }
}
