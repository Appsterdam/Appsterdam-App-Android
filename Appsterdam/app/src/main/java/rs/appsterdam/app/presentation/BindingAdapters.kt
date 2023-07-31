package rs.appsterdam.app.presentation

import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.webkit.WebView
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textview.MaterialTextView
import rs.appsterdam.app.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@BindingAdapter("url")
fun ImageView.loadImageInto(url: String?) {
    when {
        TextUtils.isEmpty(url) -> {
            Glide.with(this)
                .load(R.drawable.ic_broken_image)
                .into(this)
        }

        else -> {
            Glide.with(context)
                .load(url)
                .circleCrop()
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                )
                .into(this)
        }
    }
}

@BindingAdapter("formatDate")
fun MaterialTextView.formatDate(date: String) {
    val outFormat = SimpleDateFormat("dd LLL yyyy HH:mm", Locale.getDefault())
    val inFormat = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
    text = outFormat.format(inFormat.parse(date.split(":")[0]) ?: Date())
}

@BindingAdapter("link")
fun MaterialTextView.setMovementMethod(isLink: Boolean) {
    movementMethod = LinkMovementMethod.getInstance()
}

@BindingAdapter("data")
fun WebView.loadDescriptionInto(data: String?) {
    val html = "<html><body>" + data?.replace("\r\n", "<br/>") + "</body></html>"
    loadDataWithBaseURL(
        "",
        html,
        "text/html",
        "utf-8",
        ""
    )
}
