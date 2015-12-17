package glbnt.com.coordinatorbottomsheetlib.utils;

/**
 * Created by ismaelvayra on 17/12/15.
 */
public class BottomSheetUtils {

    public static float getScaledAlpha(float position, float startPoint, float endPoint) {

        float alpha;
        if (position<startPoint) {
            alpha = 1;
        } else if (position >= startPoint && position <= endPoint) {
            alpha = 1-(1/(endPoint-startPoint))*position +startPoint/(endPoint-startPoint);
        } else {
            alpha = 0;
        }

        return alpha;
    }

}
