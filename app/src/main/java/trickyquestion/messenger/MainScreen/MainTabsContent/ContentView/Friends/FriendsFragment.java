package trickyquestion.messenger.MainScreen.MainTabsContent.ContentView.Friends;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import trickyquestion.messenger.MainScreen.MainTabsContent.ContentAdapter.RecyclerViewAdapters.RecyclerViewFriendAdapter;
import trickyquestion.messenger.MainScreen.MainTabsContent.ContentPresenter.Friends.FriendPresenter;
import trickyquestion.messenger.MainScreen.MainTabsContent.ContentPresenter.Friends.IFriendPresenter;
import trickyquestion.messenger.R;

public class FriendsFragment extends Fragment implements IFriendsView {

    private IFriendPresenter presenter;

    RecyclerView recyclerView;

    public static FriendsFragment newInstance() {
        final Bundle args = new Bundle();
        final FriendsFragment fragment = new FriendsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_friends, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_friends);
        if (presenter == null) presenter = new FriendPresenter(this);
        presenter.onCreateView();
        return view;
    }

    @Override
    public Context getFragmentContext() {
        return getContext();
    }

    @Override
    public void showFriendsItem() {
        final RecyclerViewFriendAdapter adapter = new RecyclerViewFriendAdapter(presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
