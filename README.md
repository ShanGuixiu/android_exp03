# 实验 3_Android界面组件

专业 __软件工程__   班级__4__  学号__121052023138__  姓名 __徐国梁__

实验日期：2025年 10月 18日

## 一、实验目的

1. 掌握 Android 核心界面组件的使用方法，包括 ListView、AlertDialog、菜单（XML 定义）、ActionMode 上下文菜单等；
2. 学会通过 SimpleAdapter 为 ListView 绑定数据，实现列表展示与交互（如点击弹 Toast 提示）；
3. 理解自定义布局 AlertDialog 的构建逻辑，能够通过 `setView()` 方法将自定义布局集成到对话框中；
4. 掌握 XML 定义菜单的方式，实现字体大小调整、字体颜色修改及普通菜单项的 Toast 反馈功能；
5. 熟悉 ActionMode 上下文操作模式，为 ListView 列表项添加长按触发的上下文菜单；

## 二、实验任务

1. **Android ListView 的用法**：利用 SimpleAdapter 实现动物列表界面，包含图片与文字展示，点击列表项用 Toast 显示选中信息；
2. **创建自定义布局的 AlertDialog**：设计包含用户名输入框、密码输入框、Cancel 按钮、Sign in 按钮的自定义对话框布局，通过 `AlertDialog.Builder.setView()` 集成布局；
3. **使用 XML 定义菜单**：通过 XML 定义含 “字体大小”（小 10 号、中 16 号、大 20 号）、“普通菜单项”（点击弹 Toast）、“字体颜色”（红色、黑色）的菜单，点击选项修改测试文本样式；
4. **创建 ActionMode 上下文菜单**：使用 ListView 创建列表，为列表项添加长按触发的 ActionMode 上下文菜单，实现选中项高亮与删除功能；
5. **自学内容**：学习 RecyclerView 列表组件与 CardView 卡片布局的基础用法，参考官方文档完成简单列表展示。

## 三、实验环境

Win11 家庭版，Android Studio (2025.1.3)，Android Emulator (Nexus S API 23)

## 四、实验步骤

### （一）ListView + SimpleAdapter + Toast 实现

1. 创建布局文件

2. 准备资源与数据

    * 将动物图片（cat.png、dog.jpeg 等）放入 `res/drawable` 目录；

    - 在 `ListActivity.java` 中定义动物名称数组（Lion、Tiger 等）与对应图片资源 ID 数组。

3. 绑定适配器与交互构建 `List<Map<String, Object>>` 数据源，存储每个列表项的图片与文字数据；

    - 创建 SimpleAdapter，关联数据源、列表项布局与控件 ID；
    - 为 ListView 设置 `setOnItemClickListener`，点击时通过 Toast 显示选中的动物名称。

### （二）自定义布局 AlertDialog 实现

1. 设计自定义对话框布局：在 `res/layout` 下新建 `alarm_dialog.xml`，添加 TextView（标题 “ANDROID APP”）、两个 EditText（用户名、密码）、两个 Button（Cancel、Sign in）。

2. 构建对话框逻辑：

    * 在 `AlertDialogTestActivity.java` 中，通过 `LayoutInflater` 加载自定义布局；

    - 创建 `AlertDialog.Builder` 对象，调用 `setView()` 方法将自定义布局添加到对话框；
    - 为对话框内的 Cancel 按钮设置 “关闭对话框” 逻辑，为 Sign in 按钮设置 “获取输入内容并弹 Toast 提示” 逻辑。

### （三）XML 定义菜单实现

1. 创建菜单 XML 文件：
    * 在 `res` 下新建 `menu` 目录，创建 `menu_text_setting.xml`，定义 “字体大小” 子菜单（小 / 中 / 大）、“普通菜单项”、“字体颜色” 子菜单（红色 / 黑色），设置 `app:showAsAction="never"` 使其在溢出菜单中显示。
2. 加载菜单与处理交互
    - 在 `MenuActivity.java` 中重写 `onCreateOptionsMenu()`，通过 `getMenuInflater().inflate()` 加载 XML 菜单；
    - 重写 `onOptionsItemSelected()`，根据菜单项 ID 实现逻辑：点击字体大小选项修改测试文本字号，点击普通菜单项弹 Toast，点击字体颜色选项修改文本颜色。

### （四）ActionMode 上下文菜单实现

1. 创建布局与菜单
    - 在 `res/layout` 下新建 `activity_action_mode.xml`（添加 ListView）与 `item_list_action_mode.xml`（列表项文本布局）；
    - 在 `res/menu` 下新建 `menu_action_mode_context.xml`，定义 “删除” 菜单项。
2. 实现上下文菜单逻辑
    - 在 `ActionModeActivity.java` 中，为 ListView 设置 `setOnItemLongClickListener`，长按触发 ActionMode；
    - 实现 `ActionMode.Callback` 接口，在 `onCreateActionMode()` 中加载上下文菜单、设置标题 “1 selected”，在 `onActionItemClicked()` 中实现选中项删除逻辑，在 `onDestroyActionMode()` 中重置选中状态。

## 五、实验结果

1. **ListView 功能结果**：成功实现动物列表展示，每个列表项包含动物图片与名称；点击任意项弹出 Toast 提示 “选中的动物：XXX”，符合实验要求；
2. **自定义 AlertDialog 结果**：点击触发按钮后，弹出含用户名 / 密码输入框、Cancel/Sign in 按钮的对话框；输入内容后点击 Sign in 可通过 Toast 查看输入信息，点击 Cancel 可关闭对话框，布局与交互正常；不过页面的还原度并不是很高，和原页面有一定的区别，但是架子害死一样的
3. **XML 菜单结果**：点击页面右上角溢出菜单，可展开 “字体大小”“普通菜单项”“字体颜色” 选项；选择字号后测试文本字号实时变化（10sp/16sp/20sp），选择颜色后文本颜色切换（红色 / 黑色），普通菜单项点击弹 Toast，功能全部达标；
4. **ActionMode 上下文菜单结果**：长按 ListView 列表项，顶部弹出 ActionMode 操作栏（显示 “1 selected” 与 “删除” 图标），选中项背景高亮；点击 “删除” 可移除选中项并关闭操作栏，列表实时刷新，符合实验 “上下文菜单” 要求；但是长按后展示的是一个返回箭头，而不是图片要求的√

## 六、总结

本次实验系统学习了 Android 核心界面组件的使用，重点掌握了 ListView 数据绑定、自定义 AlertDialog 构建、XML 菜单定义、ActionMode 上下文交互的技术。

同时，在做第三个作业的时候，出现 “菜单不显示” 的问题（因 Activity 主题不含 ActionBar），通过修改主题为 `Theme.AppCompat.Light.DarkActionBar` 解决。