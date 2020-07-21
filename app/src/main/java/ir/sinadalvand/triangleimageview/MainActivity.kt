package ir.sinadalvand.triangleimageview

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import ir.sinadalvand.libs.triangleimageview.TriangleImageMode
import ir.sinadalvand.libs.triangleimageview.TriangleImageView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener, SeekBar.OnSeekBarChangeListener,
    CompoundButton.OnCheckedChangeListener {

    private val MAX_ELEVATION by lazy { Utils.convertDpToPixel(10f, this) }
    private val MAX_RADIUS by lazy { Utils.convertDpToPixel(20f, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        switch_radius.setOnCheckedChangeListener(this)
        switch_mode.setOnCheckedChangeListener(this)
        seek_shadow.setOnSeekBarChangeListener(this)
        seek_morph.setOnSeekBarChangeListener(this)
        seek_radius.apply {
            isEnabled = false
            setOnSeekBarChangeListener(this@MainActivity)
        }


    }

    override fun onClick(v: View?) {
        image_triangle.Animate()
    }


    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (fromUser)
            when (seekBar) {
                seek_morph -> {
                    image_triangle.morph = (100 - progress) / 100f
                    text_morph.text = ((100 - progress) / 100f).toString()
                }
                seek_shadow -> {
                    ViewCompat.setElevation(image_triangle, progress * (MAX_ELEVATION / 100))
                    text_shadow.text = String.format(
                        "%.1fdp",
                        Utils.convertPixelsToDp(progress * (MAX_ELEVATION / 100f), this)
                    )
                }
                seek_radius -> {
                    image_triangle.radius = MAX_RADIUS * (progress / 100f)
                    text_radius.text = String.format(
                        "%.1fdp",
                        Utils.convertPixelsToDp(MAX_RADIUS * (progress / 100f), this)
                    )
                }
            }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {

        when(buttonView){
            switch_mode->{
                image_triangle.mode = if(image_triangle.mode == TriangleImageMode.COLLAPSED)  TriangleImageMode.EXPANDED else TriangleImageMode.COLLAPSED
            }
            switch_radius->{
                seek_radius.isEnabled = !isChecked
                if (isChecked) {
                    image_triangle.radius = -1f
                    seek_radius.progress = 0
                } else {
                    image_triangle.radius = 0.0f
                    seek_radius.progress = 0
                }
                text_radius.text = "0.0dp"
            }
        }

    }


}