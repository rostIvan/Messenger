package trickyquestion.messenger.setting.expand_list;


import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import trickyquestion.messenger.R;

public class ItemParentViewHolder extends ParentViewHolder {
    public TextView settingParentTitle;
    public ImageButton parentDropDownArrow;
    public ImageView imageView;
    public ItemParentViewHolder(View itemView) {
        super(itemView);
        settingParentTitle = (TextView) itemView.findViewById(R.id.parent_list_item_title);
        parentDropDownArrow= (ImageButton) itemView.findViewById(R.id.parent_list_item_expand_arrow);
        imageView = (ImageView) itemView.findViewById(R.id.parent_setting_item_icon);
    }
}
