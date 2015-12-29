package com.example.myview;

import java.util.Random;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;

public class CaptchaVIew extends View implements OnClickListener {
	private static final String TAG = "CaptchaVIew";
	private float dimension;
	private String text;
	private int color;
	private Paint paint;
	private Rect bounds;

	public CaptchaVIew(Context context) {
		this(context, null);
	}

	public CaptchaVIew(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CaptchaVIew(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.CustomTitleView, defStyleAttr, 0);
		for (int i = 0; i < array.getIndexCount(); i++) {

			int index = array.getIndex(i);

			switch (index) {
			case R.styleable.CustomTitleView_titleText:
				text = array.getString(index);
				break;
			case R.styleable.CustomTitleView_titleTextColor:
				color = array.getColor(index, Color.BLACK);
				break;
			case R.styleable.CustomTitleView_titleTextSize:
				dimension = array.getDimension(index, (int) TypedValue
						.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16,
								getResources().getDisplayMetrics()));
				break;
			}
		}
		array.recycle();
		setOnClickListener(this);
		paint = new Paint();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int width;
		int height;

		bounds = new Rect();
		paint.setTextSize(dimension);
		paint.setColor(color);
		paint.getTextBounds(text, 0, text.length(), bounds);

		if (widthMode == MeasureSpec.AT_MOST) {
			width = getPaddingLeft() + bounds.width() + getPaddingRight() + 10;
		} else {
			width = widthSize;
		}

		if (heightMode == MeasureSpec.AT_MOST) {
			height = getPaddingTop() + bounds.height() + getPaddingBottom()
					+ 10;
		} else {
			height = heightSize;
		}

		setMeasuredDimension(width, height);

		Log.e(TAG, "onMeasure");
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.GRAY);

		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		canvas.drawText(text, width / 2 - bounds.width() / 2, height / 2
				+ bounds.height() / 2, paint);
		Log.e(TAG, "onDraw");
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		Log.e(TAG, "onLayout");
	}

	@Override
	public void onClick(View v) {
		text = new Random().nextInt(9999) + "";
		requestLayout();
		// invalidate();只会调用onDraw()方法；requestLayout()会调用onMeasure()方法和onLyout()方法,如果内容改变了对调用onDraw()方法重绘，否则不调用onDraw()方法
	}
}
