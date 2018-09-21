
ListView的Item动画---LayoutAnimation

动画框架
1.自定义控件；2.动画；3.框架
框架：思想。便于使用；扩展性很高；利于装逼。

1.ScrollView
监听滑动的程度
onScroll(){
	//计算滑动的程度 ratio
	//执行动画--平移/缩放/颜色渐变动画
	view.setTranslationX(xxx);
	view.setAlpha(ratio);
	view2.setAlpha(ratio);
	view2.setScaleX(ratio);
}

每一个view执行的动画都不一样，而且有好呢多个view


<ImageView
	android:layout_width="wrap_content"
	android:layout_height="wrap_content"
	android:layout_margin="20dp"
	android:layout_gravity="center"
	android:src="@drawable/camera"
	discrollve:discrollve_scaleY="true"
	discrollve:discrollve_translation="fromLeft" />

系统控件无法认识自定义属性。----只有自定义控件才能识别自定义属性
Appcompat类似的思想

1.如何悄悄地给每一个子控件外面包裹一层自定义容器。
	干预LinearLayout加载子控件的过程
	原来：LinearLayout.addView(new Image());

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

2.外面的容器如何获取里面子控件的自定义属性呢？
	干预LinearLayout加载参数LayoutParam是的过程
	xml里面--->加载成类(解析)
	LayoutInflater


重复绘制---MyFrameLayout透明的，不会绘制。
优化：对象池重用
----------------有没有其他的实现思路呢？性能能更优一点呢？---------------------
看邮件。
作业：实现平行空间效果。





