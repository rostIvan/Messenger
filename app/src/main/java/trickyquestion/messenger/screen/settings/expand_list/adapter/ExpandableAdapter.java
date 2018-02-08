package trickyquestion.messenger.screen.settings.expand_list.adapter;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.widget.CompoundButtonCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

import trickyquestion.messenger.R;
import trickyquestion.messenger.screen.settings.expand_list.listeners.OnChildItemClickListener;
import trickyquestion.messenger.screen.settings.expand_list.listeners.OnParentItemClickListener;
import trickyquestion.messenger.screen.settings.expand_list.model.SettingChild;
import trickyquestion.messenger.screen.settings.expand_list.model.SettingParent;
import trickyquestion.messenger.screen.settings.presenter.ISettingPresenter;
import trickyquestion.messenger.util.android.preference.ThemePreference;

public class ExpandableAdapter extends ExpandableRecyclerAdapter<ItemParentViewHolder, ItemChildViewHolder> {

    private final ISettingPresenter presenter;

    public ExpandableAdapter(final ISettingPresenter presenter, List<ParentObject> parentItemList) {
        super(presenter.getView().getContext(), parentItemList);
        this.presenter = presenter;
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
        itemParentViewHolder.imageView.setImageDrawable(parent.getDrawable());
        itemParentViewHolder.itemView.setOnClickListener(new OnParentItemClickListener(presenter, parent, itemParentViewHolder));
    }

    @Override
    public void onBindChildViewHolder(ItemChildViewHolder itemChildViewHolder, int i, Object childObject) {
        final SettingChild child = (SettingChild) childObject;
        itemChildViewHolder.title.setText(child.getTitle());
        itemChildViewHolder.checkBox.setVisibility(
                child.getTitle().equals("Ask for password") ||
                child.getTitle().equals("Show notifications") ? View.VISIBLE : View.GONE
        );
        itemChildViewHolder.checkBox.setChecked(child.isChecked());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) itemChildViewHolder.checkBox.setButtonTintList(
                ColorStateList.valueOf(new ThemePreference(mContext).getPrimaryColor())
        );
        else CompoundButtonCompat.setButtonTintList(itemChildViewHolder.checkBox,
                ColorStateList.valueOf(new ThemePreference(mContext).getPrimaryColor())
        );
        setBigSeparateLine(itemChildViewHolder, child.isLast());
        final Drawable icon = child.getIconDrawable();
        if (icon != null)
            itemChildViewHolder.imageView.setImageDrawable(icon);
        itemChildViewHolder.itemView.setOnClickListener(new OnChildItemClickListener(presenter, child));
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    private void setBigSeparateLine(ItemChildViewHolder itemChildViewHolder, boolean isLastChild) {
        if (isLastChild) {
            itemChildViewHolder.smallSeparateLine.setVisibility(View.GONE);
            itemChildViewHolder.bigSeparateLine.setVisibility(View.VISIBLE);
        }
        else {
            itemChildViewHolder.smallSeparateLine.setVisibility(View.VISIBLE);
            itemChildViewHolder.bigSeparateLine.setVisibility(View.GONE);
        }
    }
}
