package ru.skillbranch.skillarticles.ui.articles

    import android.os.Bundle
    import android.view.Menu
    import android.view.MenuItem
    import androidx.appcompat.widget.SearchView
    import androidx.fragment.app.viewModels
    import androidx.recyclerview.widget.DividerItemDecoration
    import androidx.recyclerview.widget.LinearLayoutManager
    import kotlinx.android.synthetic.main.fragment_articles.*
    import ru.skillbranch.skillarticles.R
    import ru.skillbranch.skillarticles.ui.base.BaseFragment
    import ru.skillbranch.skillarticles.ui.base.Binding
    import ru.skillbranch.skillarticles.ui.base.MenuItemHolder
    import ru.skillbranch.skillarticles.ui.base.ToolbarBuilder
    import ru.skillbranch.skillarticles.ui.delegates.RenderProp
    import ru.skillbranch.skillarticles.viewmodels.articles.ArticlesState
    import ru.skillbranch.skillarticles.viewmodels.articles.ArticlesViewModel
    import ru.skillbranch.skillarticles.viewmodels.base.IViewModelState
    import ru.skillbranch.skillarticles.viewmodels.base.NavigationCommand

    class ArticlesFragment : BaseFragment<ArticlesViewModel>() {

        override val viewModel: ArticlesViewModel by viewModels()
        override val layout: Int = R.layout.fragment_articles
        override val binding: ArticlesBinding by lazy { ArticlesBinding() }

        override val prepareToolbar: (ToolbarBuilder.() -> Unit) = {
            addMenuItem(
                MenuItemHolder(
                    "Search",
                    R.id.action_search,
                    R.drawable.ic_search_black_24dp,
                    R.layout.search_view_layout
                )
            )
        }

        private val articlesAdapter = ArticlesAdapter(
            listener = { item ->
                val direction = ArticlesFragmentDirections.actionNavArticlesToPageArticle(
                    item.id,
                    item.author,
                    item.authorAvatar,
                    item.category,
                    item.categoryIcon,
                    item.poster,
                    item.title,
                    item.date
                )

                viewModel.navigate(NavigationCommand.To(direction.actionId, direction.arguments))
            },
            bookmarkListener = { id, checked ->
                viewModel.handleToggleBookmark(id, checked)
            }
        )

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setHasOptionsMenu(true)
        }

        override fun onPrepareOptionsMenu(menu: Menu) {
            super.onPrepareOptionsMenu(menu)
            val menuItem = menu.findItem(R.id.action_search)
            val searchView = menuItem.actionView as SearchView
            if (binding.isSearch) {
                menuItem.expandActionView()
                searchView.setQuery(binding.searchQuery, false)
            }

            menuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                    viewModel.handleSearchMode(true)
                    return true
                }

                override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                    viewModel.handleSearchMode(false) // or true?
                    return true
                }
            })

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.handleSearch(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.handleSearch(newText)
                    return true
                }
            })

            searchView.setOnCloseListener {
                viewModel.handleSearchMode(false)
                true
            }
        }

        override fun setupViews() {
            with(rv_articles) {
                adapter = articlesAdapter
                addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            }

            viewModel.observeList(viewLifecycleOwner) { data ->
                articlesAdapter.submitList(data)
            }
        }

        inner class ArticlesBinding : Binding() {
            var searchQuery: String? = null
            var isSearch = false

            var isLoading: Boolean by RenderProp(true) {
                // TODO: Show shimmer on rv_list
            }

            override fun bind(data: IViewModelState) {
                data as ArticlesState
                isSearch = data.isSearch
                searchQuery = data.searchQuery
                isLoading = data.isLoading
            }
        }
    }