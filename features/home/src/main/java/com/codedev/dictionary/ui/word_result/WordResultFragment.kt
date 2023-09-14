package com.codedev.dictionary.ui.word_result

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.Player.STATE_ENDED
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.codedev.base.HTMLParser
import com.codedev.base.launchInLifecycle
import com.codedev.base.toggleVisibility
import com.codedev.data_lib.models.Word
import com.codedev.dictionary.HomeActivity
import com.codedev.dictionary.ui.common.RVMeaningAdapter
import com.codedev.dictionary.ui.word_result.di.WordResultComponent
import com.codedev.home.R
import com.codedev.home.databinding.FragmentWordResultBinding
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import javax.inject.Inject
import com.codedev.base.R as base_R


class WordResultFragment: Fragment(R.layout.fragment_word_result) {

    private var _binding: FragmentWordResultBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var wordResultComponent: WordResultComponent

    @Inject
    lateinit var wordResultViewModel: WordResultViewModel

    private lateinit var meaningAdapter: RVMeaningAdapter

    private lateinit var player: ExoPlayer

    override fun onAttach(context: Context) {
        super.onAttach(context)
        wordResultComponent = (requireActivity() as HomeActivity).homeComponent
            .wordResultComponent()
            .fragment(this)
            .build()

        wordResultComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentWordResultBinding.bind(view)

        initData()
        initViews()
    }

