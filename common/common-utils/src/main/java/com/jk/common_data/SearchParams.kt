package com.jk.common_data

data class SearchParams(
    val q: String,
    val sortBy: String,
    val isAsc: Boolean
) {
    companion object {
        fun getDefault(): SearchParams {
            return SearchParams(q = "", sortBy = "id", isAsc = true)
        }
    }

}
