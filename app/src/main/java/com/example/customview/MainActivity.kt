package com.example.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.graphics.drawable.AnimationDrawable
import android.view.View

import androidx.constraintlayout.widget.ConstraintLayout
import com.example.customview.utils.makeMaterialShapeDrawable


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view= findViewById<View>(R.id.view)

        view.background= makeMaterialShapeDrawable()


//        animatedGradienDrawble()

    }

    private fun animatedGradienDrawble() {
        val constraintLayout = findViewById<ConstraintLayout>(R.id.parentLayout)
        val animationDrawable = constraintLayout.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }


}