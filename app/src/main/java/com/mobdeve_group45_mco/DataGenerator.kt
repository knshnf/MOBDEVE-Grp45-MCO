package com.mobdeve_group45_mco

import CustomDate
import com.mobdeve_group45_mco.dailyWeather.DailyWeather
import com.mobdeve_group45_mco.hourlyWeather.HourlyWeather
import com.mobdeve_group45_mco.searchResults.Search
import com.mobdeve_group45_mco.post.Post

class DataGenerator {
    companion object {
        private val search1 : Search = Search("New York", "10:00 AM", "22°", "Sunny", "H: 25° L: 15°")
        private val search2 : Search = Search("Los Angeles", "11:00 AM", "24°", "Cloudy", "H: 27° L: 18°")
        private val search3 : Search = Search("Chicago", "12:00 PM", "20°", "Rain", "H: 23° L: 14°")
        private val search4 : Search = Search("Houston", "01:00 PM", "26°", "Stormy", "H: 29° L: 21°")
        private val search5 : Search = Search("Phoenix", "02:00 PM", "33°", "Sunny", "H: 35° L: 25°")

        fun searchResultsData(): ArrayList<Search> {
            return arrayListOf(search1, search2, search3, search4, search5)
        }

        fun loadPostData(): java.util.ArrayList<Post> {
            val data = java.util.ArrayList<Post>()
            data.add(
                Post(
                    "armin.armode.armedian", "Armin Arlert",
                    "RT: Some people consider me a... #bomb",
                    9999, R.drawable.armin, CustomDate(2023, 0, 10)
                )
            )
            data.add(
                Post(
                    "wonderboy", "Zeke Jaeger",
                    "HUUUUUUH????",
                    1, R.drawable.zeke, CustomDate(2023, 0, 10)
                )
            )
            data.add(
                Post(
                    "eldian.pride", "Falco Grice",
                    "I'm so screwed...",
                    13, R.drawable.falco, CustomDate(2023, 0, 10)
                )
            )
            data.add(
                Post(
                    "rudolph_the_reiner", "Reiner Braun",
                    "@jaegermeister awit",
                    0, R.drawable.reiner, CustomDate(2023, 0, 10)
                )
            )
            data.add(
                Post(
                    "jaegermeister", "Eren Jaeger",
                    "@rudolph_the_reiner you're just like me, bro",
                    454, R.drawable.eren, CustomDate(2023, 0, 10)
                )
            )
            data.add(
                Post(
                    "armin.armode.armedian", "Armin Arlert",
                    "RT: Some people consider me a... #bomb",
                    9999, R.drawable.armin, CustomDate(2023, 0, 10)
                )
            )
            data.add(
                Post(
                    "wonderboy", "Zeke Jaeger",
                    "HUUUUUUH????",
                    1, R.drawable.zeke, CustomDate(2023, 0, 10)
                )
            )
            data.add(
                Post(
                    "eldian.pride", "Falco Grice",
                    "I'm so screwed...",
                    13, R.drawable.falco, CustomDate(2023, 0, 10)
                )
            )
            data.add(
                Post(
                    "rudolph_the_reiner", "Reiner Braun",
                    "@jaegermeister awit",
                    0, R.drawable.reiner, CustomDate(2023, 0, 10)
                )
            )
            data.add(
                Post(
                    "jaegermeister", "Eren Jaeger",
                    "@rudolph_the_reiner you're just like me, bro",
                    454, R.drawable.eren, CustomDate(2023, 0, 10)
                )
            )
            data.add(
                Post(
                    "armin.armode.armedian", "Armin Arlert",
                    "RT: Some people consider me a... #bomb",
                    9999, R.drawable.armin, CustomDate(2023, 0, 10)
                )
            )
            data.add(
                Post(
                    "wonderboy", "Zeke Jaeger",
                    "HUUUUUUH????",
                    1, R.drawable.zeke, CustomDate(2023, 0, 10)
                )
            )
            data.add(
                Post(
                    "eldian.pride", "Falco Grice",
                    "I'm so screwed...",
                    13, R.drawable.falco, CustomDate(2023, 0, 10)
                )
            )
            data.add(
                Post(
                    "rudolph_the_reiner", "Reiner Braun",
                    "@jaegermeister awit",
                    0, R.drawable.reiner, CustomDate(2023, 0, 10)
                )
            )
            data.add(
                Post(
                    "jaegermeister", "Eren Jaeger",
                    "@rudolph_the_reiner you're just like me, bro",
                    454, R.drawable.eren, CustomDate(2023, 0, 10)
                )
            )
            return data
        }
    }
}
