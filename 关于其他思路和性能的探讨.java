

������������UI�ܹ����-- �о������ǳ��
���������л������������ĿΣ����ĺܶ�˼·����������ǰһ���ĸо���
�������ĿΣ����Ҹ��ܵ��������Ĵ��빦�ף���������ѧ���˺ܶණ����
���������в��������Լ��ļ��⣬ϣ����ʦָ��һ����
���ڽ�ϵͳ�ؼ�����̬����һ�㣬��ȡ����XML�ļ����Զ������Եķ�ʽ��
����Ϊ�������ϻ�����ģ���ĳЩ�ϸ��Ӳ����ļ��п����޷��������Զ�������ԡ�
�ҿκ�һֱ��˼���ⲿ�֣�����˼·�������������ҷ�����ϵͳ����XML�������ֵĴ��룬
�����˿�������Ľ����������ͨ��Ԥ����XML�ļ��������е��Զ������Ա���ΪTypeArray��
���Ҹ���depth�����������������MyLinearlayout ��ʼ����ϸ��������ƥ���ӿؼ���TypeArray��
�������ӿؼ��Ķ�����Ϊ����������д�Ĳ��Դ��룬���Ĳ����Ѿ�ʵ�֣�

public class MyLinearLayout extends LinearLayout {

    private SparseArray<TypedArray> typeArrays = new SparseArray<>();
    private int mDepth;//��ǰ�ؼ� ������XML�����ļ��е����

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        try {
            parseXmlAnimInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //���������ļ� ���Զ������Ա������� key�����Զ�Ӧ�Ŀؼ��ڲ��������
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

    //���ҳ������Զ������Ե�View �����Ӧ������  ������������Ϣ������ɶ���
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

