package com.inhyuck.adlist.db.entity

import androidx.room.Entity
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Entity(primaryKeys = ["appId"], tableName = "ads")
@Root(name = "ad", strict = false)
data class Ad (
    @field:Element(name = "appId")
    var appId:String="",

    @field:Element(name = "averageRatingImageURL")
    var averageRatingImageURL:String? = null,

    @field:Element(name = "callToAction")
    var callToAction:String? = null,

    @field:Element(name = "campaignDisplayOrder")
    var campaignDisplayOrder:String? = null,

    @field:Element(name = "categoryName")
    var categoryName:String? = null,

    @field:Element(name = "clickProxyURL")
    var clickProxyURL:String? = null,

    @field:Element(name = "impressionTrackingURL")
    var impressionTrackingURL:String? = null,

    @field:Element(name = "minOSVersion", required = false)
    var minOSVersion:String? = "Any Devices",

    @field:Element(name = "numberOfRatings")
    var numberOfRatings:String? = null,

    @field:Element(name = "productDescription")
    var productDescription:String? = null,

    @field:Element(name = "productId")
    var productId:Int? = null,

    @field:Element(name = "productName")
    var productName:String? = null,

    @field:Element(name = "productThumbnail")
    var productThumbnail:String? = null,

    @field:Element(name = "rating")
    var rating:String? = null,

    @field:Element(name = "numberOfDownloads")
    var numberOfDownloads:String? = null
)