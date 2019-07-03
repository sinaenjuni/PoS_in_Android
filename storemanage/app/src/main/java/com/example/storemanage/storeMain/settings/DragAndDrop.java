package com.example.storemanage.storeMain.settings;

import android.content.ClipData;
import android.content.ClipDescription;
import android.view.View;

public class DragAndDrop {

}


final class LongClickListener implements
        View.OnLongClickListener {

    public boolean onLongClick(View view) {

        // 태그 생성
        ClipData.Item item = new ClipData.Item(
                (CharSequence) view.getTag());

        String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
        ClipData data = new ClipData(view.getTag().toString(),
                mimeTypes, item);
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                view);

        view.startDrag(data, // data to be dragged
                shadowBuilder, // drag shadow
                view, // 드래그 드랍할  Vew
                0 // 필요없은 플래그
        );

        view.setVisibility(View.INVISIBLE);
        return true;
    }
}


