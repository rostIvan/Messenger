package trickyquestion.messenger.screen.settings.expand_list.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import trickyquestion.messenger.R;

public class ItemChildViewHolder extends ChildViewHolder {

    @BindView(R.id.child_list_item_title)
    public TextView title;
    @BindView(R.id.child_list_item_check_box)
    public CheckBox checkBox;
    @BindView(R.id.child_setting_item_icon)
    public ImageView imageView;
    @BindView(R.id.child_separate_big_line)
    public View bigSeparateLine;
    @BindView(R.id.child_separate_small_line)
    public View smallSeparateLine;

    public ItemChildViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
