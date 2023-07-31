package rs.appsterdam.app.presentation.events

import rs.appsterdam.app.data.model.Event

open class StickyModel {

    open val header: String? = null
}

class HeaderModel(override var header: String) : StickyModel()

class ContentModel(override val header: String, val event: Event) : StickyModel()
