package com.app.egyptlegacy.play

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.app.egyptlegacy.*
import com.app.egyptlegacy.databinding.ActivityGameBinding
import com.wajahatkarim3.easyflipview.EasyFlipView

class PlActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.startGame()

        viewModel.listGameItems.observe(this) {
            it.forEachIndexed { index, cardItem ->
                val viewItem = binding.gridCards[index]

                val easyFlipViews = viewItem.findViewById<EasyFlipView>(R.id.easy_flip)

                easyFlipViews.setOnFlipListener { easyFlipView, newCurrentSide ->
                    if (newCurrentSide == EasyFlipView.FlipState.BACK_SIDE)
                        viewModel.itemFlipped(cardItem)

                }

                easyFlipViews.findViewById<ImageView>(R.id.iv_front_side)
                    .setImageResource(cardItem.resId)

                if (cardItem.flipState == FlipState.INVISIBLE) {
                    easyFlipViews.visibility = View.INVISIBLE
                } else {
                    easyFlipViews.visibility = View.VISIBLE


                    if (easyFlipViews.currentFlipState == EasyFlipView.FlipState.BACK_SIDE && cardItem.flipState == FlipState.FRONT ||
                        easyFlipViews.currentFlipState == EasyFlipView.FlipState.FRONT_SIDE && cardItem.flipState == FlipState.BACK
                    ) {
                        easyFlipViews.flipTheView()

                    }

                }
            }

        }



    }


}