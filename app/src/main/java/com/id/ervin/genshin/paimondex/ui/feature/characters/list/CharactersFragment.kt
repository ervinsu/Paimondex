package com.id.ervin.genshin.paimondex.ui.feature.characters.list

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.id.ervin.genshin.paimondex.R
import com.id.ervin.genshin.paimondex.databinding.FragmentCharactersBinding
import com.id.ervin.genshin.paimondex.ui.MainActivity
import com.id.ervin.genshin.paimondex.util.BaseRvCallback
import com.id.ervin.genshin.paimondex.util.calculateNoOfColumn
import com.id.ervin.genshin.paimondex.util.showContentIfNotLoadingAndNotError
import org.koin.android.scope.ScopeFragment
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CharactersFragment : ScopeFragment(), BaseRvCallback {
    private val charactersFragmentViewModel: CharactersViewModel by viewModel()
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
        initRetryOnError()
        (activity as MainActivity).initDetailCharacterObserver(charactersFragmentViewModel)
        (activity as MainActivity).initFavoriteCharacterObserver(charactersFragmentViewModel)
        charactersFragmentViewModel.getCharacters()
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

    private fun initObserver() {
        charactersFragmentViewModel.charactersState.observe(viewLifecycleOwner) { homeState ->
            showContentIfNotLoadingAndNotError(
                homeState.loadingState,
                binding.rvCharacters,
                binding.pbLoading,
                binding.customViewInternetError
            )
            if (homeState.characters.isEmpty()) return@observe
            adapter.setCharacters(homeState.characters)
        }
    }

    private fun initRetryOnError() {
        binding.customViewInternetError.setRetryAction {
            charactersFragmentViewModel.getCharacters()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemViewClicked(charName: String, view: View, activity: Activity) {
        with(activity as MainActivity) {
            binding.mlMain.transitionToState(R.id.detail)
            upgradeToolbar(charName)
            initRetryOnError(charactersFragmentViewModel, charName)
            charactersFragmentViewModel.fetchCharacterDetail(charName)
            charactersFragmentViewModel.getCharacter(charName)
        }
    }
}
