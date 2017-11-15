package trickyquestion.messenger.settings_screen.expand_list.listeners;

import android.view.View;

import trickyquestion.messenger.settings_screen.expand_list.holders.ItemParentViewHolder;
import trickyquestion.messenger.settings_screen.expand_list.model.SettingParent;
import trickyquestion.messenger.settings_screen.presenter.ISettingPresenter;

public class OnParentItemClickListener implements View.OnClickListener {

    private final ISettingPresenter presenter;
    private final SettingParent parent;
    private final ItemParentViewHolder itemParentViewHolder;

    public OnParentItemClickListener(final ISettingPresenter presenter, final SettingParent parent, final ItemParentViewHolder itemParentViewHolder) {
        this.presenter = presenter;
        this.parent = parent;
        this.itemParentViewHolder = itemParentViewHolder;
    }

    @Override
    public void onClick(View v) {
        itemParentViewHolder.onClick(v);
    }
}
