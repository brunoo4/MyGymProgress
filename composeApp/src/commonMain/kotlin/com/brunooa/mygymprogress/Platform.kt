package com.brunooa.mygymprogress

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform