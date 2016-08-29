package me.khrystal.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/8/30
 * update time:
 * email: 723526676@qq.com
 */
public class BallQuadranglePathLoadingView extends View {

    private final int STATUS_STOP                  = 0;
    private final int STATUS_LOADING               = 1;
    private static final int DEFAULT_WIDTH         = 50;
    private static final int DEFAULT_HEIGHT        = 50;
    private static final int DEFAULT_PAINT_WIDTH   = 2;
    private static final int DEFAULT_DISTANCE      = 10;
    private static final int DEFAULT_ANGLE         = 0;
    private static final int DEFAULT_ANIM_DURATION = 2000;
    private static final int DEFAULT_PAINT_COLOR   = Color.parseColor("#ffffff");


    private int mStatus = STATUS_STOP;
    private Paint mPaint;
    private int mWidth, mHeight;
    private int mCircleColor;
    private int mAnimDuration;
    private int mCircleRadius;
    private int mCircleMaxDistance;
    private int mAnimRepeatCount;
//  Canvas rotate angle
    private int mCanvasAngle;
    private float mCircleY;
    private int[] mMultiColors = new int[]{-1, -1, -1, -1};
    private Path mPath;
    private int mStep;

    public BallQuadranglePathLoadingView(Context context) {
        this(context, null);
    }

    public BallQuadranglePathLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BallQuadranglePathLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BallQuadranglePathLoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.BallQuadranglePathLoadingView);
        mCircleColor = ta.getColor(R.styleable.BallQuadranglePathLoadingView_circleColor, DEFAULT_PAINT_COLOR);
        mCircleRadius = ta.getDimensionPixelSize(R.styleable.BallQuadranglePathLoadingView_circleRadius, dp2px(DEFAULT_PAINT_WIDTH));
        mCircleMaxDistance = ta.getDimensionPixelSize(R.styleable.BallQuadranglePathLoadingView_circleMaxDistance, dp2px(DEFAULT_DISTANCE));
        mAnimDuration = ta.getInt(R.styleable.BallQuadranglePathLoadingView_duration, DEFAULT_ANIM_DURATION);
        mAnimRepeatCount = ta.getInt(R.styleable.BallQuadranglePathLoadingView_animRepeatCount, ValueAnimator.INFINITE);
        mCanvasAngle = ta.getInt(R.styleable.BallQuadranglePathLoadingView_angle, DEFAULT_ANGLE);
        ta.recycle();

        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(mCircleColor);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        mWidth = getWidth() - paddingLeft - paddingRight;
        mHeight = getHeight() - paddingTop - paddingBottom;
        setPaintStrokeWidth();
    }

    private void setPaintStrokeWidth() {
        mPaint.setStrokeWidth(mCircleRadius * 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        switch (mStep % 3) {
//            case 0:
//                break;
//            case 1:
                for (int i = 0; i < 4; i++) {
                    mPaint.setColor(mMultiColors[i] == -1 ? mCircleColor : mMultiColors[i]);
                    draw(canvas, mWidth / 2 - mCircleMaxDistance / 2, mHeight / 2 + mCircleY, mPaint, mCanvasAngle + i * 90);
                }
//                break;
//            case 2:
//                break;
//        }

    }

    private void draw(Canvas canvas, float x, float y, @NonNull Paint paint, int rotate) {
        canvas.rotate(rotate, mWidth / 2, mHeight / 2);
        canvas.drawCircle(x, y, mCircleRadius, paint);
        canvas.rotate(-rotate, mWidth / 2, mHeight / 2);
    }

//    private void startZoomInAnim() {
//        Collection<Animator> animList = new ArrayList<>();
//
//        ValueAnimator canvasRotateAnim = ValueAnimator.ofInt(mCanvasAngle, mCanvasAngle + 45, mCanvasAngle + 90);
//        canvasRotateAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                mCanvasAngle = (int) animation.getAnimatedValue();
//            }
//        });
//
//        animList.add(canvasRotateAnim);
//
//        ValueAnimator circleYAnim = ValueAnimator.ofFloat(0, mCircleMaxDistance / 4, mCircleMaxDistance / 2);
//        circleYAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                mCircleY = (float) animation.getAnimatedValue();
//                invalidate();
//            }
//        });
//        animList.add(circleYAnim);
//
//        AnimatorSet animationSet = new AnimatorSet();
//        animationSet.setDuration(mAnimDuration);
//        animationSet.playTogether(animList);
//        animationSet.setInterpolator(new LinearInterpolator());
//        animationSet.addListener(new AnimatorListener() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                if (mStatus == STATUS_LOADING) {
//                    mStep++;
//                    startRotateAnim();
//                }
//            }
//        });
//        animationSet.start();
//    }

//    private void startZoomOutAnim() {
//        Collection<Animator> animList = new ArrayList<>();
//
//        ValueAnimator canvasRotateAnim = ValueAnimator.ofInt(mCanvasAngle + 270, mCanvasAngle + 315, mCanvasAngle + 360);
//        canvasRotateAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                mCanvasAngle = (int) animation.getAnimatedValue();
//            }
//        });
//
//        animList.add(canvasRotateAnim);
//
//        ValueAnimator circleYAnim = ValueAnimator.ofFloat(mCircleMaxDistance / 2, mCircleMaxDistance / 4, 0);
//        circleYAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                mCircleY = (float) animation.getAnimatedValue();
//                invalidate();
//            }
//        });
//        animList.add(circleYAnim);
//
//        AnimatorSet animationSet = new AnimatorSet();
//        animationSet.setDuration(mAnimDuration);
//        animationSet.playTogether(animList);
//        animationSet.setInterpolator(new LinearInterpolator());
//        animationSet.addListener(new AnimatorListener() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                if (mStatus == STATUS_LOADING) {
//                    mStep = 0;
//                    startZoomInAnim();
//                }
//            }
//        });
//        animationSet.start();
//    }

    private void startRotateAnim() {

        Collection<Animator> animList = new ArrayList<>();

        ValueAnimator circleSizeAnim = ValueAnimator.ofInt(mCircleRadius, mCircleRadius * 2, mCircleRadius);
        circleSizeAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCircleRadius = (int)animation.getAnimatedValue();
            }
        });
        // TODO: 16/8/30
        circleSizeAnim.setRepeatCount(mAnimRepeatCount);
        animList.add(circleSizeAnim);

