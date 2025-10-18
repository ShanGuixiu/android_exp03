package edu.fjnu.exp3;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListActivity extends AppCompatActivity {

    // 1. 定义实验所需数据：动物名称数组 + 对应图片资源ID数组（与drawable中图片名一致）
    private final String[] animalNames = {"Lion", "Tiger", "Monkey", "Dog", "Cat", "Elephant"};
    private final int[] animalIcons = {
            R.drawable.lion,
            R.drawable.tiger,
            R.drawable.monkey,
            R.drawable.dog,
            R.drawable.cat,
            R.drawable.elephant
    };

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

        ListView lvAnimalList = findViewById(R.id.lv_animal_list);

        // 3. 准备 SimpleAdapter 所需的数据源（List<Map<String, Object>> 格式）
        List<Map<String, Object>> animalDataList = new ArrayList<>();
        for (int i = 0; i < animalNames.length; i++) {
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("icon", animalIcons[i]);    // 存储图片资源ID，键为"icon"
            itemMap.put("name", animalNames[i]);    // 存储动物名称，键为"name"
            animalDataList.add(itemMap);            // 将条目添加到数据源
        }

        // 4. 创建 SimpleAdapter（关联数据源与列表项布局）
        SimpleAdapter animalAdapter = new SimpleAdapter(
                this,                          // 上下文
                animalDataList,                // 数据源
                R.layout.item_listview, // 列表项布局（item_listview.xml）
                new String[]{"icon", "name"},  // 数据源的键（与 Map 中键对应）
                new int[]{R.id.iv_animal_icon, R.id.tv_animal_name} // 布局中控件ID（与键一一对应）
        );

        // 5. 将适配器绑定到 ListView
        lvAnimalList.setAdapter(animalAdapter);

        // 6. 设置 ListView 条目点击事件：用 Toast 显示选中项（符合实验“使用Toast”要求）
        lvAnimalList.setOnItemClickListener((parent, view, position, id) -> {
            // 获取当前选中的动物名称
            String selectedAnimal = animalNames[position];
            // 弹出 Toast 提示（2秒后自动消失）
            Toast toast = Toast.makeText(
                    ListActivity.this,
                    "选中的动物：" + selectedAnimal,
                    Toast.LENGTH_SHORT
            );
            toast.show();
        });
    }
}