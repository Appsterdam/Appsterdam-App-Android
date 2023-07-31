package rs.appsterdam.app.presentation

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(message: String) {
    val snackbar: Snackbar = Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
    snackbar.setAction("OK") {
        snackbar.dismiss()
    }
    snackbar.show()
}