//        ValueAnimator canvasRotateAnim = ValueAnimator.ofInt(mCanvasAngle + 90, mCanvasAngle + 270, mCanvasAngle + 360);
        ValueAnimator canvasRotateAnim = ValueAnimator.ofInt(mCanvasAngle + 45, mCanvasAngle + 315, mCanvasAngle + 585);
        canvasRotateAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCanvasAngle = (int) animation.getAnimatedValue();
            }
        });
        // TODO: 16/8/30
        canvasRotateAnim.setRepeatCount(mAnimRepeatCount);
        animList.add(canvasRotateAnim);

//        ValueAnimator circleYAnim = ValueAnimator.ofFloat(mCircleMaxDistance / 2, mCircleMaxDistance, mCircleMaxDistance / 2);
        ValueAnimator circleYAnim = ValueAnimator.ofFloat(0, mCircleMaxDistance, 0);
        circleYAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCircleY = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        // TODO: 16/8/30
        circleYAnim.setRepeatCount(mAnimRepeatCount);
        animList.add(circleYAnim);

        AnimatorSet animationSet = new AnimatorSet();
        animationSet.setDuration(mAnimDuration);
        animationSet.playTogether(animList);
        animationSet.setInterpolator(new LinearInterpolator());
        animationSet.addListener(new AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mStatus == STATUS_LOADING) {
                    //mStep++;
                    mStep = 1;
                    //startZoomOutAnim();
                }
            }
        });
        animationSet.start();
    }

    /**
     * set repeatcount, if value = -1 the meaning is INFINITE
     * @param repeatCount
     */
    public void setRepeatCount(int repeatCount) {
        mAnimRepeatCount = repeatCount;
        reset();
    }

    public void setDefaultAngle(int angle) {
        mCanvasAngle = angle;
        reset();
    }

    public void start() {
        if (mStatus == STATUS_STOP) {
            mStatus = STATUS_LOADING;
            //startZoomInAnim();
            startRotateAnim();
        }
    }



    /**
     * set muti-color max parameter number is 4 
     * redundancy parameter will invalid
     * @param colors
     */
    public void setMutiCircleColor(int...colors) {
        for (int i = 0; i < mMultiColors.length; i++) {
            mMultiColors[i] = colors[i];
        }
    }

    /**
     * Set paint color alpha
     * @param alpha alpha
     */
    public void setPaintAlpha(int alpha) {
        mPaint.setAlpha(alpha);
        invalidate();
    }

    public void reset() {
        if (mStatus == STATUS_LOADING) {
            mStatus = STATUS_STOP;
        }
        setPaintStrokeWidth();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidthSize(widthMeasureSpec), measureHeightSize(heightMeasureSpec));
    }

    /**
     * measure width
     * @param measureSpec spec
     * @return width
     */
    private int measureWidthSize(int measureSpec) {
        int defSize = dp2px(DEFAULT_WIDTH);
        int specSize = MeasureSpec.getSize(measureSpec);
        int specMode = MeasureSpec.getMode(measureSpec);

        int result = 0;
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                result = Math.min(defSize, specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    /**
     * measure height
     * @param measureSpec spec
     * @return height
     */
    private int measureHeightSize(int measureSpec) {
        int defSize = dp2px(DEFAULT_HEIGHT);
        int specSize = MeasureSpec.getSize(measureSpec);
        int specMode = MeasureSpec.getMode(measureSpec);

        int result = 0;
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                result = Math.min(defSize, specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    private int dp2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
