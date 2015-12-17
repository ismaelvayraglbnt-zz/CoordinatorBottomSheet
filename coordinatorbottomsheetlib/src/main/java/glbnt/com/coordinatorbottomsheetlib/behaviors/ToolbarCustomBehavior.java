package glbnt.com.coordinatorbottomsheetlib.behaviors;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import glbnt.com.coordinatorbottomsheetlib.R;
import glbnt.com.coordinatorbottomsheetlib.utils.BottomSheetUtils;
import glbnt.com.coordinatorbottomsheetlib.views.BottomCollapsibleActionBar;

/**
 * Created by ismaelvayra on 11/12/15.
 */
@SuppressWarnings("unused")
public class ToolbarCustomBehavior extends AppBarLayout.ScrollingViewBehavior {

    private Context ctx;
    private float screenSizeHeight;
    private boolean mNestedScrollStarted = false;
    private float startPoint;
    private float endPoint;
    private ValueAnimator mAnimator;

    public ToolbarCustomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctx = context;
        initBehavior();
    }

    private void initBehavior() {
        screenSizeHeight = ctx.getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {

        boolean isBottomCollapsibleChild = dependency instanceof BottomCollapsibleActionBar;
        if(isBottomCollapsibleChild && (startPoint==0 || endPoint==0)) {
            BottomCollapsibleActionBar appBar = (BottomCollapsibleActionBar) dependency;
            startPoint = appBar.getAnchorPoint();
            endPoint = appBar.getEndAnimationPoint();
        }

        return isBottomCollapsibleChild;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if (dependency instanceof BottomCollapsibleActionBar) {
            float dependencyY = Math.abs(dependency.getY());
            View customToolbar = child.findViewById(R.id.fake_toolbar);
            customToolbar.setAlpha(BottomSheetUtils.getScaledAlpha(dependencyY, startPoint, endPoint));
        }

        return super.onDependentViewChanged(parent, child, dependency);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    //TODO: fix appbar drag
}
