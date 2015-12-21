package glbnt.com.coordinatorbottomsheetlib.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;

import java.lang.ref.WeakReference;

import glbnt.com.coordinatorbottomsheetlib.R;
import glbnt.com.coordinatorbottomsheetlib.behaviors.AppBarLayoutSnapBehavior;
import glbnt.com.coordinatorbottomsheetlib.interfaces.BottomCollapsibleAppBarListener;
import glbnt.com.coordinatorbottomsheetlib.interfaces.CollapseInterfaceListener;

/**
 * Created by ismaelvayra on 01/12/15.
 */
public class BottomCollapsibleActionBar extends AppBarLayout {

    private WeakReference<CoordinatorLayout> coordParent;
    private BottomCollapsingToolbarLayout collapsibleSheet;
    private float screenHeight;
    private BottomCollapsibleAppBarListener appBarLister;
    private AppBarLayoutSnapBehavior behavior;
    private CoordinatorLayout.LayoutParams params;
    private float anchorPoint;
    private float endAnimationPoint;
    private AppBarState state;

    public BottomCollapsibleActionBar(Context context) {
        super(context);
        initItems(null);
    }

    public BottomCollapsibleActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initItems(attrs);
    }

    private void initItems(@Nullable AttributeSet attrs) {

        screenHeight = getResources().getDisplayMetrics().heightPixels;

        if (attrs != null) {
            TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.BottomCollapsibleActionBar);
            anchorPoint = attributes.getDimension(R.styleable.BottomCollapsibleActionBar_anchor_point, screenHeight / 2);
            endAnimationPoint = attributes.getDimension(R.styleable.BottomCollapsibleActionBar_end_animation_point,
                    (screenHeight / 2 + screenHeight / 4));
            attributes.recycle();
        } else {
            anchorPoint = screenHeight / 2;
            endAnimationPoint = screenHeight / 2 + screenHeight / 4;
        }

        this.addOnOffsetChangedListener(new OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                BottomCollapsibleActionBar appBar = (BottomCollapsibleActionBar) appBarLayout;
                if (appBarLister != null) {
                    if (appBar.getBehavior().getAbsoluteOffset() == 0 && !getState().equals(AppBarState.ANCHORED)) {
                        state = AppBarState.COLLAPSED;
                        coordParent.get().setVisibility(GONE);
                        appBarLister.onAppBarCollapsed();
                    } else if (appBar.getBehavior().getAbsoluteOffset() == (int) screenHeight) {
                        state = AppBarState.EXPANDED;
                        appBarLister.onAppBarExpanded();
                    } else if (appBar.getBehavior().getAbsoluteOffset() == (int) anchorPoint) {
                        // TODO: fix this part, never enter here
                        state = AppBarState.ANCHORED;
                        appBarLister.onAppBarAnchored();
                    }
                }
            }
        });
    }

    public AppBarState getState() {
        return state;
    }

    public void setState(AppBarState state) {
        switch (state) {
            case ANCHORED:
                coordParent.get().setVisibility(VISIBLE);
                setAttachedAppBar();
                break;
            case COLLAPSED:
                setCollapsedAppBar();
                break;
            case EXPANDED:
                coordParent.get().setVisibility(VISIBLE);
                setExpandedAppBar();
                break;
            default:
                setCollapsedAppBar();
        }

        this.state = state;
    }

    @Override
    protected void onAttachedToWindow() {
        coordParent = new WeakReference<>((CoordinatorLayout) getParent());
        if (getChildCount() > 0 && getChildAt(0) instanceof BottomCollapsingToolbarLayout) {
            setCollapsibleSheet((BottomCollapsingToolbarLayout) getChildAt(0));
            collapsibleSheet.setCollapseInterfaceListener(new CollapseInterfaceListener() {
                @Override
                public void onTouchOutside() {
                    setState(AppBarState.COLLAPSED);
                }
            });
        }

        behavior = new AppBarLayoutSnapBehavior(anchorPoint, endAnimationPoint, screenHeight);

        super.onAttachedToWindow();
    }

    private void setAttachedAppBar() {
        state = AppBarState.ANCHORED;
        params = (CoordinatorLayout.LayoutParams) this.getLayoutParams();
        behavior.animateOffsetTo(-(int) anchorPoint);
        params.setBehavior(behavior);
        this.setLayoutParams(params);
    }

    private void setCollapsedAppBar() {
        state = AppBarState.COLLAPSED;
        setExpanded(true, true);
    }

    private void setExpandedAppBar() {
        state = AppBarState.EXPANDED;
        setExpanded(false, true);
    }

    public void setAppBarLister(BottomCollapsibleAppBarListener appBarLister) {
        this.appBarLister = appBarLister;
    }

    public BottomCollapsingToolbarLayout getCollapsibleSheet() {
        return collapsibleSheet;
    }

    public void setCollapsibleSheet(BottomCollapsingToolbarLayout collapsibleSheet) {
        this.collapsibleSheet = collapsibleSheet;
    }

    public AppBarLayoutSnapBehavior getBehavior() {
        return behavior;
    }

    public float getAnchorPoint() {
        return anchorPoint;
    }

    public void setAnchorPoint(float anchorPoint) {
        this.anchorPoint = anchorPoint;
    }

    public float getEndAnimationPoint() {
        return endAnimationPoint;
    }

    public void setEndAnimationPoint(float endAnimationPoint) {
        this.endAnimationPoint = endAnimationPoint;
    }

    public enum AppBarState {
        COLLAPSED,
        ANCHORED,
        EXPANDED,
    }

    // TODO: fix on touch outside when drag
}
