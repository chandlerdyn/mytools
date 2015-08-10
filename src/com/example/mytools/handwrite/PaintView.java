package com.example.mytools.handwrite;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Bitmap.Config;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class PaintView extends View {

	private static final int BACKGROUND_COLOR = Color.WHITE;
	private Paint paint;
	private Canvas cacheCanvas;
	private Bitmap cachebBitmap;

	private static final String TAG = "handwrite";
	// Path类可以预先在View上将N个点连成一条"路径",然后调用Canvas的drawPath(path,paint)即可沿着路径绘制图形
	private Path path;

	public Bitmap getCachebBitmap() {
		return cachebBitmap;
	}

	public PaintView(Context context) {
		super(context);
		init();
	}

	// C2多了一个AttributeSet类型的参数，在通过布局文件xml创建一个view时，
	// 这个参数会将xml里设定的属性传递给构造函数。如果你采用xml inflate的方法却没有在code里实现C2，
	// 那么运行时就会报错。

	private void init() {
		paint = new Paint();

		// 给Paint加上抗锯齿标志。由于在3D图像中，受分辨的制约，物体边缘总会或多或少的呈现三角形的锯齿，
		// 而抗锯齿就是指对图像边缘进行柔化处理，使图像边缘看起来更平滑，更接近实物的物体。
		// 它是提高画质以使之柔和的一种方法。
		paint.setAntiAlias(true);

		// 用于设置画笔的空心线宽。该方法在矩形、圆形等图形上有明显的效果。
		paint.setStrokeWidth(3);

		paint.setStyle(Paint.Style.STROKE);

		// 画笔颜色
		paint.setColor(Color.BLACK);
		path = new Path();

		// 生成的缓存的bitmap
		cachebBitmap = Bitmap.createBitmap(400, 300, Config.ARGB_8888);

		cacheCanvas = new Canvas(cachebBitmap);

		// 画布颜色
		cacheCanvas.drawColor(Color.WHITE);
	}

	public void clear() {
		if (cacheCanvas != null) {

			paint.setColor(BACKGROUND_COLOR);
			cacheCanvas.drawPaint(paint);
			paint.setColor(Color.BLACK);
			cacheCanvas.drawColor(Color.WHITE);
			invalidate();
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// canvas.drawColor(BRUSH_COLOR);
		Log.e(TAG, "onDraw");
		canvas.drawBitmap(cachebBitmap, 0, 0, null);
		// Path类可以预先在View上将N个点连成一条"路径",然后调用Canvas的drawPath(path,paint)即可沿着路径绘制图形
		canvas.drawPath(path, paint);
	}

	// 这个方法会在这个view的大小发生改变是被系统调用，我们要记住的就是view大小变化，这个方法就被执行就可以了
	/**
	 * 在此只是在初始化时只调用了一次
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		
		Log.e(TAG, "onSizeChanged");

		int curW = cachebBitmap != null ? cachebBitmap.getWidth() : 0;
		int curH = cachebBitmap != null ? cachebBitmap.getHeight() : 0;
		if (curW >= w && curH >= h) {
			return;
		}

		if (curW < w)
			curW = w;
		if (curH < h)
			curH = h;

		Bitmap newBitmap = Bitmap.createBitmap(curW, curH,
				Bitmap.Config.ARGB_8888);
		Canvas newCanvas = new Canvas();
		newCanvas.setBitmap(newBitmap);
		if (cachebBitmap != null) {
			newCanvas.drawBitmap(cachebBitmap, 0, 0, null);
		}
		cachebBitmap = newBitmap;
		cacheCanvas = newCanvas;
	}

	// move 的前一个点，
	private float cur_x, cur_y;
	

	/**
	 * 触发一次down、move、up，就invalidate一次，就调用一次ondraw
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		float x = event.getX();
		float y = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			Log.e(TAG, "ACTION_DOWN");
			cur_x = x;
			cur_y = y;
			// moveTo 不会进行绘制，只用于移动移动画笔。开始点
			path.moveTo(cur_x, cur_y);
			break;
		}

		case MotionEvent.ACTION_MOVE: {
			Log.e(TAG, "ACTION_MOVE");
			// quadTo 用于绘制圆滑曲线，即贝塞尔曲线
			path.quadTo(cur_x, cur_y, x, y);
			cur_x = x;
			cur_y = y;
			break;
		}

		case MotionEvent.ACTION_UP: {
			Log.e(TAG, "ACTION_UP");
			cacheCanvas.drawPath(path, paint);
			path.reset();
			break;
		}
		}

		
		invalidate();

		return true;
	}

}
