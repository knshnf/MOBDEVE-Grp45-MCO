package com.mobdeve_group45_mco.post

import CustomDate
import com.mobdeve_group45_mco.R

class Post {
    var userHandle: String
        private set
    var displayName: String
        private set
    var caption: String
        private set
    var likes: Int
        private set
    var imageId: Int
        private set
    var createdAt: CustomDate
        private set

    constructor(userHandle: String, displayName: String, caption: String, likes: Int, imageId: Int, createdAt: CustomDate) {
        this.userHandle = userHandle
        this.displayName = displayName
        this.caption = caption
        this.likes = likes
        this.imageId = imageId
        this.createdAt = createdAt
    }

    constructor(userHandle: String, displayName: String, caption: String) {
        this.userHandle = userHandle
        this.displayName = displayName
        this.caption = caption
        likes = 0

        imageId = R.drawable.myimage

        createdAt = CustomDate()
    }
}