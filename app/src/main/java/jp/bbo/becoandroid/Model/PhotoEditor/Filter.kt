package jp.bbo.becoandroid.Model.PhotoEditor

class Filter {
    fun filter(): ArrayList<Pair<String,PhotoFilter>> {
        val mPairList = ArrayList<Pair<String,PhotoFilter>>()
            mPairList.add(Pair("filter/filter1.acv",PhotoFilter.ORIGINAL))
            mPairList.add(Pair("filter/filter1.acv",PhotoFilter.CHROME))
            mPairList.add(Pair("filter/filter1.acv",PhotoFilter.FADE))
            mPairList.add(Pair("filter/filter1.acv",PhotoFilter.INSTANT))
            mPairList.add(Pair("filter/filter1.acv",PhotoFilter.MONO))
            mPairList.add(Pair("filter/filter1.acv",PhotoFilter.NOIR))
            mPairList.add(Pair("filter/filter1.acv",PhotoFilter.PROCESS))
            mPairList.add(Pair("filter/filter1.acv",PhotoFilter.TONAL))
            mPairList.add(Pair("filter/filter1.acv",PhotoFilter.TRANSFER))
            mPairList.add(Pair("filter/filter1.acv",PhotoFilter.TONE))
            mPairList.add(Pair("filter/filter1.acv",PhotoFilter.SEPIA))
            mPairList.add(Pair("filter/filter1.acv",PhotoFilter.SOFT))
            mPairList.add(Pair("filter/filter2.acv",PhotoFilter.CLEAR))
            mPairList.add(Pair("filter/filter3.acv",PhotoFilter.SPRING))
            mPairList.add(Pair("filter/filter4.acv",PhotoFilter.SKY))
            mPairList.add(Pair("filter/filter5.acv",PhotoFilter.RETRO))
            mPairList.add(Pair("filter/filter6.acv",PhotoFilter.PURE))
            mPairList.add(Pair("filter/filter7.acv",PhotoFilter.VIVIT))
            mPairList.add(Pair("filter/filter8.acv",PhotoFilter.ANGEL))
            mPairList.add(Pair("filter/filter9.acv",PhotoFilter.ROSE))
            mPairList.add(Pair("filter/filter10.acv",PhotoFilter.TOAST))
        return mPairList
    }
}