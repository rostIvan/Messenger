package trickyquestion.messenger.settings_screen.expand_list.model;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

public class SettingParent implements ParentObject {
    private List<Object> childrenList;
    private String title;
    private int imageResource;

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

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public int getImageResource() {
        return imageResource;
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
