
ListView��Item����---LayoutAnimation

�������
1.�Զ���ؼ���2.������3.���
��ܣ�˼�롣����ʹ�ã���չ�Ժܸߣ�����װ�ơ�

1.ScrollView
���������ĳ̶�
onScroll(){
	//���㻬���ĳ̶� ratio
	//ִ�ж���--ƽ��/����/��ɫ���䶯��
	view.setTranslationX(xxx);
	view.setAlpha(ratio);
	view2.setAlpha(ratio);
	view2.setScaleX(ratio);
}

ÿһ��viewִ�еĶ�������һ���������к��ض��view


<ImageView
	android:layout_width="wrap_content"
	android:layout_height="wrap_content"
	android:layout_margin="20dp"
	android:layout_gravity="center"
	android:src="@drawable/camera"
	discrollve:discrollve_scaleY="true"
	discrollve:discrollve_translation="fromLeft" />

ϵͳ�ؼ��޷���ʶ�Զ������ԡ�----ֻ���Զ���ؼ�����ʶ���Զ�������
Appcompat���Ƶ�˼��

1.������ĵظ�ÿһ���ӿؼ��������һ���Զ���������
	��ԤLinearLayout�����ӿؼ��Ĺ���
	ԭ����LinearLayout.addView(new Image());

	LinearLayout.addView(new MyFrameLayout);
	MyFrameLayout.addView(ImageView);

	<LinearLayout>
		<MyFrameLayout
			discrollve:discrollve_scaleY="true"
			discrollve:discrollve_translation="fromLeft" >
			<ImageView
				android:layout_width="wrap_content"
				android:layout_height="wrap_content"
				android:layout_margin="20dp"
				android:layout_gravity="center"
				android:src="@drawable/camera"
				/>
		</MyFrameLayout>
	</LinearLayout>

2.�����������λ�ȡ�����ӿؼ����Զ��������أ�
	��ԤLinearLayout���ز���LayoutParam�ǵĹ���
	xml����--->���س���(����)
	LayoutInflater


�ظ�����---MyFrameLayout͸���ģ�������ơ�
�Ż������������
----------------��û��������ʵ��˼·�أ������ܸ���һ���أ�---------------------
���ʼ���
��ҵ��ʵ��ƽ�пռ�Ч����





