package com.mobdeve_group45_mco

import CustomDate
import com.mobdeve_group45_mco.post.Post

class DataGenerator {
    companion object {
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
