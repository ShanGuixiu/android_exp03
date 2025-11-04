package edu.fjnu.exp3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class ActionModeActivity extends AppCompatActivity {

    private ActionMode mActionMode; // ActionMode实例，用于控制上下文菜单显示/隐藏
    private int mSelectedPosition = -1; // 记录选中的列表项位置（-1表示无选中）
    private List<String> mListData; // 列表数据源
    private CustomListAdapter mAdapter; // ListView自定义适配器
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_action_mode);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. 初始化ListView
        ListView lvActionModeList = findViewById(R.id.lv_action_mode_list);

        // 2. 准备列表数据源
        mListData = new ArrayList<>();
        mListData.add("One");
        mListData.add("Two");
        mListData.add("Three");
        mListData.add("Four");
        mListData.add("Five");

        // 3. 为ListView设置自定义适配器
        mAdapter = new CustomListAdapter();
        lvActionModeList.setAdapter(mAdapter);

        // 4. 为ListView列表项设置长按事件：触发ActionMode上下文菜单
        lvActionModeList.setOnItemLongClickListener((parent, view, position, id) -> {
            // 若ActionMode未开启，启动上下文菜单
            if (mActionMode == null) {
                mSelectedPosition = position; // 记录当前选中位置
                mAdapter.notifyDataSetChanged(); // 更新列表，显示选中状态
                // 启动ActionMode，绑定Callback（上下文菜单逻辑）
                mActionMode = startSupportActionMode(mActionModeCallback);
            }
            return true; // 消费长按事件，避免触发点击事件
        });
    }
    /**
     * 核心：ActionMode.Callback接口实现（定义上下文菜单的创建、点击、销毁逻辑）
     */
    private final ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        // 1. 创建上下文菜单：加载menu_action_mode_context.xml
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // 加载上下文菜单XML文件
            getMenuInflater().inflate(R.menu.menu_action_mode_context, menu);
            // 设置ActionMode标题：显示“1 selected”（匹配文档中选中状态样式）
            mode.setTitle("1 selected");
            // 设置勾选图标替代返回箭头
            return true; // 返回true表示显示菜单
        }

        // 2. 菜单准备显示时调用
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        // 3. 上下文菜单项点击事件（
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            // 处理“删除”选项点击
            if (item.getItemId() == R.id.action_delete) {
                if (mSelectedPosition != -1 && mSelectedPosition < mListData.size()) {
                    // 移除选中的列表项
                    mListData.remove(mSelectedPosition);
                    mAdapter.notifyDataSetChanged(); // 更新列表
                    Toast.makeText(ActionModeActivity.this,
                            "已删除选中项", Toast.LENGTH_SHORT).show();
                    mode.finish(); // 关闭ActionMode上下文菜单
                    return true;
                }
            }
            return false;
        }

        // 4. ActionMode销毁时调用：重置选中状态
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null; // 重置ActionMode实例
            mSelectedPosition = -1; // 重置选中位置（无选中）
            mAdapter.notifyDataSetChanged(); // 清除列表选中状态
        }
    };

    /**
     * ListView自定义适配器：用于绑定数据并显示选中状态
     */
    private class CustomListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mListData.size(); // 列表项数量
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position); // 获取对应位置数据
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            // 复用列表项视图（优化性能）
            if (convertView == null) {
                convertView = LayoutInflater.from(ActionModeActivity.this)
                        .inflate(R.layout.item_list_action_mode, parent, false);
                holder = new ViewHolder();
                holder.tvItemText = convertView.findViewById(R.id.tv_item_text);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            // 绑定列表项文本（One、Two等）
            holder.tvItemText.setText(mListData.get(position));

            // 显示选中状态：选中项背景色变灰（匹配文档中“选中高亮”效果）
            if (position == mSelectedPosition) {
                convertView.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            } else {
                convertView.setBackgroundColor(getResources().getColor(android.R.color.white));
            }

            return convertView;
        }

        // ViewHolder：缓存列表项控件，避免重复findViewById
        class ViewHolder {
            TextView tvItemText;
        }
    }

}