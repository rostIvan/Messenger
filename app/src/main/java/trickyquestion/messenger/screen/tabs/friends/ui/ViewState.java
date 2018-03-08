package trickyquestion.messenger.screen.tabs.friends.ui;


public class ViewState {
    private String searchQuery;

    public ViewState() {
    }

    public ViewState(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }
}
