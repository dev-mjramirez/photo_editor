package jp.bbo.becoandroid.Model.PhotoEditor

class TabEvent private constructor() {
    interface ShowHideTab {
        fun showHideTab(action: Boolean?)
    }

    private var showHideTab: ShowHideTab? = null
    fun setShowHideTab(showHideTab: ShowHideTab?) {
        this.showHideTab = showHideTab
    }

    fun updateShowHideTab(action: Boolean?) {
        if (showHideTab != null) {
            showHideTab!!.showHideTab(action)
        }
    }

    companion object {
        private var mInstance: TabEvent? = null
        val instance: TabEvent?
            get() {
                if (mInstance == null) {
                    mInstance =
                        TabEvent()
                }
                return mInstance
            }
    }
}