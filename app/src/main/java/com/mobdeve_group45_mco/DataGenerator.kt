package com.mobdeve_group45_mco

import CustomDate
import com.mobdeve_group45_mco.dailyWeather.DailyWeather
import com.mobdeve_group45_mco.hourlyWeather.HourlyWeather
import com.mobdeve_group45_mco.searchResults.Search
import com.mobdeve_group45_mco.post.Post

class DataGenerator {
    companion object {
        private val hour1 : HourlyWeather = HourlyWeather("NOW", R.drawable.sun, "27°")
        private val hour2 : HourlyWeather = HourlyWeather("10AM", R.drawable.sun, "27°")
        private val hour3 : HourlyWeather = HourlyWeather("11AM", R.drawable.sun, "27°")
        private val hour4 : HourlyWeather = HourlyWeather("12PM", R.drawable.sun, "28°")
        private val hour5 : HourlyWeather = HourlyWeather("1PM", R.drawable.sun, "29°")
        private val hour6 : HourlyWeather = HourlyWeather("2PM", R.drawable.sun, "30°")
        private val hour7 : HourlyWeather = HourlyWeather("3PM", R.drawable.sun, "30°")
        private val hour8 : HourlyWeather = HourlyWeather("4PM", R.drawable.cloudy, "31°")
        private val hour9 : HourlyWeather = HourlyWeather("5PM", R.drawable.cloudy, "31°")
        private val hour10 : HourlyWeather = HourlyWeather("6PM", R.drawable.cloudy, "30°")
        private val day1 : DailyWeather = DailyWeather("Today", R.drawable.sun, "27° - 31°")
        private val day2 : DailyWeather = DailyWeather("Sun", R.drawable.storm, "27° - 31°")
        private val day3 : DailyWeather = DailyWeather("Mon", R.drawable.cloudy, "27° - 32°")
        private val day4 : DailyWeather = DailyWeather("Tue", R.drawable.raining, "26° - 31°")
        private val day5 : DailyWeather = DailyWeather("Wed", R.drawable.raining, "26° - 29°")
        private val day6 : DailyWeather = DailyWeather("Thu", R.drawable.storm, "26° - 29°")
        private val day7 : DailyWeather = DailyWeather("Fri", R.drawable.raining, "27° - 29°")
        private val day8 : DailyWeather = DailyWeather("Sat", R.drawable.cloudy, "27° - 30°")
        private val search1 : Search = Search("New York", "10:00 AM", "22°", "Sunny", "H: 25° L: 15°")
        private val search2 : Search = Search("Los Angeles", "11:00 AM", "24°", "Cloudy", "H: 27° L: 18°")
        private val search3 : Search = Search("Chicago", "12:00 PM", "20°", "Rain", "H: 23° L: 14°")
        private val search4 : Search = Search("Houston", "01:00 PM", "26°", "Stormy", "H: 29° L: 21°")
        private val search5 : Search = Search("Phoenix", "02:00 PM", "33°", "Sunny", "H: 35° L: 25°")
        fun hourlyWeatherData(): ArrayList<HourlyWeather> {
            return arrayListOf<HourlyWeather>(hour1, hour2, hour3, hour4, hour5, hour6, hour7, hour8, hour9, hour10)
        }

        fun dailyWeatherData(): ArrayList<DailyWeather> {
            return arrayListOf<DailyWeather>(day1, day2, day3, day4, day5, day6, day7, day8)
        }

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
