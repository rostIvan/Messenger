package trickyquestion.messenger.screen.settings.expand_list.model;

import android.graphics.drawable.Drawable;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

public class SettingParent implements ParentObject {
    private List<Object> childrenList;
    private String title;
    private Drawable drawable;

    public SettingParent() {
    }

    public SettingParent(List<Object> mChildrenList) {
        this.childrenList = mChildrenList;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setImageDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    @Override
    public List<Object> getChildObjectList() {
        return childrenList;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        childrenList = list;
    }
}
