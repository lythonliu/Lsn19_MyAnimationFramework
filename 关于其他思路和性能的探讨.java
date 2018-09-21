

昨天听了您的UI架构设计-- 感觉受益匪浅。
很荣幸能有机会听到您讲的课，您的很多思路让我有种眼前一亮的感觉。
昨天您的课，让我感受到了您深厚的代码功底，从中让我学到了很多东西。
不过其中有部分我有自己的见解，希望老师指点一二。
关于将系统控件，动态包裹一层，获取其中XML文件中自定义属性的方式。
我认为在性能上会有损耗，在某些较复杂布局文件中可能无法解析到自定义的属性。
我课后一直在思考这部分，您的思路给了我启发，我翻找了系统解析XML构建布局的代码，
发现了可以替代的解决方案。我通过预解析XML文件，将所有的自定义属性保存为TypeArray，
并且根据depth深度来保存起来。在MyLinearlayout 初始化完毕根据深度来匹配子控件和TypeArray，
来控制子控件的动画行为。以下是我写的测试代码，核心部分已经实现：

public class MyLinearLayout extends LinearLayout {

    private SparseArray<TypedArray> typeArrays = new SparseArray<>();
    private int mDepth;//当前控件 在整个XML布局文件中的深度

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        try {
            parseXmlAnimInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //解析布局文件 将自定义属性保存起来 key是属性对应的控件在布局中深度
    private void parseXmlAnimInfo() throws Exception {

        XmlResourceParser parser = getResources().getLayout(R.layout.activity_main);
        final AttributeSet newAttrs = Xml.asAttributeSet(parser);

        final int depth = parser.getDepth();
        int type;

        while (((type = parser.next()) != XmlPullParser.END_TAG ||
                parser.getDepth() > depth) && type != XmlPullParser.END_DOCUMENT) {

            if (type != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            if (getClass().getName().equals(name)) {
                mDepth = parser.getDepth();
            }

            TypedArray ta = getContext().obtainStyledAttributes(newAttrs, R.styleable.MyView);

            int count = ta.getIndexCount();
            for (int i = 0; i < count; i++) {
                int itemId = ta.getIndex(i);
                if (itemId != R.styleable.MyView_test_msg) {
                    continue;
                }
                String test_msg = ta.getString(itemId);
                if (TextUtils.isEmpty(test_msg)) {
                    continue;
                }
                typeArrays.put(parser.getDepth(), ta);
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        parseAnimChildViews();
    }

    //查找出具有自定义属性的View 及其对应的属性  有这两部分信息可以完成动画
    private void parseAnimChildViews() {
        List<View> allChilds = getAllChilds(this);
        for (int depth = 0; depth < allChilds.size(); depth++) {
            TypedArray ta = typeArrays.get(depth + mDepth);
            if (ta == null) {
                continue;
            }
            int count = ta.getIndexCount();
            for (int i = 0; i < count; i++) {
                int itemId = ta.getIndex(i);
                if (itemId != R.styleable.MyView_test_msg) {
                    continue;
                }
                String test_msg = ta.getString(itemId);
                if (TextUtils.isEmpty(test_msg)) {
                    continue;
                }
                View view = allChilds.get(depth);
                String message = view.getClass().getSimpleName() + test_msg;
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
            ta.recycle();
        }
        typeArrays.clear();
    }

    private List<View> getAllChilds(View parentView) {
        if (parentView instanceof ViewGroup) {
            ViewGroup parentViewGroup = (ViewGroup) parentView;
            List<View> views = new ArrayList<>();
            views.remove(parentViewGroup);
            views.add(parentViewGroup);
            int childCount = parentViewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                views.addAll(getAllChilds(parentViewGroup.getChildAt(i)));
            }
            return views;
        } else {
            return Collections.singletonList(parentView);
        }
    }

