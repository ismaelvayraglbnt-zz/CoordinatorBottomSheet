package glbnt.com.coordinatorbottomsheetlib.views;

import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import glbnt.com.coordinatorbottomsheetlib.R;
import glbnt.com.coordinatorbottomsheetlib.interfaces.CollapseInterfaceListener;


/**
 * Created by ismaelvayra on 30/11/15.
 */
public class BottomCollapsingToolbarLayout extends CollapsingToolbarLayout {

    private float height;
    private CollapseInterfaceListener collapseInterfaceListener;

    public BottomCollapsingToolbarLayout(Context context) {
        super(context);
        initFakeToolBar();
    }

    public BottomCollapsingToolbarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFakeToolBar();
    }

    public BottomCollapsingToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFakeToolBar();
    }

    private void initFakeToolBar() {
        height = getContext().getResources().getDisplayMetrics().heightPixels;

        LinearLayout layoutContainer = new LinearLayout(getContext());
        layoutContainer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layoutContainer.setOrientation(LinearLayout.VERTICAL);
        layoutContainer.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        LinearLayout layoutSpace = new LinearLayout(getContext());
        layoutSpace.setId(R.id.layoutSpace);
        layoutSpace.setClickable(true);
        layoutSpace.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) height));
        layoutSpace.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                collapseInterfaceListener.onTouchOutside();
                return true;
            }
        });

        layoutContainer.addView(layoutSpace);

        this.addView(layoutContainer);

    }

    public void setCollapseInterfaceListener(CollapseInterfaceListener collapseInterfaceListener) {
        this.collapseInterfaceListener = collapseInterfaceListener;
    }
}
