package com.id.ervin.genshin.paimondex.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform