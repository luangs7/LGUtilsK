package br.com.luan2.lgutilsk.utils.extras;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import br.com.luan2.lgutilsk.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Luan on 24/11/17.
 */
public class ProfileBehavior extends CoordinatorLayout.Behavior<CircleImageView>{

    private int mDependencyHeight;
    private int mProfilePicMargin;
    private int mActionBarHeight;

    public ProfileBehavior(Context context) {
        init(context);
    }

    public ProfileBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){

        mDependencyHeight = (int)context.getResources()
                .getDimension(R.dimen.toolbar_size);

        mProfilePicMargin = (int) dpToPx(context, 8f);

        mActionBarHeight = getActionBarHeight(context);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, CircleImageView child, View dependency) {

        if(dependency instanceof AppBarLayout){
            return true;
        }
        return false;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, CircleImageView child, View dependency) {
        // Translate the CircleImageView to the right
        // Calculate first, what fraction the AppBarLayout has shrunk by
        int bottom = dependency.getBottom();
        int top = dependency.getTop();
        int viewHeight = bottom;

        float proportion = Math.min(1, 1 - ((viewHeight - mActionBarHeight) / (float)(mDependencyHeight - mActionBarHeight)));
        // Translate the child by this proportion
        float translationX = (parent.getWidth()/2 - child.getWidth()/2 - mProfilePicMargin) * proportion;
        float translationY = (child.getHeight()/2 - mProfilePicMargin) * proportion;

        child.setTranslationX(translationX);
        child.setTranslationY(translationY);

        return true;
    }

    public int dpToPx(Context context,float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }


    private int getActionBarHeight(Context context) {
        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,context.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }
}

