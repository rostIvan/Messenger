package trickyquestion.messenger.setting.expandList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

import trickyquestion.messenger.R;

public class ExpandableAdapter extends ExpandableRecyclerAdapter<ItemParentViewHolder, ItemChildViewHolder> {

    private final List<ParentObject> parentItemList;

    public ExpandableAdapter(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);
        this.parentItemList = parentItemList;
    }

    @Override
    public ItemParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        final View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.item_setting_parent, viewGroup, false
        );
        return new ItemParentViewHolder(itemView);
    }

    @Override
    public ItemChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        final View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.item_setting_child, viewGroup, false
        );
        return new ItemChildViewHolder(itemView);
    }

    @Override
    public void onBindParentViewHolder(ItemParentViewHolder itemParentViewHolder, int i, Object parentObject) {
        final SettingParent parent = (SettingParent) parentObject;
        itemParentViewHolder.settingParentTitle.setText(parent.getTitle());
        final Drawable icon = itemParentViewHolder.itemView.getResources().getDrawable(parent.getImageResourse());
        itemParentViewHolder.imageView.setImageDrawable(icon);
    }

    @Override
    public void onBindChildViewHolder(ItemChildViewHolder itemChildViewHolder, int i, Object childObject) {
        final SettingChild child = (SettingChild) childObject;
        itemChildViewHolder.title.setText(child.getTitle());
        itemChildViewHolder.checkBox.setVisibility(child.getTitle().equals("Ask for password") ? View.VISIBLE : View.GONE);
        itemChildViewHolder.checkBox.setChecked(child.isChecked());
        setBigSeparateLine(
                itemChildViewHolder,
                child.getTitle().equals("Log out")
        );
        final Drawable icon = itemChildViewHolder.itemView.getResources().getDrawable(child.getImageResourse());
        itemChildViewHolder.imageView.setImageDrawable(icon);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    private void setBigSeparateLine(ItemChildViewHolder itemChildViewHolder, boolean conditional ) {
        if (conditional) {
            itemChildViewHolder.smallSeparateLine.setVisibility(View.GONE);
            itemChildViewHolder.bigSeparateLine.setVisibility(View.VISIBLE);
        }
        else {
            itemChildViewHolder.smallSeparateLine.setVisibility(View.VISIBLE);
            itemChildViewHolder.bigSeparateLine.setVisibility(View.GONE);
        }
    }
}
