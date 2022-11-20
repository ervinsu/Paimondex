package com.id.ervin.genshin.paimondex.ui.feature.characters.favorite

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.id.ervin.genshin.paimondex.R
import com.id.ervin.genshin.paimondex.databinding.FragmentCharactersBinding
import com.id.ervin.genshin.paimondex.ui.feature.characters.detail.CharacterDetailActivity
import com.id.ervin.genshin.paimondex.ui.feature.characters.list.CharactersAdapter
import com.id.ervin.genshin.paimondex.util.BaseRvCallback
import com.id.ervin.genshin.paimondex.util.calculateNoOfColumn
import com.id.ervin.genshin.paimondex.util.gone
import com.id.ervin.genshin.paimondex.util.showContentIfNotLoadingAndNotError
import com.id.ervin.genshin.paimondex.util.visible
import org.koin.android.scope.ScopeFragment
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FavoriteFragment : ScopeFragment(), BaseRvCallback {

    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private val adapter: CharactersAdapter by inject {
        parametersOf(this)
    }
    private var _binding: FragmentCharactersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerview()
        initObserver()
    }

    private fun initObserver() {
        favoriteViewModel.favoriteState.observe(viewLifecycleOwner) { favoriteState ->
            showContentIfNotLoadingAndNotError(
                favoriteState.loadingState,
                binding.rvCharacters,
                binding.pbLoading,
                binding.customViewInternetError
            )
            if (favoriteState.characters.isEmpty()) {
                binding.customViewEmptyError.setAsEmptyError()
                binding.customViewEmptyError.visible()
            } else {
                binding.customViewEmptyError.gone()
            }

            adapter.setCharacters(favoriteState.characters)
        }
    }

    private fun initRecyclerview() {
        val recyclerview = binding.rvCharacters
        recyclerview.adapter = adapter
        val context = requireContext().applicationContext
        val rowSpan =
            context.calculateNoOfColumn(resources.getDimensionPixelSize(R.dimen.list_character_width) / resources.displayMetrics.scaledDensity)
        val layoutManager = GridLayoutManager(context, rowSpan)
        recyclerview.layoutManager = layoutManager
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemViewClicked(charName: String, view: View, activity: Activity) {
        val intent = Intent(activity, CharacterDetailActivity::class.java).apply {
            putExtra(CharacterDetailActivity.CHARACTER_EXTRA, charName)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        activity.startActivity(intent)
    }
}
