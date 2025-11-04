package edu.fjnu.exp3;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListActivity extends AppCompatActivity {

    private final String[] animalNames = {"Lion", "Tiger", "Monkey", "Dog", "Cat", "Elephant"};
    private final int[] animalIcons = {
            R.drawable.lion,
            R.drawable.tiger,
            R.drawable.monkey,
            R.drawable.dog,
            R.drawable.cat,
            R.drawable.elephant
    };

    // 通知渠道ID（Android 8.0+ 必需）
    private static final String CHANNEL_ID = "animal_channel";
    // 通知权限请求码
    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 实测发现，不加上这里一样可以成功
        /*// 初始化通知渠道（Android 8.0+ 必需）
        createNotificationChannel();

        // 检查并请求通知权限（Android 13+ 必需）
        checkNotificationPermission();*/

        ListView lvAnimalList = findViewById(R.id.lv_animal_list);

        // 准备数据源
        List<Map<String, Object>> animalDataList = new ArrayList<>();
        for (int i = 0; i < animalNames.length; i++) {
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("icon", animalIcons[i]);
            itemMap.put("name", animalNames[i]);
            animalDataList.add(itemMap);
        }

        // 创建适配器
        SimpleAdapter animalAdapter = new SimpleAdapter(
                this,
                animalDataList,
                R.layout.item_listview,
                new String[]{"icon", "name"},
                new int[]{R.id.iv_animal_icon, R.id.tv_animal_name}
        );

        lvAnimalList.setAdapter(animalAdapter);

        // 列表项点击事件：发送通知
        lvAnimalList.setOnItemClickListener((parent, view, position, id) -> {
            String selectedAnimal = animalNames[position];
            // 发送toast
            Toast.makeText(this, "已发送通知：" + selectedAnimal, Toast.LENGTH_SHORT).show();
            // 发送通知
            sendNotification(selectedAnimal);
        });
    }

    // 1. 创建通知渠道（Android 8.0+ 必需，否则通知无法显示）
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 渠道名称和描述
            CharSequence channelName = "动物列表通知";
            String channelDescription = "点击动物列表项时发送的通知";
            int importance = NotificationManager.IMPORTANCE_DEFAULT; // 通知重要性

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importance);
            channel.setDescription(channelDescription);

            // 注册渠道到系统
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // 2. 检查并请求通知权限（Android 13+ 必需）
    private void checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // 检查是否已有权限
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                // 请求权限
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        NOTIFICATION_PERMISSION_REQUEST_CODE
                );
            }
        }
    }

    // 3. 发送通知的核心方法
    private void sendNotification(String title) {
        // 获取通知管理器
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // 构建通知内容
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher) // 通知图标（应用图标）
                .setContentTitle(title) // 通知标题（列表项内容）
                .setContentText("您点击了：" + title) // 通知内容（可选）
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) // 优先级
                .setAutoCancel(true); // 点击通知后自动取消

        // 发送通知（通知ID需唯一，这里用系统时间确保唯一）
        int notificationId = (int) System.currentTimeMillis();
        notificationManager.notify(notificationId, builder.build());
    }
}