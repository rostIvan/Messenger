package trickyquestion.messenger.screen.settings.expand_list.adapter;


import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import trickyquestion.messenger.R;

public class ItemParentViewHolder extends ParentViewHolder {
    @BindView(R.id.parent_list_item_title)
    public TextView settingParentTitle;
    @BindView(R.id.parent_list_item_expand_arrow)
    public ImageButton parentDropDownArrow;
    @BindView(R.id.parent_setting_item_icon)
    public ImageView imageView;

    public ItemParentViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
