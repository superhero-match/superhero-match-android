package nl.mwsoft.www.superheromatch.modelLayer.helper.util.notificationUtil;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;

import androidx.core.app.NotificationCompat;

import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.model.Chat;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;

public class NotificationUtil {

    public static void sendNewMatchNotification(Context context, Chat chat) {

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = context.getString(R.string.channel_new_match_id);
        String channelName = context.getString(R.string.channel_new_match_name);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel mChannel = new NotificationChannel(
                    channelId,
                    channelName,
                    importance
            );

            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);

        Intent newMatchIntent = new Intent(context, MainActivity.class);
        newMatchIntent.putExtra(ConstantRegistry.NEW_MATCH_INTENT, chat);
        newMatchIntent.setAction(ConstantRegistry.NEW_MATCH_REQUEST);
        newMatchIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent replyNewMatchPendingIntent = PendingIntent.getActivity(
                context,
                ConstantRegistry.NEW_MATCH_PENDING_INTENT_REQUEST,
                newMatchIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        builder.setSmallIcon(R.drawable.mask_512);
        builder.setContentTitle(chat.getChatName());
        builder.setContentText(context.getString(R.string.it_s_a_match));
        builder.setPriority(Notification.PRIORITY_MAX);
        builder.setAutoCancel(true);
        builder.setOnlyAlertOnce(true);
        builder.setVibrate(new long[]{0, 200, 200, 200});
        builder.setLights(Color.MAGENTA, 500, 500);
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        builder.addAction(R.drawable.chat_match_64, context.getString(R.string.send_message), replyNewMatchPendingIntent);

        notificationManager.notify(notificationId, builder.build());
    }

}
