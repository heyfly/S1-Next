package me.ykrank.s1next.data.app.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.JsonNode
import java.util.*

/**
 * Created by ykrank on 2017/6/8.
 */
class News {
    @JsonProperty("id")
    var id: Int = 0
    @JsonIgnore
    var title: String? = null
    @JsonIgnore
    var img: String? = null
    @JsonProperty("date")
    var date: Date? = null
    @JsonProperty("link")
    var link: String? = null
    @JsonIgnore
    var url: String? = null

    constructor() {}

    @JsonCreator
    constructor(@JsonProperty("title") titleNode: JsonNode?, @JsonProperty("better_featured_image") imgNode: JsonNode?, @JsonProperty("_links") linksNode: JsonNode?) {
        this.title = titleNode?.get("rendered")?.asText()
        this.img = imgNode?.get("media_details")?.get("sizes")?.get("medium")?.get("source_url")?.asText()
        this.url = linksNode?.get("self")?.get("href")?.asText()
    }
}
