package com.pesto.taskhome.presentation


// Created by Nagaraju Deshetty on 07/05/24.
sealed class SearchEvent(){
    data class OnSearchQuery(val query:String): SearchEvent()
    data class OnSearchStart(val query:String): SearchEvent()
    data class TopSearchSelected(val selected:Boolean): SearchEvent()
    data class OnFocusChange(val focus:Boolean): SearchEvent()
    data object OnClearPressed : SearchEvent()
}
