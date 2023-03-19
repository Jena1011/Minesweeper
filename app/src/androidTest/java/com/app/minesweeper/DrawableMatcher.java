package com.app.minesweeper;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.ResourceBundle;

public class DrawableMatcher extends TypeSafeMatcher<View> {

    private String resourceName = null;
    private final int expectedResource;

    public DrawableMatcher(int expectedResourceId) {
        this.expectedResource = expectedResourceId;
    }

    // 比對圖片
    @Override
    protected boolean matchesSafely(View target) {
        // 設定resourceName
        Resources resources = target.getContext().getResources();
        resourceName = resources.getResourceEntryName(expectedResource);
        // 利用目標圖片和實際圖片分別生成Bitmap物件，再做比較
        Drawable expectedDrawable = ContextCompat.getDrawable(target.getContext(), expectedResource);
        Bitmap actualBitmap = getBitmap(((ImageView) target).getDrawable());
        Bitmap expectedBitmap = null;
        if(expectedDrawable!=null){
            expectedBitmap = getBitmap(expectedDrawable);
        }
        return actualBitmap.sameAs(expectedBitmap);
    }

    // 生成Bitmap物件
    private Bitmap getBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    // 描述目標資源 ID 和名稱
    @Override
    public void describeTo(Description description) {
        description.appendText("resource id:");
        description.appendValue(expectedResource);
        if (resourceName != null) {
            description.appendText(resourceName);
        }
    }
}