    private fun initViews() {

        binding.wordContent.rvMeaning.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            meaningAdapter = RVMeaningAdapter {

            }
            adapter = meaningAdapter

            val dividerItemDecoration = DividerItemDecoration(
                requireContext(),
                (layoutManager as LinearLayoutManager).orientation
            )
            addItemDecoration(dividerItemDecoration)
        }

    }

    private fun initData() {
        player = ExoPlayer.Builder(requireContext()).build()
        player.playWhenReady = false

        val word = arguments?.getString("word") ?: ""

        wordResultViewModel.execute(WordResultFragmentActions.GetWord(word))

        launchInLifecycle {
            wordResultViewModel.events.collectLatest { event ->
                when (event) {
                    is WordResultFragmentUIEvents.GetWordLoading -> {
                        binding.shimmerHistories.toggleVisibility(true)
                        binding.shimmerHeader.toggleVisibility(true)
                        binding.clHeaderContent.toggleVisibility(false)
                        binding.wordContent.root.toggleVisibility(false)
                    }
                    is WordResultFragmentUIEvents.GetWordResult -> {
                        binding.shimmerHistories.toggleVisibility(false)
                        binding.shimmerHeader.toggleVisibility(false)
                        binding.clHeaderContent.toggleVisibility(true)
                        binding.wordContent.root.toggleVisibility(true)
                        if (event.word != null) {
                            setupWordContent(event.word)
                        } else {
                            disableWordContent(event.exception ?: "")
                        }
                    }

                    is WordResultFragmentUIEvents.UpdateWordError -> {

                    }
                    is WordResultFragmentUIEvents.UpdateWordSuccess -> {
                        if (event.word.is_saved) {
                            binding.ivFavourite.setImageResource(base_R.drawable.ic_favorite)
                            binding.lFavourite.setOnClickListener {
                                wordResultViewModel.execute(WordResultFragmentActions.UpdateWord(event.word.copy(is_saved = false)))
                                binding.ivFavourite.setImageResource(base_R.drawable.ic_favorite_outlined)
                            }
                        } else {
                            binding.ivFavourite.setImageResource(base_R.drawable.ic_favorite_outlined)
                            binding.lFavourite.setOnClickListener {
                                wordResultViewModel.execute(WordResultFragmentActions.UpdateWord(event.word.copy(is_saved = true)))
                                binding.ivFavourite.setImageResource(base_R.drawable.ic_favorite)
                            }
                        }
                    }
                }
            }
        }

    }

    private fun disableWordContent(word: String) {
        binding.wordContent.clContent.toggleVisibility(false)
        binding.wordContent.lSearchResults.toggleVisibility(true)
        binding.tvWord.text = word
        binding.tvAbbr.text = "N/A"
        binding.lSpeak.apply {
            setBackgroundResource(base_R.drawable.background_gray_curved_rectangle_light)
            isEnabled = false
        }
        binding.lFavourite.apply {
            setBackgroundResource(base_R.drawable.background_gray_curved_rectangle_light)
            isEnabled = false
        }
        binding.lShare.apply {
            setBackgroundResource(base_R.drawable.background_gray_curved_rectangle_light)
            isEnabled = false
        }
        val msg = "Search results for $word is not found.\nFor more information, Click: "
        val url = "https://www.thefreedictionary.com/$word"
        binding.wordContent.tvLink.paintFlags = binding.wordContent.tvLink.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        binding.wordContent.tvLink.text = url
        binding.wordContent.tvLabelSearchResults.text = msg
        binding.wordContent.tvLink.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }
    }

    private fun setupWordContent(word: Word) {
        binding.wordContent.clContent.toggleVisibility(true)
        binding.wordContent.lSearchResults.toggleVisibility(false)

        wordResultViewModel.execute(WordResultFragmentActions.InsertHistory(word.name))

        if (word.audio.isNotBlank()) {
            setupAudio(word.audio)
        } else {
            binding.lSpeak.setOnClickListener {
                Toast.makeText(requireContext(), "Audio Unavailable", Toast.LENGTH_SHORT).show()
            }
        }

        binding.apply {
            tvWord.text = word.name
            tvAbbr.text = word.phonetic

            wordContent.tvLicense.text = word.license

            if (word.origin.isNotBlank()) {
                binding.wordContent.tvOrigin.toggleVisibility(true)
                binding.wordContent.tvOriginLabel.toggleVisibility(true)
                binding.wordContent.tvOrigin.text = word.origin
            }

            if (word.phonetics?.isNotEmpty() == true) {
                binding.wordContent.vDivider.toggleVisibility(true)
                binding.wordContent.tvLabelPronunciation.toggleVisibility(true)
                binding.wordContent.tvPronunciation.toggleVisibility(true)
                val builder = StringBuilder()
                word.phonetics?.mapIndexed { i, phonetic ->
                    builder.append("\t ★ ${phonetic.text}")
                    if (i != word.phonetics!!.size - 1) {
                        builder.append("\n")
                    }
                }
                binding.wordContent.tvPronunciation.text = builder.toString()
            }

            meaningAdapter.diffList.submitList(word.meanings)

            binding.lShare.setOnClickListener {

                val content = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(HTMLParser.createWord(word), Html.FROM_HTML_MODE_COMPACT);
                } else
                    Html.fromHtml(HTMLParser.createWord(word))

                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, content.toString().replace("]", "").replace("[", ""))
                    type = "text/html"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }

            if (word.is_saved) {
                binding.ivFavourite.setImageResource(base_R.drawable.ic_favorite)
                binding.lFavourite.setOnClickListener {
                    wordResultViewModel.execute(WordResultFragmentActions.UpdateWord(word.copy(is_saved = false)))
                    binding.ivFavourite.setImageResource(base_R.drawable.ic_favorite_outlined)
                }
            } else {
                binding.ivFavourite.setImageResource(base_R.drawable.ic_favorite_outlined)
                binding.lFavourite.setOnClickListener {
                    wordResultViewModel.execute(WordResultFragmentActions.UpdateWord(word.copy(is_saved = true)))
                    binding.ivFavourite.setImageResource(base_R.drawable.ic_favorite)
                }
            }
        }
    }

    private fun setupAudio(audio: String) {
        val mediaItem = MediaItem.fromUri(audio)
        player.setMediaItem(mediaItem)
        player.prepare()
        binding.lSpeak.setOnClickListener {
            if (!player.isPlaying) {
                binding.ivSpeak.setImageResource(base_R.drawable.ic_sound)
                player.play()
            }
        }
        player.addListener(object: Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                Toast.makeText(requireContext(), "${error.message}", Toast.LENGTH_SHORT).show()
                binding.ivSpeak.setImageResource(base_R.drawable.ic_sound_outlined)
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                Timber.e("Playback state -> $playbackState")
                if (playbackState == STATE_ENDED) {
                    binding.ivSpeak.setImageResource(base_R.drawable.ic_sound_outlined)
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        if (player.isPlaying) {
            player.stop()
        }
    }

}