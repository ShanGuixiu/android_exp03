package edu.fjnu.exp3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 初始化按钮并设置跳转事件
        Button btnGoListViewTest = findViewById(R.id.btn_go_listview_test);
        btnGoListViewTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到 ListView 实验的 Activity
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });

        // AlertDialog实验按钮逻辑
        // 声明AlertDialog实验跳转按钮
        Button btnGoAlertDialogTest = findViewById(R.id.btn_go_alert_dialog_test);
        btnGoAlertDialogTest.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AlertDialogActivity.class);
            startActivity(intent);
        });

        // XML菜单实验按钮跳转逻辑
        // 声明菜单实验跳转按钮
        Button btnGoMenuTest = findViewById(R.id.btn_go_menu_test);
        btnGoMenuTest.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, MenuActivity.class));
        });

        // 新增：ActionMode实验按钮跳转逻辑（仅关联实验3文档任务）
        // 新增：声明ActionMode实验跳转按钮
        Button btnGoActionModeTest = findViewById(R.id.btn_go_action_mode_test);
        btnGoActionModeTest.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ActionModeActivity.class));
        });
    }
}