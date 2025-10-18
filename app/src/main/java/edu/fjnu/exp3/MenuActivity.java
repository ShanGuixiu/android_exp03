package edu.fjnu.exp3;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuActivity extends AppCompatActivity {
    private TextView tvTestContent; // 测试文本控件（用于修改字体大小/颜色）

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // 初始化测试文本控件
        tvTestContent = findViewById(R.id.tv_test_content);
    }

    /**
     * 核心方法1：加载XML菜单（必须重写，才能显示菜单）
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 加载res/menu/menu_text_setting.xml定义的菜单
        getMenuInflater().inflate(R.menu.menu_text_setting, menu);
        return true; // 返回true表示显示菜单
    }

    /**
     * 核心方法2：处理菜单项点击事件（按文档要求实现对应功能）
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.size_small) {
            tvTestContent.setTextSize(10);
            return true;
        } else if (itemId == R.id.size_medium) {
            tvTestContent.setTextSize(16);
            return true;
        } else if (itemId == R.id.size_large) {
            tvTestContent.setTextSize(20);
            return true;
        } else if (itemId == R.id.item_normal) {
            Toast.makeText(this, "普通菜单项被点击", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.color_red) {
            tvTestContent.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            return true;
        } else if (itemId == R.id.color_black) {
            tvTestContent.setTextColor(getResources().getColor(android.R.color.black));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}