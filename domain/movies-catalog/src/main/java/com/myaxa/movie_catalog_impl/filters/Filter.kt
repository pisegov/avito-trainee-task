package com.myaxa.movie_catalog_impl.filters

sealed interface Filter {
    val title: String
    val isSelected: Boolean

    fun clearedSelectionCopy(): Filter

    data class YearFilter(
        override val title: String = "Год выхода",
        val from: String? = null,
        val to: String? = null,
    ) : Filter {
        override val isSelected: Boolean
            get() = from != null || to != null

        override fun toString(): String {
            return when {
                from != null && to != null && from != to ->
                    "$from-$to"

                (from == to && from != null) || (from != null) ->
                    "$from"

                (to != null) ->
                    "$to"

                else -> {
                    ""
                }
            }
        }

        override fun clearedSelectionCopy() = copy(from = null, to = null)
    }

    data class RatingFilter(
        override val title: String = "Рейтинг на Кинопоиске",
        val from: String? = null,
    ) : Filter {
        override val isSelected: Boolean
            get() = from != null

        override fun toString(): String {
            return from?.let { from.toString() } ?: ""
        }

        override fun clearedSelectionCopy() = copy(from = null)
    }

    data class ListFilter(
        override val title: String,
        val options: Map<String, FilterValue> = emptyMap(),
    ) : Filter {
        override val isSelected: Boolean
            get() = options.entries.find { it.value.isSelected } != null

        override fun clearedSelectionCopy(): ListFilter {
            val newOptions = this.options.map {
                (key, value) -> key to value.copy(isSelected = false)
            }.toMap()
            return copy(options = newOptions)
        }

        fun selectedOptions(): List<String> {
            return options.entries.filter { it.value.isSelected }.map { it.key }
        }
    }
}