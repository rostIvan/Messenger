package trickyquestion.messenger.ui.fragment

import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.*
import trickyquestion.messenger.R
import trickyquestion.messenger.util.android.preference.SearchQueryPreference
import trickyquestion.messenger.ui.mvp.fragment.MvpView

abstract class AWithSearchFragment : MvpView() {
    private lateinit var searchView: SearchView
    private lateinit var listener: SearchViewListener

    protected fun addSearchListener(listener: SearchViewListener) {
        this.listener = listener
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        retainInstance = true
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        val searchQueryPreference = SearchQueryPreference(context)
        val searchItem = menu!!.findItem(R.id.action_search)
        val settingMenuItem = menu.findItem(R.id.action_menu)
        val accountMenuItem = menu.findItem(R.id.action_account)
        searchView = searchItem.actionView as SearchView
        setupSearchListeners(searchQueryPreference, settingMenuItem, accountMenuItem)
        restoreSearchQuery(searchItem, searchQueryPreference.lastQuery)
        restoreSearchFocus(searchItem, searchQueryPreference.isSearchFocused)
    }

    private fun setupSearchListeners(searchQueryPreference: SearchQueryPreference, settingMenuItem: MenuItem, accountMenuItem: MenuItem) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean { return false }
            override fun onQueryTextChange(newText: String): Boolean {
                listener.onQueryTextChanged(newText)
                searchQueryPreference.lastQuery = newText ; return false
            }
        })

        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            settingMenuItem.isVisible = !hasFocus
            accountMenuItem.isVisible = !hasFocus
            searchView.isIconified = !hasFocus
            searchQueryPreference.setFocused(hasFocus)
        }
    }

    private fun restoreSearchQuery(menuItem: MenuItem, savedQuery: String?) {
        if (savedQuery != null && !savedQuery.isEmpty()) {
            listener.onQueryTextChanged(savedQuery)
            menuItem.expandActionView()
            searchView.setQuery(savedQuery, false)
            searchView.clearFocus()
        }
    }

    private fun restoreSearchFocus(menuItem: MenuItem, savedFocus: Boolean) {
        searchView.isFocusable = savedFocus
        searchView.isIconified = !savedFocus
        if (savedFocus) searchView.requestFocusFromTouch()
        else searchView.clearFocus()
    }

    interface SearchViewListener {
        fun onQueryTextChanged(newText: CharSequence)
    }
}