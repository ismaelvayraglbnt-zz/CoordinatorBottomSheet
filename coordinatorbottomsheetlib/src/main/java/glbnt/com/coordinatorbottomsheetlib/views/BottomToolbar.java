package glbnt.com.coordinatorbottomsheetlib.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

import glbnt.com.coordinatorbottomsheetlib.R;

/**
 * Created by ismaelvayra on 18/12/15.
 */
public class BottomToolbar extends Toolbar {

    private float parallaxTranslation;
    private float finalHeight;

    public BottomToolbar(Context context) {
        super(context);
    }

    public BottomToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCustomAttrs(attrs);
    }

    public BottomToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCustomAttrs(attrs);
    }

    private void initCustomAttrs(AttributeSet attrs) {
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.BottomToolbar);
        parallaxTranslation = attributes.getDimension(R.styleable.BottomToolbar_parallax_translation, 0);
        finalHeight = attributes.getDimension(R.styleable.BottomToolbar_final_height, 0);
        attributes.recycle();
    }

    public float getParallaxTranslation() {
        return parallaxTranslation;
    }

    public void setParallaxTranslation(float parallaxTranslation) {
        this.parallaxTranslation = parallaxTranslation;
    }

    public float getFinalHeight() {
        return finalHeight;
    }

    public void setFinalHeight(float finalHeight) {
        this.finalHeight = finalHeight;
    }
}
