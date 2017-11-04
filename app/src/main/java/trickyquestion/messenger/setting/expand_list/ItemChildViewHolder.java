package trickyquestion.messenger.setting.expand_list;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

import trickyquestion.messenger.R;
import trickyquestion.messenger.setting.presenter.SettingPresenter;
import trickyquestion.messenger.setting.view.ISettingView;

public class ItemChildViewHolder extends ChildViewHolder {

    public TextView title;
    public CheckBox checkBox;
    public ImageView imageView;
    public View bigSeparateLine;
    public View smallSeparateLine;

    public ItemChildViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.child_list_item_title);
        checkBox = (CheckBox) itemView.findViewById(R.id.child_list_item_check_box);
        imageView = (ImageView) itemView.findViewById(R.id.child_setting_item_icon);
        bigSeparateLine = itemView.findViewById(R.id.child_separate_big_line);
        smallSeparateLine = itemView.findViewById(R.id.child_separate_small_line);
    }

}
