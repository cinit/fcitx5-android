/*
 * SPDX-License-Identifier: LGPL-2.1-or-later
 * SPDX-FileCopyrightText: Copyright 2021-2023 Fcitx5 for Android Contributors
 */
package org.fcitx.fcitx5.android.core

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class InputMethodSubMode(val name: String, val label: String, val icon: String) {
    constructor() : this("", "", "")
}

data class InputMethodEntry(
    val uniqueName: String,
    val name: String,
    val icon: String,
    val nativeName: String,
    val label: String,
    val languageCode: String,
    val addon: String,
    val isConfigurable: Boolean,
    val subMode: InputMethodSubMode
) {
    constructor(
        uniqueName: String,
        name: String,
        icon: String,
        nativeName: String,
        label: String,
        languageCode: String,
        addon: String,
        isConfigurable: Boolean
    ) : this(
        uniqueName,
        name,
        icon,
        nativeName,
        label,
        languageCode,
        addon,
        isConfigurable,
        InputMethodSubMode()
    )

    constructor(
        uniqueName: String,
        name: String,
        icon: String,
        nativeName: String,
        label: String,
        languageCode: String,
        addon: String,
        isConfigurable: Boolean,
        subMode: String,
        subModeLabel: String,
        subModeIcon: String
    ) : this(
        uniqueName,
        name,
        icon,
        nativeName,
        label,
        languageCode,
        addon,
        isConfigurable,
        InputMethodSubMode(subMode, subModeLabel, subModeIcon)
    )

    constructor(name: String) : this("", name, "", "", "×", "", "", false)

    val displayName: String
        get() = name.ifEmpty { uniqueName }
}

@Parcelize
data class RawConfig(
    val name: String,
    val comment: String,
    var value: String,
    var subItems: Array<RawConfig>? = arrayOf()
) : Parcelable {
    constructor(name: String, value: String) : this(name, "", value, null)
    constructor(name: String, v: Boolean) : this(name, "", if (v) "True" else "False", null)
    constructor(subItems: Array<RawConfig>) : this("", "", "", subItems)
    constructor(name: String, subItems: Array<RawConfig>) : this(name, "", "", subItems)
    constructor() : this("", "", "", arrayOf())

    operator fun get(name: String) = findByName(name)!!

    fun findByName(name: String): RawConfig? {
        return subItems?.find { it.name == name }
    }

    fun getOrCreate(name: String): RawConfig {
        val items = subItems
        return if (items == null) {
            RawConfig(name, "", "", null).also {
                subItems = arrayOf(it)
            }
        } else {
            items.find { it.name == name }
                ?: RawConfig(name, "", "", null).also {
                    subItems = items + it
                }
        }
    }

    /**
     * generated by Android Studio
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RawConfig

        if (name != other.name) return false
        if (comment != other.comment) return false
        if (value != other.value) return false
        if (subItems != null) {
            if (other.subItems == null) return false
            if (!subItems.contentEquals(other.subItems)) return false
        } else if (other.subItems != null) return false

        return true
    }

    /**
     * generated by Android Studio
     */
    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + comment.hashCode()
        result = 31 * result + value.hashCode()
        result = 31 * result + (subItems?.contentHashCode() ?: 0)
        return result
    }
}

enum class AddonCategory {
    InputMethod, Frontend, Loader, Module, UI;

    companion object {
        private val Values = values()
        fun fromInt(i: Int) = Values[i]
    }
}

data class AddonInfo(
    val uniqueName: String,
    val name: String,
    val comment: String,
    val category: AddonCategory,
    val isConfigurable: Boolean,
    val enabled: Boolean,
    val defaultEnabled: Boolean,
    val onDemand: Boolean,
    val dependencies: Array<String> = arrayOf(),
    val optionalDependencies: Array<String> = arrayOf(),
) {
    constructor(
        uniqueName: String,
        name: String,
        comment: String,
        category: Int,
        isConfigurable: Boolean,
        enabled: Boolean,
        defaultEnabled: Boolean,
        onDemand: Boolean,
        dependencies: Array<String>,
        optionalDependencies: Array<String>,
    ) : this(
        uniqueName,
        name,
        comment,
        AddonCategory.fromInt(category),
        isConfigurable,
        enabled,
        defaultEnabled,
        onDemand,
        dependencies,
        optionalDependencies
    )

    val displayName: String
        get() = name.ifEmpty { uniqueName }

    /**
     * generated by Android Studio
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AddonInfo

        if (uniqueName != other.uniqueName) return false
        if (name != other.name) return false
        if (comment != other.comment) return false
        if (category != other.category) return false
        if (isConfigurable != other.isConfigurable) return false
        if (enabled != other.enabled) return false
        if (defaultEnabled != other.defaultEnabled) return false
        if (onDemand != other.onDemand) return false
        if (!dependencies.contentEquals(other.dependencies)) return false
        if (!optionalDependencies.contentEquals(other.optionalDependencies)) return false

        return true
    }

    /**
     * generated by Android Studio
     */
    override fun hashCode(): Int {
        var result = uniqueName.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + comment.hashCode()
        result = 31 * result + category.hashCode()
        result = 31 * result + isConfigurable.hashCode()
        result = 31 * result + enabled.hashCode()
        result = 31 * result + defaultEnabled.hashCode()
        result = 31 * result + onDemand.hashCode()
        result = 31 * result + dependencies.contentHashCode()
        result = 31 * result + optionalDependencies.contentHashCode()
        return result
    }
}

data class Action(
    val id: Int,
    val isSeparator: Boolean,
    val isCheckable: Boolean,
    val isChecked: Boolean,
    val name: String,
    val icon: String,
    val shortText: String,
    val longText: String,
    val menu: Array<Action>?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Action

        if (id != other.id) return false
        if (name != other.name) return false
        if (menu != null) {
            if (other.menu == null) return false
            if (!menu.contentEquals(other.menu)) return false
        } else if (other.menu != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + (menu?.contentHashCode() ?: 0)
        return result
    }
}
