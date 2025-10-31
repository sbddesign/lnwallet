package sbddesign.lnwallet.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform