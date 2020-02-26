package com.microastudio.demo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.microastudio.demo.util.CommonUtil;
import com.microastudio.demo.util.ConvertUtils;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.txt_value)
    TextView tvValue; // 选种值
    @BindViews({R.id.radio1, R.id.radio2, R.id.radio3, R.id.radio4, R.id.radio5, R.id.radio6, R.id.radio7})
    List<CheckBox> radios; // 单选组
    @BindViews({R.id.checkbox1, R.id.checkbox2, R.id.checkbox3, R.id.checkbox4, R.id.checkbox5, R.id.checkbox6, R.id.checkbox7})
    List<CheckBox> checkBoxes; // 多选组


    private FlexboxLayout flexboxLayout;
    private List<EvaluateBean> mList = new ArrayList();//单选数据mList
    //多选数据  为了防止单选多选数据冲突  此处多new一个mListMore 来使用  具体情况可灵活根据后台动态数据而定
    private List<EvaluateBean> mListMore = new ArrayList();
    private FlexboxLayout flexboxLayout1;


    private RecyclerView mRecyclerView;
    private ArrayList<SearchHistoryBean> mSearchHistoryBeanArrayList;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // 如果有初始状态需要显示，参见：
        // com.dommy.selectcustom.util.CommonUtil.checkOne()
        // com.dommy.selectcustom.util.CommonUtil.checkMany()

        initView();
        initFlexboxView();
        initFlexboxRecycleView();

    }

    private void initFlexboxRecycleView() {
        mRecyclerView = findViewById(R.id.recycler_view);

        //初始化集合
        mSearchHistoryBeanArrayList = new ArrayList<SearchHistoryBean>();
        String[] testDatas = new String[]{"牙刷", "灭蚊器", "移动空调", "吸尘器", "布衣柜", "收纳箱 书箱", "暑期美食满99减15", "挂烫机", "吸水拖把", "反季特惠"};
        for (int i = 0; i < testDatas.length; i++) {
            SearchHistoryBean channelBean = new SearchHistoryBean();
            channelBean.setSearchTitle(testDatas[i]);
            //获取当前日期
            Calendar calendar = Calendar.getInstance();
            channelBean.setSearchDate(calendar.getTime());

            mSearchHistoryBeanArrayList.add(channelBean);
        }

        //设置布局管理器
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(MainActivity.this);
        //flexDirection 属性决定主轴的方向（即项目的排列方向）。类似 LinearLayout 的 vertical 和 horizontal。
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);//主轴为水平方向，起点在左端。
        //flexWrap 默认情况下 Flex 跟 LinearLayout 一样，都是不带换行排列的，但是flexWrap属性可以支持换行排列。
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);//按正常方向换行
        //justifyContent 属性定义了项目在主轴上的对齐方式。
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);//交叉轴的起点对齐。

        mRecyclerView.setLayoutManager(flexboxLayoutManager);

        //设置适配器
        if (myAdapter == null) {
            //设置适配器
            myAdapter = new MyAdapter(this, mSearchHistoryBeanArrayList);
            myAdapter.setSelectMode(TagAdapter.SelectMode.SINGLE_SELECT);
            mRecyclerView.setAdapter(myAdapter);

            //添加分割线
            //设置添加删除动画
            //调用ListView的setSelected(!ListView.isSelected())方法，这样就能及时刷新布局
            mRecyclerView.setSelected(true);
        } else {
            myAdapter.notifyDataSetChanged();
        }

        //监听单选
        myAdapter.setOnItemSingleSelectListener(new TagAdapter.OnItemSingleSelectListener() {
            @Override
            public void onSelected(int itemPosition, boolean isSelected) {

                //Toast.makeText(MainActivity.this, "selectedPosition:" + itemPosition  +" == "+ myAdapter.getSingleSelectedPosition(), Toast.LENGTH_SHORT).show();
            }
        });

        // 监听点击事件
        myAdapter.setOnItemClickListener(new TagAdapter.OnItemClickListener() {
            @Override
            public void onClicked(int itemPosition) {
                Toast.makeText(MainActivity.this, "itemPosition:" + itemPosition, Toast.LENGTH_SHORT).show();
            }
        });
        //监听多选
        myAdapter.setOnItemMultiSelectListener(new TagAdapter.OnItemMultiSelectListener() {
            @Override
            public void onSelected(TagAdapter.Operation operation, int itemPosition, boolean isSelected) {
                Toast.makeText(MainActivity.this, "multiSelectedPosition:" + itemPosition, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initFlexboxView() {
        for (int i = 0; i < 10; i++) {
            mList.add(new EvaluateBean("标签选择测试字数和适应度" + Math.random()));
            mListMore.add(new EvaluateBean("标签选择测试字数和适应度" + Math.random()));
        }

        //服务评价 标签列表
        flexboxLayout = (FlexboxLayout) findViewById(R.id.flexboxLayout);
        flexboxLayout1 = (FlexboxLayout) findViewById(R.id.flexboxLayout1);
        for (int i = 0; i < mList.size(); i++) {
            addChildToFlexboxLayout(mList.get(i));
            addChildToFlexboxLayout1(mListMore.get(i));
        }
    }

    private void initView() {
        RadioGroup radiogroup = (RadioGroup) findViewById(R.id.gadiogroup);
        addview(radiogroup);
    }

    /**
     * 单选项点击事件
     *
     * @param checkBox
     */
    @OnClick({R.id.radio1, R.id.radio2, R.id.radio3, R.id.radio4, R.id.radio5, R.id.radio6, R.id.radio7})
    void changeRadios(CheckBox checkBox) {
        CommonUtil.unCheck(radios);
        checkBox.setChecked(true);

        // 显示选中项值
        String checkedValues = CommonUtil.getOne(radios);
        tvValue.setText("选中了：" + checkedValues);
    }

    /**
     * 复选项点击事件
     *
     * @param checkBox
     */
    @OnClick({R.id.checkbox1, R.id.checkbox2, R.id.checkbox3, R.id.checkbox4, R.id.checkbox5, R.id.checkbox6, R.id.checkbox7})
    void changeCheckBoxs(CheckBox checkBox) {
        // 显示选中项值
        String checkedValues = CommonUtil.getMany(checkBoxes);
        tvValue.setText("选中了：" + checkedValues);
    }

    public List<String> getListSize() {
        List<String> list = new ArrayList<String>();
        list.add("服装33333");
        list.add("玩具44444");
        list.add("饰品5555");
        list.add("饰品6666");
        list.add("文具7777");
        list.add("文具8888");
        list.add("文具9999");
        list.add("服装33333");
        list.add("玩具44444");
        list.add("饰品5555");
        list.add("饰品6666");
        list.add("文具7777");
        list.add("文具8888");
        list.add("文具9999");
        list.add("服装33333");
        list.add("玩具44444");
        list.add("饰品5555");
        list.add("饰品6666");
        list.add("文具7777");
        list.add("文具8888");
        list.add("文具9999");
        return list;
    }

    //动态添加视图
    public void addview(RadioGroup radiogroup) {

        int index = 0;
        for (String ss : getListSize()) {

            RadioButton button = new RadioButton(this);
            setRaidBtnAttribute(button, ss, index);

            radiogroup.addView(button);

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button
                    .getLayoutParams();
            layoutParams.setMargins(0, 0, ConvertUtils.dp2px(this, 10), 0);//4个参数按顺序分别是左上右下
            button.setLayoutParams(layoutParams);
            index++;
        }


    }


    private void setRaidBtnAttribute(final RadioButton codeBtn, String btnContent, int id) {
        if (null == codeBtn) {
            return;
        }
        codeBtn.setBackgroundResource(R.drawable.radio_group_selector);
        codeBtn.setTextColor(this.getResources().getColorStateList(R.drawable.color_radiobutton));
        codeBtn.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
        //codeBtn.setTextSize( ( textSize > 16 )?textSize:24 );
        codeBtn.setId(id);
        codeBtn.setText(btnContent);
        //codeBtn.setPadding(2, 0, 2, 0);

        codeBtn.setGravity(Gravity.CENTER);
        codeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, codeBtn.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        //DensityUtilHelps.Dp2Px(this,40)
        LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ConvertUtils.dp2px(this, 25));

        codeBtn.setLayoutParams(rlp);
    }


    /**
     * 添加孩子到布局中 单选
     */
    private void addChildToFlexboxLayout(final EvaluateBean bean) {
        View view = LayoutInflater.from(this).inflate(R.layout.tv, null);
        TextView tv = view.findViewById(R.id.tv);
        tv.setText(bean.getName());
        view.setTag(bean);
        if (bean.isChecked()) {
            tv.setBackgroundResource(R.drawable.checked_bg);
            tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        } else {
            tv.setBackgroundResource(R.drawable.normal_bg);
            tv.setTextColor(ContextCompat.getColor(this, R.color.black));
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean.setChecked(true);
                for (EvaluateBean labelBean : mList) {
                    if (!labelBean.equals(bean)) {
                        labelBean.setChecked(false);
                    }
                }
                checkLabeel();
            }
        });
        flexboxLayout.addView(view);
    }

    private void checkLabeel() {
        flexboxLayout.removeAllViews();
        for (EvaluateBean labelBean : mList) {
            addChildToFlexboxLayout(labelBean);
        }
    }

    /**
     * 添加孩子到布局中 多选
     */
    private void addChildToFlexboxLayout1(final EvaluateBean bean) {
        View view = LayoutInflater.from(this).inflate(R.layout.tv, null);
        TextView tv = view.findViewById(R.id.tv);
        tv.setText(bean.getName());
        view.setTag(bean);
        if (bean.isChecked()) {
            tv.setBackgroundResource(R.drawable.checked_bg);
            tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        } else {
            tv.setBackgroundResource(R.drawable.normal_bg);
            tv.setTextColor(ContextCompat.getColor(this, R.color.black));
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean.setChecked(!bean.isChecked());
                checkLabeel1();
            }
        });
        flexboxLayout1.addView(view);
    }

    private void checkLabeel1() {
        flexboxLayout1.removeAllViews();
        for (EvaluateBean labelBean : mListMore) {
            addChildToFlexboxLayout1(labelBean);
        }
    }

}
