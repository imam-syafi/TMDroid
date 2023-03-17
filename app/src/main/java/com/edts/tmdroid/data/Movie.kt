package com.edts.tmdroid.data

import androidx.annotation.DrawableRes
import com.edts.tmdroid.R

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    @DrawableRes val backdrop: Int,
    @DrawableRes val poster: Int,
    val popularity: Double,
    val voteAverage: Double,
    val voteCount: Int,
) {
    companion object {
        val SAMPLES: List<Movie> = listOf(
            Movie(
                id = 299534,
                title = "Avengers: Endgame",
                overview = "After the devastating events of Avengers: Infinity War, the universe is in ruins due to the efforts of the Mad Titan, Thanos. With the help of remaining allies, the Avengers must assemble once more in order to undo Thanos' actions and restore order to the universe once and for all, no matter what consequences may be in store.",
                releaseDate = "2019-04-24",
                backdrop = R.drawable.avengers_bd,
                poster = R.drawable.avengers_poster,
                popularity = 315.836,
                voteAverage = 8.3,
                voteCount = 20247,
            ),
            Movie(
                id = 634649,
                title = "Spider-Man: No Way Home",
                overview = "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
                releaseDate = "2021-12-15",
                backdrop = R.drawable.spiderman_bd,
                poster = R.drawable.spiderman_poster,
                popularity = 17656.865,
                voteAverage = 8.4,
                voteCount = 6786,
            ),
            Movie(
                id = 284053,
                title = "Thor: Ragnarok",
                overview = "Thor is imprisoned on the other side of the universe and finds himself in a race against time to get back to Asgard to stop Ragnarok, the destruction of his home-world and the end of Asgardian civilization, at the hands of a powerful new threat, the ruthless Hela.",
                releaseDate = "2017-10-24",
                backdrop = R.drawable.thor_bd,
                poster = R.drawable.thor_poster,
                popularity = 186.535,
                voteAverage = 7.6,
                voteCount = 17151,
            ),
            Movie(
                id = 384018,
                title = "Fast & Furious Presents: Hobbs & Shaw",
                overview = "Ever since US Diplomatic Security Service Agent Hobbs and lawless outcast Shaw first faced off, they just have traded smack talk and body blows. But when cyber-genetically enhanced anarchist Brixton's ruthless actions threaten the future of humanity, they join forces to defeat him.",
                releaseDate = "2019-08-01",
                backdrop = R.drawable.hobbsshaw_bd,
                poster = R.drawable.hobbsshaw_poster,
                popularity = 179.029,
                voteAverage = 6.9,
                voteCount = 5584,
            ),
            Movie(
                id = 259693,
                title = "The Conjuring 2",
                overview = "Lorraine and Ed Warren travel to north London to help a single mother raising four children alone in a house plagued by malicious spirits.",
                releaseDate = "2016-06-08",
                backdrop = R.drawable.conjuring_bd,
                poster = R.drawable.conjuring_poster,
                popularity = 153.485,
                voteAverage = 7.3,
                voteCount = 6573,
            ),
        )
    }
}
