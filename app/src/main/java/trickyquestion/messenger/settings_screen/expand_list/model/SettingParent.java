package trickyquestion.messenger.settings_screen.expand_list.model;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

public class SettingParent implements ParentObject {
    private List<Object> childrenlist;
    private String title;
    private int imageResourse;

    public SettingParent() {
    }

    public SettingParent(List<Object> mChildrenList) {
        this.childrenlist = mChildrenList;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setImageResourse(int imageResourse) {
        this.imageResourse = imageResourse;
    }

    public int getImageResourse() {
        return imageResourse;
    }

    @Override
    public List<Object> getChildObjectList() {
        return childrenlist;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        childrenlist = list;
    }
}
