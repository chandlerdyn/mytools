package com.example.mytools.updownlayout;

import com.example.mytools.R;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 2个地方触发加载数据动作：上拉与列表滑动到底部。由于isLoading字段，在一次加载未完成前，其他的加载动作无效，避免了重复加载的操作。
 * <p> footer 必须在 setadapter之前添加，由于 不需要，setadapter后又删除 
 * <p> 子view不一定是listview，有可能是CircleImageView，所以获取子view用for循环
 * @author WangLuJie
 * 
 */
public class RefreshLayout extends SwipeRefreshLayout implements
		OnScrollListener {

	// 滑动到最下面时的上拉动作,scrolling与单击的区别距离
	// 在可滑动的控件中用于区别单击子控件和滑动操作的一个伐值。
	// Distance in pixels a touch can wander before we think the user is
	// scrolling
	private int mTouchSlop;

	// listview的加载中view
	private View mListviewFooter;

	// 按下时y的坐标
	private int mYDown;

	// 上拉 是否在加载
	private boolean isLoading = false;

	// 抬起时的y坐标, 与mYDown一起用于滑动到底部时判断是上拉还是下拉
	private int mLastY;

	// 上拉监听
	private OnLoadListener mOnLoadListener;

	private ListView mListView;

	public RefreshLayout(Context context) {
		super(context, null);
		// TODO Auto-generated constructor stub
	}

	public RefreshLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		// ViewConfiguration 是系统中关于视图的各种特性的常量记录对象。其中包含各种基础数据
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

		mListviewFooter = LayoutInflater.from(context).inflate(
				R.layout.listview_footer, null, false);
		
		if (mListView == null) {
			getListView();
		}

	}

	public void setAdapter(ListAdapter a) {

		if (mListView == null) {
			getListView();
		}
		if (mListView.getFooterViewsCount() == 0) {

			mListView.addFooterView(mListviewFooter);

		}

		mListView.setAdapter(a);
		// 添加只是为了在ListView的setAdapter方法时将Adapter包装成HeaderViewListAdapter。因此并不需要footer，因此添加后再移除,

		mListView.removeFooterView(mListviewFooter);

	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
		
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		final int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			// 按下
			mYDown = (int) event.getRawY();

			break;

		case MotionEvent.ACTION_MOVE:
			// 移动
			mLastY = (int) event.getRawY();

			break;
		case MotionEvent.ACTION_UP:
			// 抬起
			if (canLoad()) {
				loadData();
			}

			break;
		default:
			break;
		}

		return super.dispatchTouchEvent(event);
	}

	/**
	 * 请求数据
	 */
	private void loadData() {
		if (mOnLoadListener != null) {
			// 设置状态
			setLoading(true);

			mOnLoadListener.onLoad();

		}
	}

	/**
	 * 上拉刷新的监听
	 * 
	 * @author WangLuJie
	 * 
	 */
	public static interface OnLoadListener {
		public void onLoad();
	}

	private void getListView() {
		int child = getChildCount();
		if (child > 0) {
			//第一个是circleImageview，用for循环
			for(int i = 0;i<child;i++) {
				View childView = getChildAt(i);
				if (childView instanceof ListView) {
					mListView = (ListView) childView;
			//		mListView.addFooterView(mListviewFooter);
					// mListviewFooter.setVisibility(View.INVISIBLE);
					// // 设置滚动监听器给ListView, 使得滚动的情况下也可以自动加载
					mListView.setOnScrollListener(this);
					break;

				}
			}
			
			
		}

	}

	/**
	 * 是否可以加载更多, 条件是到了最底部, listview不在加载中, 且为上拉操作.
	 * 
	 * @return
	 */
	private boolean canLoad() {
		return isBottom() && !isLoading && isPullUp();
	}

	/**
	 * 判断是否到了最底部
	 * 
	 * @return
	 */
	private boolean isBottom() {
		if (mListView != null && mListView.getAdapter() != null) {

			return mListView.getLastVisiblePosition() == (mListView
					.getAdapter().getCount() - 1);
		}

		return false;
	}

	/**
	 * 设置状态, 数据加载完后要设置成false，对应于上拉的setRefreshing
	 * 
	 * @param loading
	 */
	public void setLoading(boolean loading) {

		isLoading = loading;
		if (isLoading) {
			 mListView.addFooterView(mListviewFooter);

		//	mListviewFooter.setVisibility(View.VISIBLE);
		} else {
			
			if(mListView!=null&&mListView.getFooterViewsCount()>0) {
				mListView.removeFooterView(mListviewFooter);
			}
			
			 
			
	//		mListviewFooter.setVisibility(View.INVISIBLE);
			mYDown = 0;
			mLastY = 0;
		}

	}

	public void setmOnLoadListener(OnLoadListener mOnLoadListener) {
		this.mOnLoadListener = mOnLoadListener;
	}

	/**
	 * 是否是上拉动作
	 * 
	 * @return
	 */
	private boolean isPullUp() {
		return (mYDown - mLastY) >= mTouchSlop;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		// 滑动到最底部也可以加载更多
		if (canLoad()) {
			loadData();
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub

	}

}
