package edu.fjnu.exp3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AlertDialogActivity extends AppCompatActivity {
    private Button btnShowCustomDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_alert_dialog);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // 初始化“显示对话框”按钮
        btnShowCustomDialog = findViewById(R.id.btn_show_custom_dialog);
        btnShowCustomDialog.setOnClickListener(v -> showCustomLoginDialog());
    }

    /**
     * 核心方法：创建并显示自定义布局的AlertDialog
     * 符合文档“调用AlertDialog.Builder对象上的setView()将布局添加到AlertDialog”要求
     */
    private void showCustomLoginDialog() {
        // 1. 加载自定义对话框布局
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.alarm_dialog, null);

        // 2. 创建AlertDialog.Builder对象，设置自定义布局
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView); // 关键步骤：将自定义布局添加到对话框
        AlertDialog customDialog = dialogBuilder.create(); // 生成最终对话框实例

        // 3. 获取对话框布局中的控件（用户名/密码输入框、按钮）
        EditText etUsername = dialogView.findViewById(R.id.et_username);
        EditText etPassword = dialogView.findViewById(R.id.et_password);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
        Button btnSignIn = dialogView.findViewById(R.id.btn_sign_in);

        // 4. 处理Cancel按钮点击事件：关闭对话框
        btnCancel.setOnClickListener(v -> customDialog.dismiss());

        // 5. 处理Sign in按钮点击事件：获取输入内容并提示（实验阶段仅做验证）
        btnSignIn.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            // 用Toast提示输入的用户名（可根据实验需求扩展逻辑）
            Toast.makeText(AlertDialogActivity.this,
                    "登录信息：用户名=" + username + "，密码=" + password,
                    Toast.LENGTH_SHORT).show();
            customDialog.dismiss(); // 关闭对话框
        });

        // 6. 显示对话框
        customDialog.show();
    }
}