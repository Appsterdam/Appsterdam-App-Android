package rs.appsterdam.app.utils

import android.app.Activity
import android.util.Log
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import kotlinx.coroutines.launch

// Credit: https://proandroiddev.com/jetpack-compose-bottom-sheet-over-android-view-using-kotlin-extension-7fecfa8fe369

// Extension for Activity
fun Activity.showAsBottomSheet(content: @Composable (() -> Unit) -> Unit) {
    val viewGroup = this.findViewById(android.R.id.content) as ViewGroup
    addContentToView(viewGroup, content)
}

// Extension for Fragment
fun Fragment.showAsBottomSheet(content: @Composable (() -> Unit) -> Unit) {
    val viewGroup = requireActivity().findViewById(android.R.id.content) as ViewGroup
    addContentToView(viewGroup, content)
}

// Helper method
private fun addContentToView(
    viewGroup: ViewGroup,
    content: @Composable (() -> Unit) -> Unit
) {
    viewGroup.addView(
        ComposeView(viewGroup.context).apply {
            setContent {
                BottomSheetWrapper(viewGroup, this, content)
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BottomSheetWrapper(
    parent: ViewGroup,
    composeView: ComposeView,
    content: @Composable (() -> Unit) -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var isSheetOpened by remember { mutableStateOf(false) }

    ModalBottomSheetLayout(
        sheetBackgroundColor = Color.Transparent,
        sheetState = modalBottomSheetState,
        sheetContent = {
            content {
                // Action passed for clicking close button in the content
                coroutineScope.launch {
                    modalBottomSheetState.hide() // will trigger the LaunchedEffect
                }
            }
        }
    ) {}

    BackHandler {
        coroutineScope.launch {
            modalBottomSheetState.hide() // will trigger the LaunchedEffect
        }
    }

    // Take action based on hidden state
    LaunchedEffect(modalBottomSheetState.currentValue) {
        when (modalBottomSheetState.currentValue) {
            ModalBottomSheetValue.Hidden -> {
                when {
                    isSheetOpened -> parent.removeView(composeView)
                    else -> {
                        isSheetOpened = true
                        modalBottomSheetState.show()
                    }
                }
            }
            else -> {
                val tag = parent::class.java.simpleName
                Log.i(tag, "Bottom sheet ${modalBottomSheetState.currentValue} state")
            }
        }
    }
}